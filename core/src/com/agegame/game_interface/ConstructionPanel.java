package com.agegame.game_interface;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.function.Function;

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

    private void buttonOnClick(InterfaceState state){
        InterfaceController.state.add(state);
    }

    private void createButton(PanelRenderData buttonData, Vector2 buttonPosition, Stage UIStage){
        ConstructionButton constructionButton = null;
        if(buttonData.moveTo == null)
            constructionButton = new ConstructionButton(buttonData.image, buttonPosition);
        else {
            Class[] buttonClickTypes = new Class[1];
            buttonClickTypes[0] = InterfaceState.class;
            final InterfaceState stateToMove = buttonData.moveTo;
            try {
                Runnable buttonOnClickMethod = new Runnable() {
                    @Override
                    public void run() {
                        InterfaceController.state.add(stateToMove);
                        System.out.println("TEST: " + stateToMove);
                    }
                };
                constructionButton = new ConstructionButton(buttonData.image, buttonPosition, buttonOnClickMethod);
            }catch (Exception exception){
                System.out.println("Button creation error!");
            }

        }
        if(constructionButton != null){
            buttons.add(constructionButton);
            UIStage.addActor(constructionButton);
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
