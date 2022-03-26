package ua.syt0r.level

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ua.syt0r.logic.action.level.SpawnWaveAction
import ua.syt0r.logic.action.level.WaitForAllEnemiesKillAction
import ua.syt0r.screens.game.GameField

object LevelActionConverter {

    fun convert(level: Level, gameField: GameField): Action {
        val s = Actions.sequence()

        s.addAction(Actions.delay(2f))

        level.waves.forEach {
            s.addAction(SpawnWaveAction(gameField, it))
            s.addAction(WaitForAllEnemiesKillAction())
        }

        return s
    }

}