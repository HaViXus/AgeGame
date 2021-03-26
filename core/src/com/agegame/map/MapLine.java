package com.agegame.map;

import com.agegame.units.Unit;

import java.util.ArrayList;

public class MapLine {
    private float positionY;
    public ArrayList<Unit> units;

    public MapLine(float positionY){
        this.positionY = positionY;
        units = new ArrayList<>();
    }

    public float getPositionY(){ return positionY; }

}
