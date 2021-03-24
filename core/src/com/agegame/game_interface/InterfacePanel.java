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
    private ActionPanel actionPanel;
    private AttachmentsPanel attachmentsPanel;

    public InterfacePanel(Vector2 screenSize,
                          PanelRenderDataPacket constructionPanelData,
                          PanelRenderDataPacket attachmentsPanelData,
                          Stage UIStage){
        UIStage.addActor(this);
        cretePanelBackground(screenSize);
        initConstructionPanel(constructionPanelData, UIStage);
        initAttachmentsPanel(attachmentsPanelData, UIStage);
    }

    private void cretePanelBackground(Vector2 screenSize){
        Pixmap panelBackground = new Pixmap((int)screenSize.x, 32, Pixmap.Format.RGBA8888);
        panelBackground.setColor(Color.ORANGE);
        panelBackground.fill();

        backgroundTexture = new Texture(panelBackground);
        panelBackground.dispose();

    }

    private void initConstructionPanel(PanelRenderDataPacket constructionPanelData, Stage UIStage){
        actionPanel = new ActionPanel(constructionPanelData, new Vector2(70,2), UIStage);
    }

    private void initAttachmentsPanel(PanelRenderDataPacket attachmentsPanelData, Stage UIStage){
        attachmentsPanel= new AttachmentsPanel(attachmentsPanelData, new Vector2(4,2), UIStage);
    }

    public void update(PanelRenderDataPacket constructionPanelData){
        actionPanel.update(constructionPanelData);
    }

    public void changeConstructionPanelButtons(PanelRenderDataPacket constructionPanelData, Stage UIStage){
        actionPanel.changeButtons(constructionPanelData, UIStage);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backgroundTexture, 0, 0);
    }
}
