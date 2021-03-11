package com.agegame;

import com.agegame.Map.MapParameters;
import com.agegame.input.CameraInputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game{
    private Stage stage;


    private GameController gameController;
    private InputMultiplexer inputMultiplexer;




    public void create(Viewport viewport){

        MapParameters mapParameters= new MapParameters();
        mapParameters = new MapParameters();
        mapParameters.width = 960;

        gameController = new GameController(mapParameters);
        OrthographicCamera camera = gameController.getGameCameraController().getCamera();


        viewport = new FitViewport(320, 180, camera);
        stage = new Stage(viewport);





    }


    public void render(SpriteBatch batch){
        float delta = Gdx.graphics.getDeltaTime();
        stage.act(delta);
        stage.draw();

        gameController.update(batch, delta);
        gameController.draw(batch);

    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose () {
        stage.dispose();
    }


}
