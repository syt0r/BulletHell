package ua.syt0r.actors

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import ua.syt0r.logic.GameLogic

fun Stage.getGameFieldScreenLocation(): Rectangle {
    val leftBottom = viewport.project(Vector2(0f, 0f))
    val rightTop = viewport.project(Vector2(GameLogic.WORLD_WIDTH, GameLogic.WORLD_HEIGHT))
    return Rectangle(leftBottom.x, leftBottom.y, rightTop.x - leftBottom.x, rightTop.y - leftBottom.y)
}