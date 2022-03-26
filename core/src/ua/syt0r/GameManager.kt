package ua.syt0r

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.FPSLogger
import ua.syt0r.Assets.loadLoadingScreenAssets
import ua.syt0r.screens.main.MainMenuScreen

class GameManager(
    private val initialScreenCreator: () -> Screen = { MainMenuScreen() }
) : Game() {

    private lateinit var fpsLogger: FPSLogger

    override fun create() {
        loadLoadingScreenAssets()
        ScreenManager.getInstance().initialize(this)
        ScreenManager.getInstance().showScreen(initialScreenCreator())
        fpsLogger = FPSLogger()
    }

    override fun render() {
        super.render()
        fpsLogger.log()
    }

    override fun dispose() {
        super.dispose()
        Assets.dispose()
    }

}
