package org.firstinspires.ftc.teamcode.Competition_Code.Tele.Extra;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "MyFirstOpMode", group = "LinearOpMode")
public class MyFirstOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        ElapsedTime timer1 = new ElapsedTime();
        String message = "Hello World";

        waitForStart();

        timer1.reset();

        while (opModeIsActive()) {
            telemetry.addData("Message: ", message);
            telemetry.addData("Time: ", timer1.seconds());
            telemetry.update();
        }
    }
}
