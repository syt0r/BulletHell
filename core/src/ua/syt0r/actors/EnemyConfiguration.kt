package ua.syt0r.actors

import com.badlogic.gdx.math.Vector2
import ua.syt0r.patterns.MovementLogic

data class EnemyConfiguration(
    val size: Vector2,
    val textureRegionName: String,
    val initialPosition: Vector2,
    val hp: Int = 1
)