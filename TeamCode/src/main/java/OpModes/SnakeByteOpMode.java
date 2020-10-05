package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class SnakeByteOpMode extends OpMode{

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;

    Servo srvFlicker;
    DcMotor motorIntake;

    DcMotor motorShooter;

    public void init(){

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        srvFlicker = hardwareMap.servo.get("srvFlicker");
        motorIntake = hardwareMap.dcMotor.get("motorIntake");

        motorShooter = hardwareMap.dcMotor.get("motorShooter");

        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
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

        telemetry.addData("init ", "completed");
        telemetry.update();
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
        motorFL.setPower(WeightAvg(forward,-strafe,-rotate));
        motorFR.setPower(WeightAvg(forward,strafe,rotate));
        motorBL.setPower(WeightAvg(forward,strafe,-rotate));
        motorBR.setPower(WeightAvg(forward,-strafe,rotate));
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
        srvFlicker.setPosition(.4); //placeholder positions - need to change these
        srvFlicker.setPosition(.1);
    }

    public void shootOut(){
        motorShooter.setPower(1);
    }

    public void spinIn(){
        motorShooter.setPower(-.4);
    }

    public void stopShooter(){
        motorShooter.setPower(0);
    }


}
