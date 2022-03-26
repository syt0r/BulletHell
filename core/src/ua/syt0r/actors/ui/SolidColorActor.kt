package ua.syt0r.actors.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor

class SolidColorActor(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    color: Color = Color.valueOf("5e5e5e")
) : Actor() {

    private val texture: Texture

    init {
        setBounds(x, y, width, height)
        val pixmap = Pixmap(1, 1, Pixmap.Format.RGB888)
        pixmap.setColor(color)
        pixmap.fill()
        texture = Texture(pixmap)
        pixmap.dispose()
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.draw(texture, x, y, width, height)
    }

}