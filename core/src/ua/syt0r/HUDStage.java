package ua.syt0r;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by Yaroslav on 2018/01/21.
 */

public class HUDStage extends Stage {

    private SpriteBatch spriteBatch;

    private Vector2 moveCenter;
    private Vector2 touchPos;

    Texture moveOutline;
    Texture stick;

    public HUDStage(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;
        moveCenter = new Vector2(213,478);
        touchPos = new Vector2(0,0);

        moveOutline = new Texture("move_outline.png");
        stick = new Texture("move_stick.png");

        Gdx.input.setInputProcessor(new Input());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw() {
        super.draw();
        spriteBatch.setProjectionMatrix(getViewport().getCamera().combined);
        spriteBatch.begin();

        spriteBatch.draw(moveOutline,moveCenter.x,moveCenter.y,160,160);

        spriteBatch.end();
    }

    class Input implements InputProcessor{

        boolean isTouching = false;

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
            isTouching = true;
            touchPos.set(screenX,screenY);
            prnt(screenX,screenY);
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            isTouching = false;
            touchPos.set(moveCenter);
            prnt(screenX,screenY);
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if(isInCircle(moveCenter,3, touchPos))
                touchPos.set(screenX,screenY);
            else
                touchPos.set(moveCenter);
            prnt(screenX,screenY);
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

        private boolean isInCircle(Vector2 circleCenter, float circleRadius, Vector2 point){
            return Math.sqrt(Math.pow(point.x-circleCenter.x,2)+Math.pow(point.y-circleCenter.y,2)) < circleRadius;
        }

        private void prnt(int x, int y){
            System.out.println(x+" "+y);
        }

    }

}
