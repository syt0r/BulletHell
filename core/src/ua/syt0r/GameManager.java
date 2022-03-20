package ua.syt0r;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.FPSLogger;

public class GameManager extends Game {

    private FPSLogger fpsLogger;

    @Override
    public void create() {

        Assets.loadLoadingScreenAssets();

        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenManager.ScreenEnum.MAIN_MENU);

        fpsLogger = new FPSLogger();
    }

    @Override
    public void render() {
        super.render();
        fpsLogger.log();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

}
