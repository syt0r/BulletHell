package ua.syt0r.logic

import com.badlogic.gdx.scenes.scene2d.Action
import ua.syt0r.screens.game.GameField

class CheckCollisionAction(
    private val gameField: GameField
) : Action() {

    override fun act(delta: Float): Boolean {
        gameField.activeBullets.forEach {
            if(it.isFriendly) {
                gameField.activeEnemies
            }
        }
        return false
    }

}