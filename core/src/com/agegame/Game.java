package com.agegame;

import com.agegame.map.MapParameters;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game{
    private GameController gameController;


    public void create(Viewport viewport){

        MapParameters mapParameters= new MapParameters();
        mapParameters = new MapParameters();
        mapParameters.width = 2562;

        gameController = new GameController(mapParameters);

    }


    public void render(SpriteBatch batch){
        float delta = Gdx.graphics.getDeltaTime();

        gameController.update(batch, delta);
        gameController.draw(batch);
        System.out.println("FPS: " + (1/Gdx.graphics.getDeltaTime()));
    }

    public void resize(int width, int height) {
        gameController.onResize(width, height);
    }

    public void dispose () {

    }


}
