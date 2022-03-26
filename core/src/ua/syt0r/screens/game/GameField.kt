package ua.syt0r.screens.game

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import ua.syt0r.actors.game.Bullet
import ua.syt0r.actors.game.GameActor
import ua.syt0r.level.BulletConfiguration
import ua.syt0r.level.EnemyConfiguration
import ua.syt0r.logic.action.level.LinearMovementAction
import ua.syt0r.logic.shoot.ShootingScope

class GameField(
    val stage: Stage,
    val player: GameActor
) : ShootingScope {

    val activeBullets = mutableSetOf<Bullet>()
    val activeEnemies = mutableSetOf<GameActor>()

    fun addEnemy(enemyConfiguration: EnemyConfiguration) {
        val actor = enemyConfiguration.createActor()
        stage.addActor(actor)
        activeEnemies.add(actor)
        actor.addAction(enemyConfiguration.shootingLogic.toAction(this))
    }

    fun removeEnemy(actor: GameActor) {
        activeEnemies.remove(actor)
        actor.remove()
    }

    fun removeBullet(bullet: Bullet) {
        activeBullets.remove(bullet)
        bullet.remove()
    }

    override fun shoot(bulletConfiguration: BulletConfiguration, spawnPosition: Vector2, velocity: Vector2) {
        val actor = bulletConfiguration.createActor().apply {
            x = spawnPosition.x
            y = spawnPosition.y
        }

        actor.addAction(LinearMovementAction(velocity))

        stage.addActor(actor)
        activeBullets.add(actor)
    }

}