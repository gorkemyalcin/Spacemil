package com.gorkem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gorkem.game.Spacemil;

public class CreditsScreen implements Screen{

	Spacemil spacemil;
	
	Sprite backButtonInactive;
	Sprite backButtonActive;
	 
	BitmapFont font;

	private boolean isHoveredBack = false;
	
	public CreditsScreen(Spacemil spacemil) {
		this.spacemil = spacemil;
		font = Spacemil.font;
		
		backButtonInactive = new Sprite(new Texture("back_button_png_77116.png"));
		backButtonInactive.setPosition(300, 500);
		backButtonInactive.setScale(0.4f);
		backButtonActive = new Sprite(new Texture("back-button.png"));
		backButtonActive.setScale(0.4f);
		backButtonActive.setPosition(300, 500);
	}

	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spacemil.batch.begin();
		drawTexts();
		drawBackButton();
		spacemil.batch.end();
	}
	
	
	private void update() {
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		if(mouseX > 375 && mouseX < 480 && mouseY > 45 && mouseY < 140) {
			isHoveredBack  = true;
			if(Gdx.input.isButtonPressed(Buttons.LEFT)){
				MainGameScreen.isPaused = false;
				spacemil.setScreen(Spacemil.mainMenuScreen);
			}
		}
		else {
			isHoveredBack = false;
		}
	}

	private void drawBackButton() {
		if(isHoveredBack) {
			backButtonActive.draw(spacemil.batch);
		}
		else {
			backButtonInactive.draw(spacemil.batch);
		}
	}

	private void drawTexts() {
		font.setColor(Color.BLACK);
		font.draw(spacemil.batch, "Programming: Gorkem Yalcin", 150, 575);
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
