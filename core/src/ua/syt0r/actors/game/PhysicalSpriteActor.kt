package ua.syt0r.actors.game

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle

sealed class CollisionBody {
    class CircleCollision(val circle: Circle) : CollisionBody()
    object RectangleCollision : CollisionBody()
}

open class PhysicalSpriteActor(
    texture: TextureRegion,
    val collisionBody: CollisionBody
) : SpriteActor(texture) {

    override fun drawDebug(shapes: ShapeRenderer) {
        super.drawDebug(shapes)
        when (collisionBody) {
            is CollisionBody.CircleCollision -> shapes.circle(x, y, collisionBody.circle.radius)
            is CollisionBody.RectangleCollision -> shapes.rect(x, y, width, height)
        }
    }

}