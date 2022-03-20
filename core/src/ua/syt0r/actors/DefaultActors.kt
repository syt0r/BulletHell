package ua.syt0r.actors

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Vector2
import ua.syt0r.actors.entities.CollisionBody
import ua.syt0r.actors.entities.PhysicalSpriteActor
import ua.syt0r.logic.GameLogic

fun getPlayer(textureAtlas: TextureAtlas): PhysicalSpriteActor {
    return PhysicalSpriteActor(
        texture = textureAtlas.findRegion("player2"),
        collisionBody = CollisionBody.CircleCollision(
            Circle(0f, 0f, 20f)
        )
    ).apply {
        width = 80f
        height = 80f
        x = GameLogic.WORLD_WIDTH / 2
        y = -40f
    }
}

val enemyConfig = EnemyConfiguration(
    size = Vector2(80f, 80f),
    textureRegionName = "enemy1",
    initialPosition = Vector2(GameLogic.WORLD_WIDTH / 2, GameLogic.WORLD_HEIGHT * 2 / 3)
)