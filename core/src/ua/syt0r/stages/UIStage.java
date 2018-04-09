package ua.syt0r.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ua.syt0r.State;
import ua.syt0r.actors.entities.Player;
import ua.syt0r.actors.ui.*;
import ua.syt0r.screens.GameScreen;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UIStage extends Stage {

    private GameScreen gameScreen;

    private LoadingActor loadingActor;

    private HealthActor playerHealth;

    private MovementControlActor movementControlActor;
    private ClickListener movementClickListener;

    private FireControlActor fireControlActor;
    private ClickListener fireClickListener;

    private ImageButton pauseButton;
    private ClickListener pauseClickListener;

    private Table pauseMenu;
    private PauseActor pauseActor;

    private ClickListener keyboardListener;



    public UIStage(GameScreen gameScreen){

        this.gameScreen = gameScreen;

        setViewport(new ScreenViewport());

    }

    public void showLoading(){

        loadingActor = new LoadingActor();
        addActor(loadingActor);

    }

    public void showGameUI(){

        loadingActor.remove();

        TextureAtlas atlas = gameScreen.getTextureAtlas();
        GameStage gameStage = gameScreen.getGameStage();
        Player player = gameStage.getPlayer();

        addActor(new BackgroundColorActor(BackgroundColorActor.LEFT_SIDE,gameStage.getViewport(),gameStage.getVirtualWidth(),gameStage.getVirtualHeight()));
        addActor(new BackgroundColorActor(BackgroundColorActor.RIGHT_SIDE,gameStage.getViewport(),gameStage.getVirtualWidth(),gameStage.getVirtualHeight()));

        movementControlActor = new MovementControlActor(atlas);
        movementControlActor.resize(gameStage.getViewport(), gameStage.getVirtualWidth(), gameStage.getVirtualHeight());
        movementClickListener = new DPadInput(movementControlActor, player);
        movementControlActor.addListener(movementClickListener);
        addActor(movementControlActor);

        fireControlActor = new FireControlActor(atlas);
        fireControlActor.resize(gameStage.getViewport(), gameStage.getVirtualWidth(), gameStage.getVirtualHeight());
        fireClickListener = new FireButtonInput(player);
        fireControlActor.addListener(fireClickListener);
        addActor(fireControlActor);

        Drawable pauseDefault = new SpriteDrawable(new Sprite(atlas.findRegion("pause_button")));
        Drawable pausePressed = new SpriteDrawable(new Sprite(atlas.findRegion("pause_button_pressed")));
        pauseButton = new ImageButton(pauseDefault,pausePressed);
        pauseClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.setState(State.PAUSE);
            }
        };
        pauseButton.addListener(pauseClickListener);
        pauseButton.setSize(Gdx.graphics.getHeight()/6,Gdx.graphics.getHeight()/6);
        pauseButton.setPosition(Gdx.graphics.getWidth() - pauseButton.getWidth(), Gdx.graphics.getHeight() - pauseButton.getHeight());
        addActor(pauseButton);

        playerHealth = new HealthActor(gameStage.getViewport(), gameStage.getVirtualWidth(), gameStage.getVirtualHeight(), Player.MAX_HEALTH,Player.MAX_HEALTH);
        playerHealth.setEntity(player);
        addActor(playerHealth);

        keyboardListener = new KeyboardInput(player);
        addListener(keyboardListener);

    }

    public void showPauseMenu(){

        movementControlActor.removeListener(movementClickListener);
        fireControlActor.removeListener(fireClickListener);
        pauseButton.removeListener(pauseClickListener);
        removeListener(keyboardListener);

        pauseActor = new PauseActor(this);
        pauseActor.setBounds(0,0,getWidth(),getHeight());
        addActor(pauseActor);
        pauseActor.show();
        pauseActor.addListener(new ClickListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                gameScreen.setState(State.GAME);
                return super.keyUp(event, keycode);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gameScreen.setState(State.GAME);
                super.touchUp(event, x, y, pointer, button);
            }
        });

    }

    public void hidePauseMenu(){

        movementControlActor.addListener(movementClickListener);
        fireControlActor.addListener(fireClickListener);
        pauseButton.addListener(pauseClickListener);
        addListener(keyboardListener);

        pauseActor.remove();

    }



    private class KeyboardInput extends ClickListener{

        private Player player;
        private Set<Integer> pressed;

        KeyboardInput(Player player){

            this.player = player;
            pressed = new HashSet<Integer>();

        }

        @Override
        public boolean keyDown(InputEvent event, int keycode) {

            Vector2 playerVelocity = player.getVelocity();

            switch (keycode){
                case com.badlogic.gdx.Input.Keys.LEFT:
                    player.setVelocity(-1f,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                    player.setVelocity(1f,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                    player.setVelocity(playerVelocity.x,1f);
                    break;
                case com.badlogic.gdx.Input.Keys.DOWN:
                    player.setVelocity(playerVelocity.x,-1f);
                    break;
                case com.badlogic.gdx.Input.Keys.Z:
                    player.setFiring(true);
                    player.setLastFireTime(3f);
                    break;
            }

            pressed.add(keycode);

            return false;

        }

        @Override
        public boolean keyUp(InputEvent event,int keycode) {

            Vector2 playerVelocity = player.getVelocity();

            switch (keycode){
                case com.badlogic.gdx.Input.Keys.LEFT:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.RIGHT))
                        player.setVelocity(1f,playerVelocity.y);
                    else
                        player.setVelocity(0,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.RIGHT:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.LEFT))
                        player.setVelocity(-1f,playerVelocity.y);
                    else
                        player.setVelocity(0,playerVelocity.y);
                    break;
                case com.badlogic.gdx.Input.Keys.UP:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.DOWN))
                        player.setVelocity(playerVelocity.x,-1f);
                    else
                        player.setVelocity(playerVelocity.x,0);
                    break;
                case com.badlogic.gdx.Input.Keys.DOWN:
                    if (pressed.contains(com.badlogic.gdx.Input.Keys.UP))
                        player.setVelocity(playerVelocity.x,1f);
                    else
                        player.setVelocity(playerVelocity.x,0);
                    break;
                case Input.Keys.Z:
                    player.setFiring(false);
                    break;
            }

            pressed.remove(keycode);

            return false;

        }

    }

    private class DPadInput extends ClickListener{

        private Player player;

        private MovementControlActor movementControlActor;
        private Vector2 up,down,left,right,upLeft,upRight,downLeft,downRight;

        DPadInput(MovementControlActor movementControlActor, Player player){

            this.movementControlActor = movementControlActor;
            this.player = player;

            float width = movementControlActor.getWidth();
            float height = movementControlActor.getHeight();

            up = new Vector2(width/2,height/4*3);
            down = new Vector2(width/2,height/4);
            left = new Vector2(width/4,height/2);
            right = new Vector2(width/4*3,height/2);

            /*
            upLeft = new Vector2(width/8*3,height/8*5);
            upRight = new Vector2(width/8*5,height/8*5);
            downLeft = new Vector2(width/8*3,height/8*3);
            downRight = new Vector2(width/8*5,height/8*3);
            */

            upLeft = new Vector2(width/4,height/4*3);
            upRight = new Vector2(width/4*3,height/4*3);
            downLeft = new Vector2(width/4,height/4);
            downRight = new Vector2(width/4*3,height/4);

        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            calculateDistance(x,y);
            return super.touchDown(event, x, y, pointer, button);
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            calculateDistance(x,y);
            super.touchDragged(event, x, y, pointer);
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            player.setVelocity(0,0);
            movementControlActor.setPressedButtons(false,false,false,false);
            super.touchUp(event, x, y, pointer, button);
        }

        private void calculateDistance(float x, float y){

            float upDistance,downDistance,leftDistance,rightDistance,
                    upLeftDistance,upRightDistance,downLeftDistance,downRightDistance;

            upDistance = distance(x,y,up.x,up.y);
            downDistance = distance(x,y,down.x,down.y);
            leftDistance = distance(x,y,left.x,left.y);
            rightDistance = distance(x,y,right.x,right.y);
            upLeftDistance = distance(x,y,upLeft.x,upLeft.y);
            upRightDistance = distance(x,y,upRight.x,upRight.y);
            downLeftDistance = distance(x,y,downLeft.x,downLeft.y);
            downRightDistance = distance(x,y,downRight.x,downRight.y);

            float min = Collections.min(Arrays.asList(upDistance,downDistance,leftDistance,rightDistance,
                    upLeftDistance,upRightDistance,downLeftDistance,downRightDistance));

            if (upDistance ==  min){
                player.setVelocity(0,1f);
                movementControlActor.setPressedButtons(true,false,false,false);
            }else if (downDistance == min){
                player.setVelocity(0,-1f);
                movementControlActor.setPressedButtons(false,true,false,false);
            }else if (leftDistance == min){
                player.setVelocity(-1f,0);
                movementControlActor.setPressedButtons(false,false,true,false);
            }else if (rightDistance == min){
                player.setVelocity(1f,0);
                movementControlActor.setPressedButtons(false,false,false,true);
            }else if (upLeftDistance == min){
                player.setVelocity(-1f,1f);
                movementControlActor.setPressedButtons(true,false,true,false);
            }else if (upRightDistance == min){
                player.setVelocity(1f,1f);
                movementControlActor.setPressedButtons(true,false,false,true);
            }else if (downLeftDistance == min){
                player.setVelocity(-1f,-1f);
                movementControlActor.setPressedButtons(false,true,true,false);
            }else if (downRightDistance == min){
                player.setVelocity(1f,-1f);
                movementControlActor.setPressedButtons(false,true,false,true);
            }

        }

        private float distance(float x1, float y1, float x2, float y2){
            return (float)Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        }

    }

    private class FireButtonInput extends ClickListener{

        private Player player;

        FireButtonInput(Player player){
            this.player = player;
        }

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

            player.setFiring(true);
            player.setLastFireTime(3f);

            return super.touchDown(event, x, y, pointer, button);

        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            player.setFiring(false);
            super.touchUp(event, x, y, pointer, button);

        }
    }

}
