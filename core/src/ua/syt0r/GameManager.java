package ua.syt0r;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class GameManager extends Game {

	public static AssetManager assetManager;

	private FPSLogger fpsLogger;
	
	@Override
	public void create () {

		assetManager = new AssetManager();

		assetManager.load("loading.atlas", TextureAtlas.class);
		assetManager.load("ui.atlas", TextureAtlas.class);
		assetManager.load("game.atlas", TextureAtlas.class);

		assetManager.finishLoading();

		ScreenManager.getInstance().initialize(this);
		ScreenManager.getInstance().showScreen(ScreenManager.ScreenEnum.MAIN_MENU);

		//Assets.loadLoadingScreenAssets();

		fpsLogger = new FPSLogger();
	}

	@Override
	public void render () {

		super.render();
		fpsLogger.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

}
