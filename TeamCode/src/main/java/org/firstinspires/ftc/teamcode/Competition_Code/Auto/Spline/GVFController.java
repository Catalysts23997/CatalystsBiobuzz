package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Vector2D;

public class GVFController {
    private final double ke;       // Error gain constant (e.g., 0.1 - 0.5)
    private final double vMax;     // Maximum target linear speed (e.g., 40 in/s)

    public GVFController(double ke, double vMax) {
        this.ke = ke;
        this.vMax = vMax;
    }

    /**
     * Calculates the desired velocity vector at the robot's current position.
     *
     * @param robotPos    Current robot position (x, y)
     * @param pathPos     Closest point on the path to the robot (x, y)
     * @param pathTangent Unit tangent vector of the path at pathPos
     * @return Output velocity vector (vX, vY) ready for drive kinematics
     */
    public Vector2D calculateGVF(Vector2D robotPos, Vector2D pathPos, Vector2D pathTangent) {
        // 1. Calculate error vector pointing from robot to closest point on path
        Vector2D errorVector = pathPos.subtract(robotPos);
        double e = errorVector.magnitude(); // Cross-track error distance

        // 2. Unit normal vector pointing toward path (handle case where robot is on path)
        Vector2D nHat = e > 1e-6 ? errorVector.normalize() : new Vector2D(0, 0);

        // 3. Ensure tangent vector is normalized
        Vector2D tauHat = pathTangent.normalize();

        // 4. Error gain function phi(e)
        // Using tanh prevents the normal force from exploding when far off path
        double phi = Math.tanh(ke * e);

        // 5. Blend tangent force (push forward) and normal force (pull to line)
        Vector2D blendedVector = tauHat.add(nHat.scale(phi));

        // 6. Normalize combined direction and scale by target velocity
        return blendedVector.normalize().scale(vMax);
    }
}