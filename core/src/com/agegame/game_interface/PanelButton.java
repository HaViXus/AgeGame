package com.agegame.game_interface;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Method;

public class PanelButton extends ButtonWithImage{

    public PanelButton(){
        super();
    }

    public PanelButton(Pixmap image, Vector2 position) {
        super(image, position);
    }

    public PanelButton(Pixmap image, Vector2 position, Runnable onClick) {
        super(image, position, onClick);
    }

    public void update(boolean disabled, float constructionTime){

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imageTexture, position.x, position.y);
    }


}
