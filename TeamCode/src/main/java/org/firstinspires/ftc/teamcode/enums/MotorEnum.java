package org.firstinspires.ftc.teamcode.enums;

public enum MotorEnum {
    FRONT_LEFT_MOTOR("front_left_motor"),
    FRONT_RIGHT_MOTOR("front_right_motor"),
    BACK_LEFT_MOTOR("back_left_motor"),
    BACK_RIGHT_MOTOR("back_right_motor");

    private final String motorType;

    MotorEnum(String motorType) {
        this.motorType = motorType;
    }

    public String getMotorType() {
        return motorType;
    }
}