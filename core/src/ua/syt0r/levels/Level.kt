package ua.syt0r.levels

import ua.syt0r.actors.EnemyConfiguration

fun level(
    levelBuilder: LevelBuilder.() -> Unit
): Level {
    val level = Level()
    level.levelBuilder()
    return level
}

interface LevelBuilder {

    fun wave(
        waveMode: WaveMode = WaveMode.KillAll,
        waveBuilder: WaveBuilder.() -> Unit
    )

}

interface WaveBuilder {


    /***
     * Adds one enemy to field
     */
    fun enemy(enemy: EnemyConfiguration)

    fun bossEnemy()

}

sealed class WaveMode {
    object KillAll : WaveMode()
    class Timeout(val waveLiveTimeMillis: Long) : WaveMode()
}

class Level : LevelBuilder {

    val waves = mutableListOf<Wave>()

    override fun wave(
        waveMode: WaveMode,
        waveBuilder: WaveBuilder.() -> Unit
    ) {
        val wave = Wave()
        wave.waveBuilder()
        waves.add(wave)
    }

}

class Wave : WaveBuilder {

    val enemies = mutableListOf<EnemyConfiguration>()

    override fun enemy(enemy: EnemyConfiguration) {
        enemies.add(enemy)
    }

    override fun bossEnemy() {
    }

}
