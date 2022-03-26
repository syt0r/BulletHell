package ua.syt0r.logic.shoot

import com.badlogic.gdx.math.Vector2
import ua.syt0r.level.BulletConfiguration

interface ShootingScope {

    fun shoot(
        bulletConfiguration: BulletConfiguration,
        spawnPosition: Vector2,
        velocity: Vector2
    )

}
