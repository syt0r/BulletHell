package ua.syt0r.actors.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ua.syt0r.Assets;

public class LoadingActor extends Actor{

    private NinePatch ninePatch;

    public LoadingActor(){
        ninePatch = new NinePatch(Assets.loadingAtlas.findRegion("color"));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ninePatch.draw(batch,0,0,getStage().getWidth(),getStage().getHeight());
    }
}
