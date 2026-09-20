package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.HashMap;
import java.util.Map;

public class RobotHardware {
    private final Map<MotorEnum, DcMotor> motors = new HashMap<>();
    private IMU imu = null;
    private HardwareMap hdwMap = null;


    public void init(HardwareMap hardwareMap) {
        hdwMap = hardwareMap;

        for (MotorEnum motorType : MotorEnum.values()) {
            DcMotor motor = hdwMap.get(DcMotor.class, motorType.getMotorType());
            motors.put(motorType, motor);
        }

        imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        motors.get(MotorEnum.FRONT_LEFT_MOTOR).setDirection(DcMotorSimple.Direction.REVERSE);
        motors.get(MotorEnum.BACK_LEFT_MOTOR).setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotor motor : motors.values()) { motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); }
    }

    public void setPowerToMotors(MotorEnum motorType, double power){
        DcMotor motor = motors.get(motorType);

        if (motor != null) { motor.setPower(power); }
    }

    public double getHeading(){ return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS); }
}
