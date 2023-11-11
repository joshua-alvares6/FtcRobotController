package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

public class DualStageArm {
    private DcMotor lowerLeftMotor;
    private DcMotor lowerRightMotor;
    private DcMotor upperLeftMotor;
    private DcMotor upperRightMotor;

    public DualStageArm(HardwareMap hardwareMap){
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        lowerLeftMotor  = hardwareMap.get(DcMotor.class, "lowerLeft");
        lowerRightMotor = hardwareMap.get(DcMotor.class, "lowerRight");
        upperLeftMotor  = hardwareMap.get(DcMotor.class, "upperLeft");
        upperRightMotor = hardwareMap.get(DcMotor.class, "upperRight");

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        lowerLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        lowerRightMotor.setDirection(DcMotor.Direction.FORWARD);
        upperLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        upperRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void MoveArm(double yPower){
        double power = Range.clip(yPower, -1.0, 1.0);

        // Send calculated power to wheels
        lowerRightMotor.setPower(power);
        lowerLeftMotor.setPower(power);
        upperRightMotor.setPower(power);
        upperLeftMotor.setPower(power);
    }
}
