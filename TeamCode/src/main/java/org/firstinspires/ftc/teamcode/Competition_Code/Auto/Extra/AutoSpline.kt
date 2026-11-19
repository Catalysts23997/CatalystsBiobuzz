package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Extra

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Competition_Code.Actions.NewActions
import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoGlobals
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoSplines
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.FollowPath
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline.PathFollower
import org.firstinspires.ftc.teamcode.Competition_Code.PinpointLocalizer.Localizer
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses

@Autonomous(name = "AutoSpline", group = "Auto")
class AutoSpline : LinearOpMode() {

    override fun runOpMode() {
        AutoGlobals.targetRobotPath = PathFollower(AutoSplines.Path1.poses, false)

        val dash: FtcDashboard = FtcDashboard.getInstance()
        telemetry = dash.telemetry

        val localizer = Localizer(hardwareMap, AutoSplines.Path1.poses[0])
        val drive = Drivetrain(hardwareMap, AllianceColor.Blue)
        val robot = NewActions(hardwareMap, telemetry)


        robot.update()

        waitForStart()

        AutoGlobals.AutonomousRan = true


        runBlocking(
            ParallelAction(
                object : Action {
                    override fun run(p: TelemetryPacket): Boolean {
                        if (isStopRequested) {
                            stop()
                        }



                        localizer.update()
                        FollowPath()
                        AutoGlobals.locationOfRobot = Poses(
                            Localizer.pose.x,
                            Localizer.pose.y,
                            Localizer.pose.heading
                        )


                        telemetry.addData(
                            "Target Position",
                            AutoGlobals.targetRobotPositon.toString()
                        )
                        telemetry.addData("Current Pose", Localizer.pose.toString())
                        telemetry.addData(
                            "Location of robot being transferred",
                            AutoGlobals.locationOfRobot.toString()
                        )
                        telemetry.addData("Drive speed", AutoGlobals.driveSpeed)

                        telemetry.update()
                        robot.update()

                        return true // keep looping
                    }
                },
                SequentialAction(
                    AutoSplines.Path1.followPath(),


                )
            )
        )

    }
}