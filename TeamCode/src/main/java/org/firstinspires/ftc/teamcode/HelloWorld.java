package org.firstinspires.ftc.teamcode;//this tells location the code is in



import com.qualcomm.robotcore.eventloop.opmode.OpMode;    // tells what other packages to use
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;    // packages have extra built in code that you can call

@TeleOp
public class HelloWorld extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello", "World");


    }

}




