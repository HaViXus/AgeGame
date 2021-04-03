package com.agegame.map;

import com.agegame.player.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MapController {
    private Map map;
    private MapParameters mapParameters;

    public MapController(MapParameters mapParameters, Stage gameStage, Player[] players){
        initController(mapParameters, gameStage, players);
    }

    private void initController( MapParameters mapParameters, Stage gameStage, Player[] players){
        this.mapParameters = mapParameters;
        map = new Map(mapParameters, gameStage, players);
    }

    public void update(){
        map.update();
    }

    public void draw(){
        map.draw();
    }

    public Map getMap(){ return map; }

}
