package ua.syt0r.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DStickActor {

    private Rectangle border;

    private Vector2 center;
    private Vector2 touch;
    private Vector2 inputData;

    private Texture outline, stick;

    private float width;

    public DStickActor(Camera gameCamera){

        center = new Vector2();
        touch = new Vector2();
        inputData =  new Vector2();

        border = new Rectangle();
        outline = new Texture("move_outline.png");
        stick = new Texture("move_stick.png");

    }

    public void draw(SpriteBatch spriteBatch, float gameBorderX, float gameBorderY) {

        width = gameBorderX/(3.5f);
        center.set(gameBorderX/2,gameBorderY/4);
        border.set(center.x-width/2, center.y-width/2,width,width);

        spriteBatch.draw(outline,border.x,border.y,width,width);
        //spriteBatch.draw(stick,border.x + width * inputData.x ,border.y + width * inputData.y,width,width);

    }

    public Vector2 getInputData(int x, int y){

        touch.set(x,y);
        if (touch.dst(center) < width/2){
            inputData.set((touch.x-center.x)/(width/2),(touch.y-center.y)/(width/2));
        }
        else{
            inputData.set((touch.x-center.x)/(width/2),(touch.y-center.y)/(width/2));
            float max = Math.max(Math.abs(inputData.x),Math.abs(inputData.y));
            inputData.x /= max;
            inputData.y /= max;
            System.out.println("Out of " + inputData.x + " " + inputData.y);
        }

        return inputData;

    }

    public Rectangle getBorders(){
        return border;
    }

}
