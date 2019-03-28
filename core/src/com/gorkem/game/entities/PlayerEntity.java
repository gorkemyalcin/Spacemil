package com.gorkem.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.gorkem.game.Spacemil;
import com.gorkem.game.screens.MainGameScreen;

public class PlayerEntity extends Entity{

	private double lastBulletFireTime;
	private int speedX;
	private int speedY;
	
	public PlayerEntity(String filepath) {
		super(new Vector2(20, Spacemil.getHeight()/2), filepath);
		this.speedX = 5;
		this.speedY = 5;
	}

	/* 
	 * Change speed and bullet fire rate with bonuses and such
	 */
	@Override
	public void update() {
		if(Gdx.input.isKeyPressed(Keys.W) &&  super.getLocation().y < (Spacemil.getHeight() - super.getTexture().getHeight() - 10)) {
			super.getLocation().y += speedY;
		}
		if(Gdx.input.isKeyPressed(Keys.A) && super.getLocation().x > 10) {
			super.getLocation().x -= speedX;
		}
		if(Gdx.input.isKeyPressed(Keys.S) && super.getLocation().y > 10) {
			super.getLocation().y -= speedY;
		}
		if(Gdx.input.isKeyPressed(Keys.D) && super.getLocation().x < (Spacemil.getWidth() - super.getTexture().getWidth() - 10)) {
			super.getLocation().x += speedX;
		}
		if(Gdx.input.isButtonPressed(Buttons.LEFT) && (System.currentTimeMillis() - lastBulletFireTime) > Spacemil.bulletHitRate) {
			double directionX = Gdx.input.getX() - super.getLocation().x;
			double directionY = Spacemil.getHeight() - Gdx.input.getY() - super.getLocation().y;
			double directionLength = Math.sqrt(Math.pow(directionX, 2) + Math.pow(directionY, 2));
			
			Vector2 bulletDirection = new Vector2((float)(directionX / directionLength), (float)(directionY / directionLength));
			
			double bulletAngle = Math.atan2(directionX, directionY);
			bulletAngle = (((bulletAngle* 180/Math.PI) + 360)) % 360;
			bulletAngle = 90 - bulletAngle;
			Bullet bullet = new Bullet(super.getLocation(), new Vector2(bulletDirection.x * Spacemil.bulletSpeed, bulletDirection.y * Spacemil.bulletSpeed),
					bulletDirection, bulletAngle);
			MainGameScreen.getBulletList().add(bullet);
			lastBulletFireTime = System.currentTimeMillis();
		}
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

}
