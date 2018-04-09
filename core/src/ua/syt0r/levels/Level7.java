package ua.syt0r.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ua.syt0r.Assets;
import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.patterns.move.TriangleMovementPattern;
import ua.syt0r.patterns.shoot.FireBarrageShootingPattern;
import ua.syt0r.patterns.shoot.SimpleShootingPattern;
import ua.syt0r.patterns.shoot.ToPlayerShootingPattern;
import ua.syt0r.screens.GameScreen;

public class Level7 extends GameScreen {

    @Override
    public void preLoad() {
    }

    @Override
    public void init() {

        TextureAtlas atlas = Assets.get("game.atlas",TextureAtlas.class);

        final Enemy enemy1 = new Enemy();
        enemy1.setTexture(atlas.findRegion("enemy1"));
        enemy1.setBody(500,1000,10);
        enemy1.setBounds(500,1000,80,80);
        enemy1.setShootingPattern(new ToPlayerShootingPattern(this));
        //enemy1.setMovementPattern(new LinearMovementPattern(new Vector2(1000,700), new Vector2(0,0),2f));
        addEnemy(enemy1, 50);

        final Enemy enemy2 = new Enemy();
        enemy2.setTexture(atlas.findRegion("enemy1"));
        enemy2.setBody(250,1000,10);
        enemy2.setBounds(250,1000,80,80);
        enemy2.setShootingPattern(new FireBarrageShootingPattern(this));
        enemy2.setMovementPattern(new TriangleMovementPattern(new Vector2[]{new Vector2(430,640),new Vector2(600,1080), new Vector2(260,1080)},5f));
        enemy2.setHealth(20);
        addEnemy(enemy2, 60);

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
