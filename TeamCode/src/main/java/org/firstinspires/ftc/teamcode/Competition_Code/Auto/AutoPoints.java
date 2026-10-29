package org.firstinspires.ftc.teamcode.Competition_Code.Auto;

import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;


public enum AutoPoints {

    //testing
    Start1Red(new Vector2d(-12.5, -63), Math.PI),
    Launch1Red(new Vector2d(-12.5,-55), Math.PI),
    Flower1Red(new Vector2d(-58,-15), 3*Math.PI/2),
    Transition1Red(new Vector2d(-45,40), 9*Math.PI/8),
    Launch2Red(new Vector2d(-12.5,55.0), 0.0),
    Flower2Red(new Vector2d(-15,58), 0.0),
    GardenStartRed(new Vector2d(-40, -63), 0.0),
    GardenEndRed(new Vector2d(-63, -63), 0.0),
    EndRed(new Vector2d(-55,53), Math.PI),

    Start1Blue(new Vector2d(12.5, 63), Math.PI),
    Launch1Blue(new Vector2d(12.5,55), Math.PI),
    Flower1Blue(new Vector2d(58,15), 3*Math.PI/2),
    Transition1Blue(new Vector2d(45,-40), 9*Math.PI/8),
    Launch2Blue(new Vector2d(12.5,-55.0), 0.0),
    Flower2Blue(new Vector2d(15,-58), 0.0),
    GardenStartBlue(new Vector2d(40, 63), 0.0),
    GardenEndBlue(new Vector2d(63, 63), 0.0),
    EndBlue(new Vector2d(55,-53), Math.PI),

    Test1(new Vector2d(0.0,0.0), 0.0),
    Test2(new Vector2d(0.0,40.0), Math.PI/2),
    Test3(new Vector2d(40.0,0.0), -3*Math.PI/4),
    Test4(new Vector2d(-40.0,0.0), Math.PI);

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
