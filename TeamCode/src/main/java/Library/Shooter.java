package Library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {

    public DcMotor motorShooter;
    public DcMotor motorShooter2;
    LinearOpMode opMode;
    ElapsedTime time;

    public Shooter(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorShooter = this.opMode.hardwareMap.dcMotor.get("motorShooter");
        motorShooter = this.opMode.hardwareMap.dcMotor.get("motorShooter2");
        time = new ElapsedTime();

        motorShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorShooter2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
}
