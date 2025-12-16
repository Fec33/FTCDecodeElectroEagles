package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonomousB", group="Autonomous")
public class AutonomousB extends LinearOpMode {

    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor ShootMotor = null;
    private CRServo servoShoot1 = null;
    private CRServo servoShoot2 = null;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize hardware
        frontLeftDrive = hardwareMap.get(DcMotor.class, "front_left_drive");
        backLeftDrive = hardwareMap.get(DcMotor.class, "back_left_drive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "front_right_drive");
        backRightDrive = hardwareMap.get(DcMotor.class, "back_right_drive");
        ShootMotor = hardwareMap.get(DcMotor.class, "shoot_motor");
        servoShoot1 = hardwareMap.get(CRServo.class, "Servo_Shoot_1");
        servoShoot2 = hardwareMap.get(CRServo.class, "Servo_Shoot_2");

        // Set motor directions
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        ShootMotor.setDirection(DcMotor.Direction.FORWARD);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        // Run autonomous actions until 27 seconds have passed
        while (opModeIsActive() && runtime.seconds() < 27) {
            ShootMotor.setPower(-0.65);
            frontLeftDrive.setPower(0.8);
            backLeftDrive.setPower(0.8);
            frontRightDrive.setPower(0.8);
            backRightDrive.setPower(0.8);
            sleep(1500);

            frontLeftDrive.setPower(0.8);
            backLeftDrive.setPower(0.8);
            frontRightDrive.setPower(-0.8);
            backRightDrive.setPower(-0.8);
            sleep(100);

            frontLeftDrive.setPower(0.8);
            backLeftDrive.setPower(0.8);
            frontRightDrive.setPower(0.8);
            backRightDrive.setPower(0.8);
            sleep(500);

            frontLeftDrive.setPower(0.0);
            backLeftDrive.setPower(0.0);
            frontRightDrive.setPower(0.0);
            backRightDrive.setPower(0.0);
            sleep(10);

            servoShoot1.setPower(-0.5);
            servoShoot2.setPower(0.5);
            sleep(300);
            servoShoot1.setPower(0.0);
            servoShoot2.setPower(0.0);
            sleep(300);

            servoShoot1.setPower(-0.5);
            servoShoot2.setPower(0.5);
            sleep(300);
            servoShoot1.setPower(0.0);
            servoShoot2.setPower(0.0);
            sleep(300);

            servoShoot1.setPower(-0.5);
            servoShoot2.setPower(0.5);
            sleep(300);
            servoShoot1.setPower(0.0);
            servoShoot2.setPower(0.0);
            sleep(300);

            frontLeftDrive.setPower(-0.8);
            backLeftDrive.setPower(-0.8);
            frontRightDrive.setPower(-0.8);
            backRightDrive.setPower(-0.8);
            sleep(300);

            frontLeftDrive.setPower(-0.8);
            backLeftDrive.setPower(-0.8);
            frontRightDrive.setPower(0.8);
            backRightDrive.setPower(0.8);
            sleep(100);

            frontLeftDrive.setPower(-0.8);
            backLeftDrive.setPower(-0.8);
            frontRightDrive.setPower(-0.8);
            backRightDrive.setPower(-0.8);
            sleep(1500);

            frontLeftDrive.setPower(0.0);
            backLeftDrive.setPower(0.0);
            frontRightDrive.setPower(0.0);
            backRightDrive.setPower(0.0);
            sleep(12000);
        }
        stop();
    }
}
