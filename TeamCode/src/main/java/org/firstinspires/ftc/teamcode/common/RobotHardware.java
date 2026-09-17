package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.MathUtils;

import java.util.HashMap;
import java.util.Map;

public class RobotHardware {
    private final Map<MotorEnums, DcMotor> motors = new HashMap<>();
    private IMU imu = null;
    private HardwareMap hdwMap = null;


    public void init(HardwareMap hardwareMap) {
        hdwMap = hardwareMap;

        for (MotorEnums motorType : MotorEnums.values()) {
            DcMotor motor = hdwMap.get(DcMotor.class, motorType.getMotorType());
            motors.put(motorType, motor);
        }

        imu = hdwMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP, RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

        motors.get(MotorEnums.FRONT_LEFT_MOTOR).setDirection(DcMotorSimple.Direction.REVERSE);
        motors.get(MotorEnums.BACK_LEFT_MOTOR).setDirection(DcMotorSimple.Direction.REVERSE);

        for (DcMotor motor : motors.values()) { motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER); motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); }
    }

    public void setPowerToMotors(MotorEnums motorType, double power, double maxPower, double speed){
        DcMotor motor = motors.get(motorType);

        if (motor != null) { motor.setPower(MathUtils.getPower(power, maxPower, speed)); }
    }
}
