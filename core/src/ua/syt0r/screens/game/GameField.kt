package ua.syt0r.screens.game

import ua.syt0r.actors.game.Bullet
import ua.syt0r.actors.game.GameActor

class GameField(
    val player: GameActor
) {

    val activeBullets = mutableSetOf<Bullet>()
    val activeEnemies = mutableSetOf<GameActor>()

}