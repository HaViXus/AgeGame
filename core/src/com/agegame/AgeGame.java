package com.agegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AgeGame extends ApplicationAdapter implements ApplicationListener {
	SpriteBatch batch;
	Texture img;
	Game game;
	Viewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		game = new Game();
		game.create(viewport);


	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		game.resize(width, height);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		if(Gdx.input.isKeyJustPressed(Input.Keys.F5)){
			create();
			System.out.println("RESET");
		}

		batch.begin();
		game.render(batch);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
