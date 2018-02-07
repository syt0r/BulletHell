package ua.syt0r.actors.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.GameManager;

public class HealthActor extends Actor {

    private NinePatch lightNinePatch,darkNinePatch,redNinePatch;

    private int health = 0, maxHealth;

    private float redGaugeX;

    public HealthActor(Viewport gameViewport, int VIRTUAL_WIDTH, int VIRTUAL_HEIGHT, int maxHealth, int health) {

        setMaxHealth(maxHealth);
        setHealth(health);

        Vector2 leftBottom = gameViewport.project(new Vector2(0,0));
        Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        setSize(leftBottom.x/4*3, rightTop.y / 18);
        setPosition(leftBottom.x / 2 - getWidth()/2, rightTop.y / 8 * 7 - getHeight()/2);

        TextureAtlas atlas = GameManager.assetManager.get("loading.atlas",TextureAtlas.class);
        TextureRegion textureRegion = atlas.findRegion("color");

        lightNinePatch = new NinePatch(textureRegion,Color.valueOf("e0e0e0"));
        darkNinePatch = new NinePatch(textureRegion,Color.valueOf("717171"));
        redNinePatch = new NinePatch(textureRegion,Color.valueOf("e09090"));

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {

        darkNinePatch.draw(batch,getX(),getY(),getWidth(),getHeight());
        redNinePatch.draw(batch,getX(),getY(),redGaugeX,getHeight());
        lightNinePatch.draw(batch,getX(),getY(),getWidth()/maxHealth*health,getHeight());

    }

    @Override
    public void act(float delta){

        if (redGaugeX > getWidth()/maxHealth * health)
            redGaugeX -= 50f* delta;

    }

    public void setHealth(int health) {

        if (this.health != 0)
            redGaugeX = getWidth() / maxHealth * this.health;

        this.health = health;


    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}
