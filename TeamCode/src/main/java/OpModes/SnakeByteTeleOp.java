package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp", group = "4546")
public class SnakeByteTeleOp extends SnakeByteOpMode{

    boolean flip = false;
    int intakeState = 0; // 0 = stop, 1 = in, 2 = out
    int shootingState = 0; // 0 = stop, 1 = shoot, 2 = inwards

    public void loop(){

        // ----------------------------------------------- Drivetrain -----------------------------------------------

        // Implement macro to turn 90 degrees with one button press for turning to shoot each time <----- DO THIS

        double k = 1.0;
        if(gamepad1.left_trigger > .6){
            k = 0.5;
        }
        else{
            k = 1.0;
        }


        if (flip && (Math.abs(gamepad1.left_stick_y) > .1 || Math.abs(gamepad1.left_stick_x) > .1 || Math.abs(gamepad1.right_stick_x) > .1)){
            driveTrainPower(gamepad1.left_stick_y * k, -gamepad1.left_stick_x * k, -gamepad1.right_stick_x* .8 * k);
        }
        else{
            driveTrainPower(-gamepad1.left_stick_y * k, gamepad1.left_stick_x * k, -gamepad1.right_stick_x * .8 * k);
        }

        if (gamepad1.right_bumper){
            flip = true;
        }
        else if (gamepad1.left_bumper){
            flip = false;
        }

        telemetry.addData("Angle: ", getGyroYaw());
        telemetry.update();
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
        /*if (gamepad2.x){
            flick();
        }*/


        // ----------------------------------------------- Shooter -----------------------------------------------
        /*if (gamepad2.b){
            shootingState = 0;
        }
        else if (gamepad2.y){
            shootingState = 1;
        }
        else{
            shootingState = 2;
        }

        if (shootingState == 1){
            shootOut();
        }
        else if (shootingState == 2){
            spinIn();
        }
        else{
            stopShooter();
        }*/

        // ----------------------------------------------- Wobble Goal -----------------------------------------------

        
    }
}
