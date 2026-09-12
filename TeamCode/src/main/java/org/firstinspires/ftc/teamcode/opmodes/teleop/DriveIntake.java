package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor; // Added for the continuous motor

@TeleOp
public class MecanumDriveOpMode extends OpMode {
    newMecanumDrive drive = new newMecanumDrive();

    // Motor configuration
    private DcMotor expansionMotor;

    // Variables for drivetrain
    double forward, strafe, rotate;

    // State variables for continuous motor toggles
    boolean isContinuousForward = false;
    boolean isContinuousReverse = false;
    boolean lastGamepad1A = false;
    boolean lastGamepad1B = false;

    @Override
    public void init() {
        drive.init(hardwareMap);

        // Retrieve the motor on Port 1 from the hardware map.
        // Make sure "expansion_motor" matches the name configured on your Driver Station.
        expansionMotor = hardwareMap.get(DcMotor.class, "expansion_motor");
        expansionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        // BUTTON A (Continuous Forward Toggle) ---
        if (gamepad1.a && !lastGamepad1A) {
            isContinuousForward = !isContinuousForward;
            if (isContinuousForward) {
                isContinuousReverse = false; // Turn off reverse if forward is activated
            }
        }
        lastGamepad1A = gamepad1.a;

        // BUTTON B (Continuous Reverse Toggle) ---
        if (gamepad1.b && !lastGamepad1B) {
            isContinuousReverse = !isContinuousReverse;
            if (isContinuousReverse) {
                isContinuousForward = false; // Turn off forward if reverse is activated
            }
        }
        lastGamepad1B = gamepad1.b;

        // --- 3. SET EXPANSION MOTOR POWER BASED ON TOGGLE STATE ---
        if (isContinuousForward) {
            expansionMotor.setPower(1.0);  // Full speed forward (Adjust speed if 1.0 is too fast)
        } else if (isContinuousReverse) {
            expansionMotor.setPower(-1.0); // Full speed reverse
        } else {
            expansionMotor.setPower(0.0);  // Stop when both toggles are off
        }

        // --- 4. STANDARD DRIVETRAIN CONTROLS ---
        forward = gamepad1.left_stick_y;
        strafe = -gamepad1.left_stick_x;
        rotate = -gamepad1.right_stick_x;

        // D-pad overrides or standard stick drive
        if (gamepad1.dpad_up) {
            drive.drive(-1.0, 0, 0);
        } else if (gamepad1.dpad_down) {
            drive.drive(1.0, 0, 0);
        } else {
            drive.drive(forward, strafe, rotate);
        }

        // --- 5. TELEMETRY READOUT ---
        String motorState = "STOPPED";
        if (isContinuousForward) motorState = "FORWARD";
        if (isContinuousReverse) motorState = "REVERSE";

        telemetry.addData("Expansion Motor State", motorState);
        telemetry.addData("Front Left RPM", drive.getRPMFrontLeft());
        telemetry.addData("Back Left RPM", drive.getRPMBackLeft());
        telemetry.addData("Front Right RPM", drive.getRPMFrontRight());
        telemetry.addData("Back Right RPM", drive.getRPMBackRight());
        telemetry.update();
    }
}
