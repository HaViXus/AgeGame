package com.agegame.units.land_units;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.missile.Missile;
import com.agegame.missile.RockMissile;
import com.agegame.player.Action;
import com.agegame.units.DamageField;
import com.agegame.units.Unit;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Catapult extends Unit {
    private boolean missileCreated;

    @Override
    public void init(Map map, Vector2 startPosition, Direction.direction direction, Action.DomainType domain) {
        super.init(map, startPosition, direction, domain);
        getUnitData();
        initHealthBar();
        initHitboxes();
        initFieldOfView();

        missileCreated = false;
    }

    private void getUnitData(){
        //TODO
        speed = 100;
        int width = 80;
        int height = 30;
        setBounds(position.x, position.y, width, height);

        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.TAN);
        pixmap.fill();
        texture = new Texture(pixmap);

        HP = 20;
        damage = 0;
        knockback = 10;
        knockbackProtection = 100;

    }

    @Override
    public void move(float delta) {
        float positionOffset = -speed * direction.getIntValue() * delta;
        position.x += positionOffset;
        setPosition(position.x, position.y);
        updateHitboxes(positionOffset);
        updateFieldOfView(positionOffset);
        updateHealthBarPosition();
    }

    @Override
    protected void changeState(UnitState newState) {
        super.changeState(newState);
        if(state != newState){
            state = newState;
            actionTimer = 0;
            damageFieldCreated = false;
            missileCreated = false;
        }
    }

    @Override
    public void fight() {
        if( actionTimer > 0.5 && !missileCreated){
            shootMissileAtTheEnemy();
        }

        if(actionTimer > 3.5){
            actionTimer = 0;
            missileCreated = false;
        }
    }

    @Override
    protected void initFieldOfView() {
        fieldOfView = new ArrayList<>();
        float startX = position.x + getWidth() / 2 - direction.getIntValue() * getWidth() / 2;
        int fieldWidth = 450;
        int fieldHeight = 200;
        if(direction == Direction.direction.RIGHT) startX -= fieldWidth;
        Rectangle viewHitbox = new Rectangle(startX, position.y, fieldWidth, fieldHeight);
        fieldOfView.add(viewHitbox);

    }

    public void shootMissileAtTheEnemy(){
        RockMissile rockMissile = new RockMissile();
        Vector2 startPositionForMissile = new Vector2();
        startPositionForMissile.x = position.x - direction.getIntValue() * getWidth()/2;
        startPositionForMissile.y = position.y + getHeight();
        rockMissile.init(startPositionForMissile,
                enemyUnitsInRange.get(0).getPosition(),
                map,
                getStage(),
                enemyUnitsInRange.get(0).getDomain(),
                direction);

        createdMissiles.add(rockMissile);

        missileCreated = true;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
