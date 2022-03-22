package ua.syt0r.screens.game.stage

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction
import com.badlogic.gdx.utils.viewport.FitViewport
import ua.syt0r.Assets
import ua.syt0r.actors.game.*
import ua.syt0r.level.BulletConfiguration
import ua.syt0r.level.EnemyConfiguration
import ua.syt0r.level.Level
import ua.syt0r.level.LevelActionConverter
import ua.syt0r.logic.*
import ua.syt0r.screens.game.GameField

class GameStage(
    level: Level
) : Stage() {

    lateinit var player: GameActor

    private val playerFireAction: PausableAction = PausableAction(
        action = Actions.forever(
            Actions.sequence(FireBulletAction(), Actions.delay(0.1f))
        ),
        isInitiallyPaused = true
    )

    val gameField: GameField

    init {
        viewport = FitViewport(GameLogic.WORLD_WIDTH, GameLogic.WORLD_HEIGHT)
        isDebugAll = true

        spawnPlayer()
        gameField = GameField(player)

        addAction(LevelActionConverter.convert(level))
        addAction(CheckCollisionAction(gameField))

        player.addAction(playerFireAction)
    }

    private fun spawnPlayer() {
        player = createPlayer(Assets.gameAtlas)
        player.addAction(
            Actions.sequence(
                Actions.moveBy(0f, 60f, 1f, Interpolation.smooth),
                UpdatePlayerMovementAction()
            )
        )
        addActor(player)
    }

    fun spawnEnemy(enemy: EnemyConfiguration) {

        val actor = GameActor(
            maxHealth = enemy.hp,
            texture = Assets.gameAtlas.findRegion(enemy.textureRegionName),
            collisionBody = CollisionBody.CircleCollision(Circle(0f, 0f, 20f))
        ).apply {

            width = enemy.size.x
            height = enemy.size.y

            x = enemy.initialPosition.x
            y = enemy.initialPosition.y

        }

        addActor(actor)
        gameField.activeEnemies.add(actor)

    }

    fun spawnBullet(bulletConfiguration: BulletConfiguration) {

        val actor = Bullet(
            isFriendly = true,
            texture = Assets.gameAtlas.findRegion(bulletConfiguration.textureRegionName),
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
        gameField.activeBullets.add(actor)

    }

    fun spawnRadianBullet(bulletConfiguration: BulletConfiguration, rotation: Float) {

        val actor = PhysicalSpriteActor(
            texture = Assets.gameAtlas.findRegion(bulletConfiguration.textureRegionName),
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
        playerFireAction.isPaused = false
    }

    fun stopFire() {
        playerFireAction.isPaused = true
    }

}
