package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

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

    // Timer for the shooter
    private ElapsedTime shooterTimer = new ElapsedTime();
    private boolean shooterActive = false;

    private static final double SHOOT_TIME_SECONDS = 0.1; // Run shooter for 1 second

    @Override
    public void runOpMode() {

        // Initialize hardware
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        ShootMotor = hardwareMap.get(DcMotor.class, "shoot_motor");
        servoShoot1 = hardwareMap.get(CRServo.class, "Servo_Shoot_1");
        servoShoot2 = hardwareMap.get(CRServo.class, "Servo_Shoot_2");

        // Set motor directions for an Omni-wheel (Mecanum) drive
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        ShootMotor.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad2.left_bumper) { // Emergency stop
                frontLeftDrive.setPower(0);
                frontRightDrive.setPower(0);
                backLeftDrive.setPower(0);
                backRightDrive.setPower(0);
                ShootMotor.setPower(0);
                servoShoot1.setPower(0);
                servoShoot2.setPower(0);
            } else {
                // Omni-drive controls
                double drive = -gamepad2.left_stick_y;
                double strafe = gamepad2.left_stick_x;
                double turn = gamepad2.right_stick_x;

                double frontLeftPower = (drive * 0.8) + strafe + turn;
                double frontRightPower = (drive * 0.8) - strafe - turn;
                double backLeftPower = (drive * 0.8) - strafe + turn;
                double backRightPower = (drive * 0.8) + strafe - turn;

                double maxPower = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
                maxPower = Math.max(maxPower, Math.abs(backLeftPower));
                maxPower = Math.max(maxPower, Math.abs(backRightPower));

                if (maxPower > 1.0) {
                    frontLeftPower /= maxPower;
                    frontRightPower /= maxPower;
                    backLeftPower /= maxPower;
                    backRightPower /= maxPower;
                }

                frontLeftDrive.setPower(frontLeftPower);
                frontRightDrive.setPower(frontRightPower);
                backLeftDrive.setPower(backLeftPower);
                backRightDrive.setPower(backRightPower);

                ShootMotor.setPower(0.65 * 0.052); // Keep shooter motor running

                // --- Timed Servo Shooter Logic ---
                if (gamepad1.right_bumper && !shooterActive) {
                    shooterActive = true;
                    shooterTimer.reset();
                }

                if (shooterActive) {
                    if (shooterTimer.seconds() < SHOOT_TIME_SECONDS) {
                        servoShoot1.setPower(-0.5);
                        servoShoot2.setPower(0.5);
                    } else {
                        servoShoot1.setPower(0.0);
                        servoShoot2.setPower(0.0);
                        shooterActive = false;
                    }
                }
                // --- End of Timed Servo Logic ---

                // Telemetry data
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
                telemetry.addData("Shooter Timer", shooterTimer.seconds());
                telemetry.update();
            }
        }
    }
}
