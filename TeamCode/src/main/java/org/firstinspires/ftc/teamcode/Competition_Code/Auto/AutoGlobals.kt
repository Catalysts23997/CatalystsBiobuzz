package org.firstinspires.ftc.teamcode.Competition_Code.Auto

import org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline.PathFollower
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses

object AutoGlobals {

    var FarAuto: Boolean = false

    @JvmField
    var AutonomousRan: Boolean = false

    @JvmField
    var locationOfRobot: Poses? = null

    @JvmField
    var driveSpeed = 1.0

    @JvmField
    var targetRobotPositon: Poses = Poses(-12.5, -50.0, 0.0)
    var targetRobotPath: PathFollower = PathFollower(arrayListOf(Poses(0.0, 0.0, 0.0)), false)
    var drivingBackwards: Boolean = false
}

