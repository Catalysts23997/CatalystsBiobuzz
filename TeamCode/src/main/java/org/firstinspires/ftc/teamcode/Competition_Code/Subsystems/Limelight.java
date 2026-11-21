package org.firstinspires.ftc.teamcode.Competition_Code.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Competition_Code.AllianceColor;
import org.firstinspires.ftc.teamcode.Competition_Code.CellPosition;

import java.util.List;

public class Limelight {
    // constants :)

    public static final List<Integer> RED_CELL_AUDIENCE_APRILTAGS = List.of(
        34, 35, 36, 37
    );
    public static final List<Integer> RED_CELL_AWAY_APRILTAGS = List.of(
        30, 31, 32, 33
    );
    public static final List<Integer> BLUE_CELL_AUDIENCE_APRILTAGS = List.of(
        38, 39, 40, 41
    );
    public static final List<Integer> BLUE_CELL_AWAY_APRILTAGS = List.of(
        42, 43, 44, 45
    );

    Limelight3A limelight;
    Pipeline currentPipeline = Pipeline.None;

    public Limelight(HardwareMap hardwareMap) {
        // access limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");

        // ensure we know what pipeline is active.
        limelight.pipelineSwitch(0);
    }

    public void switchPipeline(Pipeline desiredPipeline) {
        // first, stop polling data
        limelight.stop();

        // before continuing, check if we don't want a
        // pipeline active
        if (desiredPipeline == Pipeline.None) {
            return; // done!
        }

        // next, switch the pipeline
        int index = desiredPipeline.getIndex();
        limelight.pipelineSwitch(index);
        currentPipeline = desiredPipeline;

        // finally, start polling data
        limelight.start();
    }

    /// @return If the hive we are looking at is raised,
    /// allowing us to shoot.
    public boolean isHiveRaised(AllianceColor allianceColor, CellPosition cellPosition) {
        // first, check current pipeline
        if (currentPipeline == Pipeline.RedApriltag || currentPipeline == Pipeline.BlueApriltag) {
            LLResult result = limelight.getLatestResult();

            // ensure we can trust the values we receive
            if (!result.isValid())
                return false;

            // what tags do we compare against? the
            // other side!
            List<Integer> referenceTags = getReferenceTags(allianceColor, cellPosition);

            List<LLResultTypes.FiducialResult> tags = result.getFiducialResults();

            // count the tags we see
            int tagsSeen = 0;
            for (LLResultTypes.FiducialResult tag : tags) {
                if (referenceTags.contains(tag.getFiducialId()))
                    tagsSeen++;
            }

            // say yes if we see over half of the tags
            return tagsSeen > (referenceTags.size() / 2);
        } else {
            // not supported by current pipeline. just say
            // we can't shoot :)
            return false;
        }
    }

    private static List<Integer> getReferenceTags(AllianceColor allianceColor, CellPosition cellPosition) {
        List<Integer> referenceTags = null;
        switch (cellPosition) {
            case Audience:
                if (allianceColor == AllianceColor.Red)
                    referenceTags = RED_CELL_AWAY_APRILTAGS;
                else
                    referenceTags = BLUE_CELL_AWAY_APRILTAGS;
                break;
            case Away:
                if (allianceColor == AllianceColor.Red)
                    referenceTags = RED_CELL_AUDIENCE_APRILTAGS;
                else
                    referenceTags = BLUE_CELL_AUDIENCE_APRILTAGS;
                break;
        }
        return referenceTags;
    }

    public enum Pipeline {
        None,
        BallDetector,
        RedApriltag,
        BlueApriltag;

        public int getIndex() {
            switch (this) {
                case None:
                    return -1; // not possible
                case BallDetector:
                    return 0;
                case RedApriltag:
                    return 1;
                case BlueApriltag:
                    return 2;
            }

            return -1;
        }
    }
}
