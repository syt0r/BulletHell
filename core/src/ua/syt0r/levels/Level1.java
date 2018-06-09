package ua.syt0r.levels;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ua.syt0r.Assets;
import ua.syt0r.actors.entities.Boss;
import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.actors.entities.Player;
import ua.syt0r.patterns.ShootingPattern;
import ua.syt0r.screens.GameScreen;

public class Level1 extends GameScreen {

    float time = 0, fireTime = 0;

    Boss boss;

    float angle = 0;

    @Override
    public void show() {
        super.show();

        boss = new Boss(200);
        boss.setWidth(40);
        boss.setHeight(40);
        //boss.setTexture(GameManager.assetManager.get("ui", TextureAtlas.class).createSprite("enemy1").getTexture());
        //addBoss(boss);

    }

    @Override
    public void render(float delta) {

        time += delta;
        fireTime += delta;

        if (time * 1000 % 1000  > 500)
            boss.setVelocity(-1,0);
        else
            boss.setVelocity(1,0);


        if (fireTime * 1000  > 500){
            fireTime-=0.5;
            for (int i = 0; i < 12; i++){
                //RadialShot(boss.getX(), boss.getY(), angle,200f);
                angle+=30;
            }
            angle += 10;
        }

        boss.update(delta);

        super.render(delta);

    }

    @Override
    public void preLoad() {
        LoadMusic("music/score.mp3");
    }

    @Override
    public void init() {

        TextureAtlas atlas = Assets.get("game.atlas",TextureAtlas.class);

        ShootingPattern pattern1 = new ShootingPattern(this){

            private Player player = getScreen().getGameStage().getPlayer();

            @Override
            public boolean shoot(Enemy enemy, float delta) {



                return false;
            }

        };

        final Enemy enemy1 = new Enemy();
        enemy1.setTexture(atlas.findRegion("enemy1"));
        enemy1.setBody(500,1000,10);
        enemy1.setBounds(500,1000,80,80);
        enemy1.setShootingPattern(pattern1);
        addEnemy(enemy1, 50);

    }
}
