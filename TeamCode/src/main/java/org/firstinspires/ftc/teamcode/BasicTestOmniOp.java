package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="BasicTestOmniOp", group="Linear Opmode")
public class BasicTestOmniOp extends LinearOpMode {
    // Declare motors
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor ShootMotor = null;

    // Declare Servos
    private CRServo servoShoot1 = null;
    private CRServo servoShoot2 = null;

    @Override
    public void runOpMode() {

// Initialize hardware
        // Make sure to match the names in your robot's configuration
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        ShootMotor = hardwareMap.get(DcMotor.class, "shoot_motor");
        servoShoot1 = hardwareMap.get(CRServo.class, "Servo_Shoot_1");
        servoShoot2 = hardwareMap.get(CRServo.class, "Servo_Shoot_2");


// Set motor directions for an Omni-wheel (Mecanum) drive
        // You may need to change these FORWARD/REVERSE values depending on your robot's build
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        ShootMotor.setDirection(DcMotor.Direction.REVERSE); // Set shooter motor direction

        telemetry.addData("Status", "Initialized");
        telemetry.update();

// Wait for the game to start (driver presses PLAY)
        waitForStart();

// Run until the end of the match (driver presses STOP)
        while (opModeIsActive()){
            if (gamepad2.right_bumper) {
                frontLeftDrive.setPower(0);
                frontRightDrive.setPower(0);
                backLeftDrive.setPower(0);
                backRightDrive.setPower(0);
                ShootMotor.setPower(0);
                servoShoot2.setPower(0);
                servoShoot1.setPower(0);
            }else {
                // Omni-drive is controlled by three axes: drive (forward/backward), strafe (left/right), and turn (rotation)
                double drive = -gamepad2.left_stick_y; // Controls forward and backward movement
                double strafe = gamepad2.left_stick_x;  // Controls side-to-side movement
                double turn = gamepad2.right_stick_x;   // Controls robot rotation

                // Combine the joystick inputs into Mecanum drive powers
                double frontLeftPower = (drive * 0.8) + strafe + turn;
                double frontRightPower = (drive * 0.8) - strafe - turn;
                double backLeftPower = (drive * 0.8 * 0.052) - strafe + turn;
                double backRightPower = (drive * 0.8 * 0.052) + strafe - turn;

                // Normalize the values so no wheel power exceeds 1.0
                double maxPower = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
                maxPower = Math.max(maxPower, Math.abs(backLeftPower));
                maxPower = Math.max(maxPower, Math.abs(backRightPower));

                if (maxPower > 1.0) {
                    frontLeftPower /= maxPower;
                    frontRightPower /= maxPower;
                    backLeftPower /= maxPower;
                    backRightPower /= maxPower;
                }

// Set motor power for all four wheels
                frontLeftDrive.setPower(frontLeftPower);
                frontRightDrive.setPower(frontRightPower);
                backLeftDrive.setPower(backLeftPower);
                backRightDrive.setPower(backRightPower);

// Keep shooter motor running at all times
                ShootMotor.setPower(0.65);

// Set servo power when right bumper is pressed
                if (gamepad2.right_bumper) { // Check if the trigger value is > 0.5
                    servoShoot1.setPower(-0.5);
                    servoShoot2.setPower(0.5);
                } else {
                    servoShoot1.setPower(0.0);
                    servoShoot2.setPower(0.0);
                }


// Show telemetry data
                telemetry.addData("Left Stick Y (Drive)", drive);
                telemetry.addData("Left Stick X (Strafe)", strafe);
                telemetry.addData("Right Stick X (Turn)", turn);
                telemetry.addData("FL Power", frontLeftPower);
                telemetry.addData("FR Power", frontRightPower);
                telemetry.addData("BL Power", backLeftPower);
                telemetry.addData("BR Power", backRightPower);
                telemetry.addData("Shooter Power", ShootMotor.getPower());
                telemetry.addData("Servo 1 Power", servoShoot1.getPower());
                telemetry.addData("Servo 2 Power", servoShoot2.getPower());
                telemetry.update();
            }
        }
    }
