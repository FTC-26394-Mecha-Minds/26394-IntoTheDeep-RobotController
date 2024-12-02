package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@Config
@TeleOp
public class PIDFLoopOuttake extends OpMode {
    private PIDController PID;

    public static double p = 0, i = 0, d = 0;
    public static double f = 0;

    public static int target = 0;

    private final double ticks_in_degree = 700/180.0;
    private DcMotorEx rightOuttake, leftOuttake;

    @Override
    public void init(){
        PID = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        rightOuttake = hardwareMap.get(DcMotorEx.class, "rightOuttake");

        leftOuttake = hardwareMap.get(DcMotorEx.class, "leftOuttake");

    }

    @Override
    public void loop() {
        PID.setPID(p, i, d);
        int leftPos = leftOuttake.getCurrentPosition();
//        int rightPos = rightOuttake.getCurrentPosition();
        double leftPID = PID.calculate(leftPos, target);
//        double rightPID = PID.calculate(rightPos, target);
        double feedforward = Math.cos(Math.toRadians(target/ticks_in_degree)) *f;

        double leftPower = leftPID + feedforward;
//        double rightPower = rightPID + feedforward;
        leftOuttake.setPower(leftPower);
//        rightOuttake.setPower(rightPower);

        telemetry.addData("Left Position", leftPos);
//        telemetry.addData("Right Position", rightPos);
        telemetry.update();
    }
}
