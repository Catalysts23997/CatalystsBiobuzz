package org.firstinspires.ftc.teamcode.Competition_Code.Auto.refactoring;

import static com.acmerobotics.roadrunner.ftc.Actions.runBlocking;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor;
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoPoints;

@Autonomous(name = "Red2Tip", group = "Auto")
class Red2Tip extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BaseAuto baseAuto = new BaseAuto(
                this,
                AutoPoints.Start1Red.pose,
                AllianceColor.Red
        );
        baseAuto.runAuto(
            (controller) -> true,
            (controller) -> {
                runBlocking(
                    new ParallelAction(
                        baseAuto.genericDrivetrainManager(controller),
                        new SequentialAction(
                            AutoPoints.Launch1Red.runToExact(),
                            controller.getRobot().WaitAction(500.0),
                            AutoPoints.Flower1Red.runToExact(),
                            controller.getRobot().WaitAction(500.0),
                            AutoPoints.Transition1Red.runToFast(),
                            AutoPoints.Launch2Red.runToExact(),
                            controller.getRobot().WaitAction(500.0),
                            AutoPoints.Flower2Red.runToExact(),
                            controller.getRobot().WaitAction(500.0),
                            AutoPoints.Launch2Red.runToExact(),
                            controller.getRobot().WaitAction(500.0),
                            AutoPoints.EndRed.runToExact()
                        )
                    )
                );
                return true;
            }
        );
    }
}
