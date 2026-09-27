package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Vector2D;

public class CurveProjection {

    /**
     * Finds the closest parametric point t in [0, 1] to the robot position.
     *
     * @param robotPos Current (x, y) coordinate of the robot
     * @param lastT    Closest t from the previous control loop iteration (-1 if unknown)
     * @return Parametric position t in range [0, 1]
     */
    public double findClosestParametricPoint(Vector2D robotPos, CubicSpline spline, double lastT) {
        double t = lastT;

        // Step 1: If lastT is uninitialized (-1) or invalid, perform coarse sampling to find best seed
        if (t < 0.0 || t > 1.0) {
            double minDistSq = Double.MAX_VALUE;
            int samples = 10;
            for (int i = 0; i <= samples; i++) {
                double testT = (double) i / samples;
                Vector2D pt = spline.getPoint(testT);
                double distSq = Math.pow(pt.x - robotPos.x, 2) + Math.pow(pt.y - robotPos.y, 2);

                if (distSq < minDistSq) {
                    minDistSq = distSq;
                    t = testT;
                }
            }
        }

        // Step 2: Refine t using Newton-Raphson optimization (max 5 iterations)
        for (int i = 0; i < 5; i++) {
            Vector2D p = spline.getPoint(t);              // p(t)
            Vector2D d1 = spline.getDerivative(t);           // p'(t)
            Vector2D d2 = spline.getAcceleration(t);  // p''(t)

            Vector2D r = p.subtract(robotPos);     // p(t) - robotPos

            // Dot products for numerator f'(t)/2 and denominator f''(t)/2
            double numerator = r.dot(d1);
            double denominator = d1.dot(d1) + r.dot(d2);

            // Avoid division by zero at inflection points
            if (Math.abs(denominator) < 1e-9) break;

            double delta = numerator / denominator;
            t -= delta;

            // Clamp t to stay within path boundaries [0, 1]
            t = Math.max(0.0, Math.min(1.0, t));

            // Stop early if step size drops below threshold
            if (Math.abs(delta) < 1e-5) break;
        }

        return t;
    }
}