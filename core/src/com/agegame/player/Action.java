package com.agegame.player;

public class Action {
    public static enum ActionType {START, CANCEL};

    public String actionName;
    public ActionType type;

    public Action(ActionType type, String actionName){
        this.actionName = actionName;
        this.type = type;
    }
}
