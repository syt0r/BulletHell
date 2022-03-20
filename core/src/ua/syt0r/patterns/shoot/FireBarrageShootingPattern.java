package ua.syt0r.patterns.shoot;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.patterns.ShootingPattern;
import ua.syt0r.screens.GameLevelScreen;

public class FireBarrageShootingPattern extends ShootingPattern {

    float angleDelta = 0;

    public FireBarrageShootingPattern(GameLevelScreen screen) {
        super(screen);
    }

    @Override
    public boolean shoot(Enemy enemy, float delta) {

        if (updateTime(delta) * 1000  > 400){

            for (int i = 0; i <= 360; i = i + 360/12)
                getScreen().RadialShot(enemy.getX(),enemy.getY(),i+angleDelta,200f);

            angleDelta+=5;


            return true;

        }

        return false;

    }

}
