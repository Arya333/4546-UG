package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp", group = "4546")
public class SnakeByteTeleOp extends SnakeByteOpMode{

    boolean flip = false;
    int intakeState = 0; // 0 = stop, 1 = in, 2 = out
    boolean servoMoving = false;
    ElapsedTime time = new ElapsedTime();

    public void loop(){

        // ----------------------------------------------- Drivetrain -----------------------------------------------

        // Implement macro to turn 90 degrees with one button press for turning to shoot each time <----- DO THIS

        double k = 1.0;

        if(gamepad1.right_trigger > .3){
            k = 0.4;
        }
        else{
            k = 1.0;
        }


        if (flip && (Math.abs(gamepad1.left_stick_y) > .05 || Math.abs(gamepad1.left_stick_x) > .05 || Math.abs(gamepad1.right_stick_x) > .05)){
            driveTrainPower(gamepad1.left_stick_y * k, -gamepad1.left_stick_x * k, -gamepad1.right_stick_x* .78 * k);
        }
        else{
            driveTrainPower(-gamepad1.left_stick_y * k, gamepad1.left_stick_x * k, -gamepad1.right_stick_x * .78 * k);
        }

        if (gamepad1.right_bumper){
            flip = false;
        }
        else if (gamepad1.left_bumper){
            flip = true;
        }

        if (gamepad1.dpad_up && (Math.abs(gamepad1.left_stick_y) < .05 || Math.abs(gamepad1.left_stick_x) < .05 || Math.abs(gamepad1.right_stick_x) < .05)){
            flip = false;
            if (Math.abs(0 - Math.abs(getGyroYaw())) > 10){
                startMotors(-1,1);
            }
            stopMotors();
        }

        if (gamepad1.dpad_down && (Math.abs(gamepad1.left_stick_y) < .05 || Math.abs(gamepad1.left_stick_x) < .05 || Math.abs(gamepad1.right_stick_x) < .05)){
            flip = true;
            if (Math.abs(180 - Math.abs(getGyroYaw())) > 10){
                startMotors(-1,1);
            }
            stopMotors();
        }


        // ----------------------------------------------- Intake -----------------------------------------------
        if (gamepad1.x){
            intakeState = 1;
        }
        else if (gamepad1.b){
            intakeState = 2;
        }
        else if (gamepad1.a){
            intakeState = 0;
        }

        if (intakeState == 1){
            intakeIn();
        }
        else if (intakeState == 2){
            intakeOut();
        }
        else{
            intakeStop();
        }


        // ----------------------------------------------- Shooter -----------------------------------------------

        if (gamepad2.right_trigger > .2){
            motorShooter.setPower(.355);
            motorShooter2.setPower(.366);
            //motorShooter.setPower(.347);
            //motorShooter2.setPower(.366);
        }
        else if (gamepad2.left_trigger > .2){
            motorShooter.setPower(.325);
            motorShooter2.setPower(.345);
        }
        else{
            stopShooter();
        }


        if (gamepad2.a && !servoMoving){
            flickPos();
            servoMoving = true;
            time.reset();
        }
        if (time.milliseconds() >= 400 && servoMoving){
            initPos();
            servoMoving = false;
            time.reset();
        }

        /*if (gamepad2.b){
            initPos();
        }*/

        telemetry.addData("Flip Orientation: ", flip);
        telemetry.addData("Angle: ", getGyroYaw());
        telemetry.addData("pos",motorPivot.getCurrentPosition());
        telemetry.update();

        // ----------------------------------------------- Wobble Goal -----------------------------------------------

        if (gamepad2.left_stick_y < -.1 && gamepad2.left_bumper){
            motorPivot.setPower(-.47);
        }
        else if (gamepad2.left_stick_y > .1 && gamepad2.left_bumper){
            motorPivot.setPower(.47);
        }
        else if (gamepad2.left_stick_y < -.1){
            motorPivot.setPower(-.25);
        }
        else if (gamepad2.left_stick_y > .1){
            motorPivot.setPower(.25);
        }
        else{
            motorPivot.setPower(0);
        }


        if (gamepad2.x){
            release();
        }
        if (gamepad2.y){
            grab();
        }
        
    }
}
