package com.gorkem.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.gorkem.game.Spacemil;

public class Asteroid extends Entity {

	private double lastMovement;
	private int yMovement = 5;
	private int xMovement = 3;
	
	public Asteroid(Vector2 location, String texturePath) {
		super(location, texturePath);
	} 

	@Override
	public void update() {
		super.getLocation().x -= getXMovement();
		double chance = Math.random();
		if(System.currentTimeMillis() - lastMovement > 100) {
			if(chance < 0.5 && super.getLocation().y < Spacemil.getHeight() - getYMovement() - super.getTexture().getHeight()) {
				super.getLocation().y += getYMovement();
			}
			else if(super.getLocation().y > super.getTexture().getHeight() + getYMovement()) {
				super.getLocation().y -= getYMovement();
			}
			lastMovement = System.currentTimeMillis();
		}
	}

	public int getYMovement() {
		return yMovement;
	}

	public void setYMovement(int yMovement) {
		this.yMovement = yMovement;
	}

	public int getXMovement() {
		return xMovement;
	}

	public void setXMovement(int xMovement) {
		this.xMovement = xMovement;
	}

}
