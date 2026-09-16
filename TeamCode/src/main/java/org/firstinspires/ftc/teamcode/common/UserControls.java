package org.firstinspires.ftc.teamcode.common;

import com.qualcomm.robotcore.hardware.Gamepad;

public class UserControls {
    public static double getThrottle(Gamepad gamepad){
        return -gamepad.left_stick_y;
    }

    public static double getStrafe(Gamepad gamepad){
        return gamepad.left_stick_x;
    }

    public static double getRotate(Gamepad gamepad){
        return gamepad.right_stick_x;
    }

    public static boolean getStartButton(Gamepad gamepad){
        return gamepad.start;
    }

    public static boolean getBackButton(Gamepad gamepad){
        return gamepad.back;
    }

    public static boolean getButtonA(Gamepad gamepad) {
        return gamepad.a;
    }

    public static boolean getButtonB(Gamepad gamepad) {
        return gamepad.b;
    }

    public static boolean getButtonX(Gamepad gamepad) {
        return gamepad.x;
    }

    public static boolean getButtonY(Gamepad gamepad) {
        return gamepad.y;
    }

    public static boolean getButtonDpadUp(Gamepad gamepad) {
        return gamepad.dpad_up;
    }

    public static boolean getButtonDpadDown(Gamepad gamepad) {
        return gamepad.dpad_down;
    }

    public static boolean getButtonDpadRight(Gamepad gamepad) {
        return gamepad.dpad_right;
    }

    public static boolean getButtonDpadLeft(Gamepad gamepad) {
        return gamepad.dpad_left;
    }

    public static boolean getButtonL1(Gamepad gamepad) {
        return gamepad.left_bumper;
    }

    public static boolean getButtonR1(Gamepad gamepad) {
        return gamepad.right_bumper;
    }

    public static float getButtonL2(Gamepad gamepad) {
        return gamepad.left_trigger;
    }

    public static float getButtonR2(Gamepad gamepad) {
        return gamepad.right_trigger;
    }

    public static boolean getGuide(Gamepad gamepad){
        return gamepad.guide;
    }
}
