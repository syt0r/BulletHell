package ua.syt0r.screens.modern

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import ua.syt0r.Assets
import ua.syt0r.actors.ui.LoadingActor
import ua.syt0r.actors.ui.MovementControlActor
import ua.syt0r.actors.ui.SolidColorActor
import ua.syt0r.logic.GameLogic

class UIStage(
    val gameStageViewport: Viewport
) : Stage() {

    val loadingUI = LoadingActor()
    lateinit var gameUI: Group

    init {
        viewport = ScreenViewport()
    }

    fun showLoading() {
        addActor(loadingUI)
    }

    fun hideLoading() {
        loadingUI.remove()
    }

    fun showGameUI() {
        val leftBottom = gameStageViewport.project(
            Vector2(0f, 0f)
        )
        val rightTop = gameStageViewport.project(
            Vector2(GameLogic.WORLD_WIDTH, GameLogic.WORLD_HEIGHT)
        )
        val leftSideActor = SolidColorActor(
            0f, 0f, leftBottom.x, rightTop.y
        )
        val rightSideActor = SolidColorActor(
            rightTop.x, 0f, leftBottom.x, rightTop.y
        )

        gameUI = Group()
        gameUI.addActor(leftSideActor)
        gameUI.addActor(rightSideActor)

        val atlas = Assets.get("game.atlas", TextureAtlas::class.java)
        val movementControlActor = MovementControlActor(atlas)
        gameUI.addActor(movementControlActor)

        addActor(gameUI)
    }

    fun showPauseMenu() {

    }

    fun hidePauseMenu() {

    }

    fun showWinMenu() {
    }

    fun showDeadMenu() {

    }

}