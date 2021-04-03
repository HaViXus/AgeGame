package com.agegame.game_interface;

import com.agegame.game_data.ConstructionData;
import com.agegame.game_data.GameData;
import com.agegame.player.Action;
import com.agegame.player.Player;
import com.agegame.player.PlayerStats;
import com.agegame.request.ConstructionRequest;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.ArrayList;

public class InterfaceController {

    private GameInterface gameInterface;
    public static Action.DomainType state;
    private Action.DomainType lastState;
    private PanelRenderDataPacket constructionPanelRenderData;
    private PanelRenderDataPacket attachmentsPanelRenderData;
    private Player player;
    public InterfaceController(Player player){
        initInterfaceController(player);
    }

    private void initInterfaceController(Player player){
        this.player = player;
        state = Action.DomainType.LAND_UNIT;
        lastState =  Action.DomainType.LAND_UNIT;

        constructionPanelRenderData = getConstructionPanelRenderData();
        attachmentsPanelRenderData = getAttachmentsPanelRenderDataPacket();
        gameInterface = new GameInterface(constructionPanelRenderData, attachmentsPanelRenderData);

    }

    public void update(float delta){
        updatePanelDataToDisplay();
        gameInterface.update(delta, constructionPanelRenderData);
    }

    private void updatePanelDataToDisplay(){
        handleStateChange();
        updateConstructionPanelDataPacket();
    }

    private void handleStateChange(){
        if(state != lastState){
            lastState = state;
            constructionPanelRenderData = getConstructionPanelRenderData();
            gameInterface.panelChange(constructionPanelRenderData);
        }
    }

    private PanelRenderDataPacket getConstructionPanelRenderData(){
        switch (state){
            case LAND_UNIT:
                return getConstructionPanelDataPacket();

            default:
                System.out.println("Nie prawidłowy state intefejsu!");
                return getConstructionPanelDataPacket();
        }
    }

    private PanelRenderDataPacket getConstructionPanelDataPacket(){

        ArrayList<PanelRenderData> packet = new ArrayList<>();

        for(ConstructionData constructionData : GameData.getDataFromDomain(state).get(player.getStats().era)){
            Pixmap buttonImage;
            buttonImage = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
            switch (state){
                case LAND_UNIT:
                    buttonImage.setColor(Color.GREEN);
                    break;
                case AIR_UNIT:
                    buttonImage.setColor(Color.WHITE);
                    break;
                case WATER_UNIT:
                    buttonImage.setColor(Color.NAVY);
                    break;
                case TURRET:
                    buttonImage.setColor(Color.VIOLET);
                    break;

            }
            buttonImage.fill();

            ArrayList<Action> actionsFromDomain = player.getStats().getActionByDomain(state);
            Action actionConnectedWithButton = null;
            for(Action action: actionsFromDomain){
                if(action.actionName == constructionData.name){
                    actionConnectedWithButton = action;
                    break;
                }
            }

            Action finalActionConnectedWithButton = actionConnectedWithButton;
            Runnable onClick = () -> {
                String era = player.getStats().era;
                long time = System.currentTimeMillis();
                ConstructionRequest constructionRequest = new ConstructionRequest(era, time, constructionData.name, state);
                player.getRequestQueue().addRequest(constructionRequest);
                float progress = (System.currentTimeMillis() - finalActionConnectedWithButton.useTime) / ( constructionData.constructionTime * 1000 );
                System.out.println("Clicked: " + constructionData.name + " " + progress);
            };

            if(actionConnectedWithButton != null){
                PanelRenderData buttonData = new PanelRenderData();
                buttonData.image = buttonImage;
                buttonData.onClick = onClick;
                buttonData.disabled = ( actionConnectedWithButton.state == Action.ActionState.DISABLED || actionConnectedWithButton.state == Action.ActionState.WAITING);
                buttonData.progress = (System.currentTimeMillis() - finalActionConnectedWithButton.useTime) / ( constructionData.constructionTime * 1000 );
                buttonData.relatedActionName = actionConnectedWithButton.actionName;
                packet.add(buttonData);
            }


        }

        PanelRenderDataPacket dataPacket = new PanelRenderDataPacket(packet);

        return dataPacket;
    }

    private void updateConstructionPanelDataPacket() {

        ArrayList<Action> actionsFromDomain = player.getStats().getActionByDomain(state);

        for(PanelRenderData buttonData: constructionPanelRenderData.packet){

            Action actionConnectedWithButton = null;
            for(Action action: actionsFromDomain){
                if(action.actionName == buttonData.relatedActionName){
                    actionConnectedWithButton = action;
                    break;
                }
            }

            float constructionTime = -1;
            for(ConstructionData construction: GameData.getDataFromDomain(state).get(player.getStats().era)){
                if(construction.name == buttonData.relatedActionName){
                    constructionTime = construction.constructionTime;
                    break;
                }
            }

            buttonData.disabled = ( actionConnectedWithButton.state == Action.ActionState.DISABLED || actionConnectedWithButton.state == Action.ActionState.WAITING);
            buttonData.progress = (System.currentTimeMillis() - actionConnectedWithButton.useTime) / ( constructionTime * 1000 );
        }
    }

    private PanelRenderDataPacket getAttachmentsPanelRenderDataPacket(){
        Pixmap defaultImage;
        defaultImage = new Pixmap(18, 18, Pixmap.Format.RGBA8888);
        defaultImage.setColor(Color.LIME);
        defaultImage.fill();

        PanelRenderData landUnitData = new PanelRenderData();
        landUnitData.image = defaultImage;
        landUnitData.disabled = false;
        landUnitData.moveTo = Action.DomainType.LAND_UNIT;

        PanelRenderData airUnitData = new PanelRenderData();
        airUnitData.image = defaultImage;
        airUnitData.disabled = false;
        airUnitData.moveTo = Action.DomainType.AIR_UNIT;

        PanelRenderData waterUnitData = new PanelRenderData();
        waterUnitData.image = defaultImage;
        waterUnitData.disabled = false;
        waterUnitData.moveTo = Action.DomainType.WATER_UNIT;

        PanelRenderData turretsData = new PanelRenderData();
        turretsData.image = defaultImage;
        turretsData.disabled = false;
        turretsData.moveTo = Action.DomainType.TURRET;

        ArrayList<PanelRenderData> packet = new ArrayList<>();
        packet.add(landUnitData);
        packet.add(airUnitData);
        packet.add(waterUnitData);
        packet.add(turretsData);
        PanelRenderDataPacket dataPacket = new PanelRenderDataPacket(packet);

        return dataPacket;
    }



    public GameInterface getGameInterface() {
        return gameInterface;
    }
}
