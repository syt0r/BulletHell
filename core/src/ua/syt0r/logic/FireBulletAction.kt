package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.level.playerBulletConfiguration
import ua.syt0r.screens.game.stage.GameStage

class FireBulletAction : Action() {

    override fun act(delta: Float): Boolean {
        (actor.stage as GameStage).spawnBullet(playerBulletConfiguration)
        return true
    }

}