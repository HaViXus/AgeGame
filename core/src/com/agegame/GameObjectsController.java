package com.agegame;

import com.agegame.map.Map;
import com.agegame.map.MapController;
import com.agegame.map.MapParameters;
import com.agegame.missile.MissilesController;
import com.agegame.player.Player;
import com.agegame.units.UnitsController;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameObjectsController {

    private MapController mapController;
    private UnitsController unitsController;
    private MissilesController missilesController;

    public GameObjectsController(MapParameters mapParameters, Stage gameStage, Player[] players){
        initController(mapParameters, gameStage, players);
        initUnitsController(mapController.getMap(), gameStage, players);
        initMissileController();
    }

    public void initController(MapParameters mapParameters, Stage gameStage, Player[] players){
        mapController = new MapController(mapParameters, gameStage, players);
    }

    public void initUnitsController(Map map, Stage gameStage, Player[] players){
        unitsController = new UnitsController(map, players, gameStage);
    }

    public void initMissileController(){
        missilesController = new MissilesController();
        missilesController.init(unitsController);
        unitsController.setMissilesArray(missilesController.missiles);
    }

    public void update(float delta){
        unitsController.update(delta);
        missilesController.update(delta);
        mapController.update();
    }

    public void draw(){
        mapController.draw();
    }



}
