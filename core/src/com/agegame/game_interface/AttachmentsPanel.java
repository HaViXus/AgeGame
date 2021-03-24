package com.agegame.game_interface;

import com.agegame.player.Action;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class AttachmentsPanel {
    ArrayList<PanelButton> buttons;
    Vector2 position;

    public AttachmentsPanel(PanelRenderDataPacket panelRenderDataPacket, Vector2 position, Stage UIStage){
        this.position = position;
        buttons = new ArrayList<>();
        createButtons(panelRenderDataPacket, UIStage);
    }

    private void createButtons(PanelRenderDataPacket dataPacket, Stage UIStage){
        int offsetX = 0;
        int offsetY = 0;
        int buttonCounter = 0;
        for(PanelRenderData buttonData : dataPacket.packet){
            offsetX = (buttonData.image.getWidth() + 2) * (buttonCounter % 3) ;
            offsetY = ( buttonData.image.getHeight() + 2) * (dataPacket.packet.size()/3 - buttonCounter/3);
            createButton(buttonData, new Vector2(position.x + offsetX, position.y + offsetY), UIStage);
            buttonCounter++;
        }
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
                Runnable buttonOnClickMethod = () -> InterfaceController.state = stateToMove;
                panelButton = new PanelButton(buttonData.image, buttonPosition, buttonOnClickMethod);
            }catch (Exception exception){
                System.out.println("Attachment button creation error!");
            }

        }
        if(panelButton != null){
            buttons.add(panelButton);
            UIStage.addActor(panelButton);
        }

    }

    public void update(PanelRenderDataPacket dataPacket){
        //        for(int i=0; i<dataPacket.packet.size(); i++){
        //            PanelRenderData buttonData = dataPacket.packet.get(i);
        //            buttons.get(i).update( buttonData.disabled, buttonData.constructionProgress);
        //        }
    }

    public void draw(Stage UIStage){
        //for(ConstructionButton button : buttons)
        //    UIStage.addActor(button);
    }
}
