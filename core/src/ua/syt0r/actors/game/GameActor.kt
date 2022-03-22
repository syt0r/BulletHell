package ua.syt0r.actors.game

import com.badlogic.gdx.graphics.g2d.TextureRegion
import ua.syt0r.logic.HealthChangeListener

class GameActor(
    var maxHealth: Int = 1,
    var health: Int = maxHealth,
    texture: TextureRegion,
    collisionBody: CollisionBody
) : PhysicalSpriteActor(texture, collisionBody) {

    val healthChangeListeners = mutableSetOf<HealthChangeListener>()

    fun damage() {
        val oldHealth = health
        health--
        healthChangeListeners.forEach { it.onHealthChanged(oldHealth, health) }
    }

}