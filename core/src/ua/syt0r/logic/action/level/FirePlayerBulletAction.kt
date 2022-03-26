package ua.syt0r.logic.action.level

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.level.BulletConfiguration
import ua.syt0r.level.playerBulletConfiguration
import ua.syt0r.screens.game.GameField

class FirePlayerBulletAction(
    private val gameField: GameField,
    private val bulletConfiguration: BulletConfiguration = playerBulletConfiguration
) : Action() {

    private val bulletSpawnPosition = Vector2()
    private val bulletVelocity = Vector2(0f, bulletConfiguration.speed)

    override fun act(delta: Float): Boolean {

        val player = gameField.player

        bulletSpawnPosition.apply {
            x = player.x + player.width / 2 - bulletConfiguration.size.x / 2
            y = player.y + player.height * 3 / 2
        }

        gameField.shoot(
            bulletConfiguration = bulletConfiguration,
            spawnPosition = bulletSpawnPosition,
            velocity = bulletVelocity
        )
        return true
    }

}