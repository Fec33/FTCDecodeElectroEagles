package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="DecodeStarterTeleOp", group="TeleOp") // Annotation to make the OpMode appear
public class DecodeStarterTeleOp extends LinearOpMode {

    // Declare OpMode members for the Starter Bot hardware.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFrontMotor = null;
    private DcMotor rightFrontMotor = null;
    private DcMotor leftBackMotor = null;
    private DcMotor rightBackMotor = null;
    private DcMotor armMotor = null;
    private Servo clawServo = null;

    // Define servo positions (adjust these values based on your specific servo and build)
    final double CLAW_OPEN = 0.5;
    final double CLAW_CLOSED = 0.0;
    // Define arm motor speed
    final double ARM_SPEED = 0.5;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables (Names must match the config file)
        leftFrontMotor  = hardwareMap.get(DcMotor.class, "left_front_motor");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "right_front_motor");
        leftBackMotor  = hardwareMap.get(DcMotor.class, "left_back_motor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "right_back_motor");
        armMotor = hardwareMap.get(DcMotor.class, "arm_motor");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");

        // Most robots need the motors on one side to be reversed
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);

        // Arm motor typically runs in one direction for lifting, adjust if needed
        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // Arm holds position when not powered

        // Set initial servo position
        clawServo.setPosition(CLAW_CLOSED);

        // Wait for the game to start (Driver Station presses INIT and then START)
        waitForStart();
        runtime.reset();

        // Run until the end of the match (Driver Station presses STOP)
        while (opModeIsActive()) {

            // --- Drivetrain Control (Split Arcade Drive) ---
            // Left stick Y controls forward/backward movement.
            // Right stick X controls turning.
            double drive = -gamepad1.left_stick_y;
            double turn  = gamepad1.right_stick_x;

            double leftPower    = drive + turn;
            double rightPower   = drive - turn;

            leftFrontMotor.setPower(leftPower);
            leftBackMotor.setPower(leftPower);
            rightFrontMotor.setPower(rightPower);
            rightBackMotor.setPower(rightPower);

            // --- Arm Control (Manual Movement) ---
            // Use D-pad up/down to move the arm up/down
            if (gamepad1.dpad_up) {
                armMotor.setPower(ARM_SPEED);
            } else if (gamepad1.dpad_down) {
                armMotor.setPower(-ARM_SPEED);
            } else {
                armMotor.setPower(0.0); // Stop the arm when no button is pressed
            }

            // --- Claw/Gripper Control ---
            // Use A and B buttons to open and close the claw
            if (gamepad1.a) {
                clawServo.setPosition(CLAW_OPEN);
            } else if (gamepad1.b) {
                clawServo.setPosition(CLAW_CLOSED);
            }

            // --- Telemetry ---
            telemetry.addData("Status", "Running: " + runtime.seconds());
            telemetry.addData("Motors", "Left (%.2f), Right (%.2f)", leftPower, rightPower);
            telemetry.addData("Arm Power", "%.2f", armMotor.getPower());
            telemetry.addData("Claw Position", "%.2f", clawServo.getPosition());
            telemetry.update();
        }
    }
}
