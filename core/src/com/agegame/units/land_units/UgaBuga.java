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
import com.badlogic.gdx.math.Vector2;


public class UgaBuga extends Unit {

    private Pixmap debug;
    private Texture debugT;


    @Override
    public void init(Map map, Vector2 startPosition, Direction.direction direction, Action.DomainType domain) {
        super.init(map, startPosition, direction, domain);
        getUnitData();
        initHealthBar();
        initHitboxes();
        initFieldOfView();

        debug = new Pixmap(50, 20, Pixmap.Format.RGB888);
        debug.setColor(Color.PINK);
        debug.fill();

        debugT = new Texture(debug);
    }

    private void getUnitData(){
        //TODO
        speed = 250;
        int width = 40;
        int height = 40;
        setBounds(position.x, position.y, width, height);

        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        texture = new Texture(pixmap);

        HP = 20;
        damage = 3;
        knockback = 2;
        knockbackProtection = 1;

    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
            damageFieldCreated = true;
            DamageField damageField = new DamageField(fieldOfView.get(0), direction, damage, knockback);
            damageFields.add(damageField);
        }

        if(actionTimer > 3){
            actionTimer = 0;
            damageFieldCreated = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        debugDraw(batch);
    }

    private void debugDraw(Batch batch){
        batch.draw(debugT, fieldOfView.get(0).x, fieldOfView.get(0).y);
    }

    @Override
    public void dispose() {
        super.dispose();
        debug.dispose();
        debugT.dispose();
    }
}
