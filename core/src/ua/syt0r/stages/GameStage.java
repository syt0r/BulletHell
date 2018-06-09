package ua.syt0r.stages;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.FitViewport;
import ua.syt0r.State;
import ua.syt0r.Utils;
import ua.syt0r.actors.entities.Boss;
import ua.syt0r.actors.entities.Bullet;
import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.actors.entities.Player;
import ua.syt0r.levels.LevelInterface;
import ua.syt0r.patterns.EntityMovementPattern;
import ua.syt0r.patterns.bullet.EntityLinearMovementPattern;
import ua.syt0r.screens.GameScreen;

import java.util.AbstractMap;
import java.util.Map;

public class GameStage extends Stage implements LevelInterface {

    private GameScreen gameScreen;


    private static final int VIRTUAL_HEIGHT = 1280, VIRTUAL_WIDTH = 860;

    //Bullet calculation border

    private Circle worldBorder;

    //Collections & entities

    private Array<Bullet> activeBullets;
    private Pool<Bullet> bulletPool;

    private Array<Enemy> activeEnemies;
    private Array<Map.Entry<Integer,Enemy>> enemiesToSpawn;

    private Boss boss;

    private Player player;

    //For spawn enemy calculations
    private float time = 0f;
    private int frame = 0;

    private ShapeRenderer debugRenderer;
    private boolean debug = false;

    //Performs default linear movement
    private EntityMovementPattern entityLinearMovementPattern;

    public GameStage(GameScreen gameScreen){

        this.gameScreen = gameScreen;

        setViewport(new FitViewport(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        //Bullets destroy border

        worldBorder = new Circle(VIRTUAL_WIDTH/2f,VIRTUAL_HEIGHT/2f,VIRTUAL_HEIGHT);

        //Initialize entities

        activeBullets = new Array<Bullet>();
        bulletPool = Pools.get(Bullet.class,256);

        activeEnemies = new Array<Enemy>();
        enemiesToSpawn = new Array<Map.Entry<Integer, Enemy>>();

        if (debug)
            debugRenderer = new ShapeRenderer();

    }

    public void init(){

        entityLinearMovementPattern = new EntityLinearMovementPattern();

        player = new Player(gameScreen.getTextureAtlas());
        player.setMovementBounds(0,VIRTUAL_WIDTH,0,VIRTUAL_HEIGHT);
        player.setPosition(VIRTUAL_WIDTH/2f,VIRTUAL_HEIGHT/8f);
        player.setEntityMovementPattern(entityLinearMovementPattern);
        addActor(player);


    }




    @Override
    public void act(float delta) {

        time += delta;
        if( (int)(time * 10) > frame){
            frame++;
            Utils.log("Frame " + frame);
        }

        spawnEnemy();

        movementAndCollisions(delta);

        playerFire(delta);

    }

    @Override
    public void draw() {

        super.draw();

        //Debug

        if (debug) {

            debugRenderer.setProjectionMatrix(getCamera().combined);
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

    }


    private void spawnEnemy(){

        if (enemiesToSpawn.size == 0){

            if (boss != null){

                enemiesToSpawn.add(new AbstractMap.SimpleEntry<Integer, Enemy>(0,boss));

            }

            return;

        }

        Map.Entry<Integer, Enemy> pair = enemiesToSpawn.first();

        if (frame > pair.getKey()){

            Utils.log("spawned");

            activeEnemies.add(pair.getValue());
            addActor(pair.getValue());
            enemiesToSpawn.removeValue(pair,true);

            spawnEnemy();

        }

    }

    private void movementAndCollisions(float delta){

        player.update(delta);

        if (activeEnemies.size == 0 && enemiesToSpawn.size == 0)
            gameScreen.setState(State.WIN);

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

                if (player.getHealth() == 0)
                    gameScreen.setState(State.DEAD);

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
                        enemy.damage();
                        if (!enemy.isAlive()){
                            activeEnemies.removeValue(enemy,true);
                            enemy.remove();
                        }

                        break;

                    }
                }
            }

        }

    }

    private void playerFire(float delta){

        if(player.isFiring()){

            if(player.increaseFireTime(delta) * 1000 > 80){

                player.setLastFireTime(0);

                Bullet bullet = bulletPool.obtain();
                bullet.init(Bullet.BulletType.A, Bullet.Collide.ENEMY, player.getX(),player.getY());
                bullet.setVelocity(0,1);
                bullet.setSpeed(1200f);
                bullet.setEntityMovementPattern(entityLinearMovementPattern);
                activeBullets.add(bullet);
                addActor(bullet);

            }
        }

    }


    public int getVirtualWidth() {
        return VIRTUAL_WIDTH;
    }

    public int getVirtualHeight() {
        return VIRTUAL_HEIGHT;
    }

    public Player getPlayer() {
        return player;
    }


    @Override
    public void LinearShot(float x, float y, float speed) {
        Bullet bullet = bulletPool.obtain();
        bullet.init(Bullet.BulletType.A, Bullet.Collide.PLAYER, x, y);
        bullet.setVelocity(0,-1);
        bullet.setSpeed(speed);
        bullet.setEntityMovementPattern(entityLinearMovementPattern);
        activeBullets.add(bullet);
        addActor(bullet);
    }

    @Override
    public void RadialShot(float x, float y, float angle, float speed) {
        Bullet bullet = bulletPool.obtain();
        bullet.setRotation(angle-45);
        angle = (float) Math.toRadians(angle);
        bullet.init(Bullet.BulletType.A, Bullet.Collide.PLAYER, x, y);
        bullet.setVelocity((float) (Math.cos(angle)-Math.sin(angle)),(float) (Math.sin(angle)+Math.cos(angle)));
        bullet.setSpeed(speed);
        bullet.setOrigin(bullet.getWidth()/2,bullet.getHeight()/2);
        bullet.setEntityMovementPattern(entityLinearMovementPattern);
        activeBullets.add(bullet);
        addActor(bullet);
    }

    @Override
    public void DirectionalShot(float x, float y, float speed, float targetX, float targetY) {
        float angle = (float) Math.toDegrees(Math.atan2(targetY - y, targetX - x))-45;
        RadialShot(x,y,angle,speed);
    }

    @Override
    public void addEnemy(Enemy enemy, int spawnFrame) {
        Map.Entry<Integer,Enemy> entry = new AbstractMap.SimpleEntry<Integer, Enemy>(spawnFrame,enemy);
        enemiesToSpawn.add(entry);
    }

    @Override
    public void setBoss(Boss boss) {
        this.boss = boss;
    }

}
