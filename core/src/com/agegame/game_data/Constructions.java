package com.agegame.game_data;

import com.agegame.utils.GameEra;

import java.util.ArrayList;
import java.util.HashMap;

public class Constructions {
    private HashMap<GameEra, ArrayList<ConstructionData>> building;

    public Constructions(){
        initBuildings();
    }

    private void initBuildings(){
        initStoneAgeBuildings();
    }

    private void initStoneAgeBuildings(){
        ArrayList<ConstructionData> stoneAgeBuildings = new ArrayList<>();
        stoneAgeBuildings.add( new ConstructionData(GameEra.STONE_AGE, 3.0f, "Construction1", 300) );
        stoneAgeBuildings.add( new ConstructionData(GameEra.STONE_AGE, 3.0f, "Construction2", 300) );
        stoneAgeBuildings.add( new ConstructionData(GameEra.STONE_AGE, 3.0f, "Construction3", 300) );

        building.put(GameEra.STONE_AGE, stoneAgeBuildings);
    }

    public HashMap<GameEra, ArrayList<ConstructionData>> getBuilding() {
        return building;
    }
}
