package org.firstinspires.ftc.teamcode.Competition_Code.Tele.Testing

import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import java.util.Arrays

@Config
@TeleOp(name = "ServoTest2", group = "Linear OpMode")
class Servo2 : LinearOpMode() {
    override fun runOpMode() {
        val servoList = Arrays.asList<Servo?>(hardwareMap.get<Servo?>(Servo::class.java, "holder"))

        waitForStart()
        while (opModeIsActive()) {
            servoList.get(index)!!.setPosition(servoPose)
        }
    }

    companion object {
        var servoPose: Double = 0.0
        var index: Int = 0
    }
}