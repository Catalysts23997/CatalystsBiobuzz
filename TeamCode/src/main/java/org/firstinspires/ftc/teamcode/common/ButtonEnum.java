package org.firstinspires.ftc.teamcode.common;

public enum ButtonEnum {
    L1("left_bumper"),
    L3("left_stick_button"),
    R1("right_bumper"),
    R3("right_stick_button"),
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


    private final String buttonType;


    ButtonEnum(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getButtonType(){
        return buttonType;
    }
}
