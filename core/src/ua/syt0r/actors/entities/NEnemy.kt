package ua.syt0r.actors.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion

class NEnemy(
    texture: TextureRegion,
    collisionBody: CollisionBody,
) : PhysicalSpriteActor(
    texture, collisionBody
)