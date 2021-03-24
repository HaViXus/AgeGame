package com.agegame.map;

import com.agegame.Base.Base;
import com.agegame.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Map {
    private Texture backgroundTexture;
    private MapParameters mapParameters;
    private Base[] bases;
    private Stage gameStage;
    public Map(MapParameters mapParameters, Stage gameStage){
        this.mapParameters = mapParameters;
        this.gameStage = gameStage;
        createBackground();
        createBases();
    }

    private void createBackground(){
        int mapWidth = mapParameters.width;
        Pixmap backgroundPixmap;
        backgroundPixmap = new Pixmap(mapWidth, 480, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(Color.CYAN);
        backgroundPixmap.fill();
        backgroundPixmap.setColor(Color.BROWN);
        backgroundPixmap.fillRectangle(0,80, mapWidth,190);
        backgroundPixmap.setColor(Color.BLUE);
        backgroundPixmap.fillRectangle(0,270, mapWidth,190);
        backgroundPixmap.setColor(Color.LIGHT_GRAY);
        backgroundPixmap.fillRectangle(0,430, mapWidth,160);

        backgroundTexture = new Texture( backgroundPixmap );
        backgroundPixmap.dispose();
    }

    private void createBases(){
        bases = new Base[2];
        bases[0] = new Base(Direction.direction.LEFT, new Vector2(0,200));
        bases[1] = new Base(Direction.direction.RIGTH, new Vector2(mapParameters.width, 200));

    }

    public void draw(){
        gameStage.getBatch().draw(backgroundTexture, 0, 0);
        for(Base base: bases){
            base.draw(gameStage.getBatch());
        }
    }


}
