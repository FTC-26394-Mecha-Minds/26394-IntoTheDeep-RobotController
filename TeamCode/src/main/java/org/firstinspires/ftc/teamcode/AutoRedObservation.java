package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.util.Angle;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous (name = "AutoRedObservation", preselectTeleOp = "MechaRedTeleOp")
public class AutoRedObservation extends LinearOpMode {
    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor leftOuttake;
    private DcMotor rightOuttake;
    private Servo lights;
    private Servo lights1;
    private CRServo leftIntake;
    private CRServo rightIntake;
    private Servo leftPitch;
    private Servo rightPitch;
    private Servo leftExtension;
    private Servo rightExtension;
    private Servo claw;
    private Servo leftElbow;
    private Servo rightElbow;
    private Servo clawRotation;
    private ColorSensor transfer;
    private float CurrentColor;
    private int leftPos;
    private int rightPos;
    @Override
    public void runOpMode() throws InterruptedException {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        leftOuttake = hardwareMap.dcMotor.get("leftOuttake");
        rightOuttake = hardwareMap.dcMotor.get("rightOuttake");
        lights = hardwareMap.get(Servo.class, "lights");
        lights1 = hardwareMap.get(Servo.class, "lights1");
        claw = hardwareMap.get(Servo.class, "claw");
        clawRotation = hardwareMap.get(Servo.class, "clawRotation");
        transfer = hardwareMap.colorSensor.get("transfer");
        leftIntake = hardwareMap.crservo.get("leftIntake");
        rightIntake = hardwareMap.crservo.get("rightIntake");
        leftPitch = hardwareMap.servo.get("leftPitch");
        rightPitch = hardwareMap.servo.get("rightPitch");
        leftElbow = hardwareMap.get(Servo.class, "leftElbow");
        rightElbow = hardwareMap.get(Servo.class, "rightElbow");
        leftExtension = hardwareMap.servo.get("leftExtension");
        rightExtension = hardwareMap.servo.get("rightExtension");

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        rightOuttake.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        double maxSpeed = 0.4;
        leftPos = 0;
        rightPos = 0;


//        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//        Trajectory myTrajectory = drive.trajectoryBuilder(new Pose2d())
//                .splineTo(new Vector2d(45, 45), 0)
//                .build();

        waitForStart();

        if (isStopRequested()) return;
        drive(-300, -300, 0.5);
        Thread.sleep(600);

//        drive.followTrajectory(myTrajectory);


    }
    public void drive(int leftTarget, int rightTarget, double speed){
        leftPos += leftTarget;
        rightPos += rightTarget;


        frontLeft.setTargetPosition(leftPos);
        backLeft.setTargetPosition(leftPos);
        frontRight.setTargetPosition(rightPos);
        backRight.setTargetPosition(rightPos);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        backRight.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);


    }

}

