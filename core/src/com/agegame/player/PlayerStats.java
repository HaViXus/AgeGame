package com.agegame.player;

import com.agegame.game_data.GameData;
import com.agegame.tower.Tower;
import com.agegame.turrets.TurretSlot;

import java.util.ArrayList;

public class PlayerStats {
    public int gold;
    public int exp;
    public String era;

    public int maxTowers;
    public ArrayList<Tower> towers;

    public ArrayList<Action> landUnitsState;
    public ArrayList<Action> airUnitsState;
    public ArrayList<Action> waterUnitsState;
    public ArrayList<Action> turretsState;
    public ArrayList<Action> towersState;

    public PlayerStats(){
        landUnitsState = new ArrayList<>();
        airUnitsState = new ArrayList<>();
        waterUnitsState = new ArrayList<>();
        turretsState = new ArrayList<>();
        towersState = new ArrayList<>();
        maxTowers = GameData.towerPrices.size();
    }

    public ArrayList<Action> getActionByDomain(Action.DomainType domain){
        switch(domain){
            case LAND_UNIT:
                return landUnitsState;
            case AIR_UNIT:
                return airUnitsState;
            case WATER_UNIT:
                return waterUnitsState;
            case TURRET:
                return turretsState;
            case TOWER:
                return towersState;
            default:
                return null;
        }
    }

    public int getTowersNumber(){
        System.out.println("TOWERS: " + towers);
        if(towers != null) {
            return towers.size() - 1;
        }else{
            return 0;
        }
    }

    public int getTurretsNumber(){
        int number = 0;
        for(Tower tower : towers){
            if(tower.getTurretSlot().turret != null){
                number++;
            }
        }
        return number;
    }

}
