package ua.syt0r.actors.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;
import ua.syt0r.Assets;
import ua.syt0r.GameManager;

public class Bullet extends Entity implements Pool.Poolable{

    public static final class BulletType{
        public static final int A = 1;
    }

    public static final class Collide{
        public static final int PLAYER = 1;
        public static final int ENEMY = 2;
    }

    private int bulletType;
    private int collideEntity;

    private boolean isAlive;

    public Bullet(){

        super();
        isAlive = false;

    }

    public void init(int bulletType, int collideEntity, float x, float y){

        isAlive = true;
        this.bulletType = bulletType;
        this.collideEntity = collideEntity;

        float width, height;

        switch (bulletType){

            default:
            case 1:
                width = 6;
                height = 30;
                break;

        }

        setBounds(x,y,width,height);

        setBody(x,y,15);

        setTexture(Assets.get("game.atlas",TextureAtlas.class).findRegion("bullet_long"));

    }

    @Override
    public void reset() {

        isAlive = false;
        setRotation(0);
        remove();

    }


    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int getCollideEntity() {
        return collideEntity;
    }

}