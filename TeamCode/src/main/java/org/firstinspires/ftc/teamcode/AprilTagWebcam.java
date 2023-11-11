package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class AprilTagWebcam {

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    /**
     * The variable used to store the decimation value
     * Adjust Image Decimation to trade-off detection-range for detection-rate.
     *     eg: Some typical detection data using a Logitech C920 WebCam
     *     Decimation = 1 ..  Detect 2" Tag from 10 feet away at 10 Frames per second
     *     Decimation = 2 ..  Detect 2" Tag from 6  feet away at 22 Frames per second
     *     Decimation = 3 ..  Detect 2" Tag from 4  feet away at 30 Frames Per Second (default)
     *     Decimation = 3 ..  Detect 5" Tag from 10 feet away at 30 Frames Per Second (default)
     *     Note: Decimation can be changed on-the-fly to adapt during a match.
     */
    private int decimation = 3;

    /**
     *
     * @param hardwareMap The hardware mapper from the OpMode to setup the Webcam
     */
    public AprilTagWebcam(HardwareMap hardwareMap) {

        // Create the AprilTag processor.
        aprilTag = new AprilTagProcessor.Builder()

                // The following default settings are available to un-comment and edit as needed.
                //.setDrawAxes(false)
                //.setDrawCubeProjection(false)
                //.setDrawTagOutline(true)
                //.setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
                //.setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                //.setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)

                // == CAMERA CALIBRATION ==
                // If you do not manually specify calibration parameters, the SDK will attempt
                // to load a predefined calibration for your camera.
                //.setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
                // ... these parameters are fx, fy, cx, cy.

                .build();


        aprilTag.setDecimation(decimation);

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera as a webcam.
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(aprilTag);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Disable or re-enable the aprilTag processor at any time.
        //visionPortal.setProcessorEnabled(aprilTag, true);

    }   // end method initAprilTag()


    /**
     * Return the closest April Tag
     */
    public AprilTagDetection Get_Closest_AprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        int closest_tag = 0;
        double tag_range = 1000;

        // Step through the list of detections and find the closest one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.ftcPose.range < tag_range) {
                    closest_tag = detection.id;
                    tag_range = detection.ftcPose.range;
                }
            }
        }   // end for() loop

        // Step through the list of detections again and return the closest.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.id == closest_tag) {
                    return detection;
                }
            }
        }   // end for() loop
        return null;
    }

    public AprilTagDetection Get_AprilTag_By_ID(int tag_number) {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        // Step through the list of detections again and return the closest.
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                if (detection.id == tag_number) {
                    return detection;
                }
            }
        }   // end for() loop
        return null;
    }
}
