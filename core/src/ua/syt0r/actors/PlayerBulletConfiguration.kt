package ua.syt0r.actors

import com.badlogic.gdx.math.Vector2

val playerBulletConfiguration = BulletConfiguration(
    friendly = true,
    size = Vector2(20f, 20f),
    textureRegionName = "bullet_long",
    speed = 500f
)