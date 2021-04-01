package com.agegame.player;

import com.agegame.Direction;
import com.agegame.game_data.GameData;
import com.agegame.request.RequestQueue;

public class HumanPlayer implements Player{

    private Direction.direction direction;
    private PlayerStats stats;
    private ActionController actionController;
    private RequestQueue requestQueue;

    @Override
    public void init() {
        requestQueue = new RequestQueue();
        stats = new PlayerStats();
        stats.gold = 1000;
        stats.exp = 0;
        stats.era = GameData.gameEras.get(0);
        actionController = new ActionController(stats);


    }

    private void initStats(){

    }

    @Override
    public void update() {
        actionController.updateActions(requestQueue);
    }

    @Override
    public void updateRequestQueue() {
        requestQueue.clear();
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
