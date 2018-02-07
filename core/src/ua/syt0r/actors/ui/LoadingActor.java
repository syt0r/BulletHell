package ua.syt0r.actors.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class LoadingActor extends Actor{

    NinePatch ninePatch;

    public LoadingActor(){

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        ninePatch.draw(batch,0,0,0,0);

    }
}
