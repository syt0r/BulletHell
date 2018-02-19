package ua.syt0r.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import ua.syt0r.Assets;
import ua.syt0r.Utils;

public class PauseActor extends Stack{

    private NinePatch background;
    private BitmapFont font;

    public PauseActor(){

        background = new NinePatch(Assets.get("loading.atlas", TextureAtlas.class).findRegion("color"));
        NinePatchDrawable drawable = new NinePatchDrawable(background);
        addActor(new Image(background));

        font = Utils.generateFont("OpenSans-Regular.ttf", (int)(Gdx.graphics.getWidth() * 100f / 1280));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        TextButton textButton = new TextButton("PAUSE", textButtonStyle);
        add(textButton);


    }

}
