package ua.syt0r.actors.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.utils.viewport.Viewport

class MovementControlActor(atlas: TextureAtlas) : Actor() {

    private val center: Vector2 = Vector2()
    private val up: Vector2 = Vector2()
    private val down: Vector2 = Vector2()
    private val left: Vector2 = Vector2()
    private val right: Vector2 = Vector2()

    private val defaultTexture: TextureRegion
    private val pressedTexture: TextureRegion

    //top,left,bottom,right
    private val state = booleanArrayOf(false, false, false, false)

    init {
        defaultTexture = atlas.findRegion("movement_control_up")
        pressedTexture = atlas.findRegion("movement_control_up_pressed")
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(
            if (state[0]) pressedTexture else defaultTexture,
            up.x - width / 4,
            up.y - height / 4,
            0f,
            0f,
            width / 2,
            height / 2,
            scaleX,
            scaleY,
            0f
        )
        batch.draw(
            if (state[1]) pressedTexture else defaultTexture,
            left.x + width / 4,
            left.y - height / 4,
            0f,
            0f,
            width / 2,
            height / 2,
            scaleX,
            scaleY,
            90f
        )
        batch.draw(
            if (state[2]) pressedTexture else defaultTexture,
            down.x + width / 4,
            down.y + height / 4,
            0f,
            0f,
            width / 2,
            height / 2,
            scaleX,
            scaleY,
            180f
        )
        batch.draw(
            if (state[3]) pressedTexture else defaultTexture,
            right.x - width / 4,
            right.y + height / 4,
            0f,
            0f,
            width / 2,
            height / 2,
            scaleX,
            scaleY,
            270f
        )
    }

    fun resize(gameViewport: Viewport, VIRTUAL_WIDTH: Int, VIRTUAL_HEIGHT: Int) {
        val leftBottom = gameViewport.project(Vector2(0f, 0f))
        val rightTop = gameViewport.project(Vector2(VIRTUAL_WIDTH.toFloat(), VIRTUAL_HEIGHT.toFloat()))
        val gameBorderX = leftBottom.x
        val gameBorderY = rightTop.y
        center[gameBorderX / 2] = gameBorderY / 4
        setSize(gameBorderY / 8 * 3, gameBorderY / 8 * 3)
        setPosition(center.x - width / 2, center.y - height / 2)
        up[center.x] = center.y + height / 4
        down[center.x] = center.y - height / 4
        left[center.x - width / 4] = center.y
        right[center.x + width / 4] = center.y
    }

    fun setPressedButtons(up: Boolean, down: Boolean, left: Boolean, right: Boolean) {
        state[0] = up
        state[1] = left
        state[2] = down
        state[3] = right
    }
}