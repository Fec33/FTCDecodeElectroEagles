package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonomousRight", group="Autonomous")
public class AutonomousRight extends LinearOpMode {

    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor ShootMotor = null;

    // Declare Servos
    private CRServo servoShoot1 = null;
    private CRServo servoShoot2 = null;

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize hardware
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        ShootMotor = hardwareMap.get(DcMotor.class, "shoot_motor");
        servoShoot1 = hardwareMap.get(CRServo.class, "Servo_Shoot_1");
        servoShoot2 = hardwareMap.get(CRServo.class, "Servo_Shoot_2");

        // Reverse one motor so that forward power moves both wheels forward
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        ShootMotor.setDirection(DcMotor.Direction.REVERSE); // Set shooter motor direction

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        // Action 1: Drive forward and spin up the shooter
        ShootMotor.setPower(-0.6);
        leftDrive.setPower(0.825);
        rightDrive.setPower(0.825);
        sleep(1500); // Drive for 1.5 seconds

        // Action 2: Turn
        leftDrive.setPower(-0.02);
        rightDrive.setPower(0);
        sleep(1000); // Turn for 1 second

        // Action 3: Stop moving and shoot three times
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        // Loop to shoot 3 projectiles
        for (int i = 0; i < 3; i++) {
            // Pulse the servos to push the projectile
            servoShoot1.setPower(2.5);
            servoShoot2.setPower(2.5);
            sleep(1000); // Time to push projectile

            // Stop the servos and give the feeder time to reset
            servoShoot1.setPower(0);
            servoShoot2.setPower(0);
            sleep(2000); // Increased time for mechanism to reset for the next shot
        }

        // Action 4: Stop all motors
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        ShootMotor.setPower(0);
        servoShoot1.setPower(0);
        servoShoot2.setPower(0);
    }
}