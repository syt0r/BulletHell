package ua.syt0r.patterns.move;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import ua.syt0r.patterns.MovementPattern;

public class LinearMovementPattern extends MovementPattern{

    public LinearMovementPattern(Vector2 begin, Vector2 end, float timeLength){

        super();

        setTimeLength(timeLength);

        CatmullRomSpline<Vector2> spline = new CatmullRomSpline<Vector2>(new Vector2[]{begin,end},false);
        setPath(spline);

    }

}
