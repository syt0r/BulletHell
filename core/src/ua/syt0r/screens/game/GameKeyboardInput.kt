package ua.syt0r.screens.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import ua.syt0r.logic.PlayerInput
import ua.syt0r.screens.game.stage.GameStage

class GameKeyboardInput(
    private val gameStage: GameStage
) : InputAdapter() {

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
                gameStage.startFire()
                true
            }
            else -> false
        }
    }

    override fun keyUp(keycode: Int): Boolean {
        return when (keycode) {
            Input.Keys.LEFT -> {
                if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT)) PlayerInput.input.x = 0f
                true
            }
            Input.Keys.RIGHT -> {
                if (!Gdx.input.isKeyPressed(Input.Keys.LEFT)) PlayerInput.input.x = 0f
                true
            }
            Input.Keys.UP -> {
                if (!Gdx.input.isKeyPressed(Input.Keys.DOWN)) PlayerInput.input.y = 0f
                true
            }
            Input.Keys.DOWN -> {
                if (!Gdx.input.isKeyPressed(Input.Keys.UP)) PlayerInput.input.y = 0f
                true
            }

            Input.Keys.SPACE -> {
                gameStage.stopFire()
                true
            }
            else -> false
        }
    }

}