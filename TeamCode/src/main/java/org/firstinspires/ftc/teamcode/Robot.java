package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    private TankDriveBase driveBase;
    private DualStageArm arm;

    public Robot(HardwareMap hardwareMap) {
        driveBase = new TankDriveBase(hardwareMap);
        arm = new DualStageArm(hardwareMap);
    }

    public void Drive(double xPower, double yPower){
        driveBase.Drive(xPower, yPower);
    }

    public void Move_Arm(double power){
        arm.MoveArm(power);
    }
}
