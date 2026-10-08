package org.firstinspires.ftc.teamcode.Competition_Code.Actions;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Intake;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Lights;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Pulley;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Servo;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Intake.State;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.SingleLauncher;

public class Actions {


    public Servo holder;

    public Intake intake;
    Pulley pulley;

    public SingleLauncher launcher;

    public Lights lights;

    boolean green = false;
    boolean red = false;

    Telemetry telemetry;
    ElapsedTime timer;

    public void update() {

        intake.update();
        pulley.update();

        holder.update();

        //color control
        if(green){
            lights.color = Lights.Color.green;
        } else if (red) {
            lights.color = Lights.Color.red;
        } else if(timer.milliseconds() >=500) {
            lights.color = Lights.Color.yellow;
        }
        else {
            lights.color = Lights.Color.blue;
        }
        if(timer.milliseconds() >=1000) {
            timer.reset();
        }
        lights.update();


        //telemetry
        launcher.update();
        telemetry.addData("Light", lights.color.toString());
        telemetry.addData("Light", lights.color.value);

        telemetry.addData("Intake State", intake.state);
        telemetry.addData("Pulley State", pulley.state);

        telemetry.addData("Holder State", holder.state);

        telemetry.addData("Launcher Speed", launcher.getRpm());


    }

    public Actions(HardwareMap hardwareMap, Telemetry telemetry) {
        intake = new Intake(hardwareMap);
        pulley = new Pulley(hardwareMap);

        holder = new Servo(hardwareMap,"holder");

        launcher = new SingleLauncher(hardwareMap);

        lights = new Lights(hardwareMap);

        timer = new ElapsedTime();

        this.telemetry = telemetry;
    }

    public Action WaitAction(double waitMs) {

        return new Action() {

            private final ElapsedTime timer = new ElapsedTime();
            boolean initialized = false;


            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                // initialize timer on first run
                if (!initialized) {
                    initialized =true;
                    timer.reset();
                }

                // return true until wait time is reached
                return timer.milliseconds() < waitMs;
            }
        };
    }


    //intake
    public Action StartIntake = new Action() {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            // Turn on our intake system!
            intake.state = State.INTAKING;
            pulley.state = Pulley.State.On;

            // We return false because this only has to run once
            return false;
        }
    };

    public Action ReverseIntake = new Action() {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            // reverse intake system
            intake.state = State.REVERSE;
            pulley.state = Pulley.State.Reverse;

            // We return false because this only has to run once
            return false;
        }
    };

    public Action StopIntake = new Action() {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            // Stop our intake system
            intake.state = State.STOPPED;
            pulley.state = Pulley.State.Off;

            // We return false because this only has to run once
            return false;
        }
    };

    //servo control

    public Action HoldBall = new Action() {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            holder.state = Servo.State.STOP1;

            // We return false because this only has to run once
            return false;
        }
    };


    public Action ReleaseBall = new Action() {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            holder.state = Servo.State.RESET;

            // We return false because this only has to run once
            return false;
        }
    };

    //shooting stuff
    double speedUpTime = 1300;      // time for flywheel to reach speed
    double servoReleaseTime = 400;  // ms for servo release
    double pulleyShootTime = 1400;  // ms for pulley to shoot 3 balls

    public Action StartShooter = new Action() {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            launcher.start();

            // We return false because this only has to run once
            return false;
        }

    };

    public Action StopShooter = new Action() {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            launcher.stop();

            // We return false because this only has to run once
            return false;
        }
    };


    public Action CycleShootClose() {
        return new Action() {
            final ElapsedTime timer = new ElapsedTime();
            boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                // Phase 1: Wait for ball2 (the start signal)
                if (!initialized) {
                    timer.reset();
                    initialized = true;
                }

                if(launcher.atTargetRPM(launcher.getGoalRPM(), toleranceRPM)){
                    pulley.state = Pulley.State.Slow;
                    intake.state = State.INTAKING;
                    green = true;
                    red = false;
                }
                else {
                    pulley.state = Pulley.State.Off;
                    intake.state = State.STOPPED;
                    red = true;
                    green = false;
                }

                if(timer.milliseconds()>=pulleyShootTime+200){
                    pulley.state = Pulley.State.Off;
                    intake.state = State.STOPPED;
                    holder.state = Servo.State.STOP1;
                    red = false;
                    green = false;
                    return false;
                }
                return true;
            }
        };
    }

    public Action ControlledShot() {
        return new Action() {
            final ElapsedTime timer = new ElapsedTime();
            boolean initialized = false;
            double timeMarker = -500;
            boolean recovering = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                // Phase 1: Wait for ball2 (the start signal)
                if (!initialized) {
                    timer.reset();
                    initialized = true;
                }
                double currentTime = timer.milliseconds();

                if (!launcher.atTargetRPM(launcher.getGoalRPM(), toleranceRPM)){
                    pulley.state = Pulley.State.Off;
                    intake.state = State.STOPPED;
                    red = true;
                    green = false;

                    // only mark once
                    if (!recovering) {
                        timeMarker = currentTime;
                        recovering = true;
                    }
                }

                if (launcher.atTargetRPM(launcher.getGoalRPM(), toleranceRPM)
                        && currentTime-timeMarker >=500) {

                    pulley.state = Pulley.State.Slow;
                    intake.state = State.INTAKING;
                    green = true;
                    red = false;

                    recovering = false;
                }

                if(timer.milliseconds()>=pulleyShootTime+800){
                    pulley.state = Pulley.State.Off;
                    intake.state = State.STOPPED;
                    holder.state = Servo.State.STOP1;
                    red = false;
                    green = false;

                    return false;
                }
                return true;
            }
        };
    }

    double toleranceRPM = 150;

    public Action WaitForLauncher() {
        return new Action() {

            private final ElapsedTime timer = new ElapsedTime();

            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                if (!initialized) {
                    timer.reset();
                    initialized = true;
                }

                boolean atSpeed = launcher.atTargetRPM(launcher.getGoalRPM(), toleranceRPM);

                // Keep running while:
                //  - NOT at speed
                //  - AND timeout not exceeded
                return !(atSpeed || timer.milliseconds() > (speedUpTime -  servoReleaseTime));
            }
        };
    }

    public SequentialAction Eject() {
        return new SequentialAction(
            ReverseIntake,
            WaitAction(10),
            StopIntake
        );
    }


    public SequentialAction Shoot() {
        return new SequentialAction(
                StartShooter,
                StopIntake,
                ReleaseBall,
                WaitAction(servoReleaseTime),
                WaitForLauncher(),
                CycleShootClose()
        );
    }


    public SequentialAction ShootSlow() {
        return new SequentialAction(
                StartShooter,
                StopIntake,
                ReleaseBall,
                WaitAction(servoReleaseTime),
                WaitForLauncher(),
                ControlledShot()
        );
    }

}