package ua.syt0r.logic.action.level

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.level.Wave
import ua.syt0r.screens.game.GameField

class SpawnWaveAction(
    private val gameField: GameField,
    private val wave: Wave
) : Action() {

    override fun act(delta: Float): Boolean {
        wave.enemies.forEach {
            gameField.addEnemy(it)
        }
        return true
    }

}