package com.agegame.map;

import com.agegame.Base.Base;
import com.agegame.Base.DefaultBase;
import com.agegame.Direction;
import com.agegame.player.Action;
import com.agegame.player.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;

public class Map {
    private Texture backgroundTexture;
    private MapParameters mapParameters;
    private Base[] bases;
    private Stage gameStage;
    private HashMap<Action.DomainType, MapLine> lines;
    public Map(MapParameters mapParameters, Stage gameStage, Player[] players){
        this.mapParameters = mapParameters;
        this.gameStage = gameStage;
        createBackground();
        createBases(players);
        createLines();
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

    private void createBases(Player[] players){
        bases = new Base[2];
        bases[0] = new DefaultBase(Direction.direction.LEFT, new Vector2(0,200), players[0]);
        bases[1] = new DefaultBase(Direction.direction.RIGHT, new Vector2(mapParameters.width, 200), players[1]);

    }

    private void createLines(){
        lines = new HashMap<>();
        lines.put(Action.DomainType.LAND_UNIT, new MapLine(200));
    }

    public void update(){
        updateBases();
    }

    private void updateBases(){
        for(Base base : bases){
            base.update();
        }
    }

    public void draw(){
        gameStage.getBatch().draw(backgroundTexture, 0, 0);
        for(Base base: bases){
            base.draw(gameStage.getBatch());
        }
    }

    public Base[] getBases(){ return bases; }

    public Base getBase(Direction.direction direction){
        for(Base base : bases){
            if(base.getDirection() == direction) return base;
        }
        return null;
    }

    public HashMap<Action.DomainType, MapLine> getLines() { return lines; }

    public MapParameters getMapParameters(){ return mapParameters; }


}
