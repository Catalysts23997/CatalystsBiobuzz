package org.firstinspires.ftc.teamcode.Competition_Code.Tele.Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.SingleLauncher;

@Disabled
@TeleOp(name = "LauncherTEst", group = "LinearOpMode")
public class LauncherTest extends LinearOpMode {
    SingleLauncher launcher;
    private Servo servo;
    private double position;

    @Override
    public void runOpMode() {
        launcher = new SingleLauncher(hardwareMap);
        telemetry = FtcDashboard.getInstance().getTelemetry();

        double speed = 0;
        ElapsedTime timer = new ElapsedTime();

        waitForStart();


        while (opModeIsActive()) {

            if (gamepad1.dpad_up && timer.milliseconds() >=100){
                speed += 0.1;
                timer.reset();
            }
            if (gamepad1.dpad_down&& timer.milliseconds() >=100){
                speed -= 0.1;
                timer.reset();

            }
            if(gamepad1.a){
                speed = 1;
            }
            if(gamepad1.b){
                speed = 0;
            }

            launcher.setSpeed(speed);
            launcher.update();

            telemetry.addData("power ", speed);
            telemetry.addData("rpm", launcher.rpm);


            telemetry.update();
        }
    }
}