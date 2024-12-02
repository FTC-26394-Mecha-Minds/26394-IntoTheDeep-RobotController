package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "MechaBlueTeleOp")
public class MechaBlueTeleOp extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor backLeft;
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor leftOuttake;
    private DcMotor rightOuttake;
    private Servo lights;
    private Servo lights1;
    private CRServo intake;
    private Servo leftPitch;
    private Servo rightPitch;
    private Servo leftExtension;
    private Servo rightExtension;
    private Servo claw;
    private Servo leftElbow;
    private Servo rightElbow;
    //    private ColorSensor transfer;
    @Override
    public void runOpMode() throws InterruptedException {
        float CurrentColor;
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        leftOuttake = hardwareMap.dcMotor.get("leftOuttake");
        rightOuttake = hardwareMap.dcMotor.get("rightOuttake");
        lights = hardwareMap.get(Servo.class, "lights");
        lights1 = hardwareMap.get(Servo.class, "lights1");
        claw = hardwareMap.get(Servo.class, "claw");
//      transfer = hardwareMap.colorSensor.get("transfer");
        intake = hardwareMap.crservo.get("intake");
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
        double maxSpeed = 0.1;


        double f, r, s;
        double fLeftPower, bLeftPower, fRightPower, bRightPower;


        waitForStart();
        claw.setPosition(-0.7);
        rightExtension.setDirection(Servo.Direction.REVERSE);
        rightPitch.setDirection(Servo.Direction.REVERSE);
        rightElbow.setDirection(Servo.Direction.REVERSE);
        lights.setPosition(0.5);
        lights1.setPosition(0.5);
        leftExtension.setPosition(0.4);
        rightExtension.setPosition(-0.2);
        leftPitch.setPosition(0);
        rightPitch.setPosition(0);
        leftElbow.setPosition(0.9);
        rightElbow.setPosition(0.9);

        if (opModeIsActive()) {
            while (opModeIsActive()) {
//                    CurrentColor = JavaUtil.rgbToHue(transfer.red(), transfer.green(), transfer.blue());
//                    telemetry.addData("Hue", CurrentColor);
//                    telemetry.update();
                if (gamepad1.right_bumper) {
                    rightOuttake.setPower(0.8);
                    leftOuttake.setPower(0.8);
                } else if (gamepad1.left_bumper){
                    rightOuttake.setPower(-0.8);
                    leftOuttake.setPower(-0.8);
                } else {
                    rightOuttake.setPower(0);
                    leftOuttake.setPower(0);
                }
                if (gamepad1.x) {
                    claw.setPosition(0.065);
                }
                if (gamepad1.b) {
                    claw.setPosition(-0.7);
                }

                if (gamepad1.a) {
                    leftExtension.setPosition(0.83);
                    rightExtension.setPosition(0.4);
                }if (gamepad1.y) {
                    leftExtension.setPosition(0.4);
                    rightExtension.setPosition(-0.1);
                }
                if (gamepad1.dpad_up) {
                    leftElbow.setPosition(-0.9);
                    rightElbow.setPosition(-0.9);
                }
                if (gamepad1.dpad_down) {
                    leftElbow.setPosition(0.9);
                    rightElbow.setPosition(0.9);
                }
                if (gamepad1.dpad_right) {
                    intake.setPower(1);
                }
                if (gamepad1.dpad_left) {
                    intake.setPower(-1);
                }


//                    if (CurrentColor < 240 && CurrentColor > 165) {
//                        lights.setPosition(0.66);
//                        lights1.setPosition(0.66);
//                    } else if (CurrentColor > 0 && CurrentColor < 30 || CurrentColor > 300 && CurrentColor < 360) {
//                        lights.setPosition(0.2779);
//                        lights1.setPosition(0.2779);
//                    } else if (CurrentColor < 90 && CurrentColor > 30){
//                        lights.setPosition(0.388);
//                        lights1.setPosition(0.388);
//                    } else {
//                        lights.setPosition(0.5);
//                        lights1.setPosition(0.5);
//                    }

                f = gamepad1.left_stick_y;
                r = -gamepad1.right_stick_x;
                s = -gamepad1.left_stick_x;
                fLeftPower = f + r + s;
                bLeftPower = f + r - s;
                fRightPower = f - r - s;
                bRightPower = f - r + s;
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
    public void intakeProcedure(double speed) throws InterruptedException {
        leftExtension.setPosition(0.83);
        rightExtension.setPosition(0.4);
        Thread.sleep(5);
        leftPitch.setPosition(0.4);
        rightPitch.setPosition(0.4);
        Thread.sleep(2);


    }
}
