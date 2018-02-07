package ua.syt0r.patterns;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.screens.GameScreen;

public class SimpleShootingPattern extends ShootingPattern {

    public SimpleShootingPattern(GameScreen screen) {
        super(screen);
    }

    @Override
    public boolean shoot(Enemy enemy, float time) {

        if (time * 1000  > 400){

            getScreen().LinearShot(enemy.getX(),enemy.getY(),400f);
            return true;

        }

        return false;

    }

}
