package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.enums.MotorEnum;
import org.firstinspires.ftc.teamcode.common.RobotHardware;
import org.firstinspires.ftc.teamcode.util.MathUtils;

import java.util.Map;

public class MecanumDrive {
    private final RobotHardware robot = new RobotHardware();

    public MecanumDrive(HardwareMap hardwareMap) { robot.init(hardwareMap); }

    public void driveRobotCentric(double throttle, double strafe, double turn, double speed){ drive(MathUtils.calculateRobotCentricDrivePowers(throttle, strafe, turn, speed)); }

    public void driveFieldCentric(double throttle, double strafe, double turn, double speed){
        double robotHeading = robot.getHeading();

        drive(MathUtils.calculateFieldCentricDrivePowers(throttle, strafe, turn, robotHeading, speed));
    }

    private void drive(Map<MotorEnum, Double> powers){ for (Map.Entry<MotorEnum, Double> entry : powers.entrySet()){ robot.setPowerToMotors(entry.getKey(), entry.getValue()); } }

    public void stop(){ drive(MathUtils.calculateRobotCentricDrivePowers(0, 0, 0, 0)); }
}
