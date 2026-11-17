package org.firstinspires.ftc.teamcode.Competition_Code.Auto.refactoring;

import static com.acmerobotics.roadrunner.ftc.Actions.runBlocking;

import static org.firstinspires.ftc.teamcode.Competition_Code.Auto.PIDPathingKt.RunToExactForever;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Competition_Code.Actions.NewActions;
import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor;
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoGlobals;
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoPoints;
import org.firstinspires.ftc.teamcode.Competition_Code.PinpointLocalizer.Localizer;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class BaseAuto {

    private final LinearOpMode opmodeClass;
    private final Poses startingPose;
    private final AllianceColor allianceColor;

    public BaseAuto(
        @NotNull LinearOpMode opmodeClass,
        @NotNull Poses startingPose,
        @NotNull AllianceColor allianceColor
    ) {
        this.opmodeClass = opmodeClass;
        this.startingPose = startingPose;
        this.allianceColor = allianceColor;
    }

    /*public void runAutoAction(
            @NotNull Action action
    ) {
        runAuto(
                // just return true!
                (controller) -> true,
                (controller) -> {
                    runBlocking(action);
                    return true;
                }
        );
    }

    public void runAutoAction(
            @NotNull Predicate<AutoRobotController> additionalSetupPredicate,
            @NotNull Action action
    ) {
        runAuto(
            additionalSetupPredicate,
            (controller) -> {
                runBlocking(action);
                return true;
            }
        );
    }*/

    public void runAuto(
        @NotNull Predicate<AutoRobotController> additionalSetupPredicate,
        @NotNull Predicate<AutoRobotController> autoRunnerPredicate
    ) {
        // set up the base robot here
        AutoGlobals.targetRobotPositon = startingPose;

        FtcDashboard dashboard = FtcDashboard.getInstance();
        opmodeClass.telemetry = dashboard.getTelemetry();

        Localizer localizer = new Localizer(opmodeClass.hardwareMap, AutoGlobals.targetRobotPositon);
        Drivetrain drive = new Drivetrain(opmodeClass.hardwareMap, allianceColor);
        NewActions robot = new NewActions(opmodeClass.hardwareMap, opmodeClass.telemetry);

        robot.update();

        AutoRobotController autoController = new AutoRobotController(
            localizer,
            drive,
            robot
        );

        // run any additional setup here
        if (!additionalSetupPredicate.test(autoController)) {
            opmodeClass.telemetry.addLine("The robot has failed to initialize!");
            opmodeClass.telemetry.update();
        }

        // wait for the user to press start
        // we won't write a custom implementation for this
        opmodeClass.waitForStart();

        AutoGlobals.AutonomousRan = true;

        // run the auto runner
        if (!autoRunnerPredicate.test(autoController)) {
            opmodeClass.telemetry.addLine("The auto runner returned an error value! The autonomous program has been stopped.");
            opmodeClass.telemetry.update();
        }

        // end the opmode after the runner is done
        opmodeClass.stop();
    }

    public Action genericDrivetrainManager(AutoRobotController controller) {
        return telemetryPacket -> {
            if (opmodeClass.isStopRequested()) {
                opmodeClass.stop();
            }



            controller.getLocalizer().update();
            RunToExactForever(AutoGlobals.targetRobotPositon);
            AutoGlobals.locationOfRobot = new Poses(
                    Localizer.Companion.getPose().getX(),
                    Localizer.Companion.getPose().getY(),
                    Localizer.Companion.getPose().getHeading()
            );


            opmodeClass.telemetry.addData(
                    "Target Position",
                    AutoGlobals.targetRobotPositon.toString()
            );
            opmodeClass.telemetry.addData("Current Pose", Localizer.Companion.getPose());
            opmodeClass.telemetry.addData(
                    "Location of robot being transferred",
                    AutoGlobals.locationOfRobot.toString()
            );
            opmodeClass.telemetry.addData("Drive speed", AutoGlobals.driveSpeed);

            opmodeClass.telemetry.update();
            controller.getRobot().update();

            return true;
        };
    }

}
