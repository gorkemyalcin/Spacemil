package com.gorkem.game.screens;

import java.util.ArrayList; 
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.gorkem.game.Spacemil;
import com.gorkem.game.entities.Asteroid;
import com.gorkem.game.entities.Bullet;
import com.gorkem.game.entities.PlayerEntity;

public class MainGameScreen implements Screen{

	Spacemil spacemil;
	
	Sprite bulletSprite;
	Sprite spaceBG;
	Sprite astronaut;

	Texture bulletTexture;
	
	Pixmap cursor;
	
	BitmapFont font;
	
	PlayerEntity ship;
	
	private Integer level;
	
	Vector2 shipLocation;
	Vector2 astronautLocation;
	
	public static boolean isPaused;
	
	private static List<Bullet> bulletList = new ArrayList<Bullet>();
	private List<Asteroid> enemyList = new ArrayList<Asteroid>();
	private List<Asteroid> hitEnemies;
	
	private Integer fpsCounter;
	
	private Sprite gun;
	private Sprite enemySprite;
	
	private double lastTimeEnemyCreated;
	
	double lastAstronautMovementTime;

	private GlyphLayout scoreText;
	private Integer scoreRequired;
	
	Sound asteroidExplosionSound;

	

	public MainGameScreen(Spacemil spacemil) {
		this.spacemil = spacemil;
		bulletSprite = new Sprite(new Texture("bullet.png"));
		enemySprite = new Sprite(new Texture("meteor2.png"));
		spaceBG = new Sprite(new Texture("space.jpg"));
		astronaut = new Sprite(new Texture("astronaut.jpg"));
		gun = new Sprite(new Texture("gun.png"));
		level = 1;
		cursor = new Pixmap(Gdx.files.internal("crosshair.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(cursor, cursor.getHeight()/2, cursor.getWidth()/2));
		
		font = Spacemil.font;
		
		Random rand = new Random();
		astronautLocation = new Vector2(rand.nextInt() % Spacemil.WIDTH, rand.nextInt() % Spacemil.HEIGHT);
		
		ship = new PlayerEntity("ship.png");
		scoreRequired =  (int) Math.round(2000 * Math.pow(level, 2));
		scoreText = new GlyphLayout(font, "" + Spacemil.score + " / " + scoreRequired);
		asteroidExplosionSound = Gdx.audio.newSound(Gdx.files.internal("shaplock.wav"));
	}

	@Override
	public void show() {}
	
	public void render(float delta) {
		
		if(!isPaused) {
			update();
			Gdx.gl.glClearColor(1, 0, 0, 1); 
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			spacemil.batch.begin();
			drawSpace();	
			drawShip();
			drawBullets();
			drawEnemies();
			drawAstronaut();
			drawTexts();
			spacemil.batch.end();
		}
	}
	
	public static List<Bullet> getBulletList() {
		return bulletList;
	}
	
	private void checkIfPaused() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE) && System.currentTimeMillis() - Spacemil.lastPauseTime > 50) {
			if(isPaused == false) {
				isPaused = true;
				Spacemil.lastPauseTime = System.currentTimeMillis();
				spacemil.setScreen(Spacemil.optionsScreen);
			}
		}
	}
	
	public void update() {
		fpsCounter = Gdx.graphics.getFramesPerSecond();
		checkIfPaused();
		ship.update();
		moveAstronaut();
		generateEnemy();
		checkCollisions();
		removeHitEnemies();
		optimizeEntities();
		updateScoreText();
		checkIfScoreEnoughForLevelUp();
	}
	
	private void checkIfScoreEnoughForLevelUp() {
		if(Spacemil.score >= scoreRequired- 1900){//TODO remove 1900
			bulletList.clear();
			enemyList.clear();
			Spacemil.asteroidSpawnRate = Spacemil.asteroidSpawnRate/2;
			spacemil.setScreen(new LevelUpScreen(spacemil, level++));
			scoreRequired =  (int) Math.round(2000 * Math.pow(level, 2));
		}
	}

	private void updateScoreText() {
		scoreText.setText(font, "" + Spacemil.score + " / " + scoreRequired);
	}
	
	private void checkCollisions() {
		hitEnemies = new ArrayList<Asteroid>();
		for(Bullet bullet:bulletList) {
			for(Asteroid enemy:enemyList) {
				if(enemy.getLocation().x + (enemy.getTexture().getWidth()) > bullet.getLocation().x && 
				enemy.getLocation().y + (enemy.getTexture().getHeight()) > bullet.getLocation().y &&
				enemy.getLocation().x < bullet.getLocation().x + bullet.getTexture().getWidth() &&
				enemy.getLocation().y < bullet.getLocation().y + bullet.getTexture().getHeight()) {
					hitEnemies.add(enemy);
					Spacemil.score += 100;
				}
			}
		}
	}
	
	private void removeHitEnemies() {
		Iterator<Asteroid> enemyIterator = hitEnemies.iterator();
		while(enemyIterator.hasNext()) {
			Asteroid removedEnemy = enemyIterator.next();
			enemyList.remove(removedEnemy);
			asteroidExplosionSound.play();
			enemyIterator.remove();	
		}
	}

	private void generateEnemy() {
		if (System.currentTimeMillis() - lastTimeEnemyCreated > Spacemil.asteroidSpawnRate) {
			Asteroid enemy = new Asteroid(new Vector2(Spacemil.WIDTH + 5, ThreadLocalRandom.current().nextInt(0, Spacemil.HEIGHT)), "meteor2.png");
			enemyList.add(enemy);
			lastTimeEnemyCreated = System.currentTimeMillis();
		}
	}

	/**
	 * Purposeless atm
	 */
	private void moveAstronaut() {
		if((System.currentTimeMillis() - lastAstronautMovementTime) > 150) {
			double rand = Math.random();
			if(rand < 0.25 && astronautLocation.y < (Spacemil.HEIGHT - astronaut.getHeight() - 10)) {
				astronautLocation.y += ThreadLocalRandom.current().nextInt(0, 20 + 1);
			}
			else if(rand < 0.50 && astronautLocation.x > 10) {
				astronautLocation.x -= ThreadLocalRandom.current().nextInt(0, 20 + 1);
			}
			else if (rand < 0.75 && astronautLocation.y > 10){			
				astronautLocation.y -= ThreadLocalRandom.current().nextInt(0, 20 + 1);
			}
			else if(astronautLocation.x < (Spacemil.WIDTH - astronaut.getWidth() - 10)) {
				astronautLocation.x += ThreadLocalRandom.current().nextInt(0, 20 + 1);
			}
			lastAstronautMovementTime = System.currentTimeMillis();
		}
	}
	
	/**
	 * Remove bullets that can't be seen
	 */
	private void optimizeBullets() {
		Iterator<Bullet> bulleterator = bulletList.iterator();
		while(bulleterator.hasNext()) {
			Bullet bullet = bulleterator.next();
			if(bullet.getLocation().x > Spacemil.WIDTH || bullet.getLocation().y > Spacemil.HEIGHT || bullet.getLocation().y < 0 || bullet.getLocation().x < 0) {
				bulleterator.remove();
			}
		}
	}
	
	/**
	 * Remove enemies that can't be seen
	 */
	private void optimizeEnemies() { 
		Iterator<Asteroid> enemyIterator = enemyList.iterator();
		while(enemyIterator.hasNext()) {
			Asteroid enemy = enemyIterator.next();
			if(enemy.getLocation().x > Spacemil.WIDTH + 500 || 
				enemy.getLocation().x < enemy.getTexture().getWidth() ||
				enemy.getLocation().y > Spacemil.HEIGHT + enemy.getTexture().getHeight() ||
				enemy.getLocation().y < 0) {
				enemyIterator.remove();
				Spacemil.score-=100;
			}
		}
	}
	
	private void optimizeEntities() {
		optimizeBullets();
		optimizeEnemies();
	}

	private void drawTexts() {
		font.draw(spacemil.batch, scoreText, Spacemil.WIDTH/2, Spacemil.HEIGHT - 50);
		font.draw(spacemil.batch, fpsCounter.toString(), Spacemil.WIDTH - 70, Spacemil.HEIGHT - 20);
		font.setColor(Color.CHARTREUSE);
		font.draw(spacemil.batch, "Level " + level.toString() + "!", 50, Spacemil.HEIGHT - 50);
		font.setColor(Color.WHITE);
	}

	private void drawEnemies() {
		for(Asteroid enemy:enemyList) {
			enemy.update();			
			enemySprite.setPosition(enemy.getLocation().x, enemy.getLocation().y);
			enemySprite.draw(spacemil.batch);
		}
	}

	private void drawShip() {
		spacemil.batch.draw(ship.getTexture(), ship.getLocation().x, ship.getLocation().y);
	}

	private void drawSpace() {
		spaceBG.draw(spacemil.batch);
	}

	private void drawAstronaut() {
		astronaut.setPosition(astronautLocation.x, astronautLocation.y);
		astronaut.draw(spacemil.batch);
	}
	
	/**
	 * TODO work on gun rotation
	 */
	private void drawBullets() {
		if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
			gun.setScale((float) 0.75);
			gun.setPosition(ship.getLocation().x, ship.getLocation().y - 10 );
			double gunAngle = Math.atan2(Gdx.input.getX() - ship.getLocation().x, Gdx.input.getY() - ship.getLocation().y);
			gunAngle = (((gunAngle* 180/Math.PI) + 360)) % 360;
			gunAngle = 270 + gunAngle;
			gun.rotate(Math.round(gunAngle));
			gun.draw(spacemil.batch);
			gun.rotate(Math.round(360 - gunAngle));
		}
		for(Bullet bullet:bulletList) {
			bullet.update();			
			bulletSprite.setPosition(bullet.getLocation().x + 30,  bullet.getLocation().y - 5);
			bulletSprite.rotate(Math.round(bullet.getBulletAngle()));
			bulletSprite.draw(spacemil.batch);
			bulletSprite.rotate(360 - Math.round(bullet.getBulletAngle()));
		}
	}

	@Override
	public void dispose() {
		cursor.dispose();
		spacemil.batch.dispose();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

}
