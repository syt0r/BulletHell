package ua.syt0r.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ua.syt0r.Assets;
import ua.syt0r.Utils;

public class DiedActor extends Stack {

    private SolidColorActor2 solidColorActor;
    private TextButton textButton;

    public DiedActor(Stage stage){

        solidColorActor = new SolidColorActor2(Assets.loadingAtlas.findRegion("color"),stage.getWidth(),stage.getHeight());
        add(solidColorActor);

        BitmapFont pauseFont = Utils.generateFont("MunroSmall.ttf", (int) (Gdx.graphics.getWidth() * 100f / 1280));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.fontColor = Color.valueOf("e09090");
        buttonStyle.font = pauseFont;
        textButton = new TextButton("YOU DIED", buttonStyle);
        add(textButton);

    }

    public void show(){
        solidColorActor.addAction(Actions.sequence(Actions.alpha(0),Actions.alpha(0.9f,0.2f)));
    }

}
