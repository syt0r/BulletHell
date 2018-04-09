package ua.syt0r.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ua.syt0r.Assets;
import ua.syt0r.Utils;

public class PauseActor extends Stack{

    private SolidColorActor solidColorActor;

    private Table table;

    public PauseActor(Stage stage){

        solidColorActor = new SolidColorActor(Assets.loadingAtlas.findRegion("color"),stage.getWidth(),stage.getHeight());

        add(solidColorActor);

        table = new Table();
        table.setFillParent(true);

        BitmapFont font = Utils.generateFont("MunroSmall.ttf", (int) (Gdx.graphics.getWidth() * 100f / 1280));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        TextButton textButton = new TextButton("PAUSE", textButtonStyle);
        //add(textButton);

        table.add(textButton).fill().center();
        add(table);

    }

    public void show(){
        addAction(Actions.sequence(Actions.fadeIn(0.5f)));
    }
}
