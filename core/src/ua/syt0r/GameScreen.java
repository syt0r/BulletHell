package ua.syt0r;

import com.badlogic.gdx.Screen;

/**
 * Created by Yaroslav on 2018/01/19.
 */

public class GameScreen implements Screen {

    private GdxGame gdxGame;

    private GameStage gameStage;
    private HUDStage hudStage;

    public GameScreen(GdxGame gdxGame){

        this.gdxGame = gdxGame;

    }

    @Override
    public void show() {
        gameStage = new GameStage(gdxGame);
        hudStage = new HUDStage(gdxGame.getSpriteBatch());
    }

    @Override
    public void render(float delta) {
        gameStage.act(delta);
        hudStage.act(delta);
        gameStage.getViewport().apply();
        gameStage.draw();
        hudStage.getViewport().apply();
        hudStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameStage.getViewport().update(width, height,false);
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




}
