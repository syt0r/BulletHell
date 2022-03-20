package ua.syt0r.stages

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ua.syt0r.ScreenManager
import ua.syt0r.State
import ua.syt0r.actors.entities.Player
import ua.syt0r.actors.ui.*
import ua.syt0r.screens.GameLevelScreen
import ua.syt0r.screens.MainMenuScreen
import java.util.*

class UIStage(private val gameScreen: GameLevelScreen) : Stage() {
    private var loadingActor: LoadingActor? = null
    private var playerHealth: HealthActor? = null
    private var movementControlActor: MovementControlActor? = null
    private var movementClickListener: ClickListener? = null
    private var fireControlActor: FireControlActor? = null
    private var fireClickListener: ClickListener? = null
    private var pauseButton: ImageButton? = null
    private var pauseClickListener: ClickListener? = null
    private var pauseActor: PauseActor? = null
    private var diedActor: DiedActor? = null
    private val winActor: WinActor? = null
    private var keyboardListener: ClickListener? = null

    init {
        viewport = ScreenViewport()
    }

    fun showLoading() {
        loadingActor = LoadingActor()
        addActor(loadingActor)
    }

    fun showGameUI() {
        loadingActor!!.remove()
        val atlas = gameScreen.textureAtlas
        val gameStage = gameScreen.gameStage
        val player = gameStage.player
        movementControlActor = MovementControlActor(atlas)
        movementControlActor!!.resize(gameStage.viewport, gameStage.virtualWidth, gameStage.virtualHeight)
        movementClickListener = DPadInput(movementControlActor!!, player)
        movementControlActor!!.addListener(movementClickListener)
        addActor(movementControlActor)
        fireControlActor = FireControlActor(atlas)
        fireControlActor!!.resize(gameStage.viewport, gameStage.virtualWidth, gameStage.virtualHeight)
        fireClickListener = FireButtonInput(player)
        fireControlActor!!.addListener(fireClickListener)
        addActor(fireControlActor)
        val pauseDefault: Drawable = SpriteDrawable(Sprite(atlas.findRegion("pause_button")))
        val pausePressed: Drawable = SpriteDrawable(Sprite(atlas.findRegion("pause_button_pressed")))
        pauseButton = ImageButton(pauseDefault, pausePressed)
        pauseClickListener = object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                gameScreen.setState(State.PAUSE)
            }
        }
        pauseButton!!.addListener(pauseClickListener)
        pauseButton!!.setSize((Gdx.graphics.height / 6).toFloat(), (Gdx.graphics.height / 6).toFloat())
        pauseButton!!.setPosition(Gdx.graphics.width - pauseButton!!.width, Gdx.graphics.height - pauseButton!!.height)
        addActor(pauseButton)
        playerHealth = HealthActor(
            gameStage.viewport,
            gameStage.virtualWidth,
            gameStage.virtualHeight,
            Player.MAX_HEALTH,
            Player.MAX_HEALTH
        )
        playerHealth!!.setEntity(player)
        addActor(playerHealth)
        keyboardListener = KeyboardInput(player)
        addListener(keyboardListener)

        //Init invisible menus
        //Pause
        pauseActor = PauseActor(this)
        pauseActor!!.setBounds(0f, 0f, width, height)
        pauseActor!!.addContinueButtonListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                gameScreen.setState(State.GAME)
                super.touchUp(event, x, y, pointer, button)
            }
        })
        pauseActor!!.addExitButtonListener(object : ClickListener() {
            override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
                ScreenManager.getInstance().showScreen(MainMenuScreen())
                super.touchUp(event, x, y, pointer, button)
            }
        })
        //Dead
        diedActor = DiedActor(this)
        diedActor!!.setBounds(0f, 0f, width, height)
        diedActor!!.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                ScreenManager.getInstance().showScreen(MainMenuScreen())
            }
        })
    }

    fun showPauseMenu() {
        movementControlActor!!.removeListener(movementClickListener)
        fireControlActor!!.removeListener(fireClickListener)
        pauseButton!!.removeListener(pauseClickListener)
        removeListener(keyboardListener)
        addActor(pauseActor)
        pauseActor!!.show()
    }

    fun hidePauseMenu() {
        movementControlActor!!.addListener(movementClickListener)
        fireControlActor!!.addListener(fireClickListener)
        pauseButton!!.addListener(pauseClickListener)
        addListener(keyboardListener)
        pauseActor!!.remove()
    }

    fun showWinMenu() {
        addActor(winActor)
    }

    fun showDeadMenu() {
        addActor(diedActor)
        diedActor!!.show()
    }

    private inner class KeyboardInput internal constructor(private val player: Player) : ClickListener() {
        private val pressed: MutableSet<Int>

        init {
            pressed = HashSet()
        }

        override fun keyDown(event: InputEvent, keycode: Int): Boolean {
            val playerVelocity = player.velocity
            when (keycode) {
                Input.Keys.LEFT -> player.setVelocity(-1f, playerVelocity.y)
                Input.Keys.RIGHT -> player.setVelocity(1f, playerVelocity.y)
                Input.Keys.UP -> player.setVelocity(playerVelocity.x, 1f)
                Input.Keys.DOWN -> player.setVelocity(playerVelocity.x, -1f)
                Input.Keys.Z -> {
                    player.isFiring = true
                    player.lastFireTime = 3f
                }
            }
            pressed.add(keycode)
            return false
        }

        override fun keyUp(event: InputEvent, keycode: Int): Boolean {
            val playerVelocity = player.velocity
            when (keycode) {
                Input.Keys.LEFT -> if (pressed.contains(Input.Keys.RIGHT)) player.setVelocity(
                    1f,
                    playerVelocity.y
                ) else player.setVelocity(0f, playerVelocity.y)
                Input.Keys.RIGHT -> if (pressed.contains(Input.Keys.LEFT)) player.setVelocity(
                    -1f,
                    playerVelocity.y
                ) else player.setVelocity(0f, playerVelocity.y)
                Input.Keys.UP -> if (pressed.contains(Input.Keys.DOWN)) player.setVelocity(
                    playerVelocity.x,
                    -1f
                ) else player.setVelocity(playerVelocity.x, 0f)
                Input.Keys.DOWN -> if (pressed.contains(Input.Keys.UP)) player.setVelocity(
                    playerVelocity.x,
                    1f
                ) else player.setVelocity(playerVelocity.x, 0f)
                Input.Keys.Z -> player.isFiring = false
            }
            pressed.remove(keycode)
            return false
        }
    }

    private inner class DPadInput internal constructor(
        private val movementControlActor: MovementControlActor,
        private val player: Player
    ) : ClickListener() {
        private val up: Vector2
        private val down: Vector2
        private val left: Vector2
        private val right: Vector2
        private val upLeft: Vector2
        private val upRight: Vector2
        private val downLeft: Vector2
        private val downRight: Vector2

        init {
            val width = movementControlActor.width
            val height = movementControlActor.height
            up = Vector2(width / 2, height / 4 * 3)
            down = Vector2(width / 2, height / 4)
            left = Vector2(width / 4, height / 2)
            right = Vector2(width / 4 * 3, height / 2)

            /*
            upLeft = new Vector2(width/8*3,height/8*5);
            upRight = new Vector2(width/8*5,height/8*5);
            downLeft = new Vector2(width/8*3,height/8*3);
            downRight = new Vector2(width/8*5,height/8*3);
            */upLeft = Vector2(width / 4, height / 4 * 3)
            upRight = Vector2(width / 4 * 3, height / 4 * 3)
            downLeft = Vector2(width / 4, height / 4)
            downRight = Vector2(width / 4 * 3, height / 4)
        }

        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            calculateDistance(x, y)
            return super.touchDown(event, x, y, pointer, button)
        }

        override fun touchDragged(event: InputEvent, x: Float, y: Float, pointer: Int) {
            calculateDistance(x, y)
            super.touchDragged(event, x, y, pointer)
        }

        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
            player.setVelocity(0f, 0f)
            movementControlActor.setPressedButtons(false, false, false, false)
            super.touchUp(event, x, y, pointer, button)
        }

        private fun calculateDistance(x: Float, y: Float) {
            val upDistance: Float
            val downDistance: Float
            val leftDistance: Float
            val rightDistance: Float
            val upLeftDistance: Float
            val upRightDistance: Float
            val downLeftDistance: Float
            val downRightDistance: Float
            upDistance = distance(x, y, up.x, up.y)
            downDistance = distance(x, y, down.x, down.y)
            leftDistance = distance(x, y, left.x, left.y)
            rightDistance = distance(x, y, right.x, right.y)
            upLeftDistance = distance(x, y, upLeft.x, upLeft.y)
            upRightDistance = distance(x, y, upRight.x, upRight.y)
            downLeftDistance = distance(x, y, downLeft.x, downLeft.y)
            downRightDistance = distance(x, y, downRight.x, downRight.y)
            val min = Collections.min(
                Arrays.asList(
                    upDistance, downDistance, leftDistance, rightDistance,
                    upLeftDistance, upRightDistance, downLeftDistance, downRightDistance
                )
            )
            if (upDistance == min) {
                player.setVelocity(0f, 1f)
                movementControlActor.setPressedButtons(true, false, false, false)
            } else if (downDistance == min) {
                player.setVelocity(0f, -1f)
                movementControlActor.setPressedButtons(false, true, false, false)
            } else if (leftDistance == min) {
                player.setVelocity(-1f, 0f)
                movementControlActor.setPressedButtons(false, false, true, false)
            } else if (rightDistance == min) {
                player.setVelocity(1f, 0f)
                movementControlActor.setPressedButtons(false, false, false, true)
            } else if (upLeftDistance == min) {
                player.setVelocity(-1f, 1f)
                movementControlActor.setPressedButtons(true, false, true, false)
            } else if (upRightDistance == min) {
                player.setVelocity(1f, 1f)
                movementControlActor.setPressedButtons(true, false, false, true)
            } else if (downLeftDistance == min) {
                player.setVelocity(-1f, -1f)
                movementControlActor.setPressedButtons(false, true, true, false)
            } else if (downRightDistance == min) {
                player.setVelocity(1f, -1f)
                movementControlActor.setPressedButtons(false, true, false, true)
            }
        }

        private fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
            return Math.sqrt(Math.pow((x1 - x2).toDouble(), 2.0) + Math.pow((y1 - y2).toDouble(), 2.0)).toFloat()
        }
    }

    private inner class FireButtonInput internal constructor(private val player: Player) : ClickListener() {
        override fun touchDown(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            player.isFiring = true
            player.lastFireTime = 3f
            return super.touchDown(event, x, y, pointer, button)
        }

        override fun touchUp(event: InputEvent, x: Float, y: Float, pointer: Int, button: Int) {
            player.isFiring = false
            super.touchUp(event, x, y, pointer, button)
        }
    }
}