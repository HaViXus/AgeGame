package com.agegame.units;

import com.agegame.Base.Base;
import com.agegame.map.Map;
import com.agegame.map.MapLine;
import com.agegame.player.Action;
import com.agegame.player.Player;
import com.agegame.request.ConstructionRequestData;
import com.agegame.request.Request;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class UnitsController {
    private Map map;
    private Player[] players;
    private Stage gameStage;


    public UnitsController(Map map, Player[] players, Stage gameStage) {
        this.map = map;
        this.players = players;
        this.gameStage = gameStage;
    }

    public void update(float delta){
        for(Player player: players){
            if(player != null) {
                handleRequests(player);
            }
        }

        for( Action.DomainType lineDomain: map.getLines().keySet() ){
            MapLine line = map.getLines().get(lineDomain);
            moveUnits(line, delta);
        }
    }

    private void moveUnits(MapLine line, float delta){
        for( Unit unit : line.units ){
            unit.move(delta);
        }
    }

    private void handleRequests(Player player){
        for(Request request: player.getRequestQueue().getConstructionRequests()){
            ConstructionRequestData requestData = (ConstructionRequestData) request.getRequestData();
            if(requestData.domain == Action.DomainType.LAND_UNIT && requestData.requestName.equals("UgaBuga")){
                createUnit(requestData.domain, player, requestData.requestName);
            }
        }
    }

    private void createUnit(Action.DomainType domain, Player player, String unitName){
        try{
            String classPath = "com.agegame.units.land_units." + unitName;
            Class myClass = Class.forName(classPath);

            Class[] types = {};
            Constructor constructor = myClass.getConstructor(types);

            Object[] parameters = {};
            Unit unit = (Unit) constructor.newInstance(parameters);

            Base spawnBase = map.getBase(player.getDirection());
            MapLine line = map.getLines().get(domain);
            Vector2 startPosition = new Vector2( spawnBase.getSpawnXPosition(), line.getPositionY() );

            unit.init(map, startPosition, player.getDirection(), domain);
            gameStage.addActor(unit.getActor());

            line.units.add(unit);
            System.out.println(line.units.size());

        }catch(ClassNotFoundException | NoSuchMethodException e){
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
