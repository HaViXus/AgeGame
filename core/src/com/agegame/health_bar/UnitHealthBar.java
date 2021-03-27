package com.agegame.health_bar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class UnitHealthBar {
    private float maxValue;
    private float value;
    private boolean isVisible;
    private float changeTimer;
    private boolean alwaysVisible;
    private Vector2 positionCenter;
    private Vector2 size;

    private Pixmap pixmap;
    private Texture texture;

    public UnitHealthBar(Vector2 positionCenter, Vector2 size, float maxValue, boolean alwaysVisible){
        this.maxValue = maxValue;
        this.alwaysVisible = alwaysVisible;
        this.positionCenter = positionCenter;
        this.size = size;
        isVisible = true;
        value = maxValue;
        changeTimer = 0;

        initTexture();
        updateTexture();
    }

    public void update(float value, float delta){
        changeTimer += delta;
        if(this.value != value){
            changeTimer = 0;
            updateTexture();
        }

        if(!alwaysVisible && isVisible){
            if(changeTimer > 5){
                isVisible = false;
            }
        }
    }

    private void initTexture(){
        pixmap = new Pixmap((int)size.x, (int)size.y, Pixmap.Format.RGB888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        texture = new Texture(pixmap);
    }

    private void updateTexture(){
        float valuePercentage = value / maxValue;
        int valueWidth = (int) Math.floor( size.x * valuePercentage ) - 2;
        int valueHeight = (int) size.y - 2;

        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(1,1,valueWidth, valueHeight);

        texture.draw(pixmap,0,0);
    }

    public void draw(Batch batch){
        if(isVisible) {
            float drawPositionX = positionCenter.x - size.x/2f;
            float drawPositionY = positionCenter.y - size.y/2f;

            batch.draw(texture, drawPositionX, drawPositionY);
        }
    }

    public Vector2 getPosition(){ return positionCenter; }

}
