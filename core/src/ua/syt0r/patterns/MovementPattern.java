package ua.syt0r.patterns;


import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import ua.syt0r.actors.entities.Enemy;

public class MovementPattern {

    private Path<Vector2> path;
    private float timeLength;

    private float time = 0;

    private Vector2 position;

    public MovementPattern(){
        position = new Vector2();
    }

    public boolean move(Enemy enemy, float delta){

        time += delta;

        if (time/timeLength > 1f)
            return false;

        path.valueAt(position,time/timeLength);

        enemy.setPosition(position.x,position.y);
        return true;

    }

    public void reserTime(){
        time = 0f;
    }

    public float getTimeLength() {
        return timeLength;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setTimeLength(float timeLength) {
        this.timeLength = timeLength;
    }
}
