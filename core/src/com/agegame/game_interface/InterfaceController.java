package com.agegame.game_interface;

import com.agegame.player.Player;

public class InterfaceController {

    private GameInterface gameInterface;
    public InterfaceController(){
        initInterfaceController();
    }

    private void initInterfaceController(){
        gameInterface = new GameInterface();
    }

    public void update(float delta, Player player){
        gameInterface.update(delta);
    }

    public GameInterface getGameInterface() {
        return gameInterface;
    }
}
