package org.firstinspires.ftc.teamcode.Competition_Code.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {

    /// This value controls how fast the motors will be spinning.
    /// TODO: ensure this value is fine. just got it from testing
    public static final double MOTOR_SPEED = 0.8;

    ///  The current state of the intake system
    public State state = State.STOPPED;

    /// The intake motor
    DcMotor intake;

    /// Declare a new instance of the intake system.
    ///
    /// # Only one instance should be active at a given time!
    public Intake(HardwareMap hardwareMap) {
        // Assign the physical motor to our variable
        intake = hardwareMap.get(DcMotor.class, "intake");
        // Make sure we know+ -the default state of our motor
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setPower(0);
    }

    // Helper functions to make setting the state a little more convenient
    ///  Set the current state of the intake system
    public void setState(State state) {
        this.state = state;
    }

    ///  Tick the intake system
    public void update() {
        switch (state) {
            case INTAKING:
                doIntake();
                break;
            case REVERSE:
                doReverse();
                break;
            case STOPPED:
                stopIntake();
                break;
        }
    }

    ///  Make motor spin
    private void doIntake() {
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        intake.setPower(MOTOR_SPEED);

    }

    ///  Make motor spin in reverse
    private void doReverse() {
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        intake.setPower(MOTOR_SPEED);

    }

    /// Make motor stop intaking
    private void stopIntake() {
        intake.setPower(0);

    }

    /// This enum contains every state that the intake system can be in.
    ///
    /// Fields:
    /// - INTAKING: The intake motors will **spin**
    /// - STOPPED: The intake motors will **ball2**
    public enum State {
        ///  Make motors spin
        INTAKING,
        /// Reverse the motors
        REVERSE,
        /// Make motors ball2
        STOPPED
    }
}
