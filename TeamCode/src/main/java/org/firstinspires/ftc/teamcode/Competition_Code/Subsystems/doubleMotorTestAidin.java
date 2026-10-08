package org.firstinspires.ftc.teamcode.Competition_Code.Subsystems;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Double Motor Test", group = "Linear OpMode")
public class doubleMotorTestAidin extends LinearOpMode {

    public SingleLauncher motor1;
    public SingleLauncher motor2;

    double motor1Speed;
    double motor2Speed;
    ElapsedTime timer = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = new SingleLauncher(hardwareMap, "TestMotor1");
        motor2 = new SingleLauncher(hardwareMap, "TestMotor2");

        telemetry = FtcDashboard.getInstance().getTelemetry();

        waitForStart();

        while(opModeIsActive()){
            if (gamepad1.left_bumper && timer.milliseconds() >=100){
                motor1Speed += 0.1;
                timer.reset();
            }
            else if (gamepad1.left_trigger_pressed && timer.milliseconds() >=100){
                motor1Speed -= 0.1;
                timer.reset();
            }
            if (gamepad1.right_bumper && timer.milliseconds() >=100){
                motor2Speed += 0.1;
                timer.reset();
            }
            else if (gamepad1.left_trigger_pressed && timer.milliseconds() >=100){
                motor2Speed -= 0.1;
                timer.reset();
            }

            if (gamepad1.x){
                motor1Speed = 1;
            }
            else if (gamepad1.a){
                motor1Speed = 0;
            }
            if (gamepad1.y){
                motor2Speed = 1;
            }
            else if (gamepad1.b){
                motor2Speed = 0;
            }

            motor1.setSpeed(motor1Speed);
            motor1.update();
            motor2.setSpeed(motor2Speed);
            motor2.update();

            telemetry.addData("Motor 1 Power: ", motor1Speed);
            telemetry.addData("Motor 1 RPM: ", motor1.getRpm());
            telemetry.addData("Motor 2 Power: ", motor2Speed);
            telemetry.addData("Motor 2 RPM: ", motor2.getRpm());

            telemetry.update();

        }



    }
}
