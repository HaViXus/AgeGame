package com.agegame.game_interface;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ButtonWithImage extends Actor {
    public Pixmap imagePixmap;
    public Texture imageTexture;
    public Vector2 position;

    public ButtonWithImage(){ }

    public ButtonWithImage(Pixmap image, Vector2 position){
        super();
        this.imagePixmap = image;
        this.position = position;
        imageTexture = new Texture(this.imagePixmap);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(imageTexture, position.x, position.y);
    }
}
