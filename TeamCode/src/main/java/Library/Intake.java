package Library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Intake {

    public DcMotor motorIntake;
    public Servo srvFlicker;

    LinearOpMode opMode;
    ElapsedTime time;

    public Intake(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorIntake = this.opMode.hardwareMap.dcMotor.get("motorIntake");
        srvFlicker = this.opMode.hardwareMap.servo.get("srvFlicker");
        time = new ElapsedTime();
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

    public void initPos(){
        srvFlicker.setPosition(.1); //placeholder number
    }

    public void flickPos(){
        srvFlicker.setPosition(.4); //placeholder number
    }
}
