package ua.syt0r.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ua.syt0r.*;
import ua.syt0r.actors.ui.SolidColorActor;
import ua.syt0r.levels.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class MainMenuScreen implements Screen {

    private State state;

    private Stage stage;

    private Table table;

    private SolidColorActor leftLineActor, rightLineActor;

    private TextButton.TextButtonStyle normalButtonStyle;

    private int selectedLevel;

    private SoundClick soundClick;
    private Sound clickSound;
    private Music music;

    @Override
    public void show() {

        state = State.LOADING;

        stage = new Stage(new ScreenViewport());
        //stage.setDebugAll(true);

        init();

        //showMainMenu();
        loadMenu(MenuOptions.MAIN_MENU);

        Gdx.input.setInputProcessor(stage);

    }

    private void init(){

        Assets.loadLoadingScreenAssets();
        Assets.loadMenuUIAssets();

        Skin skin = new Skin(Assets.uiAtlas);

        table = new Table(skin);
        table.setFillParent(true);

        stage.addActor(table);

        leftLineActor = new SolidColorActor(Assets.loadingAtlas.findRegion("color"), stage.getWidth() * 8 / 1280, stage.getHeight());
        rightLineActor = new SolidColorActor(Assets.loadingAtlas.findRegion("color"), stage.getWidth() * 8 / 1280, stage.getHeight());



        NinePatchDrawable defaultButtonPatch = new NinePatchDrawable(new NinePatch(Assets.loadingAtlas.findRegion("color"),Color.valueOf("00000000")));
        NinePatchDrawable pressedButtonPatch = new NinePatchDrawable(new NinePatch(Assets.loadingAtlas.findRegion("color"),Color.valueOf("5e5e5e")));
        BitmapFont bitmapFont = Utils.generateFont("MunroSmall.ttf",(int)(stage.getWidth() * 46 / 1280));
        normalButtonStyle = new TextButton.TextButtonStyle(defaultButtonPatch,pressedButtonPatch,defaultButtonPatch,bitmapFont);
        normalButtonStyle.fontColor = Color.valueOf("5e5e5e");
        normalButtonStyle.downFontColor = Color.valueOf("e0e0e0");

        //TODO click sound
        //clickSound = Gdx.audio.newSound(Assets.get("",FileHandle.class));
        //soundClick = new SoundClick();
        music = Gdx.audio.newMusic(Gdx.files.internal("music/main_menu.mp3"));
        music.play();

    }

    private Table getTextButtonTable(){
        return new Table();
    }

    private TextButton getTextButton(String text){
        return new TextButton(text,normalButtonStyle);
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
        music.pause();
    }

    @Override
    public void resume() {
        music.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    private void addButton(Table table, TextButton button){

        table.add(button).width(stage.getWidth()/32*9).padBottom(10f).padLeft(0).padRight(0).row();
        button.getLabel().setAlignment(Align.left);
        button.getLabelCell().padLeft(stage.getWidth() * 16 / 1280);

    }

    private void loadMenu(final MenuOptions menu){
        table.addAction(sequence(Actions.fadeOut(0.5f), new Action() {
            @Override
            public boolean act(float delta) {
                table.clearChildren();
                switch (menu){
                    case MAIN_MENU:
                        showMainMenu();
                        break;
                    case LEVEL_SELECT:
                        showLevelSelect();
                        break;
                }
                return true;
            }
        }, fadeIn(0.5f)));

    }

    private void showMainMenu(){

        //Left Line
        table.add(leftLineActor);

        //Buttons

        Table buttons = getTextButtonTable();

        TextButton gameStartButton = getTextButton("Game start");
        addButton(buttons,gameStartButton);

        gameStartButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadMenu(MenuOptions.LEVEL_SELECT);
            }

        });

        TextButton continueButton = getTextButton("Endless mode");
        addButton(buttons,continueButton);

        TextButton achievementsButton = getTextButton("Achievements");
        addButton(buttons,achievementsButton);

        TextButton settingsButton = getTextButton("Settings");
        addButton(buttons,settingsButton);

        TextButton exitButton = getTextButton("Exit");
        addButton(buttons,exitButton);

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        table.add(buttons).width(stage.getWidth()/3*1).expandY().top().padTop(stage.getWidth() * 200f /1280);

        //Right Line
        table.add(rightLineActor);

    }

    private void showLevelSelect(){

        //Init score items to get access to them from level button listener

        TextButton[] scoreItems = new TextButton[7];

        for(int i = 0; i < scoreItems.length; i++){
            scoreItems[i] = getTextButton((i+1) + ".----------/---------- 9999999999");
        }

        //Levels

        table.add(leftLineActor).left().padLeft( stage.getWidth() * 150 / 1280);

        Table levelButtonsTable = getTextButtonTable();

        TextButton[] levelButtons = new TextButton[7];

        for(int i = 0; i < levelButtons.length; i++){

            levelButtons[i] = getTextButton("Level " + (i+1));
            levelButtons[i].getLabelCell().padLeft(stage.getWidth() * 16 / 1280).padRight(stage.getWidth() * 16 / 1280);
            levelButtonsTable.add(levelButtons[i]).row();

            //TODO update scores

            final int finalI = i;
            levelButtons[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedLevel = (finalI +1);
                }
            });

        }

        TextButton backButton = getTextButton("Return");
        backButton.getLabelCell().padLeft(stage.getWidth() * 16 / 1280).padRight(stage.getWidth() * 16 / 1280);
        levelButtonsTable.add(backButton).expandY().bottom();

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadMenu(MenuOptions.MAIN_MENU);
            }
        });

        table.add(levelButtonsTable).width(stage.getWidth() * 200 / 1280).growY().left().padTop((stage.getWidth() * 130 / 1280)).padBottom((stage.getWidth() * 100 / 1280));

        table.add(rightLineActor).left();

        //Details

        Table detailsTable = getTextButtonTable();

        TextButton globalScoreButton = getTextButton("Scores");
        detailsTable.add(globalScoreButton).padBottom(stage.getWidth() * 50 / 1280).row();

        for (TextButton button : scoreItems)
            detailsTable.add(button).row();

        TextButton playButton = getTextButton("Play");
        playButton.getLabelCell().padLeft(stage.getWidth() * 16 / 1280).padRight(stage.getWidth() * 16 / 1280);
        detailsTable.add(playButton).expandY().bottom();

        table.add(detailsTable).grow().pad(stage.getWidth() * 100 / 1280);

        //Level load

        selectedLevel = 1;
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Screen screen;
                switch (selectedLevel){
                    case 7:
                        screen = new Level7();
                        break;
                    case 6:
                        screen = new Level6();
                        break;
                    case 5:
                        screen = new Level5();
                        break;
                    case 4:
                        screen = new Level4();
                        break;
                    case 3:
                        screen = new Level3();
                        break;
                    case 2:
                        screen = new Level2();
                        break;
                    default:
                    case 1:
                        screen = new Level1();
                        break;
                }
                ScreenManager.getInstance().showScreen(screen);

            }
        });

    }

    private void showEndlessMode(){

    }

    private void showSettings(){

    }

    private enum MenuOptions {
        MAIN_MENU,
        LEVEL_SELECT,
        ENDLESS_MODE,
        SETTINGS,
        EXIT
    }

    private class SoundClick extends ClickListener{
        @Override
        public void clicked(InputEvent event, float x, float y) {
            clickSound.play(1f);
        }
    }

}