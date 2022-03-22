package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.clamp

class UpdatePlayerMovementAction : Action() {

    companion object {
        private const val SPEED = 400f
    }

    override fun act(delta: Float): Boolean {
        val desiredX = actor.x + PlayerInput.input.x * delta * SPEED
        val desiredY = actor.y + PlayerInput.input.y * delta * SPEED
        actor.x = desiredX.clamp(0f, GameLogic.WORLD_WIDTH - actor.width)
        actor.y = desiredY.clamp(0f, GameLogic.WORLD_HEIGHT - actor.height)
        return false
    }

}