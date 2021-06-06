package com.agegame.tower;

import com.agegame.Direction;
import com.agegame.turrets.Turret;
import com.agegame.turrets.TurretSlot;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Tower {
    private TurretSlot turretSlot;
    private int ID;
    private Vector2 position;

    private Pixmap pixmap;
    private Texture texture;

    public void init(int ID, Vector2 position, Direction.direction direction){
        turretSlot = new TurretSlot(new Vector2(0,0), direction);
        this.ID = ID;
        this.position = position;

        initTexture();
    }

    private void initTexture(){
        pixmap = new Pixmap( 50, 50, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();

        texture = new Texture(pixmap);
    }

    public void update(){

    }

    public void draw(Batch batch) {
        //if(ID != 0){
            batch.draw(texture, position.x, position.y);
        //}
    }

    public void addTurret(Turret turret){
        turretSlot.turret = turret;
    }

    public void removeTurret(){

    }

    public TurretSlot getTurretSlot() {
        return turretSlot;
    }
}
