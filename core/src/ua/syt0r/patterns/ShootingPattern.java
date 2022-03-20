package ua.syt0r.patterns;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.screens.GameLevelScreen;

public class ShootingPattern {

    private GameLevelScreen screen;

    private float time = 0f;

    public ShootingPattern(GameLevelScreen screen){
        this.screen = screen;
    }

    public boolean shoot(Enemy enemy, float delta){ return false;}

    public GameLevelScreen getScreen() {
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
