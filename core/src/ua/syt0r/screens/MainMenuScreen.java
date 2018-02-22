package ua.syt0r.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ua.syt0r.*;
import ua.syt0r.levels.Level2;

public class MainMenuScreen implements Screen {

    private State state;

    private Stage stage;

    @Override
    public void show() {

        state = State.LOADING;

        stage = new Stage(new ScreenViewport());

        Skin skin = new Skin(new TextureAtlas("ui.atlas"));

        Table table = new Table(skin);
        table.setFillParent(true);
        //table.setDebug(true);

        //Logo

        //Buttons

        Table buttons = new Table();

        NinePatchDrawable defaultButtonPatch = new NinePatchDrawable(skin.getPatch("main_menu_button"));
        NinePatchDrawable pressedButtonPatch = new NinePatchDrawable(skin.getPatch("main_menu_button_pressed"));
        BitmapFont bitmapFont = Utils.generateFont("MunroSmall.ttf",(int)(stage.getWidth() * 36 / 1280));
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(defaultButtonPatch,pressedButtonPatch,defaultButtonPatch,bitmapFont);
        buttonStyle.fontColor = Color.valueOf("5e5e5e");
        buttonStyle.downFontColor = Color.valueOf("e0e0e0");

        TextButton gameStartButton = new TextButton("Game start",buttonStyle);
        gameStartButton.getLabel().setAlignment(Align.left);
        addButton(buttons,gameStartButton);

        gameStartButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                ua.syt0r.ScreenManager.getInstance().showScreen(new Level2());
            }

        });

        TextButton continueButton = new TextButton("",buttonStyle);
        continueButton.getLabel().setAlignment(Align.left);
        addButton(buttons,continueButton);
        continueButton.padTop(5f);
        continueButton.padBottom(5f);

        TextButton achievementsButton = new TextButton("Achievements",buttonStyle);
        achievementsButton.getLabel().setAlignment(Align.left);
        addButton(buttons,achievementsButton);

        TextButton settingsButton = new TextButton("Settings",buttonStyle);
        settingsButton.getLabel().setAlignment(Align.left);
        addButton(buttons,settingsButton);

        TextButton exitButton = new TextButton("Exit",buttonStyle);
        exitButton.getLabel().setAlignment(Align.left);
        addButton(buttons,exitButton);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(buttons).width(stage.getWidth()/2).expandY().top().padTop(stage.getWidth() * 200f /1280);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.878f , 0.878f , 0.878f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (state == State.LOADING){
            if (Assets.update())
                state = State.GAME;
        }

        stage.act(delta);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void addButton(Table table, TextButton button){

        table.add(button).width(stage.getWidth()/12*5).padBottom(10f).row();

    }

}