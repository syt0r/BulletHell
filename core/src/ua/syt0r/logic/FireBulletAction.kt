package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.actors.playerBulletConfiguration
import ua.syt0r.screens.modern.GameStage

class FireBulletAction : Action() {

    override fun act(delta: Float): Boolean {
        (actor.stage as GameStage).spawnBullet(playerBulletConfiguration)
        return true
    }

}