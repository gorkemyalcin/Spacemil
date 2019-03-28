package com.gorkem.game.entities;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity{

	private Vector2 bulletVelocity;
	private Vector2 bulletDirection;
	private Double bulletAngle;

	
	public Bullet(Vector2 sentLocation, Vector2 sentVelocity, Vector2 bulletDirection, Double bulletAngle) {
		super(new Vector2(sentLocation.x, sentLocation.y), "bullet.png");

		bulletVelocity = new Vector2(sentVelocity.x, sentVelocity.y);
		this.bulletDirection = bulletDirection;
		this.bulletAngle = bulletAngle;
	}


	public Vector2 getBulletDirection() {
		return bulletDirection;
	}

	public void update() {
		super.getLocation().x += bulletVelocity.x;
		super.getLocation().y += bulletVelocity.y;
	}

	public Double getBulletAngle() {
		return bulletAngle;
	}




}
