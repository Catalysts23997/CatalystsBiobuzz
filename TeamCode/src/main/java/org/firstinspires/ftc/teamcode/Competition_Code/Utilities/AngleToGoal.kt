package org.firstinspires.ftc.teamcode.Competition_Code.Utilities

import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor
import org.firstinspires.ftc.teamcode.Competition_Code.Subsystems.Drivetrain
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan
import kotlin.math.atan2

fun goalAngle(
    currentX: Double,
    currentY: Double,
    alliance: AllianceColor
): Double {

    val basketX = if (alliance == AllianceColor.Blue) 12.5 else -12.5
    val basketY = if (currentY >0) 18 else -18

    val dx = basketX - currentX
    val dy = basketY - currentY

    return atan2(dx, dy)
}
