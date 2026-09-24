package org.firstinspires.ftc.teamcode.Competition_Code.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

public class Limelight {
    Limelight3A ll3A;



    public Limelight (HardwareMap hardwareMap) {
        ll3A = hardwareMap.get(Limelight3A.class, "Limelight");
        ll3A.pipelineSwitch(0);
    }
    public void start(){
        ll3A.start();
    }

    public Pose3D getPose() {
        return  ll3A.getLatestResult().getBotpose();
    }
}
