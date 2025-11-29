package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Basic TeleOp", group="Linear Opmode")
public class BasicTeleOp extends LinearOpMode {

    // Declare motors
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor ShootMotor = null;

    // Declare Servos
    private Servo servoShoot1 = null;
    private Servo servoShoot2 = null;

    @Override
    public void runOpMode() {

// Initialize hardware
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        ShootMotor = hardwareMap.get(DcMotor.class, "shoot_motor");
        servoShoot1 = hardwareMap.get(Servo.class, "Servo_Shoot_1");
        servoShoot2 = hardwareMap.get(Servo.class, "Servo_Shoot_2");


// Reverse one motor so that forward power moves both wheels forward
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        ShootMotor.setDirection(DcMotor.Direction.FORWARD); // Set shooter motor direction

        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Wait for the game to start (driver presses PLAY)
        waitForStart();
        double leftPower;
        double rightPower;
        double drive;
        double turn;
        double strafe;


// Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

// Use left stick for forward/back, right stick for turning
            drive = -gamepad2.left_stick_y; // Forward is negative on joystick
            turn = gamepad2.right_stick_x;


// Combine drive and turn for differential steering
            leftPower = drive + turn;
            rightPower = drive - turn;

// Set motor power
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

// Set shooter motor power when right trigger is pressed
            if (gamepad2.right_bumper) { // Check if the trigger value is > 0.5
                ShootMotor.setPower(-5.0);      // Set power to a valid value (e.g., -1.0 for full reverse)
                servoShoot1.setPosition(1.0);
                servoShoot2.setPosition(0.0);
            } else {
                ShootMotor.setPower(0.0);
                servoShoot1.setPosition(0.5);
                servoShoot2.setPosition(0.5);
            }


            if (gamepad2.left_stick_x>0.5){
                leftDrive.setPower(-1.0);
                rightDrive.setPower(1.0);
            }
            if(gamepad2.left_stick_x<-0.5){
                leftDrive.setPower(1.0);
                rightDrive.setPower(-1.0);
            }


// Show telemetry data
            telemetry.addData("Left Stick Y", gamepad2.left_stick_y);
            telemetry.addData("Right Stick X", gamepad2.right_stick_x);
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Shooter Power", ShootMotor.getPower());
            telemetry.addData("Servo 1 Position", servoShoot1.getPosition());
            telemetry.addData("Servo 2 Position", servoShoot2.getPosition());
            telemetry.update();
        }
    }
}