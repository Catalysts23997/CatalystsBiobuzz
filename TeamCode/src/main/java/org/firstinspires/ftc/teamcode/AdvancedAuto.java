package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

@Autonomous(name = "QualAuto2", group = "Linear OpMode")
public class AdvancedAuto extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime driveRunTime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor intake = null;
    private DcMotor outtake = null;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTagProcessor;
    static double constantSpeed = 0.75;
    static double turnSpeed = 0.5;
    final double wheelDiameter = 4.26;
    final double motorRevCount = 28;
    //final double motorRevCount = 435;
    final double countsPerInch = (motorRevCount / (wheelDiameter * 3.1415));

    public int path = 0;

    @Override
    public void runOpMode() {
        frontLeftDrive = hardwareMap.get(DcMotor.class, "TLD");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BLD");
        frontRightDrive = hardwareMap.get(DcMotor.class, "TRD");
        backRightDrive = hardwareMap.get(DcMotor.class, "BRD");
        intake = hardwareMap.get(DcMotor.class, "IN");
        outtake = hardwareMap.get(DcMotor.class, "OT");

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        outtake.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        encoderDrive(constantSpeed, 84, 84, 1);
        encoderDrive(turnSpeed, 8, 0, 1);
        }
    public void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS) {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        // Ensure that the OpMode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = frontLeftDrive.getCurrentPosition() + (int)(leftInches * countsPerInch);
            newFrontRightTarget = frontRightDrive.getCurrentPosition() + (int)(rightInches * countsPerInch);
            newBackLeftTarget = backLeftDrive.getCurrentPosition() + (int)(leftInches * countsPerInch);
            newBackRightTarget = backRightDrive.getCurrentPosition() + (int)(rightInches * countsPerInch);
            frontLeftDrive.setTargetPosition(newFrontLeftTarget);
            frontRightDrive.setTargetPosition(newFrontRightTarget);
            backLeftDrive.setTargetPosition(newBackLeftTarget);
            backRightDrive.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            frontLeftDrive.setPower(Math.abs(speed));
            frontRightDrive.setPower(Math.abs(speed));
            backLeftDrive.setPower(Math.abs(speed));
            backRightDrive.setPower(Math.abs(speed));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (frontLeftDrive.isBusy() && frontRightDrive.isBusy() && backLeftDrive.isBusy() && backRightDrive.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Running to",  " %7d :%7d", newFrontLeftTarget,  newFrontRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d",
                        frontLeftDrive.getCurrentPosition(), frontRightDrive.getCurrentPosition());
                telemetry.update();
            }

            // Stop all motion;
            frontLeftDrive.setPower(0);
            frontRightDrive.setPower(0);
            backLeftDrive.setPower(0);
            backRightDrive.setPower(0);


            // Turn off RUN_TO_POSITION
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);   // optional pause after each move.
        }
    }
    public void shoot() {
        outtake.setPower(1);
        sleep(3000);
        outtake.setPower(0);
        sleep(100);
    }
    //Thank you Sri
    public void scan() {
        while (path == 0) {
            aprilTagProcessor = new AprilTagProcessor.Builder()
                    .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11) // Tag #
                    .setDrawTagID(true) // Draw tag ID on the preview stream
                    .build();
            visionPortal = new VisionPortal.Builder()
                    .addProcessor(aprilTagProcessor) // Add AprilTag processor
                    .setCamera(hardwareMap.get(WebcamName.class, "AprilTagCam")) // Use configured webcam
                    .enableLiveView(true) // Optional: allows live preview in DS
                    .build();
            while (opModeIsActive()) {
                // Get a list of all currently detected tags
                List<AprilTagDetection> currentDetections = aprilTagProcessor.getDetections();

                // Loop through each detection
                for (AprilTagDetection detection : currentDetections) {

                    if (detection.metadata != null) { // Check if metadata is available
                        // Basic info- It reads the tags info
                        int tagId = detection.id; // Tag numeric]
                        if (tagId == 21) {
                            //GPP
                            path = 1;
                        }
                        else if (tagId == 22) {
                            //PGP
                            path = 2;
                        }
                        else if (tagId == 23) {
                            //PPG
                            path = 3;
                        }
                    }
                }
            }
        }

    }
}
