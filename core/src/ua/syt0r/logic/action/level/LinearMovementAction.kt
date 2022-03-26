package ua.syt0r.logic.action.level

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action

class LinearMovementAction(
    private val direction: Vector2
) : Action() {

    override fun act(delta: Float): Boolean {
        actor.x += direction.x * delta
        actor.y += direction.y * delta
        return false
    }

}