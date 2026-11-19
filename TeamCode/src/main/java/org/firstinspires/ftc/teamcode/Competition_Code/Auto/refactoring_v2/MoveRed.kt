package org.firstinspires.ftc.teamcode.Competition_Code.Auto.refactoring_v2

import com.acmerobotics.roadrunner.ParallelAction
import com.acmerobotics.roadrunner.SequentialAction
import com.acmerobotics.roadrunner.ftc.runBlocking
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoPoints

@Autonomous(name = "MoveRed (Refactoring v2)", group = "Auto")
class MoveRed: LinearOpMode() {
    override fun runOpMode() {
        // create new auto controller
        val controller = AutoController(
            this,
            AutoPoints.Start2Red.pose,
            AllianceColor.Red
        )

        // wait for the controller
        waitForStart()

        // tell the controller we are starting autonomous
        controller.startAutonomous()

        // actually run our auto
        runBlocking(
            ParallelAction(
                controller.genericDriveAction(),
                SequentialAction(
                    AutoPoints.EndRed.runToExact(),
                )
            )
        )

    }
}