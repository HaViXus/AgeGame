package com.agegame;

import com.agegame.game_interface.GameInterface;
import com.agegame.game_interface.InterfaceController;
import com.agegame.map.MapParameters;
import com.agegame.player.HumanPlayer;
import com.agegame.player.Player;
import com.agegame.input.CameraInputProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameController {

    private GameObjectsController gameObjectsController;
    private InputMultiplexer inputMultiplexer;
    private GameCameraController gameCameraController;
    private InterfaceController interfaceController;
    private Player[] players;
    private Stage gameStage;


    public GameController(MapParameters mapParameters){
        initGameController(mapParameters);
    }

    private void initGameController(MapParameters mapParameters){
        initInputMultiplexer();
        initGameCameraController(mapParameters);
        initInterfaceController();
        initGameStage();
        initGameObjectController(mapParameters);

        initPlayers();
    }

    private void initGameObjectController(MapParameters mapParameters){
        gameObjectsController = new GameObjectsController(mapParameters, gameStage);
    }

    private void initGameCameraController(MapParameters mapParameters){
        CameraInputProcessor cameraInputProcessor = new CameraInputProcessor();
        inputMultiplexer.addProcessor(cameraInputProcessor);
        gameCameraController = new GameCameraController(cameraInputProcessor, mapParameters);


    }

    private void initPlayers(){
        players = new Player[2];
        players[0] = new HumanPlayer();
        players[0].init();
        players[0].setDirection( Direction.direction.LEFT );
    }

    private void initInputMultiplexer(){
        inputMultiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void initGameStage(){
        Viewport viewport = new FitViewport(320, 180, gameCameraController.getCamera());
        gameStage = new Stage(viewport);
        inputMultiplexer.addProcessor(gameStage);
    }

    private void initInterfaceController(){
        interfaceController = new InterfaceController();
        inputMultiplexer.addProcessor(interfaceController.getGameInterface().getStage());
    }

    public void update(SpriteBatch batch, float delta){
        gameCameraController.updateCamera(batch, delta);
        interfaceController.update(delta, players[0]);
        players[0].update();
        gameStage.act(delta);
    }

    public void draw(SpriteBatch batch){

        gameStage.getBatch().begin();
        gameObjectsController.draw();
        gameStage.getBatch().end();

        gameStage.draw();
        interfaceController.getGameInterface().draw(batch);
    }

    public void onResize(int width, int height){
        gameStage.getViewport().update(width, height, true);
        interfaceController.getGameInterface().getStage().getViewport().update(width, height, true);
    }

}
