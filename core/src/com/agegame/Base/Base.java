package com.agegame.Base;

import com.agegame.Direction;
import com.agegame.player.Action;
import com.agegame.player.Player;
import com.agegame.request.ConstructionRequestData;
import com.agegame.request.Request;
import com.agegame.tower.Tower;
import com.agegame.utils.PixmapModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public abstract class Base extends Actor {
    private int HP;
    private Texture texture;
    private Direction.direction baseDirection;
    private Vector2 position;
    private Vector2 baseSize = new Vector2(190, 190);
    private Player player;
    private ArrayList<Tower> towers;
    private Vector2 additionalTowersInitPos;

    public Base(Direction.direction baseDirection, Vector2 position, Player player){
        this.baseDirection = baseDirection;
        this.position = getCorrectPosition(position);
        this.player = player;

        additionalTowersInitPos = new Vector2(position.x + 50, position.y + 180);

        setBounds(position.x, position.y, baseSize.x, baseSize.y);
        createBase();
        initTowers();
    }

    public void createBase(){

        Pixmap backgroundPixmap;
        backgroundPixmap = new Pixmap((int) baseSize.x, (int) baseSize.y, Pixmap.Format.RGBA8888);
        backgroundPixmap.setColor(Color.WHITE);
        backgroundPixmap.fill();
        backgroundPixmap.setColor(Color.RED);
        backgroundPixmap.fillRectangle((int) baseSize.x/2,0, (int) baseSize.x/2,(int) baseSize.y);


        if(baseDirection == Direction.direction.RIGHT){
            backgroundPixmap = PixmapModifier.flipHorizontally(backgroundPixmap);
        }

        texture = new Texture( backgroundPixmap );
        backgroundPixmap.dispose();
    }

    private void initTowers(){
        towers = new ArrayList<>();
        Tower tower = new Tower();
        tower.init(0, new Vector2(position.x + 100, position.y + 70), baseDirection);
        towers.add(tower);
        player.getStats().towers = towers;
        System.out.println("INIT TOWERS: " +towers + " " + player.getStats().towers);
    }

    private void addTower(){
        int towersNumber = towers.size();
        Tower tower = new Tower();
        Vector2 towerPosition = new Vector2(additionalTowersInitPos.x, additionalTowersInitPos.y + 50 * (towersNumber-1));
        tower.init(towersNumber, towerPosition, baseDirection);
        towers.add(tower);
    }

    protected void handleRequests(){
        for(Request request: player.getRequestQueue().getConstructionRequests()){
            ConstructionRequestData requestData = (ConstructionRequestData) request.getRequestData();
            System.out.println("a: "+ requestData.domain + " " + requestData.requestName);
            if(requestData.domain == Action.DomainType.TOWER &&
                    ( requestData.requestName.equals("addTower") )){
                addTower();
            }
        }
    }

    abstract public void update(

    );

    private Vector2 getCorrectPosition(Vector2 position){
        if(baseDirection == Direction.direction.RIGHT){
            return new Vector2(position.x - baseSize.x, position.y);
        }
        else return position;
    }

    public void draw(Batch batch) {
        batch.draw(texture, position.x, position.y);
        for(Tower tower : towers) tower.draw(batch);
    }

    public float getSpawnXPosition(){
        return position.x + getWidth()/2 - baseDirection.getIntValue() * getWidth()/2;
    }

    public Direction.direction getDirection() { return baseDirection; }

    public ArrayList<Tower> getTowers(){
        return towers;
    }


}
