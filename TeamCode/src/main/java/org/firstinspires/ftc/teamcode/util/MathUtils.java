package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.enums.MotorEnum;

import java.util.HashMap;
import java.util.Map;

public class MathUtils {
    public static double curve(double value, double exponent) {return Math.signum(value) * Math.pow(Math.abs(value), exponent); }

    public static double clamp(double value, double min, double max){ return Math.max(min, Math.min(value, max)); }

    private static double finalPower(double power, double maxPower, double speed){ return (power / maxPower) * speed; }

    public static Map<MotorEnum, Double> calculateFieldCentricDrivePowers(double throttle, double strafe, double turn, double heading, double speed) {
        double[] rotated = rotation(strafe, throttle, heading);

        return calculateDrivePowers(rotated[1], rotated[0], turn, speed);
    }

    public static Map<MotorEnum, Double> calculateRobotCentricDrivePowers(double throttle, double strafe, double turn, double speed) {
        return calculateDrivePowers(throttle, strafe, turn, speed);
    }

    private static Map<MotorEnum, Double> calculateDrivePowers(double throttle, double strafe, double turn, double speed){
        strafe = curve(strafe, 1.5);
        throttle = curve(throttle, 1.5);
        double smoothRotation = curve(turn, 1.5);

        double frontLeftPower = throttle + strafe + smoothRotation;
        double frontRightPower = throttle - strafe - smoothRotation;
        double backLeftPower = throttle - strafe + smoothRotation;
        double backRightPower = throttle + strafe - smoothRotation;

        double maxPower = normaliseMaxPower(frontLeftPower, frontRightPower, backLeftPower, backRightPower);

        Map<MotorEnum, Double> powers = new HashMap<>();

        powers.put(MotorEnum.FRONT_LEFT_MOTOR, finalPower(frontLeftPower, maxPower, speed));
        powers.put(MotorEnum.FRONT_RIGHT_MOTOR, finalPower(frontRightPower, maxPower, speed));
        powers.put(MotorEnum.BACK_LEFT_MOTOR, finalPower(backLeftPower, maxPower, speed));
        powers.put(MotorEnum.BACK_RIGHT_MOTOR, finalPower(backRightPower, maxPower, speed));

        return powers;
    }


    private static double normaliseMaxPower(double front_left_motor, double front_right_motor, double back_left_motor, double back_right_motor){
        return Math.max(1, Math.max(Math.abs(front_left_motor),
                Math.max(Math.abs(front_right_motor),
                        Math.max(Math.abs(back_left_motor),
                                Math.abs(back_right_motor)
                                )
                        )
                )
        );
    }

    private static double[] rotation(double x, double y, double radian){
        double primeX = (x * Math.cos(radian)) + (y * Math.sin(radian));
        double primeY = (y * Math.cos(radian)) - (x * Math.sin(radian));

        return new double[]{primeX, primeY};
    }
}
