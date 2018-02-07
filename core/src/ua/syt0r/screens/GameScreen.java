package ua.syt0r.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.*;
import ua.syt0r.actors.entities.Bullet;
import ua.syt0r.actors.entities.Boss;
import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.actors.entities.Player;
import ua.syt0r.actors.ui.BackgroundColorActor;
import ua.syt0r.actors.ui.FireControlActor;
import ua.syt0r.actors.ui.HealthActor;
import ua.syt0r.actors.ui.MovementControlActor;

import java.util.*;

public class GameScreen implements Screen {

    //UI

    private SpriteBatch hudBatch;

    private OrthographicCamera hudCamera;
    private Viewport hudViewport;

    private Stage hudStage;

    private HealthActor playerHealth;

    //Game

    public static final int VIRTUAL_HEIGHT = 1280, VIRTUAL_WIDTH = 860;

    private SpriteBatch gameBatch;

    private OrthographicCamera gameCamera;
    private Viewport gameViewport;

    private Stage gameStage;


    //Bullet calculation border

    private Circle worldBorder;

    //Collections & entities

    private Array<Bullet> activeBullets;
    private Pool<Bullet> bulletPool;

    private Array<Enemy> activeEnemies;
    private Array<Map.Entry<Integer,Enemy>> enemiesToSpawn;

    private Boss boss;


    private Player player;

    private float playerFireTime = 0f;
    private boolean shouldFire = false;

    //For spawn enemy calculations
    private float time = 0f;
    private int frame = 0;

    private ShapeRenderer debugRenderer;
    private boolean debug = false;

    @Override
    public void show() {

        gameBatch = new SpriteBatch();
        hudBatch = new SpriteBatch();

        initGame();

        initUI();

        //Controls

        Gdx.input.setInputProcessor(hudStage);

    }

    @Override
    public void render(float delta) {

        time += delta;
        if( (int)(time * 10) > frame){
            frame++;
            Utils.log("Frame " + frame);
        }

        spawnEnemy();

        movementAndCollisions(delta);

        playerFire(delta);

        hudStage.act(delta);

        draw();

    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height,true);
        hudViewport.update(width,height,true);
        //dStickActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        //fireButtonActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
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

    private void initGame(){

        //Initialize gameCamera and gameViewport

        gameCamera = new OrthographicCamera(VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        gameCamera.setToOrtho(false,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        gameBatch.setProjectionMatrix(gameCamera.combined);
        gameCamera.update();
        gameViewport = new FitViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT, gameCamera);
        gameStage = new Stage(gameViewport,gameBatch);

        if (debug)
            debugRenderer = new ShapeRenderer();

        //Bullet destroy border

        worldBorder = new Circle(VIRTUAL_WIDTH/2f,VIRTUAL_HEIGHT/2f,VIRTUAL_HEIGHT);

        //Initialize entities

        activeBullets = new Array<Bullet>();
        bulletPool = Pools.get(Bullet.class,256);

        activeEnemies = new Array<Enemy>();
        enemiesToSpawn = new Array<Map.Entry<Integer, Enemy>>();

        player = new Player(Bullet.BulletType.A);
        player.setMovementBounds(0,VIRTUAL_WIDTH,0,VIRTUAL_HEIGHT);
        gameStage.addActor(player);

    }

    private void initUI(){

        //Initialize UI

        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        hudCamera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        hudBatch.setProjectionMatrix(hudCamera.combined);
        hudCamera.update();
        hudViewport = new ScreenViewport(hudCamera);
        hudStage = new Stage(hudViewport,hudBatch);

        //hudStage.setDebugAll(true);

        hudStage.addActor(new BackgroundColorActor(BackgroundColorActor.LEFT_SIDE,gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT));
        hudStage.addActor(new BackgroundColorActor(BackgroundColorActor.RIGHT_SIDE,gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        MovementControlActor movementControlActor = new MovementControlActor();
        movementControlActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        movementControlActor.addListener(new DPadInput(movementControlActor));
        hudStage.addActor(movementControlActor);

        FireControlActor fireControlActor = new FireControlActor();
        fireControlActor.resize(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT);
        fireControlActor.addListener(new FireButtonInput());
        hudStage.addActor(fireControlActor);

        TextureAtlas atlas = GameManager.assetManager.get("game.atlas",TextureAtlas.class);
        Drawable pauseDefault = new SpriteDrawable(new Sprite(atlas.findRegion("pause_button")));
        Drawable pausePressed = new SpriteDrawable(new Sprite(atlas.findRegion("pause_button_pressed")));
        ImageButton pauseButton = new ImageButton(pauseDefault,pausePressed);
        pauseButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ua.syt0r.ScreenManager.getInstance().showScreen(ua.syt0r.ScreenManager.ScreenEnum.MAIN_MENU);
            }
        });
        pauseButton.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth(), Gdx.graphics.getHeight() - pauseButton.getHeight());
        hudStage.addActor(pauseButton);

        playerHealth = new HealthActor(gameViewport,VIRTUAL_WIDTH,VIRTUAL_HEIGHT,Player.MAX_HEALTH,Player.MAX_HEALTH);
        hudStage.addActor(playerHealth);

        hudStage.addListener(new KeyboardInput());

        hudStage.setDebugAll(debug);


    }

    private void spawnEnemy(){

        if (enemiesToSpawn.size == 0){

            if (boss != null){

                enemiesToSpawn.add(new AbstractMap.SimpleEntry<Integer, Enemy>(0,boss));

            }

            //TODO Level ended
            return;

        }

        Map.Entry<Integer, Enemy> pair = enemiesToSpawn.first();

        if (frame > pair.getKey()){

            Utils.log("spawned");

            activeEnemies.add(pair.getValue());
            gameStage.addActor(pair.getValue());
            enemiesToSpawn.removeValue(pair,true);

            spawnEnemy();

        }

    }

    private void movementAndCollisions(float delta){

        player.update(delta);

        for (Enemy enemy : activeEnemies)
            enemy.update(delta);

        Bullet bullet;
        for (int i = activeBullets.size; i > 0;i--){

            bullet = activeBullets.get(i-1);

            bullet.update(delta);

            //Return dead bullets to pool

            if (!worldBorder.overlaps(bullet.getBody())){
                activeBullets.removeValue(bullet,true);
                bulletPool.free(bullet);
                continue;
            }

            //Check collisions

            //Collision with player
            if (bullet.getCollideEntity() == Bullet.Collide.PLAYER && bullet.getBody().overlaps(player.getBody())){

                Utils.log("Player damaged");

                player.damage();
                playerHealth.setHealth(player.getHealth());

                bulletPool.freeAll(activeBullets);
                activeBullets.clear();
                break;

            }

            //Collision with enemies
            if (bullet.getCollideEntity() == Bullet.Collide.ENEMY){

                for (Enemy enemy : activeEnemies){
                    if (bullet.getBody().overlaps(enemy.getBody())){

                        activeBullets.removeValue(bullet,true);
                        bulletPool.free(bullet);
                        activeEnemies.removeValue(enemy,true);
                        enemy.remove();

                        break;

                    }
                }
            }

        }

    }

    private void playerFire(float delta){

        if(shouldFire){

            playerFireTime += delta;

            if(playerFireTime * 1000 > 100){

                playerFireTime = 0;

                Bullet bullet = bulletPool.obtain();
                bullet.init(Bullet.BulletType.A, Bullet.Collide.ENEMY, player.getX(),player.getY());
                bullet.setVelocity(0,1);
                bullet.setSpeed(800f);
                activeBullets.add(bullet);
                gameStage.addActor(bullet);

            }
        }

    }

    private void draw(){

        Gdx.gl.glClearColor(0.878f , 0.878f , 0.878f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Game

        gameStage.getViewport().apply();
        gameStage.draw();

        //Debug

        if (debug) {

            debugRenderer.setProjectionMatrix(gameCamera.combined);
            debugRenderer.setAutoShapeType(true);
            debugRenderer.begin(ShapeRenderer.ShapeType.Line);

            debugRenderer.setColor(Color.GREEN);
            Circle body = player.getBody();
            debugRenderer.circle(body.x, body.y, body.radius);

            for (Bullet bullet : activeBullets) {
                body = bullet.getBody();
                debugRenderer.circle(body.x, body.y, body.radius);
            }

            debugRenderer.end();

        }

        //UI
        hudStage.getViewport().apply();
        hudStage.draw();

    }


    private class KeyboardInput extends ClickListener{

        private Set<Integer> pressed;

        KeyboardInput(){

            pressed = new HashSet<Integer>();

        }

        @Override
        public boolean keyDown(InputEvent event, int keycode) {
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
                    playerFireTime = 0f;
                    playerFire(1);
                    break;
            }
            pressed.add(keycode);
            return false;
        }

        @Override
        public boolean keyUp(InputEvent event,int keycode) {
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

    }

    private class DPadInput extends ClickListener{

        private MovementControlActor movementControlActor;
        private Vector2 up,down,left,right,upLeft,upRight,downLeft,downRight;

        DPadInput(MovementControlActor movementControlActor){

            this.movementControlActor = movementControlActor;

            float width = movementControlActor.getWidth();
            float height = movementControlActor.getHeight();

            up = new Vector2(width/2,height/4*3);
            down = new Vector2(width/2,height/4);
            left = new Vector2(width/4,height/2);
            right = new Vector2(width/4*3,height/2);

            /*
            upLeft = new Vector2(width/8*3,height/8*5);
            upRight = new Vector2(width/8*5,height/8*5);
            downLeft = new Vector2(width/8*3,height/8*3);
            downRight = new Vector2(width/8*5,height/8*3);
            */

            upLeft = new Vector2(width/4,height/4*3);
            upRight = new Vector2(width/4*3,height/4*3);
            downLeft = new Vector2(width/4,height/4);
            downRight = new Vector2(width/4*3,height/4);

        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            calculateDistance(x,y);
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            calculateDistance(x,y);
            super.touchDragged(event, x, y, pointer);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            player.setVelocity(0,0);
            movementControlActor.setPressedButtons(false,false,false,false);
            super.touchUp(event, x, y, pointer, button);
        }

        private void calculateDistance(float x, float y){

            float upDistance,downDistance,leftDistance,rightDistance,
                    upLeftDistance,upRightDistance,downLeftDistance,downRightDistance;

            upDistance = distance(x,y,up.x,up.y);
            downDistance = distance(x,y,down.x,down.y);
            leftDistance = distance(x,y,left.x,left.y);
            rightDistance = distance(x,y,right.x,right.y);
            upLeftDistance = distance(x,y,upLeft.x,upLeft.y);
            upRightDistance = distance(x,y,upRight.x,upRight.y);
            downLeftDistance = distance(x,y,downLeft.x,downLeft.y);
            downRightDistance = distance(x,y,downRight.x,downRight.y);

            float min = Collections.min(Arrays.asList(upDistance,downDistance,leftDistance,rightDistance,
                    upLeftDistance,upRightDistance,downLeftDistance,downRightDistance));

            if (upDistance ==  min){
                player.setVelocity(0,1f);
                movementControlActor.setPressedButtons(true,false,false,false);
            }else if (downDistance == min){
                player.setVelocity(0,-1f);
                movementControlActor.setPressedButtons(false,true,false,false);
            }else if (leftDistance == min){
                player.setVelocity(-1f,0);
                movementControlActor.setPressedButtons(false,false,true,false);
            }else if (rightDistance == min){
                player.setVelocity(1f,0);
                movementControlActor.setPressedButtons(false,false,false,true);
            }else if (upLeftDistance == min){
                player.setVelocity(-1f,1f);
                movementControlActor.setPressedButtons(true,false,true,false);
            }else if (upRightDistance == min){
                player.setVelocity(1f,1f);
                movementControlActor.setPressedButtons(true,false,false,true);
            }else if (downLeftDistance == min){
                player.setVelocity(-1f,-1f);
                movementControlActor.setPressedButtons(false,true,true,false);
            }else if (downRightDistance == min){
                player.setVelocity(1f,-1f);
                movementControlActor.setPressedButtons(false,true,false,true);
            }

        }

        private float distance(float x1, float y1, float x2, float y2){
            return (float)Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        }

    }

    private class FireButtonInput extends ClickListener{

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            shouldFire = true;
            playerFireTime = 0f;
            playerFire(1);
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            shouldFire = false;
            super.touchUp(event, x, y, pointer, button);
        }
    }


    //Available interface for stages


    public void LinearShot(float x, float y, float speed){
        Bullet bullet = bulletPool.obtain();
        bullet.init(Bullet.BulletType.A, Bullet.Collide.PLAYER, x, y);
        bullet.setVelocity(0,-1);
        bullet.setSpeed(speed);
        activeBullets.add(bullet);
        gameStage.addActor(bullet);
    }

    public void RadialShot(float x, float y, float angle, float speed){
        angle = (float) Math.toRadians(angle);
        Bullet bullet = bulletPool.obtain();
        bullet.init(Bullet.BulletType.A, Bullet.Collide.PLAYER, x, y);
        bullet.setVelocity((float) (Math.cos(angle)-Math.sin(angle)),(float) (Math.sin(angle)+Math.cos(angle)));
        bullet.setSpeed(speed);
        activeBullets.add(bullet);
        gameStage.addActor(bullet);
    }

    public void addEnemy(Enemy enemy, int spawnFrame){

        Map.Entry<Integer,Enemy> entry = new AbstractMap.SimpleEntry<Integer, Enemy>(spawnFrame,enemy);
        enemiesToSpawn.add(entry);

    }

    public void setBoss(Boss boss){
        this.boss = boss;
    }

}