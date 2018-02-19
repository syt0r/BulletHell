package ua.syt0r.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.GameManager;

public class FireControlActor extends Actor {

    private Vector2 center;

    private TextureRegion defaultTexture;

    public FireControlActor(TextureAtlas atlas){

        center = new Vector2();

        defaultTexture = atlas.findRegion("fire_button");

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw(defaultTexture,getX(),getY(),getHeight(),getWidth());

    }

    public void resize(Viewport gameViewport, int VIRTUAL_WIDTH, int VIRTUAL_HEIGHT){

        Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        float gameBorderX = rightTop.x;
        float gameBorderY = rightTop.y;

        center.set((Gdx.graphics.getWidth()+gameBorderX)/2,gameBorderY/4);

        setSize(gameBorderY/8*3,gameBorderY/8*3);
        setPosition(center.x-getWidth()/2,center.y-getHeight()/2);

    }

}
