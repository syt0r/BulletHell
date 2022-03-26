package ua.syt0r.logic.shoot

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ua.syt0r.actors.game.GameActor

class ShootingLogic(
    initializer: ShootingLogic.() -> Unit = {}
) {

    private val shootingActions = mutableListOf<ShootingAction>()

    init {
        initializer()
    }

    fun shoot(shootingScope: ShootingScope.(enemy: GameActor) -> Unit) {
        shootingActions.add(ShootingAction.Shoot(shootingScope))
    }

    fun wait(time: Float) {
        shootingActions.add(ShootingAction.Wait(time))
    }

    fun toAction(shootingScope: ShootingScope): Action {
        return Actions.forever(
            Actions.sequence().apply {
                shootingActions.forEach {
                    val action = when (it) {
                        is ShootingAction.Wait -> it.toAction()
                        is ShootingAction.Shoot -> it.toAction(shootingScope)
                    }
                    addAction(action)
                }
            }
        )
    }

}
