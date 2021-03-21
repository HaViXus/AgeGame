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

    public void updateActions(RequestQueue requestQueue){
        updateLandUnitsActions(requestQueue);
    }

    private void updateLandUnitsActions(RequestQueue requestQueue){
        updatePossibleUnitsActions(requestQueue,
                GameData.landUnits.get(playerStats.era),
                playerStats.landUnitsState);
    }

    private void updatePossibleUnitsActions(RequestQueue requestQueue,
                                            ArrayList<ConstructionData> unitsData,
                                            ArrayList<Action> actionList) {
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

            for(ConstructionData construction : unitsData){
                if(construction.name == action.actionName){
                    if(construction.price > playerStats.gold) actionState = Action.ActionState.DISABLED;
                    break;
                }
            }


            action.state = actionState;
            System.out.println(action.toString());
        }


    }
}
