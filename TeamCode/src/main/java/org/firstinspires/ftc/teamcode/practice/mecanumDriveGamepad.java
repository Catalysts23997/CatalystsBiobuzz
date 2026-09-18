package org.firstinspires.ftc.teamcode.practice;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "Mecanum Drive", group = "Drive")
public class mecanumDriveGamepad extends LinearOpMode {
    private DcMotor frontLeft, frontRight, backLeft, backRight;
    private IMU imu;

    private static final double STICK_DEADZONE = 0.055;
    private static final double NORMAL_SPEED = 0.82;
    private static final double SLOW_SPEED = 0.32;
    private static final double TURN_SCALE = 0.72;

    @Override
    public void runOpMode() {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        imu = hardwareMap.get(IMU.class, "imu");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        setDriveMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setBrake(true);

        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();

        telemetry.addLine("Ready. START resets field-forward heading.");
        telemetry.update();
        waitForStart();

        ElapsedTime startButtonCooldown = new ElapsedTime();
        while (opModeIsActive()) {
            if (gamepad1.start && startButtonCooldown.seconds() > 0.4) {
                imu.resetYaw();
                startButtonCooldown.reset();
            }


            double fieldX = shapeStick(gamepad1.left_stick_x);
            double fieldY = shapeStick(-gamepad1.left_stick_y);
            double turn = shapeStick(gamepad1.right_stick_x) * TURN_SCALE;


            double heading = imu.getRobotYawPitchRollAngles().getYaw(
                    org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS);
            double robotX = fieldX * Math.cos(heading) + fieldY * Math.sin(heading);
            double robotY = -fieldX * Math.sin(heading) + fieldY * Math.cos(heading);

            double frontLeftPower = robotY + robotX + turn;
            double frontRightPower = robotY - robotX - turn;
            double backLeftPower = robotY - robotX + turn;
            double backRightPower = robotY + robotX - turn;


            double largest = Math.max(1.0, Math.max(Math.abs(frontLeftPower),
                    Math.max(Math.abs(frontRightPower), Math.max(Math.abs(backLeftPower),
                            Math.abs(backRightPower)))));

            double speed = NORMAL_SPEED - (NORMAL_SPEED - SLOW_SPEED) * gamepad1.left_trigger;
            if (gamepad1.right_bumper) speed = 1.0;

            frontLeft.setPower(speed * frontLeftPower / largest);
            frontRight.setPower(speed * frontRightPower / largest);
            backLeft.setPower(speed * backLeftPower / largest);
            backRight.setPower(speed * backRightPower / largest);

            telemetry.addData("Heading", "%.1f deg", Math.toDegrees(heading));
            telemetry.addData("Speed", "%.0f%%", speed * 100);
            telemetry.addLine("Left trigger: precision | Right bumper: full | START: zero heading");
            telemetry.update();
        }
        stopDrive();
    }

        private double shapeStick(double input) {
        double magnitude = Math.abs(input);
        if (magnitude <= STICK_DEADZONE) return 0.0;
        double scaled = (magnitude - STICK_DEADZONE) / (1.0 - STICK_DEADZONE);
        return Math.copySign(0.60 * scaled + 0.40 * scaled * scaled * scaled, input);
    }

    private void setDriveMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    private void setBrake(boolean enabled) {
        DcMotor.ZeroPowerBehavior behavior = enabled
                ? DcMotor.ZeroPowerBehavior.BRAKE : DcMotor.ZeroPowerBehavior.FLOAT;
        frontLeft.setZeroPowerBehavior(behavior);
        frontRight.setZeroPowerBehavior(behavior);
        backLeft.setZeroPowerBehavior(behavior);
        backRight.setZeroPowerBehavior(behavior);
    }

    private void stopDrive() {
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }
}
