package ua.syt0r.level

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ua.syt0r.logic.SpawnWaveAction
import ua.syt0r.logic.WaitForAllEnemiesKillAction

object LevelActionConverter {

    fun convert(level: Level): Action {
        val s = Actions.sequence()

        s.addAction(Actions.delay(2f))

        level.waves.forEach {
            s.addAction(SpawnWaveAction(it))
            s.addAction(WaitForAllEnemiesKillAction())
        }

        return s
    }

}