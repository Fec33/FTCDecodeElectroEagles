package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="AutonomousB", group="Autonomous")
public class AutonomousB extends LinearOpMode {

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
        ShootMotor.setDirection(DcMotor.Direction.FORWARD); // Set shooter motor direction

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        runtime.reset();

        // Run autonomous actions until 27 seconds have passed
        while (opModeIsActive() && runtime.seconds() < 27) {
            ShootMotor.setPower(-0.65);
            leftDrive.setPower(-0.8);
            rightDrive.setPower(-0.8);
            sleep(1500);
            ShootMotor.setPower(-0.65);
            servoShoot1.setPower(-0.5);
            servoShoot2.setPower(0.5);
            sleep(1000);
            ShootMotor.setPower(-0.65);
            servoShoot1.setPower(0.0);
            servoShoot2.setPower(0.0);
            sleep(300);
            ShootMotor.setPower(-0.65);
            servoShoot1.setPower(-0.5);
            servoShoot2.setPower(0.5);
            sleep(1000);
            ShootMotor.setPower(-0.65);
            servoShoot1.setPower(0.0);
            servoShoot2.setPower(0.0);
            sleep(300);
            ShootMotor.setPower(-0.65);
            servoShoot1.setPower(-0.5);
            servoShoot2.setPower(0.5);
            sleep(1000);
            ShootMotor.setPower(-0.65);
            servoShoot1.setPower(0.0);
            servoShoot2.setPower(0.0);
            sleep(300);
        }
        stop();
    }
}
