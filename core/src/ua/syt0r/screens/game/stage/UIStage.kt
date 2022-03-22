package ua.syt0r.screens.game.stage

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ua.syt0r.Assets
import ua.syt0r.actors.ui.*
import ua.syt0r.addActors
import ua.syt0r.logic.GameLogic
import ua.syt0r.logic.HealthChangeListener

class UIStage(
    val gameStage: GameStage
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
        val leftBottom = gameStage.viewport.project(
            Vector2(0f, 0f)
        )
        val rightTop = gameStage.viewport.project(
            Vector2(GameLogic.WORLD_WIDTH, GameLogic.WORLD_HEIGHT)
        )

        val leftSideActor = SolidColorActor(
            0f, 0f, leftBottom.x, rightTop.y
        )
        val rightSideActor = SolidColorActor(
            rightTop.x, 0f, leftBottom.x, rightTop.y
        )

        val atlas = Assets.gameAtlas

        val playerHealthActor = HealthActor(
            maxHealth = gameStage.player.maxHealth,
            health = gameStage.player.health
        ).apply {

            width = leftBottom.x * 2 / 3
            height = rightTop.y / 12

            x = width / 2
            y = rightTop.y - height

        }

        gameStage.player.healthChangeListeners.add(object : HealthChangeListener {
            override fun onHealthChanged(oldHealth: Int, newHealth: Int) {
                playerHealthActor.setHealth(newHealth)
            }
        })

        val movementControlActor = MovementControlActor(atlas).apply {
            width = leftSideActor.width * 2 / 3
            height = width

            x = leftSideActor.width / 3
            y = x
        }
        movementControlActor.resize(gameStage.viewport, viewport.screenWidth, viewport.screenHeight)

        val fireControlActor = FireControlActor(atlas).apply {
            x = rightTop.x + leftSideActor.width / 3
            y = movementControlActor.y
            width = leftSideActor.width * 2 / 3
            height = width
        }

        val pauseDefault: Drawable = SpriteDrawable(Sprite(atlas.findRegion("pause_button")))
        val pausePressed: Drawable = SpriteDrawable(Sprite(atlas.findRegion("pause_button_pressed")))
        val pauseButton = ImageButton(pauseDefault, pausePressed).apply {
            x = viewport.screenWidth - width
            y = viewport.screenHeight - height
        }

        gameUI = Group()
        gameUI.addActors(
            leftSideActor,
            rightSideActor,
            playerHealthActor,
            movementControlActor,
            fireControlActor,
            pauseButton
        )

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