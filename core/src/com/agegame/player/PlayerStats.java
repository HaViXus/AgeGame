package com.agegame.player;

import java.util.ArrayList;

public class PlayerStats {
    public int gold;
    public int exp;
    public String era;

    public ArrayList<Action> landUnitsState;
    public ArrayList<Action> airUnitsState;
    public ArrayList<Action> waterUnitsState;
    public ArrayList<Action> turretsState;

    public PlayerStats(){
        landUnitsState = new ArrayList<>();
        airUnitsState = new ArrayList<>();
        waterUnitsState = new ArrayList<>();
        turretsState = new ArrayList<>();
    }


}
