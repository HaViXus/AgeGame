package com.agegame.units.land_units;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.player.Action;
import com.agegame.units.Unit;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class UgaBuga extends Actor implements Unit {
    private Map map;
    private Vector2 position;
    private Direction.direction direction;
    private Action.DomainType domain;
    private float speed;
    private Texture texture;
    private Pixmap pixmap;

    @Override
    public void init(Map map, Vector2 startPosition, Direction.direction direction, Action.DomainType domain) {
        this.map = map;
        this.direction = direction;
        this.domain = domain;
        position = startPosition;

        getUnitData();
    }

    //Get data from global class with information about unit
    private void getUnitData(){
        //TODO
        speed = 30;
        int width = 40;
        int height = 40;
        setBounds(position.x, position.y, width, height);
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        texture = new Texture(pixmap);

    }

    @Override
    public void move(float delta) {
        position.x -= speed * direction.getIntValue() * delta;
        setPosition(position.x, position.y);
    }

    @Override
    public void fight() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, position.x, position.y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
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

}
