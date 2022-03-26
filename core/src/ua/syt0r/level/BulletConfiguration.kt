package ua.syt0r.level

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction
import ua.syt0r.Assets
import ua.syt0r.actors.game.Bullet
import ua.syt0r.logic.CollisionBody

class BulletConfiguration(
    val friendly: Boolean,
    val size: Vector2,
    val textureRegionName: String,
    val speed: Float
) {

    fun createActor() = Bullet(
        isFriendly = friendly,
        texture = Assets.gameAtlas.findRegion(textureRegionName),
        collisionBody = CollisionBody.RectangleCollision(
            Rectangle(
                0f,
                0f,
                size.x,
                size.y
            )
        )
    ).apply {

        width = size.x
        height = size.y

        addAction(Actions.sequence(Actions.delay(1f), RemoveActorAction()))

    }

}