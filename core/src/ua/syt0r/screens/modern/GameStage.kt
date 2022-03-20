package ua.syt0r.screens.modern

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction
import com.badlogic.gdx.utils.viewport.FitViewport
import ua.syt0r.Assets
import ua.syt0r.actors.BulletConfiguration
import ua.syt0r.actors.EnemyConfiguration
import ua.syt0r.actors.enemyConfig
import ua.syt0r.actors.entities.CollisionBody
import ua.syt0r.actors.entities.PhysicalSpriteActor
import ua.syt0r.actors.getPlayer
import ua.syt0r.levels.Level
import ua.syt0r.levels.Wave
import ua.syt0r.logic.*
import java.util.*

class GameStage(
    level: Level
) : Stage() {

    val waves: Queue<Wave> = LinkedList(level.waves)

    lateinit var player: PhysicalSpriteActor
    lateinit var fireAction: Action

    lateinit var textureAtlas: TextureAtlas

    init {
        viewport = FitViewport(GameLogic.WORLD_WIDTH, GameLogic.WORLD_HEIGHT)
        isDebugAll = true

        addAction(
            Actions.sequence(
                Actions.delay(2f), SpawnEnemyAction(enemyConfig),
            )
        )

    }

    var shouldInitialize = true

    override fun act(delta: Float) {
        super.act(delta)
        if (shouldInitialize) {
            shouldInitialize = false
            textureAtlas = Assets.get("game.atlas", TextureAtlas::class.java)
            spawnPlayer()
        }
    }

    private fun spawnPlayer() {
        player = getPlayer(textureAtlas)
        player.addAction(
            Actions.sequence(
                Actions.moveBy(0f, 60f, 1f, Interpolation.smooth),
                UpdatePlayerMovementAction()
            )
        )
        addActor(player)
    }

    fun spawnEnemy(enemy: EnemyConfiguration) {

        val actor = PhysicalSpriteActor(
            texture = textureAtlas.findRegion(enemy.textureRegionName),
            collisionBody = CollisionBody.CircleCollision(Circle(0f, 0f, 20f))
        ).apply {

            width = enemy.size.x
            height = enemy.size.y

            x = enemy.initialPosition.x
            y = enemy.initialPosition.y

        }

        addActor(actor)

    }

    fun spawnBullet(bulletConfiguration: BulletConfiguration) {

        val actor = PhysicalSpriteActor(
            texture = textureAtlas.findRegion(bulletConfiguration.textureRegionName),
            collisionBody = CollisionBody.RectangleCollision
        ).apply {

            width = bulletConfiguration.size.x
            height = bulletConfiguration.size.y

            x = player.x + player.width / 2 - width / 2
            y = player.y + player.height * 3 / 2

            addAction(LinearMovementAction(Vector2(0f, bulletConfiguration.speed)))
            addAction(Actions.sequence(Actions.delay(3f), RemoveActorAction()))

        }

        addActor(actor)

    }

    fun spawnRadianBullet(bulletConfiguration: BulletConfiguration, rotation: Float) {

        val actor = PhysicalSpriteActor(
            texture = textureAtlas.findRegion(bulletConfiguration.textureRegionName),
            collisionBody = CollisionBody.RectangleCollision
        ).apply {

            width = bulletConfiguration.size.x
            height = bulletConfiguration.size.y

            x = player.x + player.width / 2 - width / 2
            y = player.y + player.height * 3 / 2

            addAction(LinearMovementAction(Vector2(0f, bulletConfiguration.speed)))
            addAction(Actions.sequence(Actions.delay(3f), RemoveActorAction()))

        }

        addActor(actor)

    }

    fun startFire() {
        fireAction = Actions.forever(
            Actions.sequence(FireBulletAction(), Actions.delay(0.1f))
        )
        player.addAction(fireAction)
    }

    fun stopFire() {
        player.removeAction(fireAction)
    }

}
