package com.agegame.turrets;

import com.agegame.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;


public class TurretSlot {
    public Turret turret;
    public boolean disabled;
    private Direction.direction direction;
    private Texture texture;
    private Pixmap pixmap;
    private static final int slotSize = 16;
    private Vector2 position;

    public TurretSlot(Vector2 position, Direction.direction direction) {
        disabled = true;
        turret = null;
        this.direction = direction;
        this.position = position;

        Pixmap backgroundPixmap;
        backgroundPixmap = new Pixmap(slotSize, slotSize, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(Color.GOLDENROD);
        backgroundPixmap.fill();

        texture = new Texture( backgroundPixmap );

    }

    public void draw(Batch batch) { batch.draw(texture, position.x, position.y); }
}
