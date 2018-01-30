package ua.syt0r.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class FireButtonActor {

    private Camera gameCamera;

    private Rectangle border;

    private Vector2 center;
    private Vector2 touch;
    private Vector2 inputData;

    private Texture texture;

    private float width;

    public FireButtonActor(Camera gameCamera){

        this.gameCamera = gameCamera;

        center = new Vector2();
        touch = new Vector2();
        inputData =  new Vector2();

        border = new Rectangle();
        texture = new Texture("move_outline.png");

        Vector3 gameBorder = gameCamera.unproject(new Vector3(0,0,0));
        System.out.println("Border " + gameBorder.x + " " + gameBorder.y);

        center.set(725,300);

        border.set(center.x-width/2,center.y-width/2,width,width);

    }

    public void draw(SpriteBatch spriteBatch, float gameBorderX, float gameBorderY) {

        width = (Gdx.graphics.getWidth()-gameBorderX)/(3.5f);
        center.set((Gdx.graphics.getWidth()+gameBorderX)/2,gameBorderY/4);
        border.set(center.x-width/2, center.y-width/2,width,width);

        spriteBatch.draw(texture,border.x,border.y,width,width);
    }

    public Rectangle getBorders(){
        return border;
    }

}
