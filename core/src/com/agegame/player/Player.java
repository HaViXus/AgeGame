package com.agegame.player;

import com.agegame.Direction;
import com.agegame.request.RequestQueue;

public interface Player {
    void init();

    void update();

    void updateRequestQueue();

    PlayerStats getStats();

    RequestQueue getRequestQueue();

    ActionController getActionController();

    void setDirection(Direction.direction direction);

    Direction.direction getDirection();
}
