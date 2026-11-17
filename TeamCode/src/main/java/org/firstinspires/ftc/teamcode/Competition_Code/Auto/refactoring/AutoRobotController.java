package org.firstinspires.ftc.teamcode.Competition_Code.Auto.refactoring;

import org.firstinspires.ftc.teamcode.Competition_Code.Actions.NewActions;
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoGlobals;
import org.firstinspires.ftc.teamcode.Competition_Code.PinpointLocalizer.Localizer;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain;

public class AutoRobotController {

    private final Localizer localizer;
    private final Drivetrain drive;
    private final NewActions robot;

    public AutoRobotController(Localizer localizer, Drivetrain drive, NewActions robot) {
        this.localizer = localizer;
        this.drive = drive;
        this.robot = robot;
    }

    public Localizer getLocalizer() {
        return localizer;
    }

    public Drivetrain getDrive() {
        return drive;
    }

    public NewActions getRobot() {
        return robot;
    }
}
