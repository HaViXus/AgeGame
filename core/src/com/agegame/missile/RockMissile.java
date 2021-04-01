package com.agegame.missile;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.player.Action;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class RockMissile extends Missile{
    private final float gravity = 146.4f;
    private float angle;
    private Vector2 offsetVector;

    @Override
    public void init(Vector2 startPosition, Vector2 target, Map map, Stage gameStage,
                     Action.DomainType targetLine, Direction.direction direction) {
        super.init(startPosition, target, map, gameStage, targetLine, direction);
        getUnitData();
        initHitboxes();
        calculateAngle();
        initValuesOfSpeedVector();

        offsetVector = new Vector2();

    }

    private void getUnitData(){
        //TODO
        maxSpeed = 250;
        int width = 10;
        int height = 10;
        setBounds(position.x, position.y, width, height);

        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.SLATE);
        pixmap.fill();
        texture = new Texture(pixmap);

        damage = 5;
        knockback = 100;
    }

    private void calculateAngle(){
        float distanceToTarget = target.x - position.x;
        float distanceSin = (float) (gravity * distanceToTarget / (Math.pow(maxSpeed, 2)));
        if(distanceSin > 1) distanceSin = 0.99f;
        angle = (float) ( 0.5 * Math.asin(distanceSin));
    }

    private void initValuesOfSpeedVector(){
        speed.x = (float) Math.cos(angle) * maxSpeed;
        speed.y = (float) Math.sin(angle) * maxSpeed;

    }

    @Override
    public void update(float delta) {
        verifyIsDestroyed();
        if(!destroyed){
            move(delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, position.x, position.y);
    }

    private void move(float delta){
        speed.y -= gravity * delta;
        offsetVector.x = speed.x * delta;
        offsetVector.y  = speed.y * delta;
        position.x += offsetVector.x;
        position.y += offsetVector.y;
        updateHitboxes(offsetVector);
    }

    private void verifyIsDestroyed(){
           if(targetLine == Action.DomainType.LAND_UNIT || targetLine == Action.DomainType.AIR_UNIT){
               float targetLinePositionY = map.getLines().get(Action.DomainType.LAND_UNIT).getPositionY();
               if(position.y < targetLinePositionY){
                   destroyed = true;
               }
           }
           else{
               float targetLinePositionY = map.getLines().get(Action.DomainType.WATER_UNIT).getPositionY();
               if(position.y < targetLinePositionY){
                   destroyed = true;
               }
           }
    }
}
