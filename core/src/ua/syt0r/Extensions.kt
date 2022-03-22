package ua.syt0r

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import ua.syt0r.actors.game.CollisionBody

fun Float.clamp(min: Float, max: Float): Float {
    return when {
        this > max -> max
        this < min -> min
        else -> this
    }
}

fun Group.addActors(vararg actors: Actor) {
    actors.forEach { addActor(it) }
}

//fun CollisionBody.isIntersecting(other: CollisionBody): Boolean {
//    return when {
//        this is CollisionBody.CircleCollision && other is CollisionBody.CircleCollision -> this.circle.overlaps(other.circle)
//        this is CollisionBody.CircleCollision && other is CollisionBody.RectangleCollision -> true
//    }
//}
