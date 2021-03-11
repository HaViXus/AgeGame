package com.agegame;

import com.agegame.Map.MapController;
import com.agegame.Map.MapParameters;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObjectsController {

    private MapController mapController;

    public GameObjectsController(MapParameters mapParameters){
        initController(mapParameters);
    }

    public void initController(MapParameters mapParameters){
        mapController = new MapController(mapParameters);
    }

    public void draw(SpriteBatch batch){
        mapController.draw(batch);
    }



}
