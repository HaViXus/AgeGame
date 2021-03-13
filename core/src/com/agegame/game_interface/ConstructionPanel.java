package com.agegame.game_interface;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class ConstructionPanel {

    ArrayList<ConstructionButton> buttons;
    Vector2 position;

    public ConstructionPanel(PanelRenderDataPacket panelRenderDataPacket, Vector2 position, Stage UIStage){
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
        for(ConstructionButton button: buttons){
            button.remove();
        }
    }

    private void createButton(PanelRenderData buttonData, Vector2 buttonPosition, Stage UIStage){
        ConstructionButton constructionButton = new ConstructionButton(buttonData.image, buttonPosition);
        buttons.add(constructionButton);
        UIStage.addActor(constructionButton);
    }

    public void update(PanelRenderDataPacket dataPacket){
        for(int i=0; i<dataPacket.packet.size(); i++){
            PanelRenderData buttonData = dataPacket.packet.get(i);
            System.out.println("TEST: " + i + " " + buttonData.moveTo);
            buttons.get(i).update( buttonData.disabled, buttonData.constructionProgress);
        }
    }

    public void draw(Stage UIStage){
        //for(ConstructionButton button : buttons)
        //    UIStage.addActor(button);
    }
}
