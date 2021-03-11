package com.agegame.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapController {
    private Map map;
    private MapParameters mapParameters;

    public MapController(MapParameters mapParameters){
        initController(mapParameters);
    }

    private void initController( MapParameters mapParameters){
        this.mapParameters = mapParameters;
        map = new Map(mapParameters);
    }

    public void draw(SpriteBatch batch){
        map.draw(batch);
    }

}
