package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Encoder-based, robot-centric mecanum movement example.
 *
 * In the Robot Configuration, name your four drive motors:
 * frontLeft, frontRight, backLeft, and backRight.
 *
 *  Commands to run
 *
 * goForward(inches, speed)
 * goBackward(inches, speed)
 * goRight(inches, speed)
 * goLeft(inches, speed)
 * turnClockwise(degrees, speed)
 * turnCounterClockwise(degrees, speed)
 * goDirection(degrees, inches, speed)
 *
 */
@Autonomous(name = "Mecanum Encoder Auto")
public class mechanumDriveCode extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    // Change these to match your robot, then test at a slow speed.
    private static final double TICKS_PER_REV = 537.7; // goBILDA 312 RPM motor
    private static final double WHEEL_DIAMETER_INCHES = 3.7795; // 96 mm wheel
    private static final double TICKS_PER_INCH =
            TICKS_PER_REV / (Math.PI * WHEEL_DIAMETER_INCHES);

    /* Tune this value: command turnClockwise(90), measure the actual turn,
       and adjust until it turns exactly 90 degrees. */
    private static final double TURN_TICKS_PER_DEGREE = 10.0;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        // If a wheel spins the wrong way, reverse that motor here instead.
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        resetEncoders();
        telemetry.addLine("Ready");
        telemetry.update();
        waitForStart();

        if (opModeIsActive()) {
            // Example autonomous path. Replace these with your own commands.
            goForward(24, 0.55);
            stopDrive();
        }
    }

    /** Drives forward by inches. */
    public void goForward(double inches, double speed) {
        goDirection(0, inches, speed);
    }

    /** Drives backward by inches. */
    public void goBackward(double inches, double speed) {
        goDirection(180, inches, speed);
    }

    /** Strafes right by inches. */
    public void goRight(double inches, double speed) {
        goDirection(90, inches, speed);
    }

    /** Strafes left by inches. */
    public void goLeft(double inches, double speed) {
        goDirection(270, inches, speed);
    }

    /** Turns clockwise by degrees. */
    public void turnClockwise(double degrees, double speed) {
        turn(degrees, speed);
    }

    /** Turns counterclockwise by degrees. */
    public void turnCounterClockwise(double degrees, double speed) {
        turn(-degrees, speed);
    }

    /**
     * Moves in a robot-relative direction for a distance in inches.
     * 0 = forward, 90 = right, 180 = backward, 270 = left.
     */
    public void goDirection(double degrees, double inches, double speed) {
        double radians = Math.toRadians(degrees);
        double forward = Math.cos(radians) * inches * TICKS_PER_INCH;
        double strafe = Math.sin(radians) * inches * TICKS_PER_INCH;

        // Mecanum encoder targets: forward +/- strafe.
        runToRelativePosition(
                forward + strafe,  // front left
                forward - strafe,  // front right
                forward - strafe,  // back left
                forward + strafe,  // back right
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
            telemetry.addData("FL / FR", "%d / %d", frontLeft.getCurrentPosition(), frontRight.getCurrentPosition());
            telemetry.update();
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
