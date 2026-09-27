package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Vector2D;

import java.util.ArrayList;

public class PathManager {
    private final ArrayList<CubicSpline> splines;
    private int currentSplineIndex = 0;
    private double currentT = -1.0;

    public PathManager(ArrayList<CubicSpline> splines) {
        this.splines = splines;
    }

    public SplineState update(Vector2D robotPos, CurveProjection projection) {
        if (isFinished()) return null;

        CubicSpline currentSpline = splines.get(currentSplineIndex);
        currentT = projection.findClosestParametricPoint(robotPos, currentSpline, currentT);

        // Transition to next spline segment when close to the end of the current spline
        if (currentT > 0.95 && currentSplineIndex < splines.size() - 1) {
            currentSplineIndex++;
            currentT = 0.0; // Reset parameter seed for the new segment
            currentSpline = splines.get(currentSplineIndex);
        }

        return new SplineState(
                currentSpline.getPoint(currentT),
                currentSpline.getDerivative(currentT),
                currentSplineIndex == splines.size() - 1, // Is this the final segment?
                currentT
        );
    }

    public boolean isFinished() {
        return currentSplineIndex >= splines.size() - 1 && currentT >= 0.98;
    }

    // Simple container class
    public static class SplineState {
        public final Vector2D pathPos;
        public final Vector2D pathTangent;
        public final boolean isFinalSegment;
        public final double t;

        public SplineState(Vector2D pathPos, Vector2D pathTangent, boolean isFinalSegment, double t) {
            this.pathPos = pathPos;
            this.pathTangent = pathTangent;
            this.isFinalSegment = isFinalSegment;
            this.t = t;
        }
    }
}