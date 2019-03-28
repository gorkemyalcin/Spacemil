package com.gorkem.game.screens;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gorkem.game.Spacemil;

public class ShopScreen implements Screen{

	private Spacemil spacemil;
	private BitmapFont font;
	
	private Sprite startButtonActive;
	private Sprite startButtonInactive;
	
	private Sprite bulletSpeedPlusButtonActive;
	private Sprite bulletSpeedPlusButtonInactive; 
	private Sprite bulletHitRatePlusButtonActive;
	private Sprite bulletHitRatePlusButtonInactive;
	
	private boolean isBulletSpeedPlusHovered = false;
	private boolean isBulletHitRatePlusHovered = false;
	private boolean isStartButtonHovered = false;
	
	private Integer bulletHitRatePrice = (int) Math.round(Math.pow(2, Spacemil.bulletHitRateLevel)*1000);;
	private Integer bulletSpeedPrice = (int) Math.round(Math.pow(2, Spacemil.bulletSpeedLevel)*1000);;
	
	
	public ShopScreen(Spacemil spacemil) {
		this.spacemil = spacemil;
		font = Spacemil.font;
		
		startButtonActive = new Sprite(new Texture("play.png"));
		startButtonActive.setScale(0.2f);
		startButtonInactive= new Sprite(new Texture("playin.png"));
		startButtonInactive.setScale(0.2f);
		
		bulletSpeedPlusButtonActive = new Sprite(new Texture("plusin.png"));
		bulletSpeedPlusButtonActive.setScale(0.2f);
		bulletSpeedPlusButtonInactive = new Sprite(new Texture("plusac.png"));
		bulletSpeedPlusButtonInactive.setScale(0.2f);
		
		bulletHitRatePlusButtonActive = new Sprite(new Texture("plusin.png"));
		bulletHitRatePlusButtonActive.setScale(0.2f);
		bulletHitRatePlusButtonInactive = new Sprite(new Texture("plusac.png"));
		bulletHitRatePlusButtonInactive.setScale(0.2f);
		
	}

	private void drawTexts() {
		spacemil.batch.begin();
		if(isBulletSpeedPlusHovered) {
			bulletSpeedPlusButtonActive.setPosition(600, 450);
			bulletSpeedPlusButtonActive.draw(spacemil.batch);
		}
		else {	
			bulletSpeedPlusButtonInactive.setPosition(600, 450);
			bulletSpeedPlusButtonInactive.draw(spacemil.batch);
		}
		font.draw(spacemil.batch, "Bullet Speed: " +  Spacemil.bulletSpeedLevel.toString(), 150, 575);
		font.draw(spacemil.batch, "Price: " +  bulletSpeedPrice.toString(), 150, 525);
		if(isStartButtonHovered) {
			startButtonActive.setPosition(900, 300);
			startButtonActive.draw(spacemil.batch);
		}
		else {
			startButtonInactive.setPosition(900, 300);
			startButtonInactive.draw(spacemil.batch);
		}
		font.draw(spacemil.batch, "Start!", 900, 500);
		
		if(isBulletHitRatePlusHovered) {
			bulletHitRatePlusButtonActive.setPosition(600, 125);
			bulletHitRatePlusButtonActive.draw(spacemil.batch);
		}
		else {	
			bulletHitRatePlusButtonInactive.setPosition(600, 125);
			bulletHitRatePlusButtonInactive.draw(spacemil.batch);
		}
		font.draw(spacemil.batch, "Bullet Hit Rate: " +  Spacemil.bulletHitRateLevel.toString(), 50, 250);
		font.draw(spacemil.batch, "Price: " +  bulletHitRatePrice.toString(), 150, 200);
		spacemil.batch.end();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update();
		drawTexts();
	}
	
	
	private void update() {
		Integer mouseX = Gdx.input.getX();
		Integer mouseY = Gdx.input.getY();
		System.out.println(mouseX + "," + mouseY);
		if(mouseX < 1035 && mouseX > 990 && mouseY < 330 && mouseY > 285) {
			isStartButtonHovered = true;
			if(Gdx.input.isButtonPressed(Buttons.LEFT)) {
				spacemil.setScreen(Spacemil.mainGameScreen);
			}
		}
		else {
			isStartButtonHovered = false;
		}
		if(mouseX < 735 && mouseX > 690 && mouseY < 180 && mouseY > 135) {
			isBulletSpeedPlusHovered = true;
			if(Gdx.input.justTouched() && Spacemil.score >= bulletSpeedPrice) {
				Spacemil.bulletSpeedLevel++;
				bulletSpeedPrice = (int) Math.round(Math.pow(2, Spacemil.bulletSpeedLevel)*1000);
				Spacemil.bulletSpeed++;
				Spacemil.score -= bulletSpeedPrice;
			}
		}
		else {
			isBulletSpeedPlusHovered = false;
		}
		if(mouseX < 735 && mouseX > 690 && mouseY < 505 && mouseY > 460) {
			isBulletHitRatePlusHovered = true;
			if(Gdx.input.justTouched() && Spacemil.score >= bulletHitRatePrice) {
				Spacemil.bulletHitRateLevel++;
				bulletHitRatePrice = (int) Math.round(Math.pow(2, Spacemil.bulletHitRateLevel)*1000);
				Spacemil.bulletHitRate -= 2;
				Spacemil.score -= bulletHitRatePrice;
			}
		}
		else {
			isBulletHitRatePlusHovered = false;
		}
	}

	@Override
	public void show() {
		
	}

	

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
	
	}

	@Override
	public void resume() {
	
	}

	@Override
	public void hide() {
	
	}

	@Override
	public void dispose() {
		
	}

}
