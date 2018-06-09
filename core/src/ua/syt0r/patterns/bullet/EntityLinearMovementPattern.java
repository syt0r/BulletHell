package ua.syt0r.patterns.bullet;

import com.badlogic.gdx.math.Vector2;
import ua.syt0r.actors.entities.Entity;
import ua.syt0r.patterns.EntityMovementPattern;

public class EntityLinearMovementPattern extends EntityMovementPattern {

    @Override
    public void update(Entity entity, float dt) {
        Vector2 velocity = entity.getVelocity();
        float speed = entity.getSpeed();
        entity.moveBy(velocity.x * speed * dt, velocity.y * speed * dt);
        entity.getBody().setPosition(entity.getX(),entity.getY());
    }

}
