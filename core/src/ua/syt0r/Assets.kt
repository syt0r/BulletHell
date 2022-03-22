package ua.syt0r

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas

object Assets {

    private val assetManager = AssetManager()

    lateinit var loadingAtlas: TextureAtlas
    lateinit var uiAtlas: TextureAtlas
    lateinit var gameAtlas: TextureAtlas

    @JvmStatic
    fun loadLoadingScreenAssets() {
        assetManager.load("loading.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()
        loadingAtlas = get("loading.atlas", TextureAtlas::class.java)
    }

    @JvmStatic
    fun loadMenuUIAssets() {
        assetManager.load("ui.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()
        uiAtlas = get("ui.atlas", TextureAtlas::class.java)
    }

    @JvmStatic
    fun loadGameAssets() {
        assetManager.load("game.atlas", TextureAtlas::class.java)
        assetManager.finishLoading()
        gameAtlas = get("game.atlas", TextureAtlas::class.java)
    }

    @JvmStatic
    fun unload(resourceName: String?) {
        assetManager.unload(resourceName)
    }

    @JvmStatic
    fun load(fileName: String?, classType: Class<*>?) {
        assetManager.load(fileName, classType)
    }

    @JvmStatic
    operator fun <T> get(resourceName: String?, classType: Class<T>?): T {
        return assetManager.get(resourceName, classType)
    }

    @JvmStatic
    fun update(): Boolean {
        return assetManager.update()
    }

    @JvmStatic
    fun dispose() {
        assetManager.dispose()
    }
}