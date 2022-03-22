package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.level.Wave
import ua.syt0r.screens.game.stage.GameStage

class SpawnWaveAction(
    val wave: Wave
) : Action() {

    override fun act(delta: Float): Boolean {
        wave.enemies.forEach {
            (actor.stage as GameStage).spawnEnemy(it)
        }
        return true
    }

}