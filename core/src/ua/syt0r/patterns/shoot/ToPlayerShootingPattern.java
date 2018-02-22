package ua.syt0r.patterns.shoot;

import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.actors.entities.Player;
import ua.syt0r.patterns.ShootingPattern;
import ua.syt0r.screens.GameScreen;

/**
 * Created by Yaroslav on 2018/02/18.
 */
public class ToPlayerShootingPattern extends ShootingPattern {

    private Player player;

    public ToPlayerShootingPattern(GameScreen screen) {
        super(screen);
        player = screen.getGameStage().getPlayer();
    }

    @Override
    public boolean shoot(Enemy enemy, float delta) {

        if (updateTime(delta) * 1000  > 400){

            getScreen().DirectionalShot(enemy.getX(),enemy.getY(),400f,player.getX(),player.getY());
            return true;

        }

        return false;

    }
}