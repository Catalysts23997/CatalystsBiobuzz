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
    private DCMotor motorFL;
    private DcMotor motorBR;
    private DcMotor motorBL;

    //---Code to run ONCE when the driver hits INIT---//
    @Override
    public void init() 
    {
        //---Initialize Motors---//
        motorFR  = hardwareMap.get(DcMotor.class, "motorFR");
        motorFL = hardwarMap.get(DCMotor.class, "motorFL");
        motorBR = hardwareMap.get(DCMotor.class, "motorBR");
        motorBL = hardwareMap.get(DCMotor.class, "motorBL");
        //---Set Motor Directions---//
        motorFR.setDirection(DcMotor.Direction.FORWARD);
        motorFL.setDirection(DcMotor.Direction.FORWARD);
        motorBL.setDirection(DcMotor.Direction.FORWARD);
        motorBR.setDirection(DcMotor.Direction.FORWARD);
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

    public void drivebase()
    {
        //Get variables from the gamepad
        double driveForward = gamepad1.left_stick_y;
        double driveSide = gamepad1.left_stick_x;
        double driveTurn = gamepad1.right_stick_y;
        
        //Translate inputs to outputs for motors
        motorBL.setpower = (driveForward - driveTurn - driveSide);
        motorBR.setpower = (driveForward + driveTurn + driveSide);
        motorFL.setpower = (driveForward + driveTurn + driveSide);
        motorFR.setpower = (driveForward - driveTurn - driveSide);
    
    }

}
