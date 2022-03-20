package ua.syt0r.patterns.shoot;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.patterns.ShootingPattern;
import ua.syt0r.screens.GameLevelScreen;

public class SimpleShootingPattern extends ShootingPattern {

    public SimpleShootingPattern(GameLevelScreen screen) {
        super(screen);
    }

    @Override
    public boolean shoot(Enemy enemy, float delta) {

        if (updateTime(delta) * 1000  > 400){

            getScreen().LinearShot(enemy.getX(),enemy.getY(),400f);
            return true;

        }

        return false;

    }

}
