package ua.syt0r.actors.ui

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import ua.syt0r.Assets

class HealthActor(
    private var maxHealth: Int,
    private var health: Int
) : Actor() {

    private val lightNinePatch: NinePatch
    private val darkNinePatch: NinePatch
    private val redNinePatch: NinePatch

    private var redGaugeX = 0f

    init {
        setHealth(health)

        val atlas = Assets.get("loading.atlas", TextureAtlas::class.java)

        val textureRegion: TextureRegion = atlas.findRegion("color")

        lightNinePatch = NinePatch(textureRegion, Color.valueOf("e0e0e0"))
        darkNinePatch = NinePatch(textureRegion, Color.valueOf("717171"))
        redNinePatch = NinePatch(textureRegion, Color.valueOf("e09090"))
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        darkNinePatch.draw(batch, x, y, width, height)
        redNinePatch.draw(batch, x, y, redGaugeX, height)
        lightNinePatch.draw(batch, x, y, width / maxHealth * health, height)
    }

    override fun act(delta: Float) {
        if (redGaugeX > width / maxHealth * health) redGaugeX -= 50f * delta
    }

    fun setHealth(health: Int) {
        if (this.health != 0) redGaugeX = width / maxHealth * this.health
        this.health = health
    }

}