package ua.syt0r.patterns;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.screens.GameScreen;

public class ShootingPattern {

    private GameScreen screen;

    private float time = 0f;

    public ShootingPattern(GameScreen screen){
        this.screen = screen;
    }

    public boolean shoot(Enemy enemy, float delta){ return false;}

    public GameScreen getScreen() {
        return screen;
    }

    public void resetTime(){
        time = 0f;
    }

    public float getTime() {
        return time;
    }

    public float updateTime(float delta){
        time += delta;
        return time;
    }

}
