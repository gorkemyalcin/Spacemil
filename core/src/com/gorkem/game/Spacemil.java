package com.gorkem.game;

import com.badlogic.gdx.Game; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gorkem.game.screens.MainGameScreen;
import com.gorkem.game.screens.MainMenuScreen;
import com.gorkem.game.screens.OptionsScreen;


public class Spacemil extends Game {
	public SpriteBatch batch;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static Integer asteroidSpawnRate = 300;
	public static Integer bulletHitRate = 75;
	public static Integer bulletSpeed = 20;
	
	public static Integer bulletSpeedLevel = 1;
	public static Integer bulletHitRateLevel = 1;
	
	public static Screen mainMenuScreen;
	public static Screen mainGameScreen;
	public static Screen optionsScreen;
	public static Screen shopScreen;
	
	public static BitmapFont font;
	
	public static Integer score;
	
	public static long lastPauseTime;

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
	
	@Override
	public void create () {
		score = 0;
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("fontfont.fnt"));
		optionsScreen = new OptionsScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		mainGameScreen = new MainGameScreen(this);
		this.setScreen(mainMenuScreen);
	}
	
	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
