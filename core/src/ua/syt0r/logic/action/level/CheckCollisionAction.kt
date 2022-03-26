package ua.syt0r.logic.action.level

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.screens.game.GameField

class CheckCollisionAction(
    private val gameField: GameField
) : Action() {

    override fun act(delta: Float): Boolean {
        val player = gameField.player

        gameField.activeBullets.iterator().forEach { bullet ->

            if (bullet.isFriendly) {

                for (enemy in gameField.activeEnemies) {
                    if (bullet.collisionBody.overlaps(enemy.collisionBody)) {
                        enemy.damage()
                        if (enemy.health <= 0) gameField.removeEnemy(enemy)
                        gameField
                        break
                    }
                }

            } else {

                if (bullet.collisionBody.overlaps(player.collisionBody)) {
                    player.damage()
                }

            }

        }
        return false
    }

}