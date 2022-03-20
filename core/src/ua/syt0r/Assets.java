package ua.syt0r;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

    private static final AssetManager assetManager = new AssetManager();

    public static TextureAtlas loadingAtlas;
    public static TextureAtlas uiAtlas;
    public static TextureAtlas gameAtlas;

    public static void loadLoadingScreenAssets(){

        assetManager.load("loading.atlas",TextureAtlas.class);
        assetManager.finishLoading();
        loadingAtlas = Assets.get("loading.atlas", TextureAtlas.class);

    }

    public static void loadMenuUIAssets(){

        assetManager.load("ui.atlas",TextureAtlas.class);
        assetManager.finishLoading();
        uiAtlas = Assets.get("ui.atlas", TextureAtlas.class);

    }

    public static void unload(String resourceName){

        assetManager.unload(resourceName);

    }

    public static void load(String fileName, Class<?> classType){
        assetManager.load(fileName,classType);
    }

    public static <T> T get(String resourceName, Class<T> classType){
        return assetManager.get(resourceName,classType);
    }

    public static boolean update(){
        return assetManager.update();
    }

    public static void dispose(){
        assetManager.dispose();
    }



}
