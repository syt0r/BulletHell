package ua.syt0r.actors.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import ua.syt0r.Assets;
import ua.syt0r.Utils;

public class PauseActor extends Stack{

    private SolidColorActor2 solidColorActor;

    private Table table;

    private TextButton continueButton, exitButton;

    public PauseActor(Stage stage){

        solidColorActor = new SolidColorActor2(Assets.loadingAtlas.findRegion("color"),stage.getWidth(),stage.getHeight());

        add(solidColorActor);

        table = new Table();
        table.setFillParent(true);

        BitmapFont pauseFont = Utils.generateFont("MunroSmall.ttf", (int) (Gdx.graphics.getWidth() * 100f / 1280));
        TextButton.TextButtonStyle pauseStyle = new TextButton.TextButtonStyle();
        pauseStyle.font = pauseFont;
        TextButton pauseButton = new TextButton("PAUSE", pauseStyle);

        BitmapFont buttonsFont = Utils.generateFont("MunroSmall.ttf", (int) (Gdx.graphics.getWidth() * 50f / 1280));
        TextButton.TextButtonStyle buttonsStyle = new TextButton.TextButtonStyle();
        buttonsStyle.font = buttonsFont;
        continueButton = new TextButton("continue", buttonsStyle);
        exitButton = new TextButton("exit", buttonsStyle);



        table.add(pauseButton).fill().center().row();
        table.add(continueButton).center().row();
        table.add(exitButton).center();
        add(table);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void show(){
        solidColorActor.addAction(Actions.sequence(Actions.alpha(0),Actions.alpha(0.75f,0.2f)));
    }

    public void addContinueButtonListener(EventListener eventListener){
        continueButton.addListener(eventListener);
    }

    public void addExitButtonListener(EventListener eventListener){
        exitButton.addListener(eventListener);
    }
}
