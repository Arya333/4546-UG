package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp", group = "4546")
public class SnakeByteTeleOp extends SnakeByteOpMode{

    boolean flip = false;
    int intakeState = 0; // 0 = stop, 1 = in, 2 = out
    int shootingState = 0; // 0 = stop, 1 = high goal, 2 = inwards, 3 = power shot
    double sPower = 1.0;
    ElapsedTime time = new ElapsedTime();
    double timediff = 0;
    double timeold = 0;

    public void loop(){

        // ----------------------------------------------- Drivetrain -----------------------------------------------

        // Implement macro to turn 90 degrees with one button press for turning to shoot each time <----- DO THIS

        double k = 1.0;

        if(gamepad1.right_trigger > .3){
            k = 0.45;
        }
        else{
            k = 1.0;
        }


        if (flip && (Math.abs(gamepad1.left_stick_y) > .05 || Math.abs(gamepad1.left_stick_x) > .05 || Math.abs(gamepad1.right_stick_x) > .05)){
            driveTrainPower(gamepad1.left_stick_y * k, -gamepad1.left_stick_x * k, -gamepad1.right_stick_x* .725 * k);
        }
        else{
            driveTrainPower(-gamepad1.left_stick_y * k, gamepad1.left_stick_x * k, -gamepad1.right_stick_x * .725 * k);
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
            motorShooter.setPower(.345);
            motorShooter2.setPower(.378);
        }
        else if (gamepad2.left_trigger > .2){
            motorShooter.setPower(.32);
            motorShooter2.setPower(.36);
        }
        else{
            stopShooter();
        }

        /*if (gamepad2.x){
            shootingState = 1;
        }
        else if (gamepad2.b){
            shootingState = 2;
        }
        else if (gamepad2.y){
            shootingState = 3;
        }
        else if (gamepad2.a){
            shootingState = 0;
        }

        if (shootingState == 1){
            //shootOut();
            //motorShooter.setPower(.35); ---- High Goal
            //motorShooter2.setPower(.383); ---- High Goal

            //motorShooter.setPower(.33); ---- Power Shot
            //motorShooter2.setPower(.37); ---- Power Shot

            motorShooter.setPower(.345);
            motorShooter2.setPower(.378);
        }
        else if (shootingState == 3){
            motorShooter.setPower(.32);
            motorShooter2.setPower(.36);
        }
        else if (shootingState == 2){
            spinIn();
        }
        else{
            stopShooter();
        }*/

        if (gamepad2.a){
            flickPos();
        }
        if (gamepad2.b){
            initPos();
        }

        telemetry.addData("Flip Orientation: ", flip);
        telemetry.addData("Angle: ", getGyroYaw());
        telemetry.addData("pos",motorPivot.getCurrentPosition());
        telemetry.update();

        // ----------------------------------------------- Wobble Goal -----------------------------------------------
        if (gamepad2.left_stick_y < -.1){
            motorPivot.setPower(.33);
        }
        else if (gamepad2.left_stick_y > .1){
            motorPivot.setPower(-.33);
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
