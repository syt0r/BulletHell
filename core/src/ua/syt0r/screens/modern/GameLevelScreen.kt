package ua.syt0r.screens.modern

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ua.syt0r.Assets
import ua.syt0r.State
import ua.syt0r.levels.Level
import ua.syt0r.logic.PlayerInput

open class GameLevelScreen(
    val level: Level
) : Screen {

    var state = State.LOADING

    lateinit var gameStage: GameStage
    lateinit var uiStage: UIStage

    override fun show() {
        Assets.load("game.atlas", TextureAtlas::class.java)

        gameStage = GameStage(level)
        uiStage = UIStage(gameStage.viewport)

        uiStage.showLoading()

        Gdx.input.inputProcessor = object : InputAdapter() {

            override fun keyDown(keycode: Int): Boolean {
                return when (keycode) {
                    Input.Keys.LEFT -> {
                        PlayerInput.input.x = -1f
                        true
                    }
                    Input.Keys.RIGHT -> {
                        PlayerInput.input.x = 1f
                        true
                    }
                    Input.Keys.UP -> {
                        PlayerInput.input.y = 1f
                        true
                    }
                    Input.Keys.DOWN -> {
                        PlayerInput.input.y = -1f
                        true
                    }
                    Input.Keys.SPACE -> {
                        PlayerInput.isFiring = true
                        gameStage.startFire()
                        true
                    }
                    else -> false
                }
            }

            override fun keyUp(keycode: Int): Boolean {
                return when (keycode) {
                    Input.Keys.LEFT, Input.Keys.RIGHT -> {
                        PlayerInput.input.x = 0f
                        true
                    }
                    Input.Keys.UP, Input.Keys.DOWN -> {
                        PlayerInput.input.y = 0f
                        true
                    }

                    Input.Keys.SPACE -> {
                        PlayerInput.isFiring = false
                        gameStage.stopFire()
                        true
                    }
                    else -> false
                }
            }

        }
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.878f, 0.878f, 0.878f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        when (state) {
            State.LOADING -> {
                uiStage.act(delta)
                uiStage.viewport.apply()
                uiStage.draw()

                if (Assets.update()) {
                    state = State.GAME
                    uiStage.hideLoading()
                    uiStage.showGameUI()
                }
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