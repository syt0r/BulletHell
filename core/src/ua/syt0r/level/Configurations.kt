package ua.syt0r.level

import com.badlogic.gdx.math.Vector2

data class EnemyConfiguration(
    val size: Vector2,
    val textureRegionName: String,
    val initialPosition: Vector2,
    val hp: Int = 1
)

class BulletConfiguration(
    val friendly: Boolean,
    val size: Vector2,
    val textureRegionName: String,
    val speed: Float
)