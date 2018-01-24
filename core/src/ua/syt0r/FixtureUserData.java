package ua.syt0r;

/**
 * Created by Yaroslav on 2018/01/21.
 */
public class FixtureUserData {

    public static final int BULLET = 1;
    public static final int WORLD_EDGE = 2;
    public static final int ENEMY = 3;
    public static final int PLAYER = 4;

    private int objectType;
    private Object data;

    public FixtureUserData(){}

    public FixtureUserData(int objectType, Object data){
        this.objectType = objectType;
        this.data = data;
    }

    public int getObjectType() {
        return objectType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }
}
