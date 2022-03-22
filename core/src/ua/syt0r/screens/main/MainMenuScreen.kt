package ua.syt0r.screens.main

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import ua.syt0r.Assets
import ua.syt0r.Assets.loadLoadingScreenAssets
import ua.syt0r.Assets.loadMenuUIAssets
import ua.syt0r.Assets.update
import ua.syt0r.ScreenManager
import ua.syt0r.State
import ua.syt0r.Utils
import ua.syt0r.actors.ui.SolidColorActor
import ua.syt0r.level.level1
import ua.syt0r.screens.game.GameLevelScreen

class MainMenuScreen : Screen {

    lateinit var state: State

    lateinit var stage: Stage
    lateinit var table: Table

    lateinit var normalButtonStyle: TextButtonStyle

    override fun show() {

        state = State.LOADING
        stage = Stage(ScreenViewport())

        init()

        loadMenu(MenuOptions.MAIN_MENU)
        Gdx.input.inputProcessor = stage

    }

    private fun init() {
        loadLoadingScreenAssets()
        loadMenuUIAssets()

        val skin = Skin(Assets.uiAtlas)
        table = Table(skin)
        table.setFillParent(true)
        stage.addActor(table)

        val leftLineActor = SolidColorActor(
            0f, 0f, stage.width * 8 / 1280, stage.height
        )
        val rightLineActor = SolidColorActor(
            0f, 0f, stage.width * 8 / 1280, stage.height
        )
        val defaultButtonPatch = NinePatchDrawable(
            NinePatch(Assets.loadingAtlas.findRegion("color"), Color.valueOf("00000000"))
        )
        val pressedButtonPatch = NinePatchDrawable(
            NinePatch(Assets.loadingAtlas.findRegion("color"), Color.valueOf("5e5e5e"))
        )
        val bitmapFont = Utils.generateFont("MunroSmall.ttf", (stage.width * 46 / 1280).toInt())
        normalButtonStyle = TextButtonStyle(defaultButtonPatch, pressedButtonPatch, pressedButtonPatch, bitmapFont)
        normalButtonStyle.fontColor = Color.valueOf("5e5e5e")
        normalButtonStyle.downFontColor = Color.valueOf("e0e0e0")
        normalButtonStyle.checkedFontColor = normalButtonStyle.downFontColor
    }

    private val textButtonTable: Table
        private get() = Table()

    private fun getTextButton(text: String): TextButton {
        val textButton = TextButton(text, normalButtonStyle)
        textButton.labelCell.padLeft(stage!!.width * 16 / 1280).padRight(stage!!.width * 16 / 1280)
        return textButton
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.878f, 0.878f, 0.878f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (state === State.LOADING) {
            if (update()) state = State.GAME
        }

        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
    override fun dispose() {}
    private fun addButton(table: Table, button: TextButton) {
        table.add(button).width(stage!!.width / 32 * 9).padBottom(10f).padLeft(0f).padRight(0f).row()
        button.label.setAlignment(Align.left)
    }

    private fun loadMenu(menu: MenuOptions) {
        table!!.addAction(Actions.sequence(Actions.fadeOut(0.5f), object : Action() {
            override fun act(delta: Float): Boolean {
                table!!.clearChildren()
                when (menu) {
                    MenuOptions.MAIN_MENU -> showMainMenu()
                    MenuOptions.LEVEL_SELECT -> showLevelSelect()
                }
                return true
            }
        }, Actions.fadeIn(0.5f)))
    }

    private fun showMainMenu() {

        //Left Line
//        table.add(leftLineActor);

        //Buttons
        val labels = arrayOf("Game start", "Endless mode", "Customize", "Achievements", "Settings", "About", "Exit")
        val buttonGroup = ButtonGroup<TextButton>()
        val buttonsTable = textButtonTable
        val clickListener: ClickListener = object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                when (buttonGroup.checkedIndex) {
                    0 -> loadMenu(MenuOptions.LEVEL_SELECT)
                    1 -> {}
                    2 -> {}
                    3 -> {}
                    4 -> {}
                    5 -> {}
                    6 -> Gdx.app.exit()
                    else -> Gdx.app.exit()
                }
            }
        }
        for (label in labels) {
            val textButton = getTextButton(label)
            buttonGroup.add(textButton)
            addButton(buttonsTable, textButton)
            textButton.addListener(clickListener)
        }
        buttonGroup.setMaxCheckCount(1)
        buttonGroup.setMinCheckCount(0)
        buttonGroup.setUncheckLast(true)
        buttonGroup.uncheckAll()
        table!!.add(buttonsTable).width(stage!!.width / 3 * 1).expandY().top().padTop(stage!!.width * 200f / 1280)

        //Right Line
//        table.add(rightLineActor);
    }

    private fun showLevelSelect() {

        //Init score items to get access to them from level button listener
        val labelStyle = LabelStyle(normalButtonStyle!!.font, normalButtonStyle!!.fontColor)
        val scoreItems = arrayOfNulls<Label>(7)
        for (i in scoreItems.indices) {
            scoreItems[i] = Label((i + 1).toString() + ".----------/---------- 9999999999", labelStyle)
        }

        //Levels

//        table.add(leftLineActor).left().padLeft( stage.getWidth() * 150 / 1280);
        val levelButtonsTable = textButtonTable
        val buttonGroup = ButtonGroup<TextButton?>()
        val levelButtons = arrayOfNulls<TextButton>(7)
        for (i in levelButtons.indices) {
            levelButtons[i] = getTextButton("Level " + (i + 1))
            buttonGroup.add(levelButtons[i])
            levelButtonsTable.add(levelButtons[i]).width(stage!!.width * 160 / 1280).row()

            //TODO update scores
        }
        buttonGroup.setMaxCheckCount(1)
        buttonGroup.setMinCheckCount(1)
        buttonGroup.setUncheckLast(true)
        val backButton = getTextButton("Return")
        levelButtonsTable.add(backButton).width(stage!!.width * 160 / 1280).expandY().bottom()
        backButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                loadMenu(MenuOptions.MAIN_MENU)
            }
        })
        table!!.add(levelButtonsTable).width(stage!!.width * 200 / 1280).growY().left()
            .padTop(stage!!.width * 130 / 1280).padBottom(
                stage!!.width * 100 / 1280
            )

//        table.add(rightLineActor).left();

        //Details
        val detailsTable = textButtonTable
        val scoreLabel = Label("Scores", labelStyle)
        detailsTable.add(scoreLabel).padBottom(stage!!.width * 50 / 1280).row()
        for (label in scoreItems) detailsTable.add(label).row()
        val playButton = getTextButton("Play")
        detailsTable.add(playButton).expandY().bottom()
        table!!.add(detailsTable).grow().pad(stage!!.width * 100 / 1280)

        //Level load
        playButton.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                val selectedLevel = buttonGroup.checkedIndex + 1
                val screen: Screen = GameLevelScreen(level1())
                ScreenManager.getInstance().showScreen(screen)
            }
        })
    }

    private fun showEndlessMode() {}

    private fun showSettings() {}

    private enum class MenuOptions {
        MAIN_MENU, LEVEL_SELECT, ENDLESS_MODE, SETTINGS, EXIT
    }

}
