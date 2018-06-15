package ua.syt0r.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import ua.syt0r.*;
import ua.syt0r.actors.entities.Bullet;
import ua.syt0r.actors.entities.Boss;
import ua.syt0r.actors.entities.Enemy;
import ua.syt0r.actors.ui.MovementControlActor;
import ua.syt0r.stages.GameStage;
import ua.syt0r.stages.UIStage;

import java.util.*;

public class GameScreen implements GameScreenInterface {

    private State state;

    private TextureAtlas textureAtlas;

    private GameStage gameStage;
    private UIStage uiStage;

    //Sound

    private Music music;

    @Override
    public void show() {

        state = State.LOADING;

        Assets.load("game.atlas",TextureAtlas.class);
        Assets.load("something.mp3",Music.class);

        gameStage = new GameStage(this);
        uiStage = new UIStage(this);
        uiStage.showLoading();

        preLoad();

    }

    @Override
    public void render(float delta) {

        if (state == State.LOADING){

            loading();
            return;

        } else if (state == State.GAME){

            gameStage.act(delta);

        }

        uiStage.act(delta);

        //Draw

        Gdx.gl.glClearColor(0.878f , 0.878f , 0.878f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Game

        gameStage.getViewport().apply();
        gameStage.draw();

        //UI
        uiStage.getViewport().apply();
        uiStage.draw();

    }

    @Override
    public void resize(int width, int height) {

        gameStage.getViewport().update(width, height,true);
        uiStage.getViewport().update(width,height,true);

    }

    @Override
    public void pause() {
        if (music!=null)
            music.pause();
    }

    @Override
    public void resume() {
        if (music!=null)
            music.play();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        Assets.unload("game.atlas");
        Assets.unload("something.mp3");

        if (music!=null && music.isPlaying())
            music.dispose();

    }


    private void loading(){

        if (Assets.update()){

            textureAtlas = Assets.get("game.atlas",TextureAtlas.class);

            state = State.GAME;

            gameStage.init();
            uiStage.showGameUI();

            init();

            //Controls

            Gdx.input.setInputProcessor(uiStage);

            return;

        }

        System.out.println("Loading...");

    }

    public void setState(State state){
        switch (state){
            case PAUSE:
                uiStage.showPauseMenu();
                break;
            case GAME:
                if (this.state == State.PAUSE)
                    uiStage.hidePauseMenu();
                break;
            case DEAD:
                uiStage.showDeadMenu();
                break;
            case WIN:
                uiStage.showWinMenu();
                break;
        }
        this.state = state;
    }

    public GameStage getGameStage() {
        return gameStage;
    }

    public UIStage getUiStage() {
        return uiStage;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    //Available interface for stages

    public void preLoad(){

    }

    public void init(){

    }

    public void LoadMusic(String path){
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void LinearShot(float x, float y, float speed){
        gameStage.LinearShot(x,y,speed);
    }
    @Override
    public void RadialShot(float x, float y, float angle, float speed){
        gameStage.RadialShot(x,y,angle,speed);
    }

    @Override
    public void DirectionalShot(float x, float y, float speed, float targetX, float targetY) {
        gameStage.DirectionalShot(x,y,speed,targetX,targetY);
    }

    @Override
    public void addEnemy(Enemy enemy, int spawnFrame){

        gameStage.addEnemy(enemy,spawnFrame);

    }
    @Override
    public void setBoss(Boss boss){
        gameStage.setBoss(boss);
    }

}