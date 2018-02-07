package ua.syt0r.actors.entities;

import com.badlogic.gdx.math.Vector2;
import ua.syt0r.patterns.MovementPattern;
import ua.syt0r.patterns.ShootingPattern;

public class Enemy extends Entity {

    private MovementPattern movementPattern;
    private ShootingPattern shootingPattern;
    private float movementTime = 0f;
    private float fireTime = 0f;

    private int health;

    private Vector2 tmpPos;

    public Enemy(){

        super();
        tmpPos = new Vector2();

    }

    public void setMovementPattern(MovementPattern pattern){
        this.movementPattern = pattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }

    @Override
    public void update(float delta) {

        movementTime += delta;
        fireTime += delta;

        if (movementPattern != null){

            movementPattern.getPath().valueAt(tmpPos, movementTime % movementPattern.getTimeLength() / movementPattern.getTimeLength());
            setPosition(tmpPos.x,tmpPos.y);

        }

        if (shootingPattern != null){

            if (shootingPattern.shoot(this, fireTime)) {
                fireTime = 0f;
            }

        }

    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void damage(){}
}
