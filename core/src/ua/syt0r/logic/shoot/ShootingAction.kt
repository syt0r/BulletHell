package ua.syt0r.logic.shoot

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ua.syt0r.actors.game.GameActor

sealed class ShootingAction {

    class Shoot(
        val shootingFunction: ShootingScope.(enemy: GameActor) -> Unit
    ) : ShootingAction() {

        fun toAction(shootingScope: ShootingScope): Action {
            return object : Action() {
                override fun act(delta: Float): Boolean {
                    shootingFunction.invoke(
                        shootingScope,
                        actor as GameActor
                    )
                    return true
                }
            }
        }

    }

    class Wait(val time: Float) : ShootingAction() {

        fun toAction() = Actions.delay(time)

    }
}