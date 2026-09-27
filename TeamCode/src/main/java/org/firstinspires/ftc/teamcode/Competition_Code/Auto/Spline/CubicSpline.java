package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline;

import static java.lang.Math.pow;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Vector2D;

public class CubicSpline {
    private final Vector2D p0, p1, v0, v1;

    public CubicSpline(Vector2D p0, Vector2D p1, Vector2D v0, Vector2D v1) {
        this.p0 = p0;
        this.p1 = p1;
        this.v0 = v0;
        this.v1 = v1;
    }

    /** Evaluates position P(t) on the curve */
    public Vector2D getPoint(double t) {
        double t3= t*t*t;
        double t2 = t*t;

        double h00 = 2 * t3 - 3 * t2 + 1;
        double h10 = t3 - 2 * t2 + t;
        double h01 = -2 * t3 + 3 * t2;
        double h11 = t3 - t2;

        return p0.scale(h00)
                .add(v0.scale(h10))
                .add(p1.scale(h01))
                .add(v1.scale(h11));
    }

    /** Evaluates tangent vector P'(t) for Guided Vector Field generation */
    public Vector2D getDerivative(double t) {
        double t2 = t*t;

        double dh00 = 6 * t2- 6 * t;
        double dh10 = 3 * t2 - 4 * t + 1;
        double dh01 = -6 * t2 + 6 * t;
        double dh11 = 3 * t2 - 2 * t;

        return p0.scale(dh00)
                .add(v0.scale(dh10))
                .add(p1.scale(dh01))
                .add(v1.scale(dh11));
    }
    public Vector2D getAcceleration(double t) {
        double dh00 = 12 * t - 6;
        double dh10 = 6 * t - 4;
        double dh01 = -12 * t + 6;
        double dh11 = 6 * t - 2;

        return p0.scale(dh00)
                .add(v0.scale(dh10))
                .add(p1.scale(dh01))
                .add(v1.scale(dh11));
    }

    public double getCurvature(double t) {
        double curvature = (getDerivative(t).cross(getAcceleration(t))) / (pow(getDerivative(t).magnitude(),3));
        return curvature;
    }
}