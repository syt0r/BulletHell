package ua.syt0r.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ua.syt0r.actors.ui.LoadingActor;

public class LoadingScreen implements Screen {

    private Screen screenToLoad;

    private Viewport viewport;
    private Stage stage;

    public LoadingScreen(Screen screenToLoad){

        this.screenToLoad = screenToLoad;

    }

    @Override
    public void show() {

        viewport = new ScreenViewport();
        stage = new Stage(viewport);

        stage.addActor(new LoadingActor());

    }

    @Override
    public void render(float delta) {

        viewport.apply();
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
}
