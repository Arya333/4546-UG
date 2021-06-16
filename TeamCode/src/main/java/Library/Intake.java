package Library;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class Intake {

    public DcMotor motorIntake;

    LinearOpMode opMode;
    ElapsedTime time;

    public Intake(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorIntake = this.opMode.hardwareMap.dcMotor.get("motorIntake");
        time = new ElapsedTime();
    }

    public void intakeIn(){
        motorIntake.setPower(1);
    }

    public void intakeOut(){
        motorIntake.setPower(-1);
    }

    public void intakeOutSlow(){
        motorIntake.setPower(-.38);
    }

    public void intakeStop(){
        motorIntake.setPower(0);
    }

}
