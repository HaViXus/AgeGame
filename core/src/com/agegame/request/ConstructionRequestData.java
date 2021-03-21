package com.agegame.request;

import com.agegame.player.Action;

public class ConstructionRequestData {
    public long startTime;
    public String requestName;
    public String era;
    public Action.DomainType domain;

    public ConstructionRequestData(){}

    public ConstructionRequestData(String era, long startTime, String requestName, Action.DomainType domain){
        this.era = era;
        this.startTime = startTime;
        this.requestName = requestName;
        this.domain = domain;
    }

}
