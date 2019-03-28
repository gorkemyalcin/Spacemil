package com.gorkem.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	private Vector2 location;
	private Texture texture;
	
	public Entity(Vector2 location, String texturePath) {
		this.location = location;
		this.texture = new Texture(texturePath);
	}
	
	public abstract void update();
 
	public Vector2 getLocation() {
		return location;
	}

	public Texture getTexture() {
		return texture;
	}


	

}
