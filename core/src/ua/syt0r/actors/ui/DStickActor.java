package ua.syt0r.actors.ui;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.Utils;

public class DStickActor extends Actor{

    private Rectangle border;

    private Vector2 center;
    private Vector2 touch;
    private Vector2 inputData;

    private Texture outline, stick;

    private float width, gameBorderX, gameBorderY;

    public DStickActor(){

        center = new Vector2();
        touch = new Vector2();
        inputData =  new Vector2();

        border = new Rectangle();
        outline = new Texture("move_outline.png");
        stick = new Texture("move_stick.png");

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        width = gameBorderX/(3.5f);
        center.set(gameBorderX/2,gameBorderY/4);
        border.set(center.x-width/2, center.y-width/2,width,width);

        batch.draw(outline,border.x,border.y,width,width);

    }

    public void resize(Viewport gameViewport, int VIRTUAL_WIDTH, int VIRTUAL_HEIGHT){

        Vector2 leftBottom = gameViewport.project(new Vector2(0,0));
        Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        gameBorderX = leftBottom.x;
        gameBorderY = rightTop.y;

        Utils.log(gameBorderX + " " + gameBorderY);

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
