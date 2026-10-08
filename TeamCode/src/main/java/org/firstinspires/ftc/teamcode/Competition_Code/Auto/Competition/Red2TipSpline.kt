package org.firstinspires.ftc.teamcode.Competition_Code.Auto.Competition

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Competition_Code.Actions.Actions
import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoGlobals
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoSplines
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.FollowPath
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline.PathFollower
import org.firstinspires.ftc.teamcode.Competition_Code.PinpointLocalizer.Localizer
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Servo
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses

@Autonomous(name = "Red2TipSpline", group = "Auto")
class Red2TipSpline : LinearOpMode() {

    override fun runOpMode() {
        AutoGlobals.targetRobotPath = PathFollower(AutoSplines.Path1.poses, AutoSplines.Path1.backwards)

        val dash: FtcDashboard = FtcDashboard.getInstance()
        telemetry = dash.telemetry

        val localizer = Localizer(hardwareMap, AutoSplines.Path1.poses[0])
        val drive = Drivetrain(hardwareMap, AllianceColor.Blue)
        val robot = Actions(hardwareMap, telemetry)


        robot.holder.state = Servo.State.STOP1
        robot.update()

        waitForStart()

        AutoGlobals.AutonomousRan = true


        runBlocking(
            ParallelAction(
                ParallelAction(
                    FollowPath(),
                object : Action {
                    override fun run(p: TelemetryPacket): Boolean {
                        if (isStopRequested) {
                            stop()
                        }

                        localizer.update()

                        AutoGlobals.locationOfRobot = Poses(
                            Localizer.pose.x,
                            Localizer.pose.y,
                            Localizer.pose.heading
                        )


                        telemetry.addData(
                            "Target Position",
                            AutoGlobals.targetRobotPositon.toString()
                        )
                        telemetry.addData("Current Pose", Localizer.Companion.pose.toString())
                        telemetry.addData(
                            "Location of robot being transferred",
                            AutoGlobals.locationOfRobot.toString()
                        )
                        telemetry.addData("Drive speed", AutoGlobals.driveSpeed)
                        telemetry.addData("Launcher rpm goal", robot.launcher.goalRPM)

                        telemetry.update()
                        robot.update()

                        return true // keep looping
                    }
                }
                ),
                SequentialAction(
                    AutoSplines.Launch1.followPath(),
                    robot.WaitAction(500.0),
                    AutoSplines.Intake1.followPath(),
                    robot.WaitAction(500.0),
                    AutoSplines.Launch2.followPath(),
                    robot.WaitAction(500.0),
                    AutoSplines.Intake2.followPath(),
                    robot.WaitAction(500.0),
                    AutoSplines.Launch3.followPath(),
                    robot.WaitAction(500.0),
                    AutoSplines.End.followPath(),

                    )
            )
        )

    }
}