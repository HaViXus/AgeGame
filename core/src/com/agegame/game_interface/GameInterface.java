package com.agegame.game_interface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameInterface {
    private InterfacePanel panel;
    private Stage UIStage;

    public GameInterface() {}

    public GameInterface(PanelRenderDataPacket constructionPanelData, PanelRenderDataPacket attachmentsPanelData){
        init(constructionPanelData, attachmentsPanelData);
    }

    public void init(PanelRenderDataPacket constructionPanelData, PanelRenderDataPacket attachmentsPanelData){
        UIStage = new Stage(new FitViewport(320, 180));
        Vector2 screenSize = new Vector2(UIStage.getWidth(), UIStage.getHeight());
        panel = new InterfacePanel(screenSize, constructionPanelData, attachmentsPanelData, UIStage);
    }

    public void update(float delta, PanelRenderDataPacket constructionPanelData){
        panel.update(constructionPanelData);
        UIStage.act(delta);
    }

    public void panelChange(PanelRenderDataPacket constructionPanelData){
        panel.changeConstructionPanelButtons(constructionPanelData, UIStage);
    }

    public void draw(SpriteBatch batch){ UIStage.draw(); }

    public Stage getStage(){
        return UIStage;
    }
}
