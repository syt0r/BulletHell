package ua.syt0r.actors.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.Viewport
import ua.syt0r.logic.PlayerInput

class FireControlActor(atlas: TextureAtlas) : Actor() {
    private val center: Vector2
    private val defaultTexture: TextureRegion

    init {
        center = Vector2()
        defaultTexture = atlas.findRegion("fire_button")
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(defaultTexture, x, y, height, width)
    }

    fun resize(gameViewport: Viewport, VIRTUAL_WIDTH: Int, VIRTUAL_HEIGHT: Int) {
        val rightTop = gameViewport.project(Vector2(VIRTUAL_WIDTH.toFloat(), VIRTUAL_HEIGHT.toFloat()))
        val gameBorderX = rightTop.x
        val gameBorderY = rightTop.y
        center[(Gdx.graphics.width + gameBorderX) / 2] = gameBorderY / 4
        setSize(gameBorderY / 8 * 3, gameBorderY / 8 * 3)
        setPosition(center.x - width / 2, center.y - height / 2)
    }

    class FireButtonInput : ClickListener() {

        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            PlayerInput.isFiring = true
            return super.touchDown(event, x, y, pointer, button)
        }

        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
            PlayerInput.isFiring = false
            super.touchUp(event, x, y, pointer, button)
        }

    }

}