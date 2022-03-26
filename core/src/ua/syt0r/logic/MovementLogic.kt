package ua.syt0r.logic

import com.badlogic.gdx.math.Vector2

interface MovementLogic {

    fun updatePosition(currentPosition: Vector2, delta: Float)

}