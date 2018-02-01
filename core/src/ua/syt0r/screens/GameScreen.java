package ua.syt0r.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.actors.entities.Entity;
import ua.syt0r.actors.entities.Boss;
import ua.syt0r.actors.ui.DStickActor;
import ua.syt0r.actors.ui.FireButtonActor;

import java.util.*;

/**
 * Created by Yaroslav on 2018/01/19.
 */

public class GameScreen implements Screen {

    //UI

    private SpriteBatch hudBatch;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private Stage hudStage;

    private DStickActor dStickActor;
    private FireButtonActor fireButtonActor;

    //Game

    private static final int VIRTUAL_HEIGHT = 550, VIRTUAL_WIDTH = 300;

    private SpriteBatch gameBatch;

    private OrthographicCamera gameCamera;
    private Viewport gameViewport;

    private Stage gameStage;

    //Bullet calculation border

    private Circle worldBorder;

    //Collections
    private Entity player;
    private HashMap<UUID,Entity> enemies;
    private HashMap<UUID,Entity> bullets;
    private List<UUID> toRemove;

    private float time = 0f;
    private boolean shouldFire = false;

    private Texture bulletTexture;

    @Override
    public void show() {

        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        //Initialize UI

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        hudCamera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        hudBatch.setProjectionMatrix(hudCamera.combined);
        hudCamera.update();
        hudViewport = new ScreenViewport(hudCamera);
        hudStage = new Stage(hudViewport,hudBatch);

        dStickActor = new DStickActor();
        fireButtonActor = new FireButtonActor(hudCamera);

        hudStage.addActor(dStickActor);
        hudStage.addActor(fireButtonActor);

        //Initialize gameCamera and gameViewport

        gameCamera = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        gameCamera.setToOrtho(false,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        gameBatch.setProjectionMatrix(gameCamera.combined);
        gameCamera.update();
        gameViewport = new FitViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT, gameCamera);
        gameStage = new Stage(gameViewport,gameBatch);

        dStickActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        fireButtonActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);

        //Physics

        worldBorder = new Circle(VIRTUAL_WIDTH/2f,VIRTUAL_HEIGHT/2f,VIRTUAL_HEIGHT);

        //Initialize entities

        enemies = new HashMap<UUID, Entity>();
        bullets = new HashMap<UUID, Entity>();
        toRemove = new ArrayList<UUID>();

        player = new Entity(new Texture("player.png"));
        player.setBody(new Circle(150,100,5));
        player.setBounds(150,100,20,20);
        gameStage.addActor(player);

        Entity enemy = new Entity(new Texture("enemy.png"));
        enemy.setBody(new Circle(150,480,10));
        enemies.put(enemy.getUuid(),enemy);
        gameStage.addActor(enemy);

        bulletTexture = new Texture("bullet.png");


        //Controls

        Gdx.input.setInputProcessor(new InputHandler());

    }

    @Override
    public void render(float delta) {

        //Act

        updatePhysics();

        //Player fire

        if(shouldFire){
            time += delta;
            if(time*1000 > 200){
                time -= 0.2;
                fire();
            }
        }

        //Draw

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameStage.getViewport().apply();
        gameStage.draw();

        //Draw UI


        hudStage.getViewport().apply();
        hudStage.draw();

       // Vector2 leftBottom = gameViewport.project(new Vector2(0,0));
        //Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        //fireButtonActor.draw(hudBatch,rightTop.x,rightTop.y);

        //hudBatch.end();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height,true);
        hudViewport.update(width,height,true);
        dStickActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        fireButtonActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
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

    private void fire(){

        Entity bullet = new Entity(bulletTexture);
        Circle playerPos = player.getBody();
        bullet.setBody(new Circle(playerPos.x,playerPos.y+20,5));
        bullet.setWidth(10);
        bullet.setHeight(10);
        bullet.setVelocity(0,1f);
        gameStage.addActor(bullet);
        bullets.put(bullet.getUuid(),bullet);

    }

    private void updatePhysics(){

        player.move();

        //Utils.log("num " + bullets.size());

        for (Map.Entry<UUID,Entity> entry : bullets.entrySet()){

            Entity entity = entry.getValue();
            entity.move();


            if (!worldBorder.overlaps(entity.getBody()))
                toRemove.add(entry.getKey()); /*
            else
                if(entity.getBody().overlaps(player.getBody()))
                    player.setHealth(player.getHealth()-1);
*/
            //Utils.log("iteration");

        }


        for (UUID uuid : toRemove){
            if (enemies.containsKey(uuid))
                enemies.remove(uuid);
            if (bullets.containsKey(uuid))
                bullets.remove(uuid);

        }

        toRemove.clear();

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
            Vector2 playerVelocity = player.getVelocity();
            switch (keycode){
                case com.badlogic.gdx.Input.Keys.LEFT:
                    player.setVelocity(-1f,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                    player.setVelocity(1f,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                    player.setVelocity(playerVelocity.x,1f);
                    break;
                case com.badlogic.gdx.Input.Keys.DOWN:
                    player.setVelocity(playerVelocity.x,-1f);
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
            Vector2 playerVelocity = player.getVelocity();
            switch (keycode){
                case com.badlogic.gdx.Input.Keys.LEFT:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.RIGHT))
                        player.setVelocity(1f,playerVelocity.y);
                    else
                        player.setVelocity(0,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.LEFT))
                        player.setVelocity(-1f,playerVelocity.y);
                    else
                        player.setVelocity(0,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.DOWN))
                        player.setVelocity(playerVelocity.x,-1f);
                    else
                        player.setVelocity(playerVelocity.x,0);
                    break;
                case com.badlogic.gdx.Input.Keys.DOWN:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.UP))
                        player.setVelocity(playerVelocity.x,1f);
                    else
                        player.setVelocity(playerVelocity.x,0);
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
                player.setVelocity(dStickActor.getInputData(screenX,Gdx.graphics.getHeight()-screenY));

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
                player.setVelocity(0,0);

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
                player.setVelocity(dStickActor.getInputData(screenX,Gdx.graphics.getHeight()-screenY));
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


    public void addBoss(Boss boss){
        boss.setPosition(VIRTUAL_WIDTH/2f,VIRTUAL_HEIGHT/4f*3f);
        gameStage.addActor(boss);
    }

    public void LinearShot(){

    }

}
