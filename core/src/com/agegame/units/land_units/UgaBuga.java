package com.agegame.units.land_units;

import com.agegame.Direction;
import com.agegame.health_bar.UnitHealthBar;
import com.agegame.map.Map;
import com.agegame.map.MapLine;
import com.agegame.player.Action;
import com.agegame.units.DamageField;
import com.agegame.units.Unit;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public class UgaBuga extends Actor implements Unit {
    private Map map;
    private Vector2 position;
    private Direction.direction direction;
    private Action.DomainType domain;
    private float speed;
    private UnitState state;
    private Texture texture;
    private Pixmap pixmap;
    private ArrayList<Rectangle> hitboxes;
    private ArrayList<Rectangle> fieldOfView;
    private ArrayList<DamageField> damageFields;
    private float actionTimer;
    private boolean damageFieldCreated;
    private UnitHealthBar healthBar;

    private float HP;
    private float damage;
    private float knockback;
    private float knockbackProtection;

    private boolean isDead;

    private ArrayList<Unit> enemyUnitsInRange;

    private float takenDamage;
    private float takenKnockback;

    private Pixmap debug;
    private Texture debugT;


    @Override
    public void init(Map map, Vector2 startPosition, Direction.direction direction, Action.DomainType domain) {
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

        getUnitData();
        initHitboxes();
        initFieldOfView();
        initHealthBar();

        debug = new Pixmap(50, 20, Pixmap.Format.RGB888);
        debug.setColor(Color.PINK);
        debug.fill();

        debugT = new Texture(debug);
    }

    //Get data from global class with information about unit
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

    private void initHealthBar(){
        Vector2 positionCenter = new Vector2(position.x + getWidth()/2, position.y + getHeight() + 5);
        Vector2 size = new Vector2(getWidth() + 10, 8);
        healthBar = new UnitHealthBar(positionCenter, size, HP, false);
    }

    private void updateHealthBarPosition(){
        healthBar.getPosition().x = position.x + getWidth()/2;
        healthBar.getPosition().y = position.y + getHeight() + 5;
    }

    private void changeState(UnitState newState){
        if(state != newState){
            state = newState;
            actionTimer = 0;
            damageFieldCreated = false;
        }
    }

    @Override
    public void update(float delta) {
        actionTimer += delta;
        updateEnemiesInRange();
        handleTakenDamage();
        updateState();
        handleState(delta);

        healthBar.update(HP, delta);
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

    private void updateState(){
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

    private void handleState(float delta){
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

    private void updateEnemiesInRange(){
        enemyUnitsInRange.clear();

        MapLine landLine = map.getLines().get(Action.DomainType.LAND_UNIT);
        for(Unit unit : landLine.units){
            if(unit != this && unit.getDirection() != direction && unit.getState() != UnitState.DEAD){
                float distanceX = Math.abs(unit.getPosition().x - position.x);
                if(distanceX < 100){
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

    private void initHitboxes(){
        hitboxes = new ArrayList<>();
        Rectangle hitbox = new Rectangle(position.x, position.y, getWidth(), getHeight());
        hitboxes.add(hitbox);
    }

    private void updateHitboxes(float offset) {
        for(Rectangle hitbox : hitboxes){
            hitbox.x += offset;
        }
    }

    private void initFieldOfView(){
        fieldOfView = new ArrayList<>();
        float startX = position.x + getWidth() / 2 - direction.getIntValue() * getWidth() / 2;
        int fieldWidth = 50;
        int fieldHeight = 30;
        if(direction == Direction.direction.RIGHT) startX -= fieldWidth;
        Rectangle viewHitbox = new Rectangle(startX, position.y, fieldWidth, fieldHeight);
        fieldOfView.add(viewHitbox);
    }

    private void updateFieldOfView(float offset){
        for(Rectangle view: fieldOfView){
            view.x += offset;
        }
    }

    private void death(){
        if(actionTimer > 2){
            isDead = true;
        }
    }

    private void handleTakenDamage(){
        HP -= takenDamage;

        takenDamage = 0;
        takenKnockback = 0;
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
    public void damage(float damage, float knockback) {
        takenDamage += damage;
        takenKnockback += knockback;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, position.x, position.y);
        healthBar.draw(batch);
        debugDraw(batch);
    }

    private void debugDraw(Batch batch){
        batch.draw(debugT, fieldOfView.get(0).x, fieldOfView.get(0).y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {
        pixmap.dispose();
        texture.dispose();
        debug.dispose();
        debugT.dispose();
    }

    @Override
    public Actor getActor(){
        return this;
    }

    @Override
    public Action.DomainType getDomain() {
        return domain;
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Direction.direction getDirection() {
        return direction;
    }

    @Override
    public ArrayList<Rectangle> getHitboxes() {
        return hitboxes;
    }

    @Override
    public ArrayList<DamageField> getDamageFields() {
        return damageFields;
    }

    @Override
    public UnitState getState() {
        return state;
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

}
