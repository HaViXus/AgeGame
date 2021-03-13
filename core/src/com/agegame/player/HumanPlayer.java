package com.agegame.player;

import com.agegame.Direction;
import com.agegame.request.RequestQueue;
import com.agegame.utils.GameEra;

public class HumanPlayer implements Player{

    private Direction.direction direction;
    private PlayerStats stats;
    private ActionController actionController;
    private RequestQueue requestQueue;

    @Override
    public void init() {
        requestQueue = new RequestQueue();
        actionController = new ActionController(requestQueue);
        stats = new PlayerStats();
        stats.gold = 350;
        stats.exp = 0;
        stats.era = GameEra.STONE_AGE;


    }

    @Override
    public void update() {
        actionController.updatePossibleActions(stats);
    }

    @Override
    public PlayerStats getStats() {
        return stats;
    }

    @Override
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    @Override
    public ActionController getActionController() {
        return actionController;
    }

    @Override
    public void setDirection(Direction.direction direction) {
        this.direction = direction;
    }

    @Override
    public Direction.direction getDirection() {
        return direction;
    }


}
