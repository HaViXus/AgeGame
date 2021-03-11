package com.agegame;

import com.agegame.Map.MapParameters;
import com.agegame.input.CameraInputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameController {

    private GameObjectsController gameObjectsController;
    private InputMultiplexer inputMultiplexer;
    private GameCameraController gameCameraController;


    public GameController(MapParameters mapParameters){
        initGameController(mapParameters);
    }

    private void initGameController(MapParameters mapParameters){
        initInputMultiplexer();
        initGameObjectController(mapParameters);
        initGameCameraController(mapParameters);
    }

    private void initGameObjectController(MapParameters mapParameters){
        gameObjectsController = new GameObjectsController(mapParameters);
    }

    private void initGameCameraController(MapParameters mapParameters){
        CameraInputProcessor cameraInputProcessor = new CameraInputProcessor();
        inputMultiplexer.addProcessor(cameraInputProcessor);
        gameCameraController = new GameCameraController(cameraInputProcessor, mapParameters);


    }

    private void initInputMultiplexer(){
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);

    }

    public void update(SpriteBatch batch, float delta){
        gameCameraController.updateCamera(batch, delta);
    }

    public void draw(SpriteBatch batch){
        gameObjectsController.draw(batch);
    }

    public GameCameraController getGameCameraController(){
        return gameCameraController;
    }


}
