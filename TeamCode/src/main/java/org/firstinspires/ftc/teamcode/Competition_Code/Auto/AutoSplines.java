package org.firstinspires.ftc.teamcode.Competition_Code.Auto;

import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;

import java.util.ArrayList;
import java.util.List;

public enum AutoSplines {

    Path1(new ArrayList<Poses>(List.of(AutoPoints.Test1.pose, AutoPoints.Test2.pose, AutoPoints.Test3.pose)));


    public final ArrayList <Poses> poses;

    AutoSplines(ArrayList <Poses> poses) {
        this.poses = poses;
    }

    public SetPathTarget followPath(){
        return new SetPathTarget(poses);
    }
}
