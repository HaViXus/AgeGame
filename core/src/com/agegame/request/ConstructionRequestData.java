package com.agegame.request;

import com.agegame.utils.GameEra;

public class ConstructionRequestData {
    public long startTime;
    public String construction;
    public GameEra era;

    public ConstructionRequestData(){}

    public ConstructionRequestData(GameEra era, long startTime, String construction){
        this.era = era;
        this.startTime = startTime;
        this.construction = construction;
    }

}
