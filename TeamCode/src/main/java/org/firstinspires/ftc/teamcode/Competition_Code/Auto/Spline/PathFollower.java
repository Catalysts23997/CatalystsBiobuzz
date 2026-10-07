package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.PIDController;
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Vector2D;

import java.util.ArrayList;

public class PathFollower {
    private final PathManager pathManager;
    private final CurveProjection projection = new CurveProjection();
    private final GVFController gvf = new GVFController(0.15, 45.0); // ke = 0.15, maxSpeed = 45 in/s
    private final Vector2D finalWaypoint;

    public PathFollower(ArrayList<Poses> waypoints, boolean backwards) {
        ArrayList<Poses> adjustedWaypoints = new ArrayList<>();

        for (int i = 0; i < waypoints.size(); i++) {
            Poses current = waypoints.get(i);
            if (backwards) {
                // FLIP 1: Force spline tangents to project out the back
                double flippedHeading = current.getHeading() + Math.PI;
                adjustedWaypoints.add(new Poses(current.getX(), current.getY(), flippedHeading));
            } else {
                adjustedWaypoints.add(current); // Use as-is
            }
        }
        PathGeneration generator = new PathGeneration();
        this.pathManager = new PathManager(generator.createPath(adjustedWaypoints));

        Poses lastPose = adjustedWaypoints.get(adjustedWaypoints.size() - 1);
        this.finalWaypoint = new Vector2D(lastPose.getX(), lastPose.getY());
    }

    public Poses update(Vector2D robotPos, double robotHeading) {
        // 1. Get current spline target state from PathManager
        PathManager.SplineState state = pathManager.update(robotPos, projection);
        if (state == null) return new Poses (0, 0, 0); // Path finished

        // 2. Dynamic velocity scaling for deceleration
        double currentVMax = calculateTargetVelocity(robotPos, finalWaypoint, state.isFinalSegment, 45.0);

        // 3. Compute field-centric translation vector via GVF
        Vector2D fieldVel = gvf.calculateGVF(robotPos, state.pathPos, state.pathTangent);
        fieldVel = fieldVel.normalize().scale(currentVMax);

        // 5. Calculate rotational output via Heading PID
        double targetHeading = Math.atan2(state.pathTangent.y, state.pathTangent.x);

        return new Poses(fieldVel.x, fieldVel.y, targetHeading);
    }

    public double calculateTargetVelocity(Vector2D robotPos, Vector2D finalWaypoint, boolean isFinalSegment, double vMax) {
        if (!isFinalSegment) return vMax;

        double distanceToTarget = finalWaypoint.subtract(robotPos).magnitude();
        double decelDistance = 10.0; // Start slowing down 12 inches away

        if (distanceToTarget < decelDistance) {
            // Ramp speed down proportionally, maintaining a small min speed to prevent stalling
            double minSpeed = 4.0; // in/s
            return minSpeed + (vMax - minSpeed) * (distanceToTarget / decelDistance);
        }
        return vMax;
    }
}