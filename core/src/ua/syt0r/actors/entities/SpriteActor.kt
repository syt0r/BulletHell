package ua.syt0r.actors.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

open class SpriteActor(
    private val texture: TextureRegion
) : Actor() {

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY, rotation)
    }

}