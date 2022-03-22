package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.level.EnemyConfiguration
import ua.syt0r.screens.game.stage.GameStage

class SpawnEnemyAction(
    val configuration: EnemyConfiguration
) : Action() {

    override fun act(delta: Float): Boolean {
        (actor.stage as GameStage).spawnEnemy(configuration)
        return true
    }

}