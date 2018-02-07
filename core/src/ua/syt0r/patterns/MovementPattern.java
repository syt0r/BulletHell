package ua.syt0r.patterns;


import com.badlogic.gdx.math.Path;

public class MovementPattern {

    private Path path;
    private float timeLength;

    public MovementPattern(){

    }

    public MovementPattern(Path path, float timeLength){
        this.path = path;
        this.timeLength = timeLength;
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
