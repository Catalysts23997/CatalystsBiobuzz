package org.firstinspires.ftc.teamcode.OpModes.Autos;

import org.firstinspires.ftc.teamcode.RIANRobot;

import dev.nextftc.robot.opmode.BulkReadHook;
import dev.nextftc.robot.opmode.NextAutonomous;
import dev.nextftc.robot.opmode.NextOpMode;
import dev.nextftc.robot.triggers.CommandGamepad;

@NextAutonomous(name = "Main Autonomous")
public class MainAutonomous extends NextOpMode {
    private final RIANRobot robot;
    CommandGamepad driver = new CommandGamepad(gamepad1);

    public MainAutonomous(RIANRobot robot) {
        super(robot, BulkReadHook.INSTANCE);
        this.robot = robot;
    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void start() {

    }

    @Override
    public void periodic() {

    }

    @Override
    public void end() {

    }
}
