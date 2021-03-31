package com.agegame.units.land_units;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.player.Action;
import com.agegame.units.DamageField;
import com.agegame.units.Unit;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Slingshot extends Unit {

    @Override
    public void init(Map map, Vector2 startPosition, Direction.direction direction, Action.DomainType domain) {
        super.init(map, startPosition, direction, domain);
        getUnitData();
        initHealthBar();
        initHitboxes();
        initFieldOfView();
    }

    private void getUnitData(){
        //TODO
        speed = 300;
        int width = 30;
        int height = 40;
        setBounds(position.x, position.y, width, height);

        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GOLDENROD);
        pixmap.fill();
        texture = new Texture(pixmap);

        HP = 12;
        damage = 2;
        knockback = 1;
        knockbackProtection = 0;

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
    public void fight() {
        if( actionTimer > 0.5 && !damageFieldCreated){
            shootTheEnemy();
        }

        if(actionTimer > 2.5){
            actionTimer = 0;
            damageFieldCreated = false;
        }
    }

    @Override
    protected void initFieldOfView() {
        fieldOfView = new ArrayList<>();
        float startX = position.x + getWidth() / 2 - direction.getIntValue() * getWidth() / 2;
        int fieldWidth = 150;
        int fieldHeight = 200;
        if(direction == Direction.direction.RIGHT) startX -= fieldWidth;
        Rectangle viewHitbox = new Rectangle(startX, position.y, fieldWidth, fieldHeight);
        fieldOfView.add(viewHitbox);

    }

    public void shootTheEnemy(){
        Rectangle target = enemyUnitsInRange.get(0).getHitboxes().get(0);
        damageFieldCreated = true;
        DamageField damageField = new DamageField(target, direction, damage, knockback);
        damageFields.add(damageField);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
