package ua.syt0r.actors.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.syt0r.patterns.EntityMovementPattern;

public class Entity extends Actor {

    private Vector2 velocity;
    private float speed;

    private TextureRegion texture;

    private Circle body;

    private int health = 1;
    private HealthChangeListener healthChangeListener;

    private EntityMovementPattern entityMovementPattern;

    public Entity() {
        velocity = new Vector2();
        speed = 1f;
        body = new Circle();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX() - getWidth() / 2, getY() - getHeight() / 2, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        body.setPosition(x, y);
    }

    public void update(float delta) {

        entityMovementPattern.update(this, delta);

    }


    public void damage() {

        health--;
        if (healthChangeListener != null)
            healthChangeListener.onHealthChanged();

    }


    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x, velocity.y);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Circle getBody() {
        return body;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public EntityMovementPattern getEntityMovementPattern() {
        return entityMovementPattern;
    }

    public void setEntityMovementPattern(EntityMovementPattern entityMovementPattern) {
        this.entityMovementPattern = entityMovementPattern;
    }

    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    public void setTexture(TextureRegion texture) {
        this.texture = texture;
    }

    public void setBody(float x, float y, float radius) {
        body.set(x, y, radius);
        setX(body.x);
        setY(body.y);
    }

    public void setHealthChangeListener(HealthChangeListener healthChangeListener) {
        this.healthChangeListener = healthChangeListener;
    }

    public interface HealthChangeListener {
        void onHealthChanged();
    }

}
