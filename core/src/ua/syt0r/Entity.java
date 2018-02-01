package ua.syt0r;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.UUID;

public class Entity extends Actor {

    private UUID uuid;

    private int health;

    private Circle body;

    private Texture texture;

    private float speed;

    private Vector2 velocity;

    public Entity(Texture texture){
        uuid = UUID.randomUUID();
        this.texture = texture;
        velocity = new Vector2();
        speed = 1f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }

    public void draw(SpriteBatch spriteBatch){

        //spriteBatch.draw(texture,body.getPosition().x* Utils.scale-20,body.getPosition().y* Utils.scale-20,40,40);

    }

    public void move(){

        body.setX( body.x + speed * velocity.x);
        body.setY( body.y + speed * velocity.y);
        setX(body.x);
        setY(body.y);

    }


    public void setHealth(int health) {
        this.health = health;
    }
    public void setBody(Circle body) {
        this.body = body;
        setX(body.x);
        setY(body.y);
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public void setVelocity(Vector2 velocity) {
        setVelocity(velocity.x,velocity.y);
    }
    public void setVelocity(float x, float y){
        velocity.x = x;
        velocity.y = y;
    }

    public UUID getUuid() {
        return uuid;
    }
    public int getHealth() {
        return health;
    }
    public Circle getBody() {
        return body;
    }
    public float getSpeed() {
        return speed;
    }
    public Vector2 getVelocity() {
        return velocity;
    }

}