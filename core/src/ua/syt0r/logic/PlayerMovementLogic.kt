package ua.syt0r.logic

import com.badlogic.gdx.math.Vector2
import ua.syt0r.clamp
import ua.syt0r.patterns.MovementLogic

object PlayerMovementLogic : MovementLogic {

    private const val SPEED = 200f

    override fun updatePosition(currentPosition: Vector2, delta: Float) {
        val desiredX = currentPosition.x + PlayerInput.input.x * delta * SPEED
        val desiredY = currentPosition.y + PlayerInput.input.y * delta * SPEED
//        currentPosition.set(
//            desiredX.clamp(0f, GameLogic.WORLD_WIDTH),
//            desiredY.clamp(0f, GameLogic.WORLD_HEIGHT)
//        )
    }

}