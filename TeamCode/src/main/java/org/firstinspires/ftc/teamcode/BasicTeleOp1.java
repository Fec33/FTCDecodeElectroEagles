package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Basic TeleOp1", group="Linear Opmode")
public class BasicTeleOp1 extends LinearOpMode {
    // Declare motors
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor ShootMotor = null;

    // Declare Servos
    private CRServo servoShoot1 = null;
    private CRServo servoShoot2 = null;

    @Override
    public void runOpMode() {

// Initialize hardware
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        ShootMotor = hardwareMap.get(DcMotor.class, "shoot_motor");
        servoShoot1 = hardwareMap.get(CRServo.class, "Servo_Shoot_1");
        servoShoot2 = hardwareMap.get(CRServo.class, "Servo_Shoot_2");


// Reverse one motor so that forward power moves both wheels forward
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        ShootMotor.setDirection(DcMotor.Direction.FORWARD); // Set shooter motor direction

        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Wait for the game to start (driver presses PLAY)
        waitForStart();
        double leftPower = 0.0;
        double rightPower = 0.0;
        double drive = 0.0;
        double turn = 0.0;


// Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            if (gamepad2.right_bumper) {
                leftDrive.setPower(0);
                rightDrive.setPower(0);
                ShootMotor.setPower(0);
                servoShoot2.setPower(0);
                servoShoot1.setPower(0);
                leftPower = 0.0; // Set for telemetry
                rightPower = 0.0; // Set for telemetry
            }else {
// Use left stick for forward/back, right stick for turning
                drive = -gamepad1.left_stick_y; // Forward is negative on joystick
                turn = gamepad1.right_stick_x;


// Combine drive and turn for differential steering
                leftPower = (drive * 0.8) - turn;
                rightPower = (drive * 0.8) + turn;

// Set motor power
                leftDrive.setPower(leftPower);
                rightDrive.setPower(rightPower);
                ShootMotor.setPower(0.65);
// Set shooter servo power when right bumper is pressed
                if (gamepad1.right_bumper) {
                    servoShoot1.setPower(-0.5);
                    servoShoot2.setPower(0.5);
                } else {
                    servoShoot1.setPower(0.0);
                    servoShoot2.setPower(0.0);
                }
            }


// Show telemetry data
            telemetry.addData("Left Stick Y", gamepad2.left_stick_y);
            telemetry.addData("Right Stick X", gamepad2.right_stick_x);
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Shooter Power", ShootMotor.getPower());
            telemetry.addData("Servo 1 Power", servoShoot1.getPower());
            telemetry.addData("Servo 2 Power", servoShoot2.getPower());
            telemetry.update();
        }
    }
}
