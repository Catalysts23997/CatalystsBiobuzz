package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Move Forward Auto", group = "Drive")
public class moveForward extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private static final double TICKS_PER_REV = 537.7;
    private static final double WHEEL_DIAMETER_INCHES = 3.7795;
    private static final double TICKS_PER_INCH = TICKS_PER_REV / (Math.PI * WHEEL_DIAMETER_INCHES);
    private static final double TURN_TICKS_PER_DEGREE = 10.0;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // Reverse right side so positive power = forward
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();

        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            // --- EDIT YOUR PATH HERE ---
            
            goForward(24, 0.5); // Move forward 24 inches at 50% speed
            
            // ---------------------------
        }
    }

    public void goForward(double inches, double speed) {
        goDirection(0, inches, speed);
    }

    public void goBackward(double inches, double speed) {
        goDirection(180, inches, speed);
    }

    public void goRight(double inches, double speed) {
        goDirection(90, inches, speed);
    }

    public void goLeft(double inches, double speed) {
        goDirection(270, inches, speed);
    }

    public void turnClockwise(double degrees, double speed) {
        turn(degrees, speed);
    }

    public void turnCounterClockwise(double degrees, double speed) {
        turn(-degrees, speed);
    }

    public void goDirection(double degrees, double inches, double speed) {
        double radians = Math.toRadians(degrees);
        double forward = Math.cos(radians) * inches * TICKS_PER_INCH;
        double strafe = Math.sin(radians) * inches * TICKS_PER_INCH;

        runToRelativePosition(
                forward + strafe,
                forward - strafe,
                forward - strafe,
                forward + strafe,
                speed);
    }

    private void turn(double degrees, double speed) {
        double ticks = degrees * TURN_TICKS_PER_DEGREE;
        runToRelativePosition(ticks, -ticks, ticks, -ticks, speed);
    }

    private void runToRelativePosition(double flTicks, double frTicks,
                                       double blTicks, double brTicks, double speed) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + (int) Math.round(flTicks));
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + (int) Math.round(frTicks));
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + (int) Math.round(blTicks));
        backRight.setTargetPosition(backRight.getCurrentPosition() + (int) Math.round(brTicks));

        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(Math.abs(speed));

        while (opModeIsActive() &&
                (frontLeft.isBusy() || frontRight.isBusy() ||
                        backLeft.isBusy() || backRight.isBusy())) {
            idle();
        }
        stopDrive();
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void resetEncoders() {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    private void setPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    private void stopDrive() {
        setPower(0);
    }
}
