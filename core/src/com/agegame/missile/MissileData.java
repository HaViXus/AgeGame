package com.agegame.missile;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.player.Action;
import com.badlogic.gdx.math.Vector2;

public class MissileData {
    public Vector2 startPosition;
    public Vector2 target;
    public Map map;
    public Action.DomainType targetLine;
    public Direction.direction direction;
    public boolean explosive;
    public float damage;
    public float knockback;
}
