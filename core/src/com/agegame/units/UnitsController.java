package com.agegame.units;

import com.agegame.Base.Base;
import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.map.MapLine;
import com.agegame.player.Action;
import com.agegame.player.Player;
import com.agegame.request.ConstructionRequestData;
import com.agegame.request.Request;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class UnitsController {
    private Map map;
    private Player[] players;
    private Stage gameStage;

    private ArrayList<Unit> leftUnits;
    private ArrayList<Unit> rightUnits;
    private ArrayList<DamageField> leftDamageFields;
    private ArrayList<DamageField> rightDamageFields;

    public enum UnitType{
        LAND_UNIT(Action.DomainType.LAND_UNIT),
        AIR_UNIT(Action.DomainType.AIR_UNIT),
        WATER_UNIT(Action.DomainType.WATER_UNIT);

        private Action.DomainType domain;

        UnitType(Action.DomainType domain){
            this.domain = domain;
        }

        public Action.DomainType getDomain(){
            return domain;
        }
    }

    public UnitType domainToUnitType(Action.DomainType domain){
        for(UnitType unitType : UnitType.values()){
            if(unitType.domain == domain) return unitType;
        }
        return null;
    }


    public UnitsController(Map map, Player[] players, Stage gameStage) {
        this.map = map;
        this.players = players;
        this.gameStage = gameStage;

        init();
    }

    private void init(){
        leftUnits = new ArrayList<>();
        rightUnits = new ArrayList<>();
        leftDamageFields = new ArrayList<>();
        rightDamageFields = new ArrayList<>();
    }

    public void update(float delta){
        for(Player player: players){
            if(player != null) {
                handleRequests(player);
            }
        }

        for( Action.DomainType lineDomain: map.getLines().keySet() ){
            MapLine line = map.getLines().get(lineDomain);
            updateUnits(line, delta);
        }

        handleDamage();
        handleDeadUnits();
    }

    private void updateUnits(MapLine line, float delta){
        for( Unit unit : line.units ){
            unit.update(delta);
        }
    }

    private void handleRequests(Player player){
        for(Request request: player.getRequestQueue().getConstructionRequests()){
            ConstructionRequestData requestData = (ConstructionRequestData) request.getRequestData();
            if(requestData.domain == Action.DomainType.LAND_UNIT &&
                    ( requestData.requestName.equals("UgaBuga") || requestData.requestName.equals("Slingshot"))){
                createUnit(requestData.domain, player, requestData.requestName);
            }
        }
    }

    private void divideUnitsByDirection(){
        for(MapLine line : map.getLines().values()){
            for(Unit unit : line.units){
                if(unit.getDirection() == Direction.direction.LEFT){
                    leftUnits.add(unit);
                    divideDamageFieldsByDirection(unit, leftDamageFields);
                }
                else{
                    rightUnits.add(unit);
                    divideDamageFieldsByDirection(unit, rightDamageFields);
                }
            }
        }
    }

    private void divideDamageFieldsByDirection(Unit unit, ArrayList<DamageField> damageFields){
        for(DamageField damageField : unit.getDamageFields()) {
            damageFields.add(damageField);
        }
    }



    private void handleDamage(){
        leftDamageFields.clear();
        rightDamageFields.clear();
        leftUnits.clear();
        rightUnits.clear();

        divideUnitsByDirection();

        handleOneSideDamage(leftDamageFields, rightUnits);
        handleOneSideDamage(rightDamageFields, leftUnits);

        clearDamageFields();
    }

    private void handleOneSideDamage(ArrayList<DamageField> damageFields, ArrayList<Unit> units){
        for(DamageField damageField : damageFields){
            for(Unit unit : units){
                if(unit.getState() != Unit.UnitState.DEAD){
                    boolean overlaps = unitOverlapsDamageField(unit, damageField);
                    if(overlaps) {
                        unit.damage(damageField.damage, damageField.knockback);
                        if(!damageField.areaDamage){
                            damageField.isActive = false;
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean unitOverlapsDamageField(Unit unit, DamageField damageField){
        for(Rectangle hitbox : unit.getHitboxes()){
            if(Intersector.overlaps(hitbox, damageField)) return true;
        }
        return false;
    }

    private void clearDamageFields() {
        for(MapLine line : map.getLines().values()) {
            for (Unit unit : line.units) {
                unit.getDamageFields().clear();
            }
        }
    }

    private void createUnit(Action.DomainType domain, Player player, String unitName){
        try{
            Unit unit = createUnitClassInstantion(domain, unitName);
            MapLine line = map.getLines().get(domain);
            Vector2 startPosition = getUnitSpawnPosition(player, line);

            unit.init(map, startPosition, player.getDirection(), domain);
            gameStage.addActor(unit.getActor());

            line.units.add(unit);
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

    private Unit createUnitClassInstantion(Action.DomainType domain, String unitName) throws IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        String unitTypePath = getLineClassPathToUnit(domainToUnitType(domain));
        String classPath = "com.agegame.units." + unitTypePath + "." + unitName;
        Class myClass = Class.forName(classPath);

        Class[] types = {};
        Constructor constructor = myClass.getConstructor(types);

        Object[] parameters = {};

        return (Unit) constructor.newInstance(parameters);
    }

    private Vector2 getUnitSpawnPosition(Player player, MapLine line){
        Base spawnBase = map.getBase(player.getDirection());
        return new Vector2( spawnBase.getSpawnXPosition(), line.getPositionY() );
    }

    private String getLineClassPathToUnit(UnitType unitType){
        if(unitType == UnitType.LAND_UNIT) return "land_units";
        else if(unitType == UnitType.AIR_UNIT) return "air_units";
        else if(unitType == UnitType.WATER_UNIT) return "water_units";
        else return null;
    }

    private void handleDeadUnits(){
        for(MapLine line : map.getLines().values()) {
            Iterator<Unit> unitIterator = line.units.iterator();
            while(unitIterator.hasNext()){
                Unit unit = unitIterator.next();
                if(unit.isDead()){
                    unit.dispose();
                    unit.getActor().remove();
                    unitIterator.remove();
                }
            }
        }
    }

}
