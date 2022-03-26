package ua.syt0r.logic

import com.badlogic.gdx.math.Vector2

class LinearMovement(
    private val direction: Vector2
) : MovementLogic {

    override fun updatePosition(currentPosition: Vector2, delta: Float) {
        currentPosition.x = currentPosition.x + direction.x * delta
        currentPosition.y = currentPosition.y + direction.y * delta
    }

}