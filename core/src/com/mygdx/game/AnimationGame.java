package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	private TextureAtlas textureAtlas;
	private Sprite sprite;
	private Sprite backgroundSprite;
	private Texture backgroundTexture;
	private TextureRegion textureRegion;
	int curentFrame = 1;
	int MAX_FRAMES = 10;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("spritesheets/mySpritesheet.atlas"));
		textureRegion = textureAtlas.findRegion("0001");
		sprite = new Sprite(textureRegion);
		sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth()/2, Gdx.graphics.getHeight()/2 - sprite.getHeight()/2);
		backgroundTexture = new Texture("spites/background.jpg");
		backgroundSprite = new Sprite(backgroundTexture);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		backgroundSprite.draw(batch);
		sprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.RIGHT){
			curentFrame++;
			if(curentFrame > MAX_FRAMES){
				curentFrame = 1;
			}

			sprite.setRegion(textureAtlas.findRegion(String.format("%04d", curentFrame)));
		}
		else if(keycode == Input.Keys.LEFT){
			curentFrame--;
			if(curentFrame < 1){
				curentFrame = MAX_FRAMES;
			}

			sprite.setRegion(textureAtlas.findRegion(String.format("%04d", curentFrame)));
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {

		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
