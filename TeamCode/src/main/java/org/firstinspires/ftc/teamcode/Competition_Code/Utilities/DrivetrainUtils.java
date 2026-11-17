package org.firstinspires.ftc.teamcode.Competition_Code.Utilities;

import org.firstinspires.ftc.teamcode.Competition_Code.PinpointLocalizer.Localizer;
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain;

public class DrivetrainUtils {

    /// Sets drivetrain motor values to drive to a given [Poses]
    /// with the given `driveSpeed`. This function will return
    /// `true` if we have reached the target.
    ///
    /// Default values:
    ///  - `driveSpeed` = 1.0
    ///  - `toleranceDistance` = 3.0
    ///  - `toleranceDegrees` = 5.0
    public static boolean tickDrivetrainWithPose(
        Poses pose,
        Double driveSpeed,
        Double toleranceDistance,
        Double toleranceDegrees
    ) {

        Poses current = Localizer.pose;
        Drivetrain drive = Drivetrain.instance;

        double latError = pose.getY() - current.getY();
        double axialError = pose.getX() - current.getX();
        double headingError = Angles.wrap(pose.getHeading() - current.getHeading());

        double lateral = drive.getYpid().calculate(latError);
        double axial = drive.getXpid().calculate(axialError);
        double turn = drive.getRpid().calculate(headingError);


        double h = -Localizer.pose.getHeading();
        double rotX = -axial * Math.cos(h) - lateral * Math.sin(h);
        double rotY = -axial * Math.sin(h) + lateral * Math.cos(h);

        double powerCoefficient = driveSpeed;
        drive.leftFront.setPower(powerCoefficient*(rotY - rotX + turn));
        drive.leftBack.setPower(powerCoefficient*(rotY + rotX + turn));
        drive.rightFront.setPower(powerCoefficient*(rotY + rotX - turn));
        drive.rightBack.setPower(powerCoefficient*(rotY - rotX - turn));

        return (Math.abs(pose.getX() - Localizer.pose.getX()) <= toleranceDistance &&
                Math.abs(pose.getY() - Localizer.pose.getY()) <= toleranceDistance &&
                Math.abs(Angles.wrap(-pose.getHeading() + Localizer.pose.getHeading())) <= Math.toRadians(toleranceDegrees));
    }

}
