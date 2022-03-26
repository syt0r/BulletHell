package ua.syt0r

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group

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