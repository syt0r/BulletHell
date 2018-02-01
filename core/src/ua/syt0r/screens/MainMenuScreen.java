package ua.syt0r.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ua.syt0r.actors.ui.MenuButtonActor;
import ua.syt0r.levels.Stage1;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private MenuButtonActor buttonActor;

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        //stage.setDebugAll(true);


        Texture defaultTexture = new Texture("start_default.png");
        Texture pressedTexture = new Texture("start_pressed.png");
        buttonActor = new MenuButtonActor(stage, defaultTexture, pressedTexture);
        stage.addActor(buttonActor);

        Drawable drawable = new SpriteDrawable(new Sprite(new Texture("logo.png")));
        ImageButton logo = new ImageButton(drawable,drawable);
        float width = stage.getWidth()*(3f/4);
        float height = stage.getHeight()*(1f/2);
        logo.setBounds(stage.getWidth()/2-width/2,stage.getHeight()/2-height/4,width,height);
        stage.addActor(logo);

        Gdx.input.setInputProcessor(new Input());

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

    class Input implements InputProcessor{

        Camera camera;

        Input(){
            camera = stage.getCamera();
        }

        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {

            Vector3 touchPos = camera.unproject(new Vector3(screenX,screenY,0));

            if (buttonActor.getBoundaries().contains(touchPos.x,touchPos.y)){
                buttonActor.setIsPressed(true);
            }

            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {

            Vector3 touchPos = camera.unproject(new Vector3(screenX,screenY,0));

            if (buttonActor.getBoundaries().contains(touchPos.x,touchPos.y)){
                ScreenManager.getInstance().showScreen(new Stage1());
            }
            buttonActor.setIsPressed(false);
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;
        }

        @Override
        public boolean scrolled(int amount) {
            return false;
        }
    }

}
