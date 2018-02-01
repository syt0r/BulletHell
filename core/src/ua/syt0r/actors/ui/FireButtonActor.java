package ua.syt0r.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.Utils;

public class FireButtonActor extends Actor {

    private Camera gameCamera;

    private Rectangle border;

    private Vector2 center;
    private Vector2 touch;
    private Vector2 inputData;

    private Texture texture;

    private float width, gameBorderX, gameBorderY;

    public FireButtonActor(Camera gameCamera){

        this.gameCamera = gameCamera;

        center = new Vector2();
        touch = new Vector2();
        inputData =  new Vector2();

        border = new Rectangle();
        texture = new Texture("move_outline.png");

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        width = (Gdx.graphics.getWidth()-gameBorderX)/(3.5f);
        center.set((Gdx.graphics.getWidth()+gameBorderX)/2,gameBorderY/4);
        border.set(center.x-width/2, center.y-width/2,width,width);

        batch.draw(texture,border.x,border.y,width,width);

    }

    public void resize(Viewport gameViewport, int VIRTUAL_WIDTH, int VIRTUAL_HEIGHT){

        Vector2 leftBottom = gameViewport.project(new Vector2(0,0));
        Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        gameBorderX = rightTop.x;
        gameBorderY = rightTop.y;

        Utils.log(gameBorderX + " " + gameBorderY);

    }

    public Rectangle getBorders(){
        return border;
    }

}
