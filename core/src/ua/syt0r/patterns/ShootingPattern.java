package ua.syt0r.patterns;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.screens.GameScreen;

public class ShootingPattern {

    private GameScreen screen;

    public ShootingPattern(GameScreen screen){
        this.screen = screen;
    }

    public boolean shoot(Enemy enemy, float time){ return false;}

    public GameScreen getScreen() {
        return screen;
    }
}
