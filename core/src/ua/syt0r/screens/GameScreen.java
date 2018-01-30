package ua.syt0r.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.Entity;
import ua.syt0r.FixtureUserData;
import ua.syt0r.GdxGame;
import ua.syt0r.Utils;

import java.util.*;

/**
 * Created by Yaroslav on 2018/01/19.
 */

public class GameScreen implements Screen {

    //UI

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;
    private ua.syt0r.actors.DStickActor dStickActor;
    private ua.syt0r.actors.FireButtonActor fireButtonActor;

    //Game

    private static final int VIRTUAL_HEIGHT = 550, VIRTUAL_WIDTH = 300;

    private OrthographicCamera gameCamera;
    private Viewport gameViewport;
    private SpriteBatch spriteBatch;

    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Entity player;
    private HashMap<UUID,Entity> enemies;
    private HashMap<UUID,Entity> bullets;
    private List<UUID> toRemove;

    private float time = 0f;
    private boolean shouldFire = false;

    //Bezier<Vector2> curve = new Bezier<Vector2>(new Vector2(300,550),new Vector2(100,400));

    @Override
    public void show() {

        spriteBatch = new SpriteBatch();

        //Initialize gameCamera and gameViewport
        gameCamera = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        gameCamera.setToOrtho(false,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        spriteBatch.setProjectionMatrix(gameCamera.combined);
        gameCamera.update();
        gameViewport = new ExtendViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT, gameCamera);

        //Box2d renderer
        debugRenderer = new Box2DDebugRenderer();

        //Physics
        world = new World(new Vector2(0,0),true);

        addWorldEdges(new Vector2((-10)/ Utils.scale,-10/ Utils.scale),new Vector2((VIRTUAL_WIDTH+10)/ Utils.scale,-10/ Utils.scale)); //bottom
        addWorldEdges(new Vector2(-10/ Utils.scale,-10/ Utils.scale),new Vector2(-10/ Utils.scale,(VIRTUAL_HEIGHT+10)/ Utils.scale)); //left
        addWorldEdges(new Vector2((VIRTUAL_WIDTH+10)/ Utils.scale,-10/ Utils.scale),new Vector2((VIRTUAL_WIDTH+10)/ Utils.scale,(VIRTUAL_HEIGHT+10)/ Utils.scale)); //right
        addWorldEdges(new Vector2(-10/ Utils.scale,(VIRTUAL_HEIGHT+10)/ Utils.scale),new Vector2((VIRTUAL_WIDTH+10)/ Utils.scale,(VIRTUAL_HEIGHT+10)/ Utils.scale)); //top

        world.setContactListener(new PhysicsContactListener());

        //Initialize entities
        enemies = new HashMap<UUID, Entity>();
        bullets = new HashMap<UUID, Entity>();
        toRemove = new ArrayList<UUID>();

        player = new Entity(new Texture("player.png"));
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5/ Utils.scale,5/ Utils.scale);
        player.initPhysics(world, shape, FixtureUserData.PLAYER, 150/ Utils.scale, 100/ Utils.scale);

        Entity enemy = new Entity(new Texture("enemy.png"));
        shape = new PolygonShape();
        shape.setAsBox(10/ Utils.scale,10/ Utils.scale);
        enemy.initPhysics(world, shape, FixtureUserData.ENEMY, 150/ Utils.scale, 480/ Utils.scale);
        enemies.put(enemy.getUuid(),enemy);

        //Initialize UI
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        hudCamera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        hudCamera.update();
        hudViewport = new ScreenViewport(hudCamera);

        dStickActor = new ua.syt0r.actors.DStickActor(gameCamera);
        fireButtonActor = new ua.syt0r.actors.FireButtonActor(gameCamera);

        //Controls
        Gdx.input.setInputProcessor(new InputHandler());

    }

    @Override
    public void render(float delta) {

        //Act

        world.step(delta,10,10);


        for (UUID uuid : toRemove){
            if (enemies.containsKey(uuid))
                world.destroyBody(enemies.remove(uuid).getBody());
            if (bullets.containsKey(uuid))
                world.destroyBody(bullets.remove(uuid).getBody());

        }

        toRemove.clear();

        //Move enemies


        //Vector2 vector2 = new Vector2();
        //enemies.values().iterator().next().getBody().getPosition().set(curve.va);

        //Fire

        if(shouldFire){
            time += delta;
            if(time*1000 > 200){
                time -= 0.2;
                fire();
            }
        }

        //Draw

        gameCamera.update();
        spriteBatch.setProjectionMatrix(gameCamera.combined);
        spriteBatch.enableBlending();
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

        Matrix4 debugMatrix = gameCamera.combined.cpy();
        debugMatrix.scale(Utils.scale, Utils.scale,1f);
        debugRenderer.render(world, debugMatrix);

        //Draw UI
        hudCamera.update();
        spriteBatch.setProjectionMatrix(hudCamera.combined);
        spriteBatch.enableBlending();
        spriteBatch.begin();

        Vector2 leftBottom = gameViewport.project(new Vector2(0,0));
        Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        dStickActor.draw(spriteBatch, leftBottom.x,rightTop.y);
        fireButtonActor.draw(spriteBatch,rightTop.x,rightTop.y);

        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height,false);
        hudViewport.update(width,height,false);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
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

    private void fire(){

        Entity bullet = new Entity(new Texture("bullet.png"));
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(5/ Utils.scale);
        Vector2 playerPos = player.getBody().getPosition();
        bullet.initPhysics(world, circleShape, FixtureUserData.BULLET, playerPos.x, playerPos.y + 20/ Utils.scale);
        bullets.put(bullet.getUuid(),bullet);
        bullet.getBody().applyForce(new Vector2(0,0.1f),bullet.getBody().getWorldCenter(),true);

    }

    private class InputHandler implements InputProcessor{

        private Set<Integer> pressed;

        private int movePointer = -1;
        private int firePointer = -1;

        InputHandler(){

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
                    shouldFire = true;
                    time = 0f;
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
                case Input.Keys.Z:
                    shouldFire = false;
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


            Vector3 gameBorder = gameCamera.project(new Vector3(0,0,0));
            System.out.println("Border " + gameBorder.x + " " + gameBorder.y);

            if(dStickActor.getBorders().contains(screenX,Gdx.graphics.getHeight()-screenY)){

                movePointer = pointer;
                Vector2 speed = dStickActor.getInputData(screenX,Gdx.graphics.getHeight()-screenY);
                player.getBody().setLinearVelocity(speed.x,speed.y);

            }

            if (fireButtonActor.getBorders().contains(screenX,Gdx.graphics.getHeight()-screenY)){

                firePointer = pointer;
                shouldFire = true;
                time = 0f;
                fire();

            }

            return false;

        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {

            if (pointer == movePointer){
                movePointer = -1;
                player.getBody().setLinearVelocity(0,0);
            }

            if (pointer == firePointer){

                firePointer = -1;
                shouldFire = false;

            }

            return false;

        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {

            if(pointer == movePointer){
                Vector2 speed = dStickActor.getInputData(screenX,Gdx.graphics.getHeight()-screenY);
                player.getBody().setLinearVelocity(speed.x,speed.y);
            }

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

    private class PhysicsContactListener implements ContactListener{

        @Override
        public void beginContact(Contact contact) {

            FixtureUserData aData = (FixtureUserData) contact.getFixtureA().getUserData();
            FixtureUserData bData = (FixtureUserData) contact.getFixtureB().getUserData();

            if (aData.getObjectType() == FixtureUserData.WORLD_EDGE)
                toRemove.add((UUID)bData.getData());
            if (bData.getObjectType() == FixtureUserData.WORLD_EDGE)
                toRemove.add((UUID)aData.getData());

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
    }

}
