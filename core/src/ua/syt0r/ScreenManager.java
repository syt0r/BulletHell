package ua.syt0r;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import ua.syt0r.screens.GameLevelScreen;
import ua.syt0r.screens.MainMenuScreen;

public class ScreenManager {

    private static ScreenManager instance;

    private Game game;

    private ScreenManager() {
        super();
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public void initialize(Game game) {
        this.game = game;
    }

    public void showScreen(ScreenEnum screenEnum) {

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        Screen newScreen = screenEnum.getScreen();
        game.setScreen(newScreen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }

    public void showScreen(Screen screen){

        Screen currentScreen = game.getScreen();

        game.setScreen(screen);

        if (currentScreen != null) {
            currentScreen.dispose();
        }

    }

    public enum ScreenEnum {

        MAIN_MENU{
            public Screen getScreen(){
                return new MainMenuScreen();
            }
        },

        GAME{
            public Screen getScreen(){
                return new GameLevelScreen();
            }
        };

        public abstract Screen getScreen();

    }

}