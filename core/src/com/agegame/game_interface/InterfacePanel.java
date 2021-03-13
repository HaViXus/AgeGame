package com.agegame.game_interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class InterfacePanel extends Actor {
    private Texture backgroundTexture;
    private ConstructionPanel constructionPanel;

    public InterfacePanel(Vector2 screenSize, PanelRenderDataPacket constructionPanelData, Stage UIStage){
        UIStage.addActor(this);
        cretePanelBackground(screenSize);
        initConstructionPanel(constructionPanelData, UIStage);
    }

    private void cretePanelBackground(Vector2 screenSize){
        Pixmap panelBackground = new Pixmap((int)screenSize.x, 30, Pixmap.Format.RGBA8888);
        panelBackground.setColor(Color.ORANGE);
        panelBackground.fill();

        backgroundTexture = new Texture(panelBackground);
        panelBackground.dispose();

    }

    private void initConstructionPanel(PanelRenderDataPacket constructionPanelData, Stage UIStage){
        constructionPanel = new ConstructionPanel(constructionPanelData, new Vector2(100,5), UIStage);
    }

    public void update(PanelRenderDataPacket constructionPanelData){
        constructionPanel.update(constructionPanelData);
    }

    public void changeConstructionPanelButtons(PanelRenderDataPacket constructionPanelData, Stage UIStage){
        constructionPanel.changeButtons(constructionPanelData, UIStage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backgroundTexture, 0, 0);
    }
}
