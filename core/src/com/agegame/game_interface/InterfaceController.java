package com.agegame.game_interface;

import com.agegame.player.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.ArrayList;

public class InterfaceController {

    private GameInterface gameInterface;
    public static ArrayList<InterfaceState> state;
    private InterfaceState lastState;
    private PanelRenderDataPacket constructionPanelRenderData;
    public InterfaceController(){
        initInterfaceController();
    }

    private void initInterfaceController(){
        state = new ArrayList<>();
        state.add( InterfaceState.MAIN_VIEW );
        lastState = InterfaceState.MAIN_VIEW;

        constructionPanelRenderData = getConstructionPanelRenderData();
        gameInterface = new GameInterface(constructionPanelRenderData);

    }

    public void update(float delta, Player player){
        updatePanelDataToDisplay(player);
        gameInterface.update(delta, constructionPanelRenderData);
    }

    private void updatePanelDataToDisplay(Player player){
        handleStateChange();
    }

    private void handleStateChange(){
        InterfaceState actualState = state.get(state.size()-1);
        if(actualState != lastState){
            lastState = actualState;
            constructionPanelRenderData = getConstructionPanelRenderData();
            gameInterface.panelChange(constructionPanelRenderData);
        }
    }

    private PanelRenderDataPacket getConstructionPanelRenderData(){
        InterfaceState actualConstructionInterfaceState = state.get(state.size()-1);
        switch (actualConstructionInterfaceState){
            case MAIN_VIEW:
                return getConstructionPanelMainViewDataPacket();

            default:
                System.out.println("Nie prawidłowy state intefejsu!");
                return getConstructionPanelMainViewDataPacket();
        }
    }

    private PanelRenderDataPacket getConstructionPanelMainViewDataPacket(){
        Pixmap unitImage;
        unitImage = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        unitImage.setColor(Color.CYAN);
        unitImage.fill();

        PanelRenderData unitData = new PanelRenderData();
        unitData.image = unitImage;
        unitData.disabled = false;
        unitData.moveTo = InterfaceState.UNITS;

        Pixmap turretsImage;
        turretsImage = new Pixmap(20, 20, Pixmap.Format.RGBA8888);
        turretsImage.setColor(Color.RED);
        turretsImage.fill();

        PanelRenderData turretsData = new PanelRenderData();
        turretsData.image = turretsImage;
        turretsData.disabled = false;
        turretsData.moveTo = InterfaceState.TURRETS;

        ArrayList<PanelRenderData> packet = new ArrayList<>();
        packet.add(unitData);
        packet.add(turretsData);
        PanelRenderDataPacket dataPacket = new PanelRenderDataPacket(packet);

        return dataPacket;
    }



    public GameInterface getGameInterface() {
        return gameInterface;
    }
}
