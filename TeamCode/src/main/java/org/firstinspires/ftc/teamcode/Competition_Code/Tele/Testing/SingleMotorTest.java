package org.firstinspires.ftc.teamcode.Competition_Code.Tele.Testing;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "SingleMotorTest", group = "Linear OpMode")
public class SingleMotorTest extends LinearOpMode {

    public DcMotorEx motor;

    double motorPower;
    ElapsedTime timer = new ElapsedTime();


    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.get(DcMotorEx.class, "intake");
        //telemetry = FtcDashboard.getInstance().getTelemetry();

        waitForStart();

        while(opModeIsActive()){
            if (gamepad1.left_bumper && timer.milliseconds() >=100){
                motorPower += 0.1;
                timer.reset();
            }
            else if (gamepad1.left_trigger_pressed && timer.milliseconds() >=100){
                motorPower -= 0.1;
                timer.reset();
            }

            if (gamepad1.x){
                motorPower = 1;
            }
            else if (gamepad1.a){
                motorPower = 0;
            }

            motor.setPower(motorPower);

            telemetry.addData("Motor Power: ", motorPower);
            telemetry.addData("Motor Velocity: ", motor.getVelocity());

            telemetry.update();
        }
    }
}
