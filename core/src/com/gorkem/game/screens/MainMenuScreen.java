package com.gorkem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gorkem.game.Spacemil;

public class MainMenuScreen implements Screen{

	private Spacemil spacemil;
	
	private Sprite exitButtonActive;
	private Sprite exitButtonInactive;
	private Sprite playButtonActive;
	private Sprite playButtonInactive;

	private boolean isPlayHovered = false;
	private boolean isExitHovered = false;
	private boolean isCreditsHovered = false;

	private BitmapFont font;


	public MainMenuScreen(Spacemil spacemil) {
		this.spacemil = spacemil;
		font = Spacemil.font;
		
		exitButtonActive = new Sprite(new Texture("exit_button_active.png"));
		exitButtonInactive = new Sprite(new Texture("exit_button_inactive.png"));
		playButtonActive = new Sprite(new Texture("play_button_active.png"));
		playButtonInactive = new Sprite(new Texture("play_button_inactive.png"));
		
		playButtonInactive.setScale(0.4f);
		playButtonInactive.setPosition(Gdx.graphics.getWidth()/2 - playButtonInactive.getWidth()/2 + 15, 500);;
		playButtonActive.setScale(0.4f);
		playButtonActive.setPosition(Gdx.graphics.getWidth()/2 - playButtonInactive.getWidth()/2 + 15, 500);
		exitButtonInactive.setScale(0.5f);
		exitButtonInactive.setPosition(Gdx.graphics.getWidth()/2 - exitButtonInactive.getWidth()/2,50);
		exitButtonActive.setScale(0.5f);
		exitButtonActive.setPosition(Gdx.graphics.getWidth()/2 - exitButtonActive.getWidth()/2,50);
	}
	
	public void update() {
		checkForExit();
		checkForPlay();
		checkForCredits();
	}

	@Override
	public void render(float delta) {
		update();
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
		spacemil.batch.begin();
		drawExit();
		drawCredits();
		drawPlay();
		spacemil.batch.end();
	}
	
	private void drawCredits() {
		if(isCreditsHovered) {
			font.setColor(Color.YELLOW);
			font.draw(spacemil.batch, "Credits", 525, 400);
		}
		else {
			font.setColor(Color.WHITE);
			font.draw(spacemil.batch, "Credits", 525, 400);
		}	
	}
	
	private void checkForCredits() {
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		if(mouseX > 525 && mouseX < 750 && mouseY > 320 && mouseY < 345) {
			isCreditsHovered = true;
			if(Gdx.input.isButtonPressed(Buttons.LEFT)){
				spacemil.setScreen(new CreditsScreen(spacemil));
			}
		}
		else {
			isCreditsHovered = false;
		}
	}

	private void checkForExit() {
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		if(mouseX > Gdx.graphics.getWidth()/2 - 125 && mouseX < (Gdx.graphics.getWidth()/2 + 125) && mouseY > 525 && mouseY < 600) {
			isExitHovered = true;
			if(Gdx.input.isButtonPressed(Buttons.LEFT)){
				Gdx.app.exit();
			}
		}
		else {
			isExitHovered = false;
		}
	}
	
	private void checkForPlay() {
		int mouseX = Gdx.input.getX();
		int mouseY = Gdx.input.getY();
		if(mouseX > Gdx.graphics.getWidth()/2 - 125 && mouseX < (Gdx.graphics.getWidth()/2 + 125) && mouseY > 50 && mouseY < 125) {
			isPlayHovered = true;
			if(Gdx.input.isButtonPressed(Buttons.LEFT)){
				spacemil.setScreen(Spacemil.mainGameScreen);
			}
		}
		else {
			isPlayHovered = false;
		}
	}

	private void drawPlay() {
		if(isPlayHovered) {
			playButtonActive.draw(spacemil.batch);
		}
		else {
			playButtonInactive.draw(spacemil.batch);
		}
	}

	private void drawExit() {
		if(isExitHovered) {
			exitButtonActive.draw(spacemil.batch);
		}
		else {
			exitButtonInactive.draw(spacemil.batch);
		}
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}
	
	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}
	
	@Override
	public void show() {}
	
}
