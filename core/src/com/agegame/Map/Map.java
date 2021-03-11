package com.agegame.Map;

import com.agegame.Base.Base;
import com.agegame.Direction;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Map {
    private Texture backgroundTexture;
    private MapParameters mapParameters;
    private Base[] bases;
    public Map(MapParameters mapParameters){
        this.mapParameters = mapParameters;
        createBackground();
        createBases();
    }

    private void createBackground(){
        int mapWidth = mapParameters.width;
        Pixmap backgroundPixmap;
        backgroundPixmap = new Pixmap(mapWidth, 180, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(Color.CYAN);
        backgroundPixmap.fill();
        backgroundPixmap.setColor(Color.BROWN);
        backgroundPixmap.fillRectangle(0,50, mapWidth,65);
        backgroundPixmap.setColor(Color.BLUE);
        backgroundPixmap.fillRectangle(0,115, mapWidth,50);
        backgroundPixmap.setColor(Color.LIGHT_GRAY);
        backgroundPixmap.fillRectangle(0,150, mapWidth,30);

        backgroundTexture = new Texture( backgroundPixmap );
        backgroundPixmap.dispose();
    }

    private void createBases(){
        bases = new Base[2];
        bases[0] = new Base(Direction.direction.LEFT, new Vector2(0,70));
        bases[1] = new Base(Direction.direction.RIGTH, new Vector2(mapParameters.width, 70));

    }

    public void draw(SpriteBatch batch){
        batch.draw(backgroundTexture, 0, 0);
        for(Base base: bases){
            base.draw(batch);
        }
    }


}
