package com.agegame.units;

import com.agegame.Direction;
import com.agegame.health_bar.UnitHealthBar;
import com.agegame.map.Map;
import com.agegame.map.MapLine;
import com.agegame.missile.Missile;
import com.agegame.player.Action;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public abstract class Unit extends Actor{
    public enum UnitState {MOVE, FIGHT, KNOCK_BACKED, WOUNDED, DEAD};
    protected Map map;
    protected Vector2 position;
    protected Direction.direction direction;
    protected Action.DomainType domain;
    protected float speed;
    protected UnitState state;
    protected Texture texture;
    protected Pixmap pixmap;

    protected ArrayList<Rectangle> hitboxes;
    protected ArrayList<Rectangle> fieldOfView;
    protected ArrayList<DamageField> damageFields;
    protected float actionTimer;
    protected boolean damageFieldCreated;
    protected UnitHealthBar healthBar;

    protected float HP;
    protected float damage;
    protected float knockback;
    protected float knockbackProtection;

    protected boolean isDead;

    protected ArrayList<Unit> enemyUnitsInRange;

    protected float takenDamage;
    protected float takenKnockback;

    protected ArrayList<Missile> createdMissiles;
    
    public void init(Map map, Vector2 startPosition, Direction.direction direction, Action.DomainType domain){
        this.map = map;
        this.direction = direction;
        this.domain = domain;
        position = startPosition;
        state = UnitState.MOVE;
        actionTimer = 0;
        damageFieldCreated = false;
        isDead = false;
        enemyUnitsInRange = new ArrayList<>();
        damageFields = new ArrayList<>();
        createdMissiles = new ArrayList<>();
    }

    public void update(float delta){
        actionTimer += delta;
        updateEnemiesInRange();
        handleTakenDamage();
        updateState();
        handleState(delta);

        healthBar.update(HP, delta);
    }

    abstract public void move(float delta);

    abstract public void fight();

    public void damage(float damage, float knockback) {
        takenDamage += damage;
        takenKnockback += knockback;
    }

    public void addCreatedMissilesToPool(ArrayList<Missile> missiles){
        if(createdMissiles != null && createdMissiles.size() > 0){
            for(Missile missile: createdMissiles) {
                missiles.add(missile);
            }
            createdMissiles.clear();
        }
    }

    protected void initHitboxes(){
        hitboxes = new ArrayList<>();
        Rectangle hitbox = new Rectangle(position.x, position.y, getWidth(), getHeight());
        hitboxes.add(hitbox);
    }

    protected void initFieldOfView(){
        fieldOfView = new ArrayList<>();
        float startX = position.x + getWidth() / 2 - direction.getIntValue() * getWidth() / 2;
        int fieldWidth = 50;
        int fieldHeight = 30;
        if(direction == Direction.direction.RIGHT) startX -= fieldWidth;
        Rectangle viewHitbox = new Rectangle(startX, position.y, fieldWidth, fieldHeight);
        fieldOfView.add(viewHitbox);
    }

    protected void initHealthBar(){
        Vector2 positionCenter = new Vector2(position.x + getWidth()/2, position.y + getHeight() + 5);
        Vector2 size = new Vector2(getWidth() + 10, 8);
        healthBar = new UnitHealthBar(positionCenter, size, HP, false);
    }

    protected void updateHealthBarPosition(){
        healthBar.getPosition().x = position.x + getWidth()/2;
        healthBar.getPosition().y = position.y + getHeight() + 5;
    }

    protected void updateHitboxes(float offset) {
        for(Rectangle hitbox : hitboxes){
            hitbox.x += offset;
        }
    }

    protected void handleTakenDamage(){
        HP -= takenDamage;
        takenDamage = 0;
        takenKnockback = 0;
    }

    protected void updateState(){
        if(state != UnitState.DEAD)
        {
            if(enemyUnitsInRange.size() > 0){
                changeState(UnitState.FIGHT);
            }
            else{
                changeState(UnitState.MOVE);
            }

            if(HP <= 0){
                changeState(UnitState.DEAD);
            }
        }
    }

    protected void handleState(float delta){
        switch (state){
            case MOVE:
                move(delta);
                break;
            case FIGHT:
                fight();
                break;
            case DEAD:
                death();
                break;
        }
    }

    protected void death(){
        if(actionTimer > 2){
            isDead = true;
        }
    }

    protected void updateFieldOfView(float offset){
        for(Rectangle view: fieldOfView){
            view.x += offset;
        }
    }

    protected void changeState(UnitState newState){
        if(state != newState){
            state = newState;
            actionTimer = 0;
            damageFieldCreated = false;
        }
    }

    protected void updateEnemiesInRange(){
        enemyUnitsInRange.clear();

        MapLine landLine = map.getLines().get(Action.DomainType.LAND_UNIT);
        for(Unit unit : landLine.units){
            if(unit != this && unit.getDirection() != direction && unit.getState() != UnitState.DEAD){
                float distanceX = Math.abs(unit.getPosition().x - position.x);
                if(distanceX < 400){
                    Rectangle enemyHitbox = unit.getHitboxes().get(0);
                    for(Rectangle fieldOfView : fieldOfView){
                        if(Intersector.overlaps(enemyHitbox, fieldOfView)){
                            enemyUnitsInRange.add(unit);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, position.x, position.y);
        healthBar.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose() {
        pixmap.dispose();
        texture.dispose();
    }

    public Actor getActor(){
        return this;
    }

    public Action.DomainType getDomain() {
        return domain;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Direction.direction getDirection() {
        return direction;
    }

    public ArrayList<Rectangle> getHitboxes() {
        return hitboxes;
    }

    public ArrayList<DamageField> getDamageFields() {
        return damageFields;
    }

    public UnitState getState() {
        return state;
    }

    public boolean isDead() {
        return isDead;
    }


}
