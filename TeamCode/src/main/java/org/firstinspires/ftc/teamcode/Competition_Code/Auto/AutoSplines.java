package org.firstinspires.ftc.teamcode.Competition_Code.Auto;

import android.text.BoringLayout;

import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;

import java.util.ArrayList;
import java.util.List;

public enum AutoSplines {
    Launch1(new ArrayList<Poses>(List.of(AutoPoints.StartRed.pose, AutoPoints.Launch1Red.pose))),
    Intake1(new ArrayList<Poses>(List.of(AutoPoints.Launch1Red.pose, AutoPoints.Intake1Red.pose)), true),
    Launch2(new ArrayList<Poses>(List.of(AutoPoints.Intake1Red.pose, AutoPoints.TransitionRed.pose, AutoPoints.Launch2Red.pose))),
    Intake2(new ArrayList<Poses>(List.of(AutoPoints.Launch2Red.pose, AutoPoints.Intake2Red.pose)), true),
    Launch3(new ArrayList<Poses>(List.of(AutoPoints.Intake2Red.pose,AutoPoints.Launch2Red.pose))),
    End(new ArrayList<Poses>(List.of(AutoPoints.Launch2Red.pose, AutoPoints.EndRed.pose)), true),


    Path1(new ArrayList<Poses>(List.of(AutoPoints.Test1.pose, AutoPoints.Test2.pose, AutoPoints.Test3.pose)));


    public final ArrayList <Poses> poses;
    public Boolean backwards = false;


    AutoSplines(ArrayList <Poses> poses) {
        this.poses = poses;
    }

    AutoSplines(ArrayList <Poses> poses, Boolean backwards) {
        this.poses = poses;
        this.backwards = backwards;
    }


    public SetPathTarget followPath(){
        return new SetPathTarget(poses, backwards);
    }
}
