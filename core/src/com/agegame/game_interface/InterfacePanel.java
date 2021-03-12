package com.agegame.game_interface;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class InterfacePanel extends Actor {
    private Texture backgroundTexture;

    public InterfacePanel(Vector2 screenSize){
        cretePanelBackground(screenSize);
    }

    private void cretePanelBackground(Vector2 screenSize){
        Pixmap panelBackground = new Pixmap((int)screenSize.x, 30, Pixmap.Format.RGBA8888);
        panelBackground.setColor(Color.ORANGE);
        panelBackground.fill();

        backgroundTexture = new Texture(panelBackground);
        panelBackground.dispose();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(backgroundTexture, 0, 0);
    }
}
