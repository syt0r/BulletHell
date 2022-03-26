package ua.syt0r.level

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import ua.syt0r.Assets
import ua.syt0r.actors.game.GameActor
import ua.syt0r.logic.CollisionBody
import ua.syt0r.logic.MovementLogic
import ua.syt0r.logic.StationaryMovement
import ua.syt0r.logic.shoot.ShootingLogic

data class EnemyConfiguration(
    val size: Vector2,
    val textureRegionName: String,
    val initialPosition: Vector2,
    val hp: Int = 1,
    val movementLogic: MovementLogic = StationaryMovement,
    val shootingLogic: ShootingLogic = ShootingLogic {

        wait(1f)

        shoot { enemy ->
            shoot(
                enemyBulletConfiguration,
                Vector2(enemy.run { x + width / 2 - enemyBulletConfiguration.size.x / 2 }, enemy.run { y - height }),
                Vector2(0f, -300f)
            )
        }

    }
) {

    fun createActor(): GameActor {
        return GameActor(
            maxHealth = hp,
            texture = Assets.gameAtlas.findRegion(textureRegionName),
            collisionBody = CollisionBody.CircleCollision(Circle(0f, 0f, 20f))
        ).apply {

            width = size.x
            height = size.y

            x = initialPosition.x
            y = initialPosition.y

        }
    }

}




