package Library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Shooter {

    public DcMotor motorShooter;
    LinearOpMode opMode;
    ElapsedTime time;

    public Shooter(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorShooter = this.opMode.hardwareMap.dcMotor.get("motorShooter");
        time = new ElapsedTime();

        motorShooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
