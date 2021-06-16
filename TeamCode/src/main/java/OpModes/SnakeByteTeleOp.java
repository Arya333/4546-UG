package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleOp", group = "4546")
public class SnakeByteTeleOp extends SnakeByteOpMode{

    boolean flip = false;
    int intakeState = 0; // 0 = stop, 1 = in, 2 = out, 3 = out slow
    boolean servoMoving = false;
    ElapsedTime time = new ElapsedTime();

    public void loop(){

        // ----------------------------------------------- Drivetrain -----------------------------------------------

        double k = 1.0;

        if(gamepad1.right_trigger > .3){
            k = 0.45;
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
            intakeState = 0;
        }
        else if (gamepad1.left_bumper){
            flip = true;
            if (intakeState == 1){
                intakeState = 3;
            }
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
        else if (intakeState == 3){
            intakeOutSlow();
        }
        else{
            intakeStop();
        }


        // ----------------------------------------------- Shooter -----------------------------------------------

        if (gamepad2.right_trigger > .2){ // high goal
            motorShooter.setPower(.354);
            //motorShooter2.setPower(.368);
            motorShooter2.setPower(.366);
        }
        else if (gamepad2.left_trigger > .2){ // power shot
            motorShooter.setPower(.327);
            motorShooter2.setPower(.345);
        }
        else if (gamepad2.right_bumper){ // mid goal
            motorShooter.setPower(.32);
            motorShooter2.setPower(.33);
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
            motorPivot.setPower(-.57);
        }
        else if (gamepad2.left_stick_y > .1 && gamepad2.left_bumper){
            motorPivot.setPower(.51);
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
