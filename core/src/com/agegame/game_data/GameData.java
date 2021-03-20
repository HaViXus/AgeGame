package com.agegame.game_data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.HashMap;

public class GameData {
    private HashMap<String, ArrayList<ConstructionData>> landUnits;
    private HashMap<String, ArrayList<ConstructionData>> airUnits;
    private HashMap<String, ArrayList<ConstructionData>> waterUnits;
    private HashMap<String, ArrayList<ConstructionData>> turrets;
    public static ArrayList<String> gameEras = new ArrayList();

    private JsonValue constructionDataJSON;

    public GameData(){
        initJSONData();
        initGameEras();
        initUnits();
        initTurrets();
    }

    private void initJSONData(){
        JsonReader json = new JsonReader();
        constructionDataJSON = json.parse(Gdx.files.internal("ConstructionsData.json"));
    }

    private void initGameEras(){
        JsonValue gameErasJson = constructionDataJSON.get("gameEras");
        for(JsonValue era: gameErasJson) gameEras.add(era.toString());
    }

    private void initUnits(){
        initLandUnits();
        initAirUnits();
        initWaterUnits();
    }

    private void initLandUnits(){
        landUnits = new HashMap<>();
        JsonValue landUnitsJson = constructionDataJSON.get("landUnits");
        initUnitsFromJson(landUnitsJson, landUnits);
    }

    private void initAirUnits(){
        airUnits = new HashMap<>();
        JsonValue airUnitsJson = constructionDataJSON.get("airUnits");
        initUnitsFromJson(airUnitsJson, airUnits);

    }

    private void initWaterUnits(){
        waterUnits = new HashMap<>();
        JsonValue waterUnitsJson = constructionDataJSON.get("waterUnits");
        initUnitsFromJson(waterUnitsJson, waterUnits);
    }

    private void initUnitsFromJson(JsonValue unitsType, HashMap unitsMap){
        for(String era: gameEras){
            JsonValue unitsTypeFromEraJson = unitsType.get(era);
            ArrayList<ConstructionData> unitsFromEra = new ArrayList<>();
            for(JsonValue unit: unitsTypeFromEraJson)
                initUnit(unit, unitsFromEra);
            unitsMap.put(era, unitsFromEra);
        }
    }

    private void initUnit(JsonValue unit, ArrayList<ConstructionData> unitsFromEra){
        float constructionTime = unit.getFloat("refreshTime");
        String name = unit.getString("name");
        int price = unit.getInt("price");
        ConstructionData unitData = new ConstructionData(constructionTime, name, price);
        unitsFromEra.add(unitData);
    }

    private void initTurrets(){
        turrets = new HashMap<>();
        JsonValue turretsJson = constructionDataJSON.get("turrets");
        initTurretsFromJson(turretsJson, turrets);
    }

    private void initTurretsFromJson(JsonValue turrets, HashMap turretsMap){
        for(String era: gameEras){
            JsonValue turretsFromEraJson = turrets.get(era);
            ArrayList<ConstructionData> turretsFromEra = new ArrayList<>();
            for(JsonValue turret: turretsFromEraJson)
                initTurrets(turret, turretsFromEra);
            turretsMap.put(era, turretsFromEra);
        }
    }

    private void initTurrets(JsonValue turret, ArrayList<ConstructionData> turretsFromEra){
        String name = turret.getString("name");
        int price = turret.getInt("price");
        ConstructionData turretData = new ConstructionData(name, price);
        turretsFromEra.add(turretData);
    }

    public HashMap<String, ArrayList<ConstructionData>> getTurrets() { return turrets; }

    public HashMap<String, ArrayList<ConstructionData>> getLandUnits() { return landUnits; }

    public HashMap<String, ArrayList<ConstructionData>> getAirUnits() { return airUnits; }

    public HashMap<String, ArrayList<ConstructionData>> getWaterUnits() { return waterUnits; }
}
