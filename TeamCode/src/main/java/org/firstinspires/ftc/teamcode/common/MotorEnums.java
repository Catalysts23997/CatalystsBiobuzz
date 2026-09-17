package org.firstinspires.ftc.teamcode.common;

public enum MotorEnums {
    FRONT_LEFT_MOTOR("front_left_motor"),
    FRONT_RIGHT_MOTOR("front_right_motor"),
    BACK_LEFT_MOTOR("back_left_motor"),
    BACK_RIGHT_MOTOR("back_right_motor");

    private final String motorType;

    MotorEnums(String motorType) {
        this.motorType = motorType;
    }

    public String getMotorType() {
        return motorType;
    }
}