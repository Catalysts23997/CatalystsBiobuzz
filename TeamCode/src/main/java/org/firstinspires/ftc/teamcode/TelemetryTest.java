package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
;
@TeleOp
public class TelemetryTest extends OpMode {

    @Override
    public void init() {
        telemetry.addData("Hello", "Decagonators");
    }

    @Override
    public void loop() {

    }
}
