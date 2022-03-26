package ua.syt0r.actors.game

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import ua.syt0r.logic.CollisionBody

open class PhysicalSpriteActor(
    texture: TextureRegion,
    val collisionBody: CollisionBody
) : SpriteActor(texture) {

    override fun act(delta: Float) {
        super.act(delta)
        collisionBody.update(this)
    }

    override fun drawDebug(shapes: ShapeRenderer) {
        super.drawDebug(shapes)
        when (collisionBody) {
            is CollisionBody.CircleCollision -> shapes.circle(
                collisionBody.circle.x,
                collisionBody.circle.y,
                collisionBody.circle.radius
            )
            is CollisionBody.RectangleCollision -> shapes.rect(
                collisionBody.rectangle.x,
                collisionBody.rectangle.y,
                collisionBody.rectangle.width,
                collisionBody.rectangle.height
            )
        }
    }

}