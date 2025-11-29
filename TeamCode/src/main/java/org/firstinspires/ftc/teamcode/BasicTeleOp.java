package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp(name="Basic TeleOp", group="Linear Opmode")
public class BasicTeleOp extends LinearOpMode {
    // Declare motors
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor ShootMotor = null;

    // Declare Servos

    @Override
    public void runOpMode() {

// Initialize hardware
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        ShootMotor = hardwareMap.get(DcMotor.class, "shoot_motor");


// Reverse one motor so that forward power moves both wheels forward
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Wait for the game to start (driver presses PLAY)
        waitForStart();
        double leftPower;
        double rightPower;
        double drive;
        double turn;


// Run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

// Use left stick for forward/back, right stick for turning
            drive = -gamepad2.left_stick_y; // Forward is negative on joystick
            turn = gamepad2.right_stick_x;


// Combine drive and turn for differential steering

// Set motor power
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            if (gamepad2.right_bumper) { // Check if the trigger value is > 0.5
            } else {
            }




// Show telemetry data
            telemetry.addData("Left Stick Y", gamepad2.left_stick_y);
            telemetry.addData("Right Stick X", gamepad2.right_stick_x);
            telemetry.addData("Left Power", leftPower);
            telemetry.addData("Right Power", rightPower);
            telemetry.addData("Shooter Power", ShootMotor.getPower());
            telemetry.update();
        }
    }
}