package com.agegame.units;

import com.agegame.Direction;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DamageField extends Rectangle {
    public Direction.direction direction;
    public float damage;
    public float knockback;

    public DamageField(Vector2 position, Vector2 size, Direction.direction direction, float damage, float knockback){
        super(position.x, position.y, size.x, size.y);
        this.damage = damage;
        this.direction = direction;
        this.knockback = knockback;
    }

    public DamageField(Rectangle rectangle, Direction.direction direction, float damage, float knockback){
        super(rectangle);
        this.damage = damage;
        this.direction = direction;
        this.knockback = knockback;
    }
}
