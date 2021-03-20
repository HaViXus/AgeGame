package com.agegame.player;

import com.agegame.GameController;
import com.agegame.game_data.ConstructionData;
import com.agegame.request.ConstructionRequest;
import com.agegame.request.ConstructionRequestData;
import com.agegame.request.RequestQueue;
import com.agegame.utils.GameEra;

import java.util.ArrayList;

public class ActionController {
    private ArrayList<Action> possibleConstructionActions;
    private ArrayList<Action> possibleUnitAction;

    private RequestQueue requestQueue;

    private ArrayList<ConstructionData> mockedConstructions;

    public ActionController( RequestQueue requestQueue){
        mockedConstructions = GameController.gameData.getLandUnits().get("STONE_AGE");

        this.requestQueue = requestQueue;

        possibleConstructionActions = new ArrayList<>();
        possibleUnitAction = new ArrayList<>();

    }

    public void updatePossibleActions(PlayerStats playerStats){
        updatePossibleConstructionActions(playerStats);
    }

    private void updatePossibleConstructionActions(PlayerStats playerStats){
        ArrayList<ConstructionRequest> actualContstructionRequests = requestQueue.getConstructionRequests();
        possibleConstructionActions.clear();

        for(ConstructionData construction : mockedConstructions){
            boolean possible = true;
            for(ConstructionRequest constructionRequest : actualContstructionRequests){
                ConstructionRequestData requestData = (ConstructionRequestData) constructionRequest.getRequestData();
                if(requestData.construction == construction.construction){
                    possible = false;
                    break;
                }
            }

            if(construction.price > playerStats.gold) possible = false;

            if(possible){
                Action constructionAction = new Action(Action.ActionType.START, Action.DomainType.TOWER, construction.construction);
                possibleConstructionActions.add(constructionAction);
            }
        }


    }

    public ArrayList<Action> getPossibleConstructionActions(){
        return possibleConstructionActions;
    }

    public ArrayList<Action> getPossibleUnitAction() {
        return possibleUnitAction;
    }
}
