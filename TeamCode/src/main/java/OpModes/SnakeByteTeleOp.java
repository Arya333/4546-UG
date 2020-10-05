package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public class SnakeByteTeleOp extends SnakeByteOpMode{

    boolean flip = false;

    public void loop(){

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


        if (gamepad1.x){
            intakeIn();
        }
        if (gamepad1.b){
            intakeOut();
        }
        if (gamepad2.x){
            flick();
        }

    }
}
