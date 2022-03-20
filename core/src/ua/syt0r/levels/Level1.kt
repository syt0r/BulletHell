package ua.syt0r.levels

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ua.syt0r.Assets
import ua.syt0r.actors.entities.Boss
import ua.syt0r.actors.entities.Enemy
import ua.syt0r.patterns.ShootingPattern
import ua.syt0r.screens.GameLevelScreen

class Level1 : GameLevelScreen() {
    var time = 0f
    var fireTime = 0f

    lateinit var _boss: Boss
    var angle = 0f
    override fun show() {
        super.show()
        _boss = Boss(200)
        _boss.width = 40f
        _boss.height = 40f
        //boss.setTexture(GameManager.assetManager.get("ui", TextureAtlas.class).createSprite("enemy1").getTexture());
        //addBoss(boss);
    }

    override fun render(delta: Float) {
        time += delta
        fireTime += delta
        if (time * 1000 % 1000 > 500) _boss.setVelocity(-1f, 0f) else _boss.setVelocity(1f, 0f)
        if (fireTime * 1000 > 500) {
            fireTime -= 0.5.toFloat()
            for (i in 0..11) {
                //RadialShot(boss.getX(), boss.getY(), angle,200f);
                angle += 30f
            }
            angle += 10f
        }
        _boss.update(delta)
        super.render(delta)
    }

    override fun preLoad() {
        LoadMusic("music/score.mp3")
    }

    override fun init() {
        val atlas = Assets.get("game.atlas", TextureAtlas::class.java)
        val pattern1: ShootingPattern = object : ShootingPattern(this) {
            private val player = screen.gameStage.player
            override fun shoot(enemy: Enemy, delta: Float): Boolean {
                return false
            }
        }
        val enemy1 = Enemy()
        enemy1.setTexture(atlas.findRegion("enemy1"))
        enemy1.setBody(500f, 1000f, 10f)
        enemy1.setBounds(500f, 1000f, 80f, 80f)
        enemy1.setShootingPattern(pattern1)
        addEnemy(enemy1, 50)
    }
}