package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp_2026_2027", group="Iterative OpMode")
public class TeleOp_2026_2027 extends OpMode
{
    //---Declare Motors---//
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motorFR;

    //---Code to run ONCE when the driver hits INIT---//
    @Override
    public void init() 
    {
        //---Initialize Motors---//
        motorFR  = hardwareMap.get(DcMotor.class, "motorFR");

        //---Set Motor Directions---//
        leftDrive.setDirection(DcMotor.Direction.REVERSE);

        //---Add telemetry data---//
        telemetry.addData("Status", "Initialized");
    }

    //---Code to run REPEATEDLY after the driver hits INIT, but before they hit START---//
    @Override
    public void init_loop() 
    {
    
    }

    //---Code to run ONCE when the driver hits START---//
    @Override
    public void start() 
    {
        runtime.reset();
    }

    //---Code to run REPEATEDLY after the driver hits START but before they hit STOP---//
    @Override
    public void loop() 
    {

    }

    //---Code to run ONCE after the driver hits STOP---//
    @Override
    public void stop() 
    {
    
    }
    
    public void drivebase()
    {
    
    }
    
    public void launcher()
    {
    
    }
    
    public void intake()
    {
    
    }
    
    public void hood()
    {
    
    }

}
