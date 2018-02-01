package ua.syt0r.actors.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Boss extends Actor{

    Texture texture;
    int health;

    public Boss(Texture texture, int health){

        this.texture = texture;
        this.health = health;

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture,getX(),getY(),getWidth(),getHeight());
    }
}
