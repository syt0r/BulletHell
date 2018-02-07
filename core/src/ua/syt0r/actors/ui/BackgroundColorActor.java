package ua.syt0r.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;

public class BackgroundColorActor extends Actor {

    public static final int LEFT_SIDE = 1;
    public static final int RIGHT_SIDE = 2;

    private Texture texture;

    public BackgroundColorActor(int side, Viewport gameViewport, int VIRTUAL_WIDTH, int VIRTUAL_HEIGHT){

        Vector2 leftBottom = gameViewport.project(new Vector2(0,0));
        Vector2 rightTop = gameViewport.project(new Vector2(VIRTUAL_WIDTH,VIRTUAL_HEIGHT));

        Pixmap pixmap;
        switch (side){
            default:
            case LEFT_SIDE:
                pixmap = new Pixmap((int)leftBottom.x, (int)rightTop.y, Pixmap.Format.RGB888);
                setBounds(0,0,leftBottom.x,rightTop.y);
                break;
            case RIGHT_SIDE:
                pixmap = new Pixmap((int)(Gdx.graphics.getWidth() - rightTop.x), (int)rightTop.y, Pixmap.Format.RGB888);
                setBounds(rightTop.x,0,Gdx.graphics.getWidth() - rightTop.x, rightTop.y);
                break;
        }

        pixmap.setColor(Color.valueOf("5e5e5e"));
        pixmap.fill();
        texture = new Texture(pixmap);
        pixmap.dispose();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }
}
