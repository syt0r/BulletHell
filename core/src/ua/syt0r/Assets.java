package ua.syt0r;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {

    private static final AssetManager assetManager = new AssetManager();

    public static TextureAtlas loadingAtlas;
    public static TextureAtlas uiAtlas;
    public static TextureAtlas gameAtlas;

    public static void loadLoadingScreenAssets(){

        assetManager.load("loading.atlas",TextureAtlas.class);
        assetManager.finishLoading();

        loadingAtlas = assetManager.get("loading",TextureAtlas.class);

    }

    public static void unload(String resourceName){

        assetManager.unload(resourceName);

    }

    public static void loadUIAtlas(){

        assetManager.load("ui.atlas",TextureAtlas.class);

    }

    public static boolean update(){

        return assetManager.update();

    }



}
