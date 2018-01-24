package ua.syt0r;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.*;

/**
 * Created by Yaroslav on 2018/01/19.
 */

public class GameStage extends Stage {

    private static final int VIRTUAL_HEIGHT = 550, VIRTUAL_WIDTH = 300;

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;

    private Entity player;
    private HashMap<UUID,Entity> enemies;
    private HashMap<UUID,Entity> bullets;
    private List<UUID> toRemove;


    public GameStage(GdxGame gdxGame){

        this.spriteBatch = gdxGame.getSpriteBatch();

        enemies = new HashMap<UUID, Entity>();
        bullets = new HashMap<UUID, Entity>();
        toRemove = new ArrayList<UUID>();

        initPhysics();

        initCameraAndViewport();

        loadEntities();

        Gdx.input.setInputProcessor(new Input());

    }

    private void initPhysics(){

        world = new World(new Vector2(0,0),true);

        addWorldEdges(new Vector2((-10)/Const.scale,10/Const.scale),new Vector2((VIRTUAL_WIDTH+10)/Const.scale,10/Const.scale)); //bottom
        addWorldEdges(new Vector2(-10/Const.scale,-10/Const.scale),new Vector2(-10/Const.scale,(VIRTUAL_HEIGHT+10)/Const.scale)); //left
        addWorldEdges(new Vector2((VIRTUAL_WIDTH+10)/Const.scale,-10/Const.scale),new Vector2((VIRTUAL_WIDTH+10)/Const.scale,(VIRTUAL_HEIGHT+10)/Const.scale)); //right
        addWorldEdges(new Vector2(-10/Const.scale,(VIRTUAL_HEIGHT+10)/Const.scale),new Vector2((VIRTUAL_WIDTH+10)/Const.scale,(VIRTUAL_HEIGHT+10)/Const.scale)); //top

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {

                FixtureUserData a = (FixtureUserData)contact.getFixtureA().getUserData();
                FixtureUserData b = (FixtureUserData)contact.getFixtureB().getUserData();

                if(a.getObjectType() == FixtureUserData.WORLD_EDGE){
                    toRemove.add((UUID) b.getData());
                    System.out.println("edge collision");
                }
                else if(b.getObjectType()== FixtureUserData.WORLD_EDGE){
                    toRemove.add((UUID) a.getData());
                    System.out.println("edge collision");
                } else {
                    if(a.getData() != null && b.getData() != null){
                        toRemove.add((UUID)a.getData());
                        toRemove.add((UUID)b.getData());
                    }
                }

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }

        });

    }

    private void addWorldEdges(Vector2 a, Vector2 b){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,0);
        Body body = world.createBody(bodyDef);
        FixtureDef fixtureDef = new FixtureDef();
        EdgeShape shape = new EdgeShape();
        shape.set(a,b);
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new FixtureUserData(FixtureUserData.WORLD_EDGE,null));
        shape.dispose();
    }

    private void initCameraAndViewport(){

        camera = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        camera.setToOrtho(false,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        spriteBatch.setProjectionMatrix(camera.combined);
        camera.update();
        setViewport(new ExtendViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT,camera));

        //Box2d

        debugRenderer = new Box2DDebugRenderer();

    }

    private void loadEntities(){

        Texture texture = new Texture("player.png");

        player = new Entity(texture);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5/Const.scale,5/Const.scale);
        player.initPhysics(world, shape, FixtureUserData.PLAYER, 150/Const.scale, 100/Const.scale);


        Texture texture2 = new Texture("enemy.png");

        Entity enemy = new Entity(texture2);
        shape = new PolygonShape();
        shape.setAsBox(10/Const.scale,10/Const.scale);
        enemy.initPhysics(world, shape, FixtureUserData.ENEMY, 150/Const.scale, 480/Const.scale);
        enemies.put(enemy.getUuid(),enemy);

    }

    private void fire(){

        Entity bullet = new Entity(new Texture("bullet.png"));
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5/Const.scale);
        Vector2 playerPos = player.getBody().getPosition();
        bullet.initPhysics(world, circleShape, FixtureUserData.BULLET, playerPos.x, playerPos.y + 20/Const.scale);
        bullets.put(bullet.getUuid(),bullet);
        bullet.getBody().applyForce(new Vector2(0,0.1f),bullet.getBody().getWorldCenter(),true);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        world.step(delta,10,10);


        for (UUID uuid : toRemove){
            if (enemies.containsKey(uuid))
                world.destroyBody(enemies.remove(uuid).getBody());
            if (bullets.containsKey(uuid))
                world.destroyBody(bullets.remove(uuid).getBody());

        }

        toRemove.clear();
    }

    @Override
    public void draw() {
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();


        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        player.draw(spriteBatch);
        for (Entity enemy : enemies.values())
            enemy.draw(spriteBatch);
        for (Entity bullet : bullets.values())
            bullet.draw(spriteBatch);

        spriteBatch.end();

        //Draw debug physics
        debugMatrix = camera.combined.cpy();
        debugMatrix.scale(Const.scale,Const.scale,1f);
        debugRenderer.render(world, debugMatrix);
    }

    class Input implements InputProcessor {

        private Set<Integer> pressed;

        Input(){

            pressed = new HashSet<Integer>();

        }

        @Override
        public boolean keyDown(int keycode) {
            Vector2 playerVelocity = player.getBody().getLinearVelocity();
            switch (keycode){
                case com.badlogic.gdx.Input.Keys.LEFT:
                    player.getBody().setLinearVelocity(-1f,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                    player.getBody().setLinearVelocity(1f,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                    player.getBody().setLinearVelocity(playerVelocity.x,1f);
                    break;
                case com.badlogic.gdx.Input.Keys.DOWN:
                    player.getBody().setLinearVelocity(playerVelocity.x,-1f);
                    break;
                case com.badlogic.gdx.Input.Keys.Z:
                    fire();
                    break;
            }
            pressed.add(keycode);
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            Vector2 playerVelocity = player.getBody().getLinearVelocity();
            switch (keycode){
                case com.badlogic.gdx.Input.Keys.LEFT:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.RIGHT))
                        player.getBody().setLinearVelocity(1f,playerVelocity.y);
                    else
                        player.getBody().setLinearVelocity(0,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.LEFT))
                        player.getBody().setLinearVelocity(-1f,playerVelocity.y);
                    else
                        player.getBody().setLinearVelocity(0,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.DOWN))
                        player.getBody().setLinearVelocity(playerVelocity.x,-1f);
                    else
                        player.getBody().setLinearVelocity(playerVelocity.x,0);
                    break;
                case com.badlogic.gdx.Input.Keys.DOWN:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.UP))
                        player.getBody().setLinearVelocity(playerVelocity.x,1f);
                    else
                        player.getBody().setLinearVelocity(playerVelocity.x,0);
                    break;
            }
            pressed.remove(keycode);
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }
}
