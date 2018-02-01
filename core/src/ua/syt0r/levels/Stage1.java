package ua.syt0r.levels;

import com.badlogic.gdx.graphics.Texture;
import ua.syt0r.actors.entities.Boss;
import ua.syt0r.screens.GameScreen;

public class Stage1 extends GameScreen {



    @Override
    public void show() {
        super.show();

        Boss boss = new Boss(new Texture("enemy.png"),100);
        boss.setWidth(40);
        boss.setHeight(40);
        addBoss(boss);

    }

    @Override
    public void render(float delta) {



        super.render(delta);

    }
}
