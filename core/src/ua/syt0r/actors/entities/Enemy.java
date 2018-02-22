package ua.syt0r.actors.entities;

import ua.syt0r.patterns.MovementPattern;
import ua.syt0r.patterns.ShootingPattern;

public class Enemy extends Entity {

    private MovementPattern movementPattern;
    private ShootingPattern shootingPattern;

    private int health = 1;

    public Enemy(){
        super();
    }

    public void setMovementPattern(MovementPattern pattern){
        this.movementPattern = pattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    @Override
    public void update(float delta) {

        if (movementPattern != null){

            if (!movementPattern.move(this, delta))
                movementPattern.reserTime();

        }

        if (shootingPattern != null){

            if (shootingPattern.shoot(this, delta)) {
                shootingPattern.resetTime();
            }

        }

    }

    @Override
    public void damage(){
        health--;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }


    public boolean isAlive(){
        return health !=0 ;
    }
}
