package ua.syt0r.level

import com.badlogic.gdx.math.Vector2

val playerBulletConfiguration = BulletConfiguration(
    friendly = true,
    size = Vector2(20f, 30f),
    textureRegionName = "bullet_long",
    speed = 500f
)


val enemyBulletConfiguration = BulletConfiguration(
    friendly = false,
    size = Vector2(20f, 30f),
    textureRegionName = "bullet_long",
    speed = 500f
)