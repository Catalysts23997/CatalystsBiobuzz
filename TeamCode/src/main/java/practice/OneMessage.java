package practice;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "One Message")
public class OneMessage extends LinearOpMode {

    @Override
    public void runOpMode() {
        telemetry.addData("Hello", "It works!");
        telemetry.update();

        waitForStart();
    }
}
