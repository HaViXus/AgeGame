package com.agegame;

import com.agegame.map.MapController;
import com.agegame.map.MapParameters;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameObjectsController {

    private MapController mapController;

    public GameObjectsController(MapParameters mapParameters, Stage gameStage){
        initController(mapParameters, gameStage);
    }

    public void initController(MapParameters mapParameters, Stage gameStage){
        mapController = new MapController(mapParameters, gameStage);
    }

    public void draw(){
        mapController.draw();
    }



}
