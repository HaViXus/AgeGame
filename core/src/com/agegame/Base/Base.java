package com.agegame.Base;

import com.agegame.Direction;
import com.agegame.utils.PixmapModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Base extends Actor {
    private int HP;
    private Texture texture;
    private Direction.direction baseDirection;
    private Vector2 position;
    private Vector2 baseSize = new Vector2(190, 190);

    public Base(Direction.direction baseDirection, Vector2 position){
        this.baseDirection = baseDirection;
        this.position = getCorrectPosition(position);
        setBounds(position.x, position.y, baseSize.x, baseSize.y);

        createBase();
    }

    public void createBase(){

        Pixmap backgroundPixmap;
        backgroundPixmap = new Pixmap((int) baseSize.x, (int) baseSize.y, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(Color.WHITE);
        backgroundPixmap.fill();
        backgroundPixmap.setColor(Color.RED);
        backgroundPixmap.fillRectangle((int) baseSize.x/2,0, (int) baseSize.x/2,(int) baseSize.y);


        if(baseDirection == Direction.direction.RIGHT){
            backgroundPixmap = PixmapModifier.flipHorizontally(backgroundPixmap);
        }

        texture = new Texture( backgroundPixmap );
        backgroundPixmap.dispose();
    }

    private Vector2 getCorrectPosition(Vector2 position){
        if(baseDirection == Direction.direction.RIGHT){
            return new Vector2(position.x - baseSize.x, position.y);
        }
        else return position;
    }

    public void draw(Batch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public float getSpawnXPosition(){
        return position.x + getWidth()/2 - baseDirection.getIntValue() * getWidth()/2;
    }

    public Direction.direction getDirection() { return baseDirection; }


}
