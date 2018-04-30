package ua.syt0r.actors.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SolidColorActor extends Actor{

    private NinePatch ninePatch;
    private Color defaultColor = Color.valueOf("5e5e5e");

    public SolidColorActor(TextureRegion textureRegion, float width, float height){
        setSize(width,height);
        ninePatch = new NinePatch(textureRegion, defaultColor);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        ninePatch.draw(batch,getX(),getY(),getOriginX(),getOriginY(),getWidth(),getHeight(),getScaleX(),getScaleY(),getRotation());
        batch.setColor(color.r, color.g, color.b, 1f);
    }
}
