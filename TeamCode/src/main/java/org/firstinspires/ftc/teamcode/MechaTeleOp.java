package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "MechaTeleOp")
public class MechaTeleOp extends LinearOpMode {
    private DcMotor frontLeft, backLeft, frontRight, backRight;
    private DcMotor leftOuttake, rightOuttake;
    private Servo lights, lights1, leftExtension, rightExtension, claw, leftElbow, rightElbow, clawRotation, rightPitch, leftPitch;
    private CRServo leftIntake, rightIntake;
    private ColorSensor transfer;
    private float CurrentColor;
    private static double kP = 0.009, kI = 0, kD = 0, kF = 0.08;
    private final double ticks_in_degree = 700/180.0;
    PIDController outtakeLift = new PIDController(kP, kI, kD);

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

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

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightOuttake.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        double maxSpeed = 0.8;


        double f, r, s;
        double fLeftPower, bLeftPower, fRightPower, bRightPower;


        waitForStart();
        // Direction
        leftExtension.setDirection(Servo.Direction.REVERSE);
        rightPitch.setDirection(Servo.Direction.REVERSE);
        leftElbow.setDirection(Servo.Direction.REVERSE);

        // PIDF Target Positions
        int basketTargetPosition = 2500;
        int chamberTargetPosition = 1000;
        int restingPosition = 0;

        // Rising Edge Detector
        Gamepad lastGamepad2 = new Gamepad();

        //PIDF State // 1: Rest, 2: Basket, 3: Chamber, 0: OFF
        int usePIDF = 0;

        // Automatic States
        claw.setPosition(0.8);
        clawRotation.setPosition(0);
        lights.setPosition(0.5);
        lights1.setPosition(0.5);
        leftExtension.setPosition(0.39);
        rightExtension.setPosition(0.59);
        leftPitch.setPosition(0.65);
        rightPitch.setPosition(0.65);
        leftElbow.setPosition(0.13);
        rightElbow.setPosition(0.13);


        if (opModeIsActive()) {
            while (opModeIsActive()) {
                if (gamepad2.dpad_up && !lastGamepad2.dpad_up) {
                    usePIDF = 2;
                } 
                if (gamepad2.dpad_right && !lastGamepad2.dpad_right){
                    usePIDF = 3;
                } 
                if (gamepad2.dpad_left && !lastGamepad2.dpad_left){
                    usePIDF = 1;
                }
                if (gamepad2.right_trigger > 0 || gamepad2.left_trigger > 0) {
                    usePIDF = 0;
                }
                if (usePIDF > 0) {
                    if (usePIDF == 2) {
                        // Basket
                        double BasketPIDCalc = outtakeLift.calculate(leftOuttake.getCurrentPosition(), basketTargetPosition);
                        double BasketFeedforward = Math.cos(Math.toRadians(basketTargetPosition/ticks_in_degree)) * kF;
                        double BasketPower = BasketPIDCalc + BasketFeedforward;
                        leftOuttake.setPower(BasketPower);
                        rightOuttake.setPower(leftOuttake.getPower());
                        Thread.sleep(250);
                        leftElbow.setPosition(0.8);
                        rightElbow.setPosition(0.8);
                        clawRotation.setPosition(0.3);


                    } else if (usePIDF == 3) {
                        // Chamber
                        double ChamberPIDCalc = outtakeLift.calculate(leftOuttake.getCurrentPosition(), chamberTargetPosition);
                        double ChamberFeedforward = Math.cos(Math.toRadians(chamberTargetPosition/ticks_in_degree)) * kF;
                        double ChamberPower = ChamberPIDCalc + ChamberFeedforward;
                        leftOuttake.setPower(ChamberPower);
                        rightOuttake.setPower(leftOuttake.getPower());
                        Thread.sleep(250);
                        leftElbow.setPosition(0.8);
                        rightElbow.setPosition(0.8);
                        clawRotation.setPosition(0.3);


                    } else {
                        //  Rest
                        double RestPIDCalc = outtakeLift.calculate(leftOuttake.getCurrentPosition(), restingPosition);
                        double RestFeedforward = Math.cos(Math.toRadians(restingPosition/ticks_in_degree)) * kF;
                        double RestPower = RestPIDCalc + RestFeedforward;
                        leftElbow.setPosition(0.13);
                        rightElbow.setPosition(0.13);
                        clawRotation.setPosition(0);
                        leftOuttake.setPower(RestPower);
                        rightOuttake.setPower(leftOuttake.getPower());
                    }
                } else {
                    if (gamepad2.right_trigger > 0) {
                        leftOuttake.setPower(Math.max(gamepad2.right_trigger, 0.8));
                        rightOuttake.setPower(Math.max(gamepad2.right_trigger, 0.8));
                    } else if (gamepad2.left_trigger > 0) {
                        leftOuttake.setPower(-(Math.max(gamepad2.right_trigger, 0.8)));
                        rightOuttake.setPower(-(Math.max(gamepad2.right_trigger, 0.8)));
                    } else {
                        leftOuttake.setPower(0);
                        rightOuttake.setPower(0);
                    }

                }

                   CurrentColor = JavaUtil.rgbToHue(transfer.red(), transfer.green(), transfer.blue());
                    telemetry.addData("Hue", CurrentColor);
                    telemetry.update();

                    if (gamepad2.x) {
                        claw.setPosition(0.9);
                    } else if (gamepad2.b) {
                        claw.setPosition(0.8);
                    }
                    if (gamepad2.a) {
                        clawRotation.setPosition(0.3);
                    } else if (gamepad2.y) {
                        clawRotation.setPosition(0);
                    }
                    if (gamepad2.left_bumper) {
                        leftElbow.setPosition(0.13);
                        rightElbow.setPosition(0.13);
                    } else if (gamepad2.right_bumper) {
                        leftElbow.setPosition(0.8);
                        rightElbow.setPosition(0.8);
                    }

                if (gamepad1.a) {
                    leftExtension.setPosition(0.74);
                    rightExtension.setPosition(0.93);
                } else if (gamepad1.y) {
                    leftExtension.setPosition(0.4);
                    rightExtension.setPosition(0.6);
                }
//                if (gamepad2.dpad_up) {
//                        leftElbow.setPosition(0.8);
//                        rightElbow.setPosition(0.8);
//                } else if (gamepad2.dpad_down) {
//                    leftElbow.setPosition(0.13);
//                    rightElbow.setPosition(0.13);
//                }
                if (gamepad1.dpad_up) {
                    leftPitch.setPosition(0.64);
                    rightPitch.setPosition(0.64);
                } else if (gamepad1.dpad_down) {
                    leftPitch.setPosition(0.52);
                    rightPitch.setPosition(0.52);
                }
                    if (gamepad1.x) {
                        leftIntake.setPower(1);
                        rightIntake.setPower(-1);
                    } else if (gamepad1.b) {
                        rightIntake.setPower(1);
                        leftIntake.setPower(-1);
                    } else {
                        leftIntake.setPower(0);
                        rightIntake.setPower(0);
                    }


                    if (CurrentColor < 240 && CurrentColor > 165) {
                        lights.setPosition(0.61);
                        lights1.setPosition(0.61);
                    } else if ((CurrentColor > 0 && CurrentColor < 30) || (CurrentColor > 300 && CurrentColor < 390)) {
                        lights.setPosition(0.279);
                        lights1.setPosition(0.279);
                    } else if (CurrentColor < 90 && CurrentColor > 30){
                        lights.setPosition(0.35);
                        lights1.setPosition(0.35);
                    } else {
                        lights.setPosition(0.5);
                        lights1.setPosition(0.5);
                    }
            }
        }
    }
}

