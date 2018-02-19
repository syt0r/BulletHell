package ua.syt0r.levels;

import ua.syt0r.actors.entities.Boss;
import ua.syt0r.actors.entities.Enemy;


public interface LevelInterface {

    void LinearShot(float x, float y, float speed);

    void RadialShot(float x, float y, float angle, float speed);

    void DirectionalShot(float x, float y, float speed, float targetX, float targetY);

    void addEnemy(Enemy enemy, int spawnFrame);

    void setBoss(Boss boss);
}
