package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOp", group = "4546")
public class SnakeByteTeleOp extends SnakeByteOpMode{

    boolean flip = false;
    int shootingState = 0; // 0 = stop, 1 = shoot, 2 = inwards

    public void loop(){

        // ----------------------------------------------- Drivetrain -----------------------------------------------
        double k = 1.0;
        if(gamepad1.left_trigger > .6){
            k = 0.5;
        }
        else{
            k = 1.0;
        }
        if (flip){
            driveTrainPower(gamepad1.left_stick_y * k, -gamepad1.left_stick_x * k, -gamepad1.right_stick_x * .6 * k);
        }
        else{
            driveTrainPower(-gamepad1.left_stick_y * k, gamepad1.left_stick_x * k, -gamepad1.right_stick_x * .6 * k);
        }

        if (gamepad1.right_bumper){
            flip = true;
        }
        else if (gamepad1.left_bumper){
            flip = false;
        }


        // ----------------------------------------------- Intake -----------------------------------------------
        if (gamepad1.x){
            intakeIn();
        }
        else if (gamepad1.b){
            intakeOut();
        }
        else{
            intakeStop();
        }
        if (gamepad2.x){
            flick();
        }


        // ----------------------------------------------- Shooter -----------------------------------------------
        if (gamepad2.b){
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
        }

        // ----------------------------------------------- Wobble Goal -----------------------------------------------

        
    }
}
