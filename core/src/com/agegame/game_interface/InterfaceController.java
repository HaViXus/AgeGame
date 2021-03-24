package com.agegame.game_interface;

import com.agegame.game_data.ConstructionData;
import com.agegame.game_data.GameData;
import com.agegame.player.Action;
import com.agegame.player.Player;
import com.agegame.player.PlayerStats;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.ArrayList;

public class InterfaceController {

    private GameInterface gameInterface;
    public static Action.DomainType state;
    private Action.DomainType lastState;
    private PanelRenderDataPacket constructionPanelRenderData;
    private PanelRenderDataPacket attachmentsPanelRenderData;
    private PlayerStats playerStats;
    public InterfaceController(PlayerStats playerStats){
        initInterfaceController(playerStats);
    }

    private void initInterfaceController(PlayerStats playerStats){

        this.playerStats = playerStats;
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
                return getConstructionPanelLandUnitsDataPacket();

            default:
                System.out.println("Nie prawidłowy state intefejsu!");
                return getConstructionPanelLandUnitsDataPacket();
        }
    }

    private PanelRenderDataPacket getConstructionPanelLandUnitsDataPacket(){

        ArrayList<PanelRenderData> packet = new ArrayList<>();

        for(ConstructionData constructionData : GameData.getDataFromDomain(state).get(playerStats.era)){
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
                case TOWER:
                    buttonImage.setColor(Color.VIOLET);
                    break;

            }

            buttonImage.fill();

            PanelRenderData buttonData = new PanelRenderData();
            buttonData.image = buttonImage;
            buttonData.disabled = false;
            packet.add(buttonData);

        }

        PanelRenderDataPacket dataPacket = new PanelRenderDataPacket(packet);

        return dataPacket;
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
        turretsData.moveTo = Action.DomainType.TOWER;

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
