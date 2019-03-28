package com.gorkem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gorkem.game.Spacemil;

public class OptionsScreen implements Screen{

	private Spacemil spacemil;
	
	private Sprite backButtonInactive;
	private Sprite backButtonActive;
	
	private BitmapFont font;
	
	private boolean isHoveredBack = false;
	
	public OptionsScreen(Spacemil spacemil) {
		this.spacemil = spacemil;
		font = Spacemil.font;
		
		backButtonInactive = new Sprite(new Texture("back_button_png_77116.png"));
		backButtonInactive.setPosition(300, 500);
		backButtonInactive.setScale(0.4f);
		backButtonActive = new Sprite(new Texture("back-button.png"));
		backButtonActive.setScale(0.4f);
		backButtonActive.setPosition(300, 500);
	}
	
	private void checkIfUnpaused() {
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		if(mouseX > 375 && mouseX < 480 && mouseY > 45 && mouseY < 140) {
			isHoveredBack = true;
			if(Gdx.input.isButtonPressed(Buttons.LEFT)){
				MainGameScreen.isPaused = false;
				spacemil.setScreen(Spacemil.mainGameScreen);
			}
		}
		else {
			isHoveredBack = false;
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE) && System.currentTimeMillis() - Spacemil.lastPauseTime > 50) {
			MainGameScreen.isPaused = false;
			Spacemil.lastPauseTime = System.currentTimeMillis();
			spacemil.setScreen(Spacemil.mainGameScreen);
		}
	}
	
	public void update() {
		checkIfUnpaused();
	}
	
	private void drawBackButton() {
		if(isHoveredBack) {
			backButtonActive.draw(spacemil.batch);
		}
		else {
			backButtonInactive.draw(spacemil.batch);
		}
	}


	@Override
	public void render(float delta) {
		update();
		
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		spacemil.batch.begin();
		drawBackButton();
		drawText();
		spacemil.batch.end();
	}

	

	private void drawText() {
		font.draw(spacemil.batch, "Paused!" , 550, 575);
	}

	@Override
	public void show() {}
	
	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
