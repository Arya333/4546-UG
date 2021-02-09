package Library;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WobbleGoal {

    public DcMotor motorPivot;
    public Servo srvClaw;

    LinearOpMode opMode;
    ElapsedTime time;

    public WobbleGoal(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorPivot = this.opMode.hardwareMap.dcMotor.get("motorPivot");
        srvClaw = this.opMode.hardwareMap.servo.get("srvClaw");
        time = new ElapsedTime();

        motorPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorPivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void resetPivotEncoders(){
        motorPivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorPivot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorPivot.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void stopPivotMotor(){
        motorPivot.setPower(0);
    }

    public void rotateToEncoderPos(int target, double power){
        resetPivotEncoders();
        while (motorPivot.getCurrentPosition() <= target && opMode.opModeIsActive()){
            motorPivot.setPower(power);
            opMode.telemetry.addData("position:", motorPivot.getCurrentPosition());
            opMode.telemetry.update();
        }
        stopPivotMotor();
    }

    public void rotateTime(int timeMilli, double power){
        resetPivotEncoders();
        time.reset();
        while (time.milliseconds() <= timeMilli && opMode.opModeIsActive()){
            motorPivot.setPower(power);
        }
        stopPivotMotor();
    }

    public void grab(){
        srvClaw.setPosition(.59); //placeholder number
    }

    public void release(){
        srvClaw.setPosition(.13); //placeholder number
    }

}
