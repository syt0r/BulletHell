package ua.syt0r.logic

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor

sealed class CollisionBody {

    abstract fun overlapsCircle(circle: Circle): Boolean
    abstract fun overlapsRect(rect: Rectangle): Boolean

    abstract fun update(actor: Actor)

    fun overlaps(other: CollisionBody): Boolean = when (other) {
        is CircleCollision -> overlapsCircle(other.circle)
        is RectangleCollision -> overlapsRect(other.rectangle)
    }

    class CircleCollision(val circle: Circle) : CollisionBody() {

        override fun overlapsCircle(circle: Circle) = this.circle.overlaps(circle)
        override fun overlapsRect(rect: Rectangle) = Intersector.overlaps(circle, rect)

        override fun update(actor: Actor) {
            circle.setPosition(actor.run { x + width / 2 }, actor.run { y + width / 2 })
        }

    }

    class RectangleCollision(val rectangle: Rectangle) : CollisionBody() {

        override fun overlapsCircle(circle: Circle) = Intersector.overlaps(circle, rectangle)
        override fun overlapsRect(rect: Rectangle) = rect.overlaps(rectangle)

        override fun update(actor: Actor) {
            rectangle.setPosition(actor.run { x }, actor.run { y })
        }

    }

}