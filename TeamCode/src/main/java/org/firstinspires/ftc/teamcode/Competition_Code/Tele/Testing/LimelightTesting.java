package org.firstinspires.ftc.teamcode.Competition_Code.Tele.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.Competition_Code.CellPosition;

@TeleOp(name = "LimelightTesting", group = "Linear OpMode")
public class LimelightTesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Limelight limelight = new Limelight(hardwareMap);

        waitForStart();

        limelight.switchPipeline(Limelight.Pipeline.RedApriltag);

        while (opModeIsActive()) {
            telemetry.addData("Is Hive Raised?", limelight.isHiveRaised(
                AllianceColor.Red, CellPosition.Audience
            ));

            telemetry.update();
        }
    }
}
