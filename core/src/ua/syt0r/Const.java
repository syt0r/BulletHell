package ua.syt0r;

/**
 * Created by Yaroslav on 2018/01/20.
 */
public class Const {

    public static final float scale = 200;

    public static float toPhysCoords(float a){
        return a/scale;
    }

    public static float toDrawCoords(float a){
        return a*scale;
    }

}
