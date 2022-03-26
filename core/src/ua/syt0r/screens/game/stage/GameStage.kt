package ua.syt0r.screens.game.stage

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.utils.viewport.FitViewport
import ua.syt0r.Assets
import ua.syt0r.actors.game.GameActor
import ua.syt0r.actors.game.createPlayer
import ua.syt0r.level.Level
import ua.syt0r.level.LevelActionConverter
import ua.syt0r.level.playerBulletConfiguration
import ua.syt0r.logic.action.level.FirePlayerBulletAction
import ua.syt0r.logic.GameLogic
import ua.syt0r.logic.action.PausableAction
import ua.syt0r.logic.action.level.CheckCollisionAction
import ua.syt0r.logic.action.level.UpdatePlayerMovementAction
import ua.syt0r.screens.game.GameField

class GameStage(
    level: Level
) : Stage() {

    val player: GameActor

    private val playerFireAction: PausableAction

    init {
        viewport = FitViewport(GameLogic.WORLD_WIDTH, GameLogic.WORLD_HEIGHT)
        isDebugAll = true

        player = createPlayer(Assets.gameAtlas)
        player.addAction(
            Actions.sequence(
                Actions.moveBy(0f, 60f, 1f, Interpolation.smooth),
                UpdatePlayerMovementAction()
            )
        )
        addActor(player)

        val gameField = GameField(this, player)

        addAction(LevelActionConverter.convert(level, gameField))
        addAction(CheckCollisionAction(gameField))

        playerFireAction = PausableAction(
            action = Actions.forever(
                Actions.sequence(
                    FirePlayerBulletAction(gameField, playerBulletConfiguration), Actions.delay(0.1f)
                )
            ),
            isInitiallyPaused = true
        )
        player.addAction(playerFireAction)
    }

    fun startFire() {
        playerFireAction.isPaused = false
    }

    fun stopFire() {
        playerFireAction.isPaused = true
    }

}
