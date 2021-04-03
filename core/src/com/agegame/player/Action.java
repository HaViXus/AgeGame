package com.agegame.player;

public class Action {

    public enum DomainType {LAND_UNIT, AIR_UNIT, WATER_UNIT, TOWER, TURRET, SELL};
    public enum ActionState {READY, WAITING, DISABLED};

    public DomainType domain;
    public ActionState state;
    public long useTime;
    public String actionName;

    public Action(DomainType domain, String actionName){
        this.actionName = actionName;
        this.domain = domain;
        this.useTime = -1;
        state = ActionState.READY;
    }

    public Action(DomainType domain, String actionName, long useTime){
        this.actionName = actionName;
        this.domain = domain;
        this.useTime = useTime;
        state = ActionState.READY;
    }

    public Action(DomainType domain, String actionName, long useTime, ActionState state){
        this.actionName = actionName;
        this.domain = domain;
        this.useTime = useTime;
        this.state = state;
    }

    public Action(DomainType domain, String actionName, ActionState state){
        this.actionName = actionName;
        this.domain = domain;
        this.state = state;
        this.useTime = -1;
    }

    @Override
    public String toString() {
        return "Action{" +
                "domain=" + domain +
                ", state=" + state +
                ", useTime=" + useTime +
                ", actionName='" + actionName + '\'' +
                '}';
    }
}
