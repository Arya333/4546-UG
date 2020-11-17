package OpModes;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import Library.Sensors;

public abstract class SnakeByteOpMode extends OpMode{

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;

    Servo srvFlicker;
    DcMotor motorIntake;

    DcMotor motorShooter;
    DcMotor motorShooter2;

    Servo srvClaw;
    DcMotor motorPivot;

    public BNO055IMU gyro;
    Orientation angles;
    Acceleration gravity;
    BNO055IMU.Parameters parameters;

    public void init(){

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        gyro = this.hardwareMap.get(BNO055IMU.class, "imu");
        gyro.initialize(parameters);

        srvFlicker = hardwareMap.servo.get("srvFlicker");
        motorIntake = hardwareMap.dcMotor.get("motorIntake");

        motorShooter = hardwareMap.dcMotor.get("motorShooter");
        motorShooter2 = hardwareMap.dcMotor.get("motorShooter2");

        srvClaw = hardwareMap.servo.get("srvClaw");
        motorPivot = hardwareMap.dcMotor.get("motorPivot");

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorIntake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorIntake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorShooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorShooter2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorShooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        srvFlicker.setDirection(Servo.Direction.REVERSE);

        motorPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorPivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("init ", "completed");
        telemetry.update();
    }

    public double getGyroYaw() {
        updateValues();
        double yaw = angles.firstAngle * -1;
        if(angles.firstAngle < -180)
            yaw -= 360;
        return yaw;
    }

    public void updateValues() {
        angles = gyro.getAngularOrientation();
    }

    public void startMotors(double left, double right){
        motorFL.setPower(left);
        motorFR.setPower(right);
        motorBL.setPower(left);
        motorBR.setPower(right);
    }

    public void stopMotors(){
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public double WeightAvg(double x, double y, double z) {
        double speed_D = 0;


        if ((Math.abs(x) + Math.abs(y) + Math.abs(z))  != 0.0) {
            speed_D = ((x * Math.abs(x)) + (y * Math.abs(y)) + (z * Math.abs(z)))
                    / (Math.abs(x) + Math.abs(y) + Math.abs(z));
        }
        return (speed_D);
    }

    public void driveTrainPower(double forward, double strafe, double rotate){
        motorFL.setPower(WeightAvg(forward,strafe,-rotate));
        motorFR.setPower(WeightAvg(forward,-strafe,rotate));
        motorBL.setPower(WeightAvg(forward,-strafe,-rotate));
        motorBR.setPower(WeightAvg(forward,strafe,rotate));
    }

    public void intakeIn(){
        motorIntake.setPower(1);
    }

    public void intakeOut(){
        motorIntake.setPower(-1);
    }

    public void intakeStop(){
        motorIntake.setPower(0);
    }

    public void flick(){
        srvFlicker.setPosition(.55);
        srvFlicker.setPosition(.71);
    }

    public void flickPos(){
        srvFlicker.setPosition(.55);
    }

    public void initPos(){
        srvFlicker.setPosition(.71);
    }

    public void shootOut(){
        motorShooter.setPower(1);
        motorShooter2.setPower(1);
    }

    public void spinIn(){
        motorShooter.setPower(-.4);
        motorShooter2.setPower(-.4);
    }

    public void stopShooter(){
        motorShooter.setPower(0);
        motorShooter2.setPower(0);
    }

    public void resetPivotEncoders(){
        motorPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void grab(){
        srvClaw.setPosition(.55); //placeholder number
    }

    public void release(){
        srvClaw.setPosition(.3); //placeholder number
    }

    // Need to add code for the wobble goal pivot motor later

}
