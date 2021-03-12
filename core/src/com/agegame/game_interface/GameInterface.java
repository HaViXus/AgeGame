package com.agegame.game_interface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameInterface {
    private InterfacePanel panel;
    private Stage UIStage;

    public GameInterface(){
        UIStage = new Stage(new FitViewport(320, 180));
        Vector2 screenSize = new Vector2(UIStage.getWidth(), UIStage.getHeight());
        panel = new InterfacePanel(screenSize);
        UIStage.addActor(panel);
    }

    public void update(float delta){
        UIStage.act(delta);
    }

    public void draw(SpriteBatch batch){
        UIStage.draw();
    }

    public Stage getStage(){
        return UIStage;
    }
}
