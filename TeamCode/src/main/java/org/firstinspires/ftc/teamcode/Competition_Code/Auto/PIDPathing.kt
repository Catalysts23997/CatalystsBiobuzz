package org.firstinspires.ftc.teamcode.Competition_Code.Auto

import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.acmerobotics.roadrunner.Action
import com.acmerobotics.roadrunner.Vector2d
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoGlobals.targetRobotPath
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.AutoGlobals.targetRobotPositon
import org.firstinspires.ftc.teamcode.Competition_Code.Auto.Spline.PathFollower
import org.firstinspires.ftc.teamcode.Competition_Code.PinpointLocalizer.Localizer
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Angles
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Poses
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.Vector2D
import org.firstinspires.ftc.teamcode.Competition_Code.Utilities.findNearestPoint
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sign
import kotlin.math.sin


class FollowPath: Action {
    val time: ElapsedTime = ElapsedTime()
    var lastTime = -1.0

    private var lastVFL = 0.0
    private var lastVFR = 0.0
    private var lastVBL = 0.0
    private var lastVBR = 0.0

    private val kV = 0.015
    private val kA = 0.003
    private val kS = 0.05

    override fun run(p: TelemetryPacket): Boolean {
        val current = Localizer.pose
        val drive = Drivetrain.instance
        val currentTime = time.time()

        if (lastTime < 0) {
            lastTime = currentTime
            return true
        }

        val dt = currentTime-lastTime
        lastTime = currentTime

        if (dt <= 0.0) return true

        val path = targetRobotPath.update(Vector2D(current.x, current.y), current.heading)

        val vX = -path.x * cos(current.heading) - path.y * sin(current.heading)
        val vY = -path.x * sin(current.heading) + path.y * cos(current.heading)

        var targetHeading = path.heading
        if (AutoGlobals.drivingBackwards) {
            targetHeading = Angles.wrap(targetHeading + Math.PI) // Facing flipped 180 degrees
        }

        val headingError = Angles.wrap(path.heading - targetHeading)

        val turn = drive.Rpid.calculate(headingError)

        val vFL: Double = vX - vY + (turn)
        val vBL: Double = vX + vY + (turn)
        val vFR: Double = vX + vY - (turn)
        val vBR: Double = vX - vY - (turn)


        // 2. Calculate target wheel accelerations for Feedforward
        val aFL = (vFL - lastVFL) / dt
        val aFR = (vFR - lastVFR) / dt
        val aBL = (vBL - lastVBL) / dt
        val aBR = (vBR - lastVBR) / dt

        lastVFL = vFL
        lastVFR = vFR
        lastVBL = vBL
        lastVBR = vBR

        // 3. Compute motor power using Feedforward formula: V = kS * sgn(v) + kV * v + kA * a
        val pFL: Double = kS * sign(vFL) + kV * vFL + kA * aFL
        val pFR: Double = kS * sign(vFR) + kV * vFR + kA * aFR
        val pBL: Double = kS * sign(vBL) + kV * vBL + kA * aBL
        val pBR: Double = kS * sign(vBR) + kV * vBR + kA * aBR

        val maxPower = max(
            1.0, max(
                abs(pFL), max(
                    abs(pFR),
                    max(abs(pBL), abs(pBR))
                )
            )
        )

        val powerCoefficient = AutoGlobals.driveSpeed

        drive.leftFront.power = powerCoefficient*(pFL / maxPower)
        drive.rightFront.power = powerCoefficient*(pFR / maxPower)
        drive.leftBack.power = powerCoefficient*(pBL / maxPower)
        drive.rightBack.power = powerCoefficient*(pBR / maxPower)

        return true
    }
}



fun RunToExactForever(pose: Poses): Boolean {

        val current = Localizer.pose
        val drive = Drivetrain.instance

        val latError = pose.y - current.y
        val axialError = pose.x - current.x
        val headingError = Angles.wrap(pose.heading - current.heading)

        val lateral = drive.Ypid.calculate(latError)
        val axial = drive.Xpid.calculate(axialError)
        val turn = drive.Rpid.calculate(headingError)


        val h = -Localizer.pose.heading
        val rotX = -axial * cos(h) - lateral * sin(h)
        val rotY = -axial * sin(h) + lateral * cos(h)

        val powerCoefficient = AutoGlobals.driveSpeed
        drive.leftFront.power = powerCoefficient*(rotY - rotX + turn)
        drive.leftBack.power = powerCoefficient*(rotY + rotX + turn)
        drive.rightFront.power = powerCoefficient*(rotY + rotX - turn)
        drive.rightBack.power = powerCoefficient*(rotY - rotX - turn)


    return true
}

class SetDriveTarget @JvmOverloads constructor( val pose: Poses, val driveSpeed: Double = 1.0, val maxTime: Double = 8.0, val toleranceDistance: Double = 3.0, val toleranceDegrees: Double = 5.0): Action{
    val timer = ElapsedTime()
    private var started = false

    override fun run(p: TelemetryPacket): Boolean {

        if (!started) {
            AutoGlobals.driveSpeed = driveSpeed
            targetRobotPositon = pose
            timer.reset()
            started = true
        }

        val targetReached: Boolean = (abs( targetRobotPositon.x - Localizer.pose.x) <= toleranceDistance &&
                abs( targetRobotPositon.y - Localizer.pose.y) <= toleranceDistance &&
                abs(Angles.wrap(-targetRobotPositon.heading + Localizer.pose.heading)) <= Math.toRadians(toleranceDegrees))

        val isComplete = targetReached || timer.seconds() > maxTime

        return !isComplete
    }
}

class SetPathTarget @JvmOverloads constructor(val poses: ArrayList<Poses>, val isBackwards: Boolean = false, val driveSpeed: Double = 1.0, val maxTime: Double = 8.0, val toleranceDistance: Double = 3.0, val toleranceDegrees: Double = 5.0): Action{
    val timer = ElapsedTime()
    private var started = false

    override fun run(p: TelemetryPacket): Boolean {

        if (!started) {
            AutoGlobals.driveSpeed = driveSpeed
            AutoGlobals.drivingBackwards = isBackwards
            targetRobotPath = PathFollower(poses, isBackwards)

            timer.reset()
            started = true
        }
        val size= poses.size
        val finalPoint = poses.get(size-1)

        val targetReached: Boolean = (abs( finalPoint.x - Localizer.pose.x) <= toleranceDistance &&
                abs( finalPoint.y - Localizer.pose.y) <= toleranceDistance &&
                abs(Angles.wrap(-finalPoint.heading + Localizer.pose.heading)) <= Math.toRadians(toleranceDegrees))

        val isComplete = targetReached || timer.seconds() > maxTime

        return !isComplete
    }
}