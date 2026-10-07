package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline;

import static java.lang.Math.cos;
import static java.lang.Math.hypot;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Vector2D;

import java.util.ArrayList;

public class PathGeneration {

    public ArrayList<CubicSpline> createPath (ArrayList<Poses> poses) {
        ArrayList <Vector2D> points = new ArrayList<>();
        ArrayList <Vector2D> tangents = new ArrayList<>();
        ArrayList <CubicSpline> splines = new ArrayList<>();

        int size = poses.size();
        if (size < 2) return splines;

        for (int i = 0; i < size - 1; i++){
            Poses current = poses.get(i);
            Poses next = poses.get(i + 1);

            double k = hypot(next.getX()-current.getX(), next.getY()-current.getY());

            double heading = current.getHeading();

            points.add(new Vector2D(current.getX(), current.getY()));
            tangents.add(new Vector2D(k * sin(heading), k * cos(heading)));
        }

        Poses last = poses.get(size - 1);
        Poses prev = poses.get(size - 2);
        double kLast = hypot(last.getX() - prev.getX(), last.getY() - prev.getY());
        double lastHeadingRad = last.getHeading();

        points.add(new Vector2D(last.getX(), last.getY()));
        tangents.add(new Vector2D(kLast * sin(lastHeadingRad), kLast * cos(lastHeadingRad)));

        size =  points.size();

        for (int i = 0; i < size - 1; i++) {
              CubicSpline spline = new CubicSpline(points.get(i), points.get(i+1), tangents.get(i), tangents.get(i+1));
              splines.add(spline);
        }
        return splines;
    }
}
