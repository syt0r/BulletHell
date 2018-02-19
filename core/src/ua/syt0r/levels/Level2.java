package ua.syt0r.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ua.syt0r.Assets;
import ua.syt0r.GameManager;
import ua.syt0r.Utils;
import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.patterns.FireBarrageShootingPattern;
import ua.syt0r.patterns.ShootingPattern;
import ua.syt0r.patterns.SimpleShootingPattern;
import ua.syt0r.patterns.ToPlayerShootingPattern;
import ua.syt0r.screens.GameScreen;

import javax.rmi.CORBA.Util;

public class Level2 extends GameScreen {

    @Override
    public void preLoad() {
        /*
        CatmullRomSpline<Vector2> spline = new CatmullRomSpline<Vector2>(new Vector2[]{new Vector2(0,0), new Vector2(500,1500)},true);
        Vector2 vector2 = new Vector2();
        spline.valueAt(vector2,0.25f);

        Utils.log(vector2.x + " " + vector2.y);

        TextureAtlas atlas = GameManager.assetManager.get("game.atlas",TextureAtlas.class);

        */
    }

    @Override
    public void init() {

        TextureAtlas atlas = Assets.get("game.atlas",TextureAtlas.class);

        final Enemy enemy1 = new Enemy();
        enemy1.setTexture(atlas.findRegion("enemy1"));
        enemy1.setBody(500,1000,10);
        enemy1.setBounds(500,1000,80,80);
        enemy1.setShootingPattern(new ToPlayerShootingPattern(this));
        addEnemy(enemy1, 50);

        final Enemy enemy2 = new Enemy();
        enemy2.setTexture(atlas.findRegion("enemy1"));
        enemy2.setBody(250,1000,10);
        enemy2.setBounds(250,1000,80,80);
        enemy2.setShootingPattern(new FireBarrageShootingPattern(this));
        addEnemy(enemy2, 200);

        final Enemy enemy3 = new Enemy();
        enemy3.setTexture(atlas.findRegion("enemy1"));
        enemy3.setBody(750,1300,10);
        enemy3.setBounds(750,1300,80,80);
        enemy3.setShootingPattern(new SimpleShootingPattern(this));
        addEnemy(enemy3, 200);

        final Enemy enemy4 = new Enemy();
        enemy4.setTexture(atlas.findRegion("enemy1"));
        enemy4.setBody(300,700,10);
        enemy4.setBounds(300,700,80,80);
        enemy4.setShootingPattern(new ToPlayerShootingPattern(this));
        addEnemy(enemy4, 250);

    }

}
