package org.firstinspires.ftc.teamcode.common;

public enum ButtonEnum {
    L1("left_bumper"),
    L2("left_trigger"),
    L3("left_stick_button"),
    R1("right_bumper"),
    R2("right_trigger"),
    R3("right_stick_button"),
    LEFT_ANALOG_X("left_stick_x"),
    LEFT_ANALOG_Y("left_stick_y"),
    RIGHT_ANALOG_X("right_stick_x"),
    RIGHT_ANALOG_Y("right_stick_y"),
    SHARE("share"),
    OPTIONS("options"),
    TRIANGLE("triangle"),
    CIRCLE("circle"),
    CROSS("cross"),
    SQUARE("square"),
    DPAD_UP("dpad_up"),
    DPAD_DOWN("dpad_down"),
    DPAD_LEFT("dpad_left"),
    DPAD_RIGHT("dpad_right"),
    PS("ps");


    private String buttonType;


    ButtonEnum(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonType(){
        return buttonType;
    }
}
