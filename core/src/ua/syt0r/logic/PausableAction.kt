package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.DelegateAction

class PausableAction(
    action: Action,
    private val isInitiallyPaused: Boolean = false
) : DelegateAction() {

    var isPaused: Boolean = isInitiallyPaused

    init {
        setAction(action)
    }

    override fun delegate(delta: Float): Boolean {
        return if (isPaused) false
        else action?.act(delta) ?: true
    }

    override fun reset() {
        super.reset()
        isPaused = isInitiallyPaused
    }

    override fun restart() {
        super.restart()
        isPaused = isInitiallyPaused
    }

}