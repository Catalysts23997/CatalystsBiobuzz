package org.firstinspires.ftc.teamcode.Competition_Code.Auto.refactoring_v2;

import static org.firstinspires.ftc.teamcode.Competition_Code.Auto.PIDPathingKt.RunToExactForever;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Competition_Code.Actions.NewActions;
import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor;
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoGlobals;
import org.firstinspires.ftc.teamcode.Competition_Code.PinpointLocalizer.Localizer;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;

public class AutoController {
    private final LinearOpMode autoClass;
    private final Poses startingPose;
    private final AllianceColor allianceColor;

    // robot hardware
    private Localizer localizer;
    private Drivetrain drivetrain;
    private NewActions actions;

    public AutoController(
        LinearOpMode autoClass,
        Poses startingPose,
        AllianceColor allianceColor
    ) {
        // set some variables
        this.autoClass = autoClass;
        this.startingPose = startingPose;
        this.allianceColor = allianceColor;

        // set up the robot!
        this.initializeRobot();
    }

    /// Initialize the robot as needed by all autos.
    /// This function ignores any specific setup.
    /// ## Do not call this function more than once.
    private void initializeRobot() {
        // make sure to tell the AutoGlobals where to go
        AutoGlobals.targetRobotPositon = this.startingPose;

        // set up FTC Dashboard
        FtcDashboard dashboard = FtcDashboard.getInstance();
        autoClass.telemetry = dashboard.getTelemetry();

        // set up robot hardware
        HardwareMap hardwareMap = autoClass.hardwareMap;
        this.localizer = new Localizer(hardwareMap, AutoGlobals.targetRobotPositon);
        this.drivetrain = new Drivetrain(hardwareMap, this.allianceColor);
        this.actions = new NewActions(hardwareMap, autoClass.telemetry);

        actions.update();
    }

    public void startAutonomous() {
        AutoGlobals.AutonomousRan = true;
    }

    public Action genericDriveAction() {
        return telemetryPacket -> {
            if (autoClass.isStopRequested()) {
                autoClass.stop();
            }

            this.localizer.update();
            RunToExactForever(AutoGlobals.targetRobotPositon);
            AutoGlobals.locationOfRobot = new Poses(
                Localizer.Companion.getPose().getX(),
                Localizer.Companion.getPose().getY(),
                Localizer.Companion.getPose().getHeading()
            );


            autoClass.telemetry.addData(
                "Target Position",
                AutoGlobals.targetRobotPositon.toString()
            );
            autoClass.telemetry.addData("Current Pose", Localizer.Companion.getPose());
            autoClass.telemetry.addData(
                "Location of robot being transferred",
                AutoGlobals.locationOfRobot.toString()
            );
            autoClass.telemetry.addData("Drive speed", AutoGlobals.driveSpeed);

            autoClass.telemetry.update();
            actions.update();

            return true;
        };
    }
}
