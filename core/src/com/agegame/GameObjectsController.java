package com.agegame;

import com.agegame.map.Map;
import com.agegame.map.MapController;
import com.agegame.map.MapParameters;
import com.agegame.player.Player;
import com.agegame.units.UnitsController;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameObjectsController {

    private MapController mapController;
    private UnitsController unitsController;

    public GameObjectsController(MapParameters mapParameters, Stage gameStage, Player[] players){
        initController(mapParameters, gameStage);
        initUnitsController(mapController.getMap(), gameStage, players);
    }

    public void initController(MapParameters mapParameters, Stage gameStage){
        mapController = new MapController(mapParameters, gameStage);
    }

    public void initUnitsController(Map map, Stage gameStage, Player[] players){
        unitsController = new UnitsController(map, players, gameStage);
    }

    public void update(float delta){
        unitsController.update(delta);
    }

    public void draw(){
        mapController.draw();
    }



}
