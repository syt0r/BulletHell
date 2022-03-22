package ua.syt0r;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

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

    public void showScreen(Screen screen) {

        // Get current screen to dispose it
        Screen currentScreen = game.getScreen();

        // Show new screen
        game.setScreen(screen);

        // Dispose previous screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }

}