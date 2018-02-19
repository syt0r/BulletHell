package ua.syt0r.actors.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.GameManager;

public class MovementControlActor extends Actor {

    private Vector2 center, up, down, left, right;

    private TextureRegion defaultTexture, pressedTexture;


    //top,left,bottom,right
    private boolean[] state = {false,false,false,false};

    public MovementControlActor(TextureAtlas atlas){

        center = new Vector2();
        up = new Vector2();
        down = new Vector2();
        left = new Vector2();
        right = new Vector2();

        defaultTexture = atlas.findRegion("movement_control_up");
        pressedTexture = atlas.findRegion("movement_control_up_pressed");

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.draw( state[0] ? pressedTexture : defaultTexture, up.x - getWidth()/4 ,up.y - getHeight()/4,0,0,getWidth()/2,getHeight()/2,getScaleX(),getScaleY(),0);
        batch.draw( state[1] ? pressedTexture : defaultTexture,left.x  + getWidth()/4,left.y - getHeight()/4,0,0,getWidth()/2,getHeight()/2,getScaleX(),getScaleY(),90);
        batch.draw( state[2] ? pressedTexture : defaultTexture, down.x + getWidth()/4 ,down.y + getHeight()/4,0,0,getWidth()/2,getHeight()/2,getScaleX(),getScaleY(),180);
        batch.draw( state[3] ? pressedTexture : defaultTexture,right.x  - getWidth()/4,right.y + getHeight()/4,0,0,getWidth()/2,getHeight()/2,getScaleX(),getScaleY(),270);

    }

    public void resize(Viewport gameViewport, int VIRTUAL_WIDTH, int VIRTUAL_HEIGHT){

        Vector2 leftBottom = gameViewport.project(new Vector2(0,0));
        Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        float gameBorderX = leftBottom.x;
        float gameBorderY = rightTop.y;

        center.set(gameBorderX/2,gameBorderY/4);

        setSize(gameBorderY/8*3,gameBorderY/8*3);
        setPosition(center.x-getWidth()/2,center.y-getHeight()/2);

        up.set(center.x, center.y + getHeight()/4);
        down.set(center.x, center.y - getHeight()/4);
        left.set(center.x - getWidth()/4, center.y);
        right.set(center.x + getWidth()/4, center.y);

    }

    public void setPressedButtons(boolean up, boolean down, boolean left, boolean right){
        state[0] = up;
        state[1] = left;
        state[2] = down;
        state[3] = right;
    }

}
