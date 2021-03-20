package com.agegame.player;

public class Action {
    public static enum ActionType {START, SELL};
    public static enum DomainType {LAND_UNIT, AIR_UNIT, WATER_UNIT, TOWER, SELL};

    public String actionElement;
    public ActionType type;
    public DomainType domain;

    public Action(ActionType type, DomainType domain, String actionElement){
        this.actionElement = actionElement;
        this.type = type;
        this.domain = domain;
    }
}
