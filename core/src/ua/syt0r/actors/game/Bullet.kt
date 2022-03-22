package ua.syt0r.actors.game

import com.badlogic.gdx.graphics.g2d.TextureRegion

class Bullet(
    val isFriendly: Boolean,
    texture: TextureRegion,
    collisionBody: CollisionBody
) : PhysicalSpriteActor(texture, collisionBody)