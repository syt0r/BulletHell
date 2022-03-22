package ua.syt0r.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import ua.syt0r.Assets
import ua.syt0r.State
import ua.syt0r.level.Level
import ua.syt0r.screens.game.stage.GameStage
import ua.syt0r.screens.game.stage.UIStage

open class GameLevelScreen(
    val level: Level
) : Screen {

    var state = State.LOADING

    lateinit var gameStage: GameStage
    lateinit var uiStage: UIStage

    override fun show() {
        Assets.loadGameAssets()

        gameStage = GameStage(level)
        uiStage = UIStage(gameStage)

        uiStage.showLoading()

        Gdx.input.inputProcessor = GameKeyboardInput(gameStage)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.878f, 0.878f, 0.878f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        when (state) {
            State.LOADING -> {
                uiStage.act(delta)
                uiStage.viewport.apply()
                uiStage.draw()

                state = State.GAME
                gameStage.act(delta)
                uiStage.hideLoading()
                uiStage.showGameUI()

            }
            State.GAME -> {
                gameStage.act(delta)
                uiStage.act(delta)

                gameStage.viewport.apply()
                gameStage.draw()

                uiStage.viewport.apply()
                uiStage.draw()
            }
        }

    }

    override fun resize(width: Int, height: Int) {
        gameStage.viewport.update(width, height, true)
        uiStage.viewport.update(width, height, true)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun hide() {

    }

    override fun dispose() {

    }

}