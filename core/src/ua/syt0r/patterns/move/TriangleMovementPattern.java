package ua.syt0r.patterns.move;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import ua.syt0r.patterns.MovementPattern;

public class TriangleMovementPattern extends MovementPattern {

    public TriangleMovementPattern(Vector2[] points, float timeLength){

        super();

        setTimeLength(timeLength);

        CatmullRomSpline<Vector2> spline = new CatmullRomSpline<Vector2>(points,true);
        setPath(spline);

    }

}
