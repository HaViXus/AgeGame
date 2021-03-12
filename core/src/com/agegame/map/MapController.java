package com.agegame.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapController {
    private Map map;
    private MapParameters mapParameters;

    public MapController(MapParameters mapParameters, Stage gameStage){
        initController(mapParameters, gameStage);
    }

    private void initController( MapParameters mapParameters, Stage gameStage){
        this.mapParameters = mapParameters;
        map = new Map(mapParameters, gameStage);
    }

    public void draw(){
        map.draw();
    }

}
