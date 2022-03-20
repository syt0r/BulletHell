package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action

class RemoveActorAction : Action() {
    override fun act(delta: Float): Boolean {
        actor.remove()
        return true
    }
}