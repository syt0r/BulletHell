package ua.syt0r.desktop

import com.badlogic.gdx.Screen
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import ua.syt0r.GameManager
import ua.syt0r.level.level1
import ua.syt0r.screens.game.GameLevelScreen
import ua.syt0r.screens.main.MainMenuScreen

object DesktopLauncher {

    @JvmStatic
    fun main(arg: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()

        config.setForegroundFPS(60)
        config.setWindowedMode(1280, 720)
        config.setResizable(false)

        val initialScreenCreator: () -> Screen = when (arg.firstOrNull()) {
            "1" -> {
                { GameLevelScreen(level1()) }
            }
            else -> {
                { MainMenuScreen() }
            }
        }

        Lwjgl3Application(GameManager(initialScreenCreator), config)
    }

}