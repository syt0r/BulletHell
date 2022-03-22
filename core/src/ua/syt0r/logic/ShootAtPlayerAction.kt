package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import ua.syt0r.screens.game.stage.GameStage
import kotlin.math.atan2

class ShootAtPlayerAction(
    private val player: Actor
) : Action() {

    override fun act(delta: Float): Boolean {
        val angle = Math.toDegrees(
            atan2(
                (player.y - actor.y).toDouble(),
                (player.x - actor.x).toDouble()
            )
        ).toFloat() - 45
        (actor.stage as GameStage)
        return true
    }

}