package com.agegame.player;

import com.agegame.game_data.ConstructionData;
import com.agegame.game_data.GameData;
import com.agegame.request.ConstructionRequest;
import com.agegame.request.ConstructionRequestData;
import com.agegame.request.RequestQueue;

import java.util.ArrayList;

public class ActionController {
    private PlayerStats playerStats;

    public ActionController( PlayerStats playerStats){
        this.playerStats = playerStats;
        initActionsForNewEra();
    }

    public void initActionsForNewEra(){
        initUnitsActionsForNewEra(GameData.landUnits.get(playerStats.era),
                playerStats.landUnitsState,
                Action.DomainType.LAND_UNIT);
        initUnitsActionsForNewEra(GameData.airUnits.get(playerStats.era),
                playerStats.airUnitsState,
                Action.DomainType.AIR_UNIT);
        initUnitsActionsForNewEra(GameData.waterUnits.get(playerStats.era),
                playerStats.waterUnitsState,
                Action.DomainType.WATER_UNIT);
        initTurretsActionsForNewEra(GameData.turrets.get(playerStats.era),
                playerStats.turretsState);

        initTowerActions();
    }

    private void initTowerActions() {
        ArrayList<Action> towerActionList = playerStats.towersState;
        Action.ActionState addTowerState = Action.ActionState.READY;
        if(playerStats.getTowersNumber() >= playerStats.maxTowers || playerStats.gold < GameData.towerPrices.get(playerStats.getTowersNumber())){
            addTowerState = Action.ActionState.DISABLED;
        }

        Action addTower = new Action(Action.DomainType.TOWER, "addTower", addTowerState);
        towerActionList.add(addTower);
    }

    private void initUnitsActionsForNewEra(ArrayList<ConstructionData> unitsData, ArrayList<Action> actionList, Action.DomainType domain){
        actionList.clear();
        for(ConstructionData unit : unitsData) {
            Action.ActionState actionState = Action.ActionState.READY;
            if(unit.price > playerStats.gold) {
                actionState = Action.ActionState.DISABLED;
            }
                actionList.add( new Action(domain, unit.name, actionState) );
        }
    }

    private void initTurretsActionsForNewEra(ArrayList<ConstructionData> turretsData, ArrayList<Action> actionList){
        actionList.clear();
        for(ConstructionData turret : turretsData) {
            Action.ActionState actionState = Action.ActionState.READY;
            if(turret.price > playerStats.gold) {
                actionState = Action.ActionState.DISABLED;
            }
            actionList.add( new Action(Action.DomainType.TURRET, turret.name, actionState) );
        }
    }

    public void updateActions(RequestQueue requestQueue){
        updateLandUnitsActions(requestQueue);
        updateTowerPossibleActions();
    }

    private void updateLandUnitsActions(RequestQueue requestQueue){
        updatePossibleUnitsActions(requestQueue,
                GameData.landUnits.get(playerStats.era),
                playerStats.landUnitsState,
                Action.DomainType.LAND_UNIT);
    }

    private void updatePossibleUnitsActions(RequestQueue requestQueue,
                                            ArrayList<ConstructionData> unitsData,
                                            ArrayList<Action> actionList,
                                            Action.DomainType domain) {
        ArrayList<ConstructionRequest> actualConstructionRequests = requestQueue.getConstructionRequests();

        for(Action action : actionList){
            Action.ActionState actionState = Action.ActionState.READY;
            for(ConstructionRequest constructionRequest : actualConstructionRequests){
                ConstructionRequestData requestData = (ConstructionRequestData) constructionRequest.getRequestData();

                if(requestData.requestName == action.actionName){
                    actionState = Action.ActionState.WAITING;
                    action.useTime = requestData.startTime;
                }
            }

            if(isActionInProgress(action, domain)){
                actionState = Action.ActionState.WAITING;
            }

            if(actionState!= Action.ActionState.WAITING){
                for(ConstructionData construction : unitsData){
                    if(construction.name == action.actionName){
                        if(construction.price > playerStats.gold) actionState = Action.ActionState.DISABLED;
                        break;
                    }
                }
            }

            action.state = actionState;
        }
    }

    private void updateTowerPossibleActions(){
        for(Action action : playerStats.towersState){
            if(action.actionName == "addTower"){
                if(playerStats.maxTowers <= playerStats.getTowersNumber() || playerStats.gold < GameData.towerPrices.get(playerStats.getTowersNumber())){
                    action.state = Action.ActionState.DISABLED;
                }else{
                    action.state = Action.ActionState.READY;
                }
            }
        }
    }

    private boolean isActionInProgress(Action action, Action.DomainType domain){
        for(ConstructionData construction: GameData.getDataFromDomain(domain).get(playerStats.era)){
            if(action.actionName == construction.name){
                float progress = (System.currentTimeMillis() - action.useTime) / ( construction.constructionTime * 1000 );
                if(progress > 0 && progress < 1){
                    //System.out.println("ACTION: " + action.actionName + " " + action.state + " " + (System.currentTimeMillis() - action.useTime) / ( construction.constructionTime * 1000 ));
                    return true;
                }
                else return false;
            }
        }
        return false;
    }
}
