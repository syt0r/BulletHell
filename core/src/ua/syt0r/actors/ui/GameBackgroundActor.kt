package ua.syt0r.actors.ui

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import ua.syt0r.actors.getGameFieldScreenLocation
import ua.syt0r.screens.game.stage.GameStage

class GameBackgroundActor(
    private val gameStage: GameStage
) : WidgetGroup() {

    private val leftSideActor = SolidColorActor(
        0f, 0f, 1f, 1f
    )

    private val rightSideActor = SolidColorActor(
        0f, 0f, 1f, 1f
    )

    init {
        setFillParent(true)
        addActor(leftSideActor)
        addActor(rightSideActor)
    }

    override fun getPrefWidth(): Float {
        println("getPrefWidth")
        return stage.width
    }
    override fun getPrefHeight(): Float {
        println("getPrefHeight")
        return stage.height
    }

    override fun layout() {
        super.layout()
        println("layout[$this]")
        val gameFieldSize = gameStage.getGameFieldScreenLocation()
        println("gameFieldSize[$gameFieldSize]")
        leftSideActor.apply {
            x = 0f
            y = 0f
            width = gameFieldSize.x
            height = gameFieldSize.height
        }
        rightSideActor.apply {
            x = gameFieldSize.run { x + width }
            y = 0f
            width = leftSideActor.width
            height = leftSideActor.height
        }
    }

}