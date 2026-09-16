package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {
    private DcMotor[] motors;
    private DcMotor front_left_motor = null;
    private DcMotor front_right_motor = null;
    private DcMotor back_left_motor = null;
    private DcMotor back_right_motor = null;
    private IMU imu = null;


    HardwareMap hdwMap = null;

    public void init(HardwareMap hardwareMap) {
        hdwMap = hardwareMap;

        front_left_motor = hdwMap.get(DcMotor.class, "front_left_motor");
        front_right_motor = hdwMap.get(DcMotor.class, "front_right_motor");
        back_left_motor = hdwMap.get(DcMotor.class, "back_left_motor");
        back_right_motor = hdwMap.get(DcMotor.class, "back_right_motor");
        imu = hdwMap.get(IMU.class, "imu");

        motors = new DcMotor[]{front_left_motor, front_right_motor, back_left_motor, back_right_motor};

        front_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        back_left_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotor motor : motors) { motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }
        for (DcMotor motor : motors) { motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); }
        for (DcMotor motor : motors) { motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); }
    }
}
