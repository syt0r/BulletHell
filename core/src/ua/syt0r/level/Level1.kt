package ua.syt0r.level

import ua.syt0r.actors.game.enemyConfig

fun level1() = level {

    wave {
        enemy(enemyConfig)
    }

}
