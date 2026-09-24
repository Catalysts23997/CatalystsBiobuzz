package org.firstinspires.ftc.teamcode.Competition_Code.Auto;

import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;


public enum AutoPoints {

    //testing
    Test1(new Vector2d(-34,63), 0.0),
    Test2(new Vector2d(-39,68), Math.PI / 2),
    Test3(new Vector2d(-44,63), 0.0),
    Test4(new Vector2d(-39,58), 0.0);

    public final double x,y, heading;
    public double driveSpeed = 1.0, maxTime = 11.0;

    AutoPoints(Vector2d vector, Double rotation) {
        this.x = vector.x;
        this.y = vector.y;
        this.heading = rotation;

        pose = new Poses(vector.x, vector.y, rotation);
    }
    AutoPoints(Vector2d vector, Double rotation, Double driveSpeed) {
        this.x = vector.x;
        this.y = vector.y;
        this.heading = rotation;
        this.driveSpeed = driveSpeed;

        pose = new Poses(vector.x, vector.y, rotation);
    }
    AutoPoints(Vector2d vector, Double rotation, Double driveSpeed, Double maxTime) {
        this.x = vector.x;
        this.y = vector.y;
        this.heading = rotation;
        this.driveSpeed = driveSpeed;
        this.maxTime = maxTime;

        pose = new Poses(vector.x, vector.y, rotation);
    }

    public SetDriveTarget runToExact(){
        return new SetDriveTarget(new Poses(this.x, this.y, this.heading), this.driveSpeed, this.maxTime);
    }

    public SetDriveTarget runToFast(){
        return new SetDriveTarget(new Poses(this.x, this.y, this.heading), this.driveSpeed, this.maxTime, 10.0, 15.0);
    }

    public final Poses pose;
}
