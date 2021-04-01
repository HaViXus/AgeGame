package com.agegame.missile;

import com.agegame.Direction;
import com.agegame.map.Map;
import com.agegame.units.Unit;
import com.agegame.units.UnitsController;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

public class MissilesController {
    private Map map;
    public ArrayList<Missile> missiles;
    private ArrayList<Missile> leftMissiles;
    private ArrayList<Missile> rightMissiles;
    private UnitsController unitsController;

    public void init(UnitsController unitsController){
        this.unitsController = unitsController;
        unitsController.setMissilesArray(missiles);
        missiles = new ArrayList<>();
        leftMissiles = new ArrayList<>();
        rightMissiles = new ArrayList<>();
    }

    public void update(float delta){
        sortMissiles();
        handleCollisionWithUnits();
        for(Missile missile : missiles){
            missile.update(delta);
        }

        handleDestroyedMissiles();
    }

    private void sortMissiles(){
        leftMissiles.clear();
        rightMissiles.clear();
        for(Missile missile: missiles){
            if(missile.getDirection() == Direction.direction.LEFT){
                leftMissiles.add(missile);
            }
            else{
                rightMissiles.add(missile);
            }
        }
    }

    private void handleCollisionWithUnits(){
        handleOneSideCollisionWithUnits(leftMissiles, unitsController.getRightUnits());
        handleOneSideCollisionWithUnits(rightMissiles, unitsController.getLeftUnits());
    }

    private void handleOneSideCollisionWithUnits(ArrayList<Missile> missiles, ArrayList<Unit> units){
        for(Missile missile: missiles){
            boolean collision = false;
            for(Unit unit: units){
                float distanceX = Math.abs( missile.getPosition().x - unit.getPosition().x );
                if(distanceX < 400 && !unit.isDead()){
                    for(Rectangle unitHitbox: unit.getHitboxes()) {
                        collision |= Intersector.overlaps(missile.getHitboxes().get(0), unitHitbox);
                        if(collision && !missile.isUnitDamagedByMissile(unit)){
                            unit.damage(missile.getDamage(), missile.getKnockback());
                            missile.getDamagedUnits().add(unit);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void handleDestroyedMissile(Missile missile){
        missile.destroy();
    }

    private void handleDestroyedMissiles(){
        Iterator<Missile> missileIterator = missiles.iterator();
        while(missileIterator.hasNext()){
            Missile missile = missileIterator.next();
            if(missile.isDestroyed()){
                handleDestroyedMissile(missile);
                missileIterator.remove();
            }
        }
    }
}
