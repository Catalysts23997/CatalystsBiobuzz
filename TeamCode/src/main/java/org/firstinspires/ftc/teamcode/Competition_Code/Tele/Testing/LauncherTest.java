package org.firstinspires.ftc.teamcode.Competition_Code.Tele.Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@TeleOp(name = "LauncherTEst", group = "LinearOpMode")
public class LauncherTest extends LinearOpMode {
    Launcher launcher;
    private Servo servo;
    private double position;

    @Override
    public void runOpMode() {
        launcher = new Launcher(hardwareMap);
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
            telemetry.addData("leftrpm", launcher.getLeftRpm());
            telemetry.addData("rightrpm", launcher.getRightRpm());


            telemetry.update();
        }
    }
}