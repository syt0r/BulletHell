package ua.syt0r;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import java.util.UUID;

public class Entity {

    private UUID uuid;

    private int health;

    private Body body;

    private Texture texture;


    public Entity(Texture texture){
        uuid = UUID.randomUUID();
        this.texture = texture;
    }


    public void initPhysics(World world, Shape shape, int entityType, float xPos, float yPos){

        BodyDef bodyDef;
        FixtureDef fixtureDef;
        Fixture fixture;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(xPos, yPos);
        body = world.createBody(bodyDef);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureUserData(entityType,uuid));
        shape.dispose();

    }

    public void draw(SpriteBatch spriteBatch){

        spriteBatch.draw(texture,body.getPosition().x* Utils.scale-20,body.getPosition().y* Utils.scale-20,40,40);

    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public UUID getUuid() {
        return uuid;
    }
}