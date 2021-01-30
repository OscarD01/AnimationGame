package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class AnimationGame extends ApplicationAdapter {
	// Constant rows and columns of the sprite sheet
	private static final int FRAME_COLS = 10, FRAME_ROWS = 1;
	// Objects used
	Animation<TextureRegion> walkAnimationRight; // Must declare frame type (TextureRegion)
	Animation<TextureRegion> walkAnimationLeft; // Must declare frame type (TextureRegion)
	Texture walkSheetRight;
	Texture walkSheetLeft;
	Texture backgroundTexture;
	Sprite backgroundSprite;
	public Animation<TextureRegion> runningAnimation;
	// A variable for tracking elapsed time for the animation
	float stateTime;
	SpriteBatch spriteBatch;

	OrthographicCamera camera;
	Rectangle player;
	Music ambientSound;


	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		ambientSound = Gdx.audio.newMusic(Gdx.files.internal("ambient_sound.mp3"));
		ambientSound.setLooping(true);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 400);

		// Load the sprite sheet as a Texture
		walkSheetRight = new Texture(Gdx.files.internal("spritesheets/spritesheet.png"));
		backgroundTexture = new Texture(Gdx.files.internal("spites/background.jpg"));
		backgroundSprite = new Sprite(backgroundTexture);
		// Use the split utility method to create a 2D array of TextureRegions. This is
		// possible because this sprite sheet contains frames of equal size and they are
		// all aligned.
		TextureRegion[][] tmp = TextureRegion.split(walkSheetRight,
				walkSheetRight.getWidth() / FRAME_COLS,
				walkSheetRight.getHeight() / FRAME_ROWS);

		// Place the regions into a 1D array in the correct order, starting from the top
		// left, going across first. The Animation constructor requires a 1D array.
		TextureRegion[] walkFramesRight = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFramesRight[index++] = tmp[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimationRight = new Animation<TextureRegion>(0.025f, walkFramesRight);

		walkSheetLeft = new Texture(Gdx.files.internal("spritesheets/spritesheetleft.png"));

		TextureRegion[][] tmpLeft = TextureRegion.split(walkSheetLeft,
				walkSheetLeft.getWidth() / FRAME_COLS,
				walkSheetLeft.getHeight() / FRAME_ROWS);

		TextureRegion[] walkFramesLeft = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int indexLeft = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
			for (int j = 0; j < FRAME_COLS; j++) {
				walkFramesLeft[indexLeft++] = tmpLeft[i][j];
			}
		}

		// Initialize the Animation with the frame interval and array of frames
		walkAnimationLeft = new Animation<TextureRegion>(0.025f, walkFramesLeft);


		player = new Rectangle();
		player.x = 800 / 2 - 64 / 2;
		player.y = 20;
		player.width = 50;
		player.height = 80;

		// Instantiate a SpriteBatch for drawing and reset the elapsed animation
		// time to 0
		stateTime = 0f;
	}




	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		ambientSound.setVolume(1.4f);
		ambientSound.play();
		spriteBatch.setProjectionMatrix(camera.combined);
		stateTime += Gdx.graphics.getDeltaTime();

		spriteBatch.begin();
		backgroundSprite.draw(spriteBatch);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			spriteBatch.draw(walkAnimationLeft.getKeyFrame(stateTime, true), player.x, player.y);
			if(player.x > 0) {
				player.x -= 100 * Gdx.graphics.getDeltaTime();
			}
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			spriteBatch.draw(walkAnimationRight.getKeyFrame(stateTime, true), player.x, player.y);
			if(player.x < 750) {
				player.x += 120 * Gdx.graphics.getDeltaTime();
			}
		}
		else{
			spriteBatch.draw(walkAnimationRight.getKeyFrames()[1], player.x, player.y,player.width,player.height);
		}
		spriteBatch.end();


	}


	@Override
	public void dispose () {
		walkSheetRight.dispose();
		walkSheetLeft.dispose();
	}




}
