package org.firstinspires.ftc.teamcode.util;

public class MathUtils {
    public static double getCurve(double value, double exponent) {return Math.signum(value) * Math.pow(Math.abs(value), exponent); }

    public static double clamp(double value, double min, double max){ return Math.max(min, Math.min(value, max)); }

    public static double getPower(double power, double maxPower, double speed){ return (power / clamp(maxPower, 0.01, 1)) * speed; }
}
