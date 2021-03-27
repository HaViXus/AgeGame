package com.agegame.units;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.player.Action;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public interface Unit {
    enum UnitState {MOVE, FIGHT, KNOCK_BACKED, WOUNDED, DEAD};
    
    void init(Map map, Vector2 startPosition, Direction.direction direction, Action.DomainType domain);

    void update(float delta);

    void move(float delta);

    void fight();

    void damage(float damage, float knockback);

    void dispose();

    Actor getActor();

    Action.DomainType getDomain();

    Vector2 getPosition();

    Direction.direction getDirection();

    ArrayList<Rectangle> getHitboxes();

    ArrayList<DamageField> getDamageFields();

    UnitState getState();

    boolean isDead();


}
