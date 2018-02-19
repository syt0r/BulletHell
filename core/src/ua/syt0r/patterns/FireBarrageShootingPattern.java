package ua.syt0r.patterns;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.screens.GameScreen;

public class FireBarrageShootingPattern extends ShootingPattern {

    float angleDelta = 0;

    public FireBarrageShootingPattern(GameScreen screen) {
        super(screen);
    }

    @Override
    public boolean shoot(Enemy enemy, float time) {

        if (time * 1000  > 400){

            for (int i = 0; i <= 360; i = i + 360/12)
                getScreen().RadialShot(enemy.getX(),enemy.getY(),i+angleDelta,200f);

            angleDelta+=5;


            return true;

        }

        return false;

    }

}
