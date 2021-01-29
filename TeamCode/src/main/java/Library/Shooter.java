package Library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {

    public DcMotor motorShooter;
    public DcMotor motorShooter2;
    public Servo srvFlicker;
    LinearOpMode opMode;
    ElapsedTime time;

    public Shooter(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorShooter = this.opMode.hardwareMap.dcMotor.get("motorShooter");
        motorShooter2 = this.opMode.hardwareMap.dcMotor.get("motorShooter2");
        srvFlicker = this.opMode.hardwareMap.servo.get("srvFlicker");
        time = new ElapsedTime();

        motorShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorShooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorShooter2.setDirection(DcMotorSimple.Direction.REVERSE);
        srvFlicker.setDirection(Servo.Direction.REVERSE);

    }

    public void shootOutPowerShot(){
        motorShooter.setPower(.324);
        motorShooter2.setPower(.3452);
    }

    public void shootOutHighGoal(){
        motorShooter.setPower(.355);
        motorShooter2.setPower(.366);
    }

    public void spinIn(){
        motorShooter.setPower(-.2);
        motorShooter2.setPower(-.2);
    }

    public void stopShooter(){
        motorShooter.setPower(0);
        motorShooter2.setPower(0);
    }

    public void flick(){
        srvFlicker.setPosition(.45); //placeholder positions - need to change these
        opMode.sleep(100);
        srvFlicker.setPosition(.65);
    }

    public void initPos(){
        srvFlicker.setPosition(.65); //placeholder number
    }

    public void flickPos(){
        srvFlicker.setPosition(.45); //placeholder number
    }
}
