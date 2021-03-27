package com.agegame.units;

import com.agegame.Direction;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DamageField extends Rectangle {
    public Direction.direction direction;
    public float damage;
    public float knockback;
    public boolean areaDamage;
    public boolean isActive;

    public DamageField(Vector2 position, Vector2 size, Direction.direction direction, float damage, float knockback, boolean areaDamage){
        super(position.x, position.y, size.x, size.y);
        this.damage = damage;
        this.direction = direction;
        this.knockback = knockback;
        this.areaDamage = areaDamage;
        isActive = true;
    }

    public DamageField(Rectangle rectangle, Direction.direction direction, float damage, float knockback, boolean areaDamage){
        super(rectangle);
        this.damage = damage;
        this.direction = direction;
        this.knockback = knockback;
        this.areaDamage = areaDamage;
        isActive = true;
    }

    public DamageField(Vector2 position, Vector2 size, Direction.direction direction, float damage, float knockback){
        this(position, size, direction, damage, knockback, false);
    }

    public DamageField(Rectangle rectangle, Direction.direction direction, float damage, float knockback){
        this(rectangle, direction, damage, knockback, false);
    }
}
