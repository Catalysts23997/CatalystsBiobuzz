package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.Gamepad;

public class UserControls {
    private static final double deadZone = 0.05;


    private static double getDeadZone(double value) { return Math.abs(value) < deadZone ? 0 : value; }

    public static double getL2Button(Gamepad gamepad) { return getDeadZone(gamepad.left_trigger); }

    public static double getR2Button(Gamepad gamepad) { return getDeadZone(gamepad.right_trigger); }

    public static double getLeftAnalogY(Gamepad gamepad){ return getDeadZone(-gamepad.left_stick_y); }

    public static double getLeftAnalogX(Gamepad gamepad){ return getDeadZone(gamepad.left_stick_x); }

    public static double getRightAnalogX(Gamepad gamepad){ return getDeadZone(gamepad.right_stick_x); }

    public static double getRightAnalogY(Gamepad gamepad){ return getDeadZone(gamepad.right_stick_y); }

    public static boolean getShareButton(Gamepad gamepad){ return gamepad.share; }

    public static boolean getOptionsButton(Gamepad gamepad){ return gamepad.options; }

    public static boolean getTriangleButton(Gamepad gamepad) { return gamepad.triangle; }

    public static boolean getCircleButton(Gamepad gamepad) { return gamepad.circle; }

    public static boolean getCrossButton(Gamepad gamepad) { return gamepad.cross; }

    public static boolean getSquareButton(Gamepad gamepad) { return gamepad.square; }

    public static boolean getDpadUpButton(Gamepad gamepad) {
        return gamepad.dpad_up;
    }

    public static boolean getDpadDownButton(Gamepad gamepad) {
        return gamepad.dpad_down;
    }

    public static boolean getDpadRightButton(Gamepad gamepad) {
        return gamepad.dpad_right;
    }

    public static boolean getDpadLeftButton(Gamepad gamepad) {
        return gamepad.dpad_left;
    }

    public static boolean getL1Button(Gamepad gamepad) {
        return gamepad.left_bumper;
    }

    public static boolean getR1Button(Gamepad gamepad) {
        return gamepad.right_bumper;
    }

    public static boolean getL3Button(Gamepad gamepad) { return gamepad.left_stick_button; }

    public static boolean getR3Button(Gamepad gamepad) { return gamepad.right_stick_button; }

    public static boolean getPSButton(Gamepad gamepad){ return gamepad.ps; }
}
