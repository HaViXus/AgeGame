package com.agegame.missile;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.player.Action;
import com.agegame.units.Unit;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public abstract class Missile extends Actor {

    protected Map map;
    protected Vector2 target;
    protected Pixmap pixmap;
    protected Texture texture;
    protected Action.DomainType targetLine;
    protected Direction.direction direction;

    protected ArrayList<Rectangle> hitboxes;
    protected Vector2 position;
    protected Vector2 speed;
    protected float maxSpeed;
    protected boolean explosive;
    protected boolean destroyed;

    protected float damage;
    protected float knockback;

    protected boolean piercing;
    protected ArrayList<Unit> damagedUnits;

    public void init(Vector2 startPosition, Vector2 target, Map map, Stage gameStage, Action.DomainType targetLine,
                     Direction.direction direction){
        this.position = startPosition;
        this.target = target;
        this.map = map;
        this.targetLine = targetLine;
        this.direction = direction;
        gameStage.addActor(this);
        speed = new Vector2(0,0);
        destroyed = false;
        damagedUnits = new ArrayList<>();
        piercing = true;
        explosive = false;
    }

    abstract public void update(float delta);

    public void destroy(){
        dispose();
    }

    public void dispose(){
        pixmap.dispose();
        texture.dispose();
        this.remove();
    }

    public boolean isExplosive() { return explosive; }

    public boolean isDestroyed() { return destroyed; }

    public boolean isPiercing() { return piercing; }

    public float getDamage() { return damage; }

    public float getKnockback() { return knockback; }

    public Direction.direction getDirection() { return direction; }

    public Vector2 getPosition() { return position; }

    public ArrayList<Rectangle> getHitboxes() { return hitboxes; }

    public ArrayList<Unit> getDamagedUnits() { return damagedUnits; }

    public boolean isUnitDamagedByMissile(Unit unit){
        for(Unit unitFromList : damagedUnits){
            if(unitFromList == unit) return true;
        }
        return false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    protected void initHitboxes(){
        hitboxes = new ArrayList<>();
        Rectangle hitbox = new Rectangle(position.x, position.y, getWidth(), getHeight());
        hitboxes.add(hitbox);
    }

    protected void updateHitboxes(Vector2 offset) {
        for(Rectangle hitbox : hitboxes){
            hitbox.x += offset.x;
            hitbox.y += offset.y;
        }
    }
}
