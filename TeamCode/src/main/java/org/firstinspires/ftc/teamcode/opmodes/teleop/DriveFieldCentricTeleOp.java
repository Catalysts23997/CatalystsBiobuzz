package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;


@TeleOp (name = "Field Centric", group = "Drive")
public class DriveFieldCentricTeleOp extends OpMode {
    private MecanumDrive mecanumDrive;

    @Override
    public void init(){
        mecanumDrive = new MecanumDrive(hardwareMap);
    }

    @Override
    public void loop(){

    }
}
