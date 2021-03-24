package com.agegame.game_interface;

import com.agegame.player.Action;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class ActionPanel {

    ArrayList<PanelButton> buttons;
    Vector2 position;

    public ActionPanel(PanelRenderDataPacket panelRenderDataPacket, Vector2 position, Stage UIStage){
        this.position = position;
        buttons = new ArrayList<>();
        changeButtons(panelRenderDataPacket, UIStage);
    }

    public void changeButtons(PanelRenderDataPacket dataPacket, Stage UIStage){
        deleteOldButtonsFromStage();

        buttons.clear();

        int offsetX = 0;
        for(PanelRenderData buttonData : dataPacket.packet){
            createButton(buttonData, new Vector2(position.x + offsetX, position.y), UIStage);
            offsetX += buttonData.image.getWidth() + 5;
        }
    }

    private void deleteOldButtonsFromStage(){
        for(PanelButton button: buttons){
            button.remove();
        }
    }

    private void buttonOnClick(Action.DomainType state){
        InterfaceController.state = state;
    }

    private void createButton(PanelRenderData buttonData, Vector2 buttonPosition, Stage UIStage){
        PanelButton panelButton = null;
        if(buttonData.moveTo == null)
            panelButton = new PanelButton(buttonData.image, buttonPosition);
        else {
            Class[] buttonClickTypes = new Class[1];
            buttonClickTypes[0] = Action.DomainType.class;
            final Action.DomainType stateToMove = buttonData.moveTo;
            try {
                Runnable buttonOnClickMethod = new Runnable() {
                    @Override
                    public void run() {
                        InterfaceController.state = stateToMove;
                    }
                };
                panelButton = new PanelButton(buttonData.image, buttonPosition, buttonOnClickMethod);
            }catch (Exception exception){
                System.out.println("Button creation error!");
            }

        }
        if(panelButton != null){
            buttons.add(panelButton);
            UIStage.addActor(panelButton);
        }

    }

    public void update(PanelRenderDataPacket dataPacket){
        for(int i=0; i<dataPacket.packet.size(); i++){
            PanelRenderData buttonData = dataPacket.packet.get(i);
            buttons.get(i).update( buttonData.disabled, buttonData.constructionProgress);
        }
    }

    public void draw(Stage UIStage){
        //for(ConstructionButton button : buttons)
        //    UIStage.addActor(button);
    }
}
