package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "MechaRedTeleOp")
public class MechaRedTeleOp extends LinearOpMode {

    private DcMotor frontLeft, backLeft, frontRight, backRight;
    private DcMotor leftOuttake, rightOuttake;
    private Servo lights, lights1, leftExtension, rightExtension, claw, leftElbow, rightElbow, clawRotation, rightPitch, leftPitch;
    private CRServo leftIntake, rightIntake;
    private ColorSensor transfer;
    private float CurrentColor;
    PIDFLoopOuttake outtake = new PIDFLoopOuttake();
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

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        rightOuttake.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        double maxSpeed = 0.4;


        double f, r, s;
        double fLeftPower, bLeftPower, fRightPower, bRightPower;


        waitForStart();
        claw.setPosition(0.1);
        clawRotation.setPosition(0.25);
        leftExtension.setDirection(Servo.Direction.REVERSE);
        rightPitch.setDirection(Servo.Direction.REVERSE);
        leftElbow.setDirection(Servo.Direction.REVERSE);
        lights.setPosition(0.5);
        lights1.setPosition(0.5);
        leftExtension.setPosition(0.75);
        rightExtension.setPosition(0);
        leftPitch.setPosition(1);
        rightPitch.setPosition(1);
        leftElbow.setPosition(0.1);
        rightElbow.setPosition(0.1);


        if (opModeIsActive()) {
            while (opModeIsActive()) {
                   CurrentColor = JavaUtil.rgbToHue(transfer.red(), transfer.green(), transfer.blue());
                    telemetry.addData("Hue", CurrentColor);
                    telemetry.update();
                    if (gamepad2.right_bumper) {
                        rightOuttake.setPower(0.8);
                        leftOuttake.setPower(0.8);
                    } else if (gamepad2.left_bumper){
                        rightOuttake.setPower(-0.8);
                        leftOuttake.setPower(-0.8);
                    } else {
                        rightOuttake.setPower(0);
                        leftOuttake.setPower(0);
                    }
                    if (gamepad2.x) {
                        claw.setPosition(0);
                        clawRotation.setPosition(0.6);
                    }
                    if (gamepad2.b) {
                        claw.setPosition(0.1);
                        clawRotation.setPosition(0.25);
                    }

                    if (gamepad1.a) {
                        leftExtension.setPosition(0.45);
                        rightExtension.setPosition(0.33);
                    }if (gamepad1.y) {
                        leftExtension.setPosition(0);
                        rightExtension.setPosition(0);
                    }
                    if (gamepad2.dpad_up) {
                        leftElbow.setPosition(0.8);
                        rightElbow.setPosition(0.8);
                    }
                    if (gamepad2.dpad_down) {
                        leftElbow.setPosition(0.1);
                        rightElbow.setPosition(0.1);
                    }
                    if (gamepad1.dpad_up) {
                        leftPitch.setPosition(0.62);
                        rightPitch.setPosition(0.62);
                    }
                    if (gamepad1.dpad_down) {
                        leftPitch.setPosition(0.49);
                        rightPitch.setPosition(0.49);
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

                f = gamepad1.left_stick_y;
                r = -gamepad1.right_stick_x;
                s = -gamepad1.left_stick_x;
                fLeftPower = (f + r + s);
                bLeftPower = (f + r - s);
                fRightPower = (f - r - s);
                bRightPower = (f - r + s);
                double maxN = Math.max(Math.abs(fLeftPower), Math.max(Math.abs(bLeftPower),
                        Math.max(Math.abs(fRightPower), Math.abs(bRightPower))));

                if (maxN > 1) {
                    fLeftPower /= maxN;
                    bLeftPower /= maxN;
                    fRightPower /= maxN;
                    bRightPower /= maxN;
                }
                frontLeft.setPower(fLeftPower * maxSpeed);
                backLeft.setPower(bLeftPower * maxSpeed);
                frontRight.setPower(fRightPower * maxSpeed);
                backRight.setPower(bRightPower * maxSpeed);
            }
        }
    }
//    public void intakeProcedure(double speed) throws InterruptedException {
//        leftExtension.setPosition(0.83);
//        rightExtension.setPosition(0.4);
//        Thread.sleep(5);
//        leftPitch.setPosition(0.4);
//        rightPitch.setPosition(0.4);
//        Thread.sleep(2);
//        intake.setPower(1);
//        if (CurrentColor < 240 && CurrentColor > 165) {
//            lights.setPosition(0.66);
//            lights1.setPosition(0.66);
//            intake.setPower(-1);
//            Thread.sleep(10);
//            intake.setPower(1);
//        } else if (CurrentColor > 0 && CurrentColor < 30 || CurrentColor > 300 && CurrentColor < 360) {
//            lights.setPosition(0.2779);
//            lights1.setPosition(0.2779);
//            intake.setPower(0);
//            Thread.sleep(10);
//            leftPitch.setPosition(0);
//            rightPitch.setPosition(0);
//            Thread.sleep(5);
//            leftExtension.setPosition(0.4);
//            rightExtension.setPosition(-0.2);
//        } else if (CurrentColor < 90 && CurrentColor > 30) {
//            lights.setPosition(0.388);
//            lights1.setPosition(0.388);
//            intake.setPower(0);
//            Thread.sleep(10);
//        } else {
//            lights.setPosition(0.5);
//            lights1.setPosition(0.5);
//            intake.setPower(1);
//
//       }
//
//    }
}

