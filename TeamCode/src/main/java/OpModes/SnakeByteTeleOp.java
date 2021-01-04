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


        if (flip && (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.left_stick_x) > .1 || Math.abs(gamepad1.right_stick_x) > .1)){
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

        if (gamepad2.x){
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

            motorShooter.setPower(.35);
            motorShooter2.setPower(.383);
        }
        else if (shootingState == 3){
            motorShooter.setPower(.33);
            motorShooter2.setPower(.365);
        }
        else if (shootingState == 2){
            spinIn();
        }
        else{
            stopShooter();
        }

        if (gamepad2.dpad_right){
            flickPos();
        }
        if (gamepad2.dpad_left){
            initPos();
        }

        telemetry.addData("sPower", sPower);
        telemetry.addData("Flip Orientation: ", flip);
        telemetry.addData("Angle: ", getGyroYaw());
        telemetry.update();

        // ----------------------------------------------- Wobble Goal -----------------------------------------------
        if (gamepad2.right_bumper){
            motorPivot.setPower(.25);
        }
        else if (gamepad2.left_bumper && gamepad2.left_trigger > .5){
            motorPivot.setPower(-.5);
        }
        else if (gamepad2.left_bumper){
            motorPivot.setPower(-.25);
        }
        else{
            motorPivot.setPower(0);
        }

        if (gamepad2.dpad_up){
            release();
        }
        if (gamepad2.dpad_down){
            grab();
        }
        
    }
}
