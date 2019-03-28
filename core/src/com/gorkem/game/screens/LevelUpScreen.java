package com.gorkem.game.screens;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.gorkem.game.Spacemil;

public class LevelUpScreen implements Screen{

	private BitmapFont font;
	private Spacemil spacemil;
	private String levelText;
	private double firstTime;

	public LevelUpScreen(Spacemil spacemil, Integer level) {
		this.spacemil = spacemil;
		font = Spacemil.font;
		level++;
		this.levelText = "Level " + level.toString() + "!";
		this.firstTime = System.currentTimeMillis();
	}
	
	private void drawTexts() {
		font.draw(spacemil.batch, levelText, Spacemil.getWidth()/2 - 100, Spacemil.getHeight() - 100);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.15f, 0.15f, 0.3f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spacemil.batch.begin();
		if(System.currentTimeMillis() - firstTime < 5) {//TODO 2000
			drawTexts();
		}
		else {
			spacemil.setScreen(new ShopScreen(spacemil));
		}
		spacemil.batch.end();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
