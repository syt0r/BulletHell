package ua.syt0r.actors.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MenuButtonActor extends Actor {

    private Texture defaultTexture, pressedTexture;
    private boolean isPressed = false;

    public MenuButtonActor(Stage stage, Texture defaultTexture, Texture pressedTexture){

        this.defaultTexture = defaultTexture;
        this.pressedTexture = pressedTexture;

        float width = stage.getHeight()/6;

        setBounds(stage.getWidth()*(3f/4),stage.getHeight()*(1f/4)-width/2,width,width);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(isPressed ? pressedTexture : defaultTexture,getX(),getY(),getWidth(),getHeight());
    }

    public Rectangle getBoundaries(){

        return new Rectangle(getX(),getY(),getWidth(),getHeight());

    }

    public void setIsPressed(boolean isPressed){

        this.isPressed = isPressed;

    }
}
