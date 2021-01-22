package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import Library.Drivetrain;
import Library.Intake;
import Library.Sensors;
import Library.Shooter;
import Library.VuforiaBitmap;
import Library.WobbleGoal;

@Autonomous(name = "RedPowerShot", group = "4546")
public class RedPowerShot extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Intake intake;
    private Shooter shooter;
    private WobbleGoal wobbleGoal;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // ----------------------------------------------- Init -----------------------------------------------
        VuforiaBitmap sample = new VuforiaBitmap(this);
        String targetZone = "B";
        boolean secondWobbleGoal = false; // Do we go for another wobble goal in auto?
        boolean selectionMade = false;
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        intake = new Intake(this);
        shooter = new Shooter(this);
        wobbleGoal = new WobbleGoal(this);

        wobbleGoal.grab();
        wobbleGoal.motorPivot.setPower(-.1);

        /*
        while (!isStarted()){
            telemetry.addData("2nd Wobble Goal? ", secondWobbleGoal);
            telemetry.update();
            if (gamepad1.y) {
                secondWobbleGoal = true;
                selectionMade = true;
                telemetry.addData("2nd Wobble Goal? ", secondWobbleGoal);
                telemetry.update();
            } else if (gamepad1.x) {
                secondWobbleGoal = false;
                selectionMade = true;
                telemetry.addData("2nd Wobble Goal? ", secondWobbleGoal);
                telemetry.update();
            }

            if (selectionMade){
                targetZone = sample.getStack();
                telemetry.addData("2nd Wobble Goal? ", secondWobbleGoal);
                telemetry.addData("Target Zone: ", sample.getStack());
                telemetry.update();
                sleep(500);
            }
        }
        */
        waitForStart();

        // ----------------------------------------------- Auto -----------------------------------------------
        //drivetrain.turnPI(90,.35,.07,10000);  ---- Not using PI, too much overshoot

        //drivetrain.turnPD(90,.6,.5,5000); ---- Constants for 90 degree turn
        //drivetrain.turnPD(45, .7, .6, 5000); ---- Constants for 45 degree turn

        if (!isStopRequested()){
            // ----------------------------------------------- Drive to Launch Line -----------------------------------------------
            drivetrain.moveGyro(-.8,20,0);
            sleep(750);
            drivetrain.strafeRightInches(.8,13.5);
            sleep(250);
            drivetrain.turnPD(0,.8,.7,2000);
            drivetrain.moveGyro(-.8,30, 0);
            sleep(300);
            drivetrain.turnPD(4.3,.75,.65,2000);
            sleep(250);


            // ----------------------------------------------- Shoot Power Goals -----------------------------------------------
            shooter.shootOutPowerShot();
            sleep(1700);
            shooter.flickPos();
            sleep(1100);
            shooter.initPos();

            drivetrain.turnPD(0,.8,.7,2000);
            sleep(500);
            drivetrain.strafeRightInches(.4, 4.35);
            sleep(100);
            drivetrain.turnPD(0,.8,.7,2000);
            shooter.flickPos();
            sleep(1100);
            shooter.initPos();

            drivetrain.turnPD(-6.2,.75,.65,2000);
            sleep(500);
            shooter.flickPos();
            sleep(1100);
            shooter.initPos();
            sleep(500);
            drivetrain.turnPD(0,.8,.7,2000);
            shooter.stopShooter();

            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A"){
                drivetrain.moveGyro(-.8, 12.4, 0);
                sleep(300);
                drivetrain.turnPD(-90,.7,.4,3000);
                sleep(300);
                drivetrain.moveGyro(.8,17,-90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(1000,.45);
                sleep(250);
                wobbleGoal.release();
            }
            else if (targetZone == "B"){
                drivetrain.moveGyro(-.8, 39.2, 0);
                sleep(300);
                drivetrain.turnPD(-90,.7,.4,3000);
                sleep(200);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(1000,.45);
                sleep(250);
                wobbleGoal.release();
            }
            else{
                drivetrain.moveGyro(-.6, 81.3, 0);
                sleep(300);
                drivetrain.turnPD(-90,.7,.6,5000);
                sleep(300);
                drivetrain.moveGyro(.6,24,-90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(1000,.45);
                sleep(250);
                wobbleGoal.release();
            }

            // ----------------------------------------------- Park -----------------------------------------------
            if (!secondWobbleGoal){
                if (targetZone == "A"){
                    sleep(100);
                    drivetrain.moveGyro(-.8,19.5,-90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.25);
                    sleep(250);
                    drivetrain.turnPD(180,.7,.4,4000);
                    sleep(300);
                    drivetrain.moveGyro(-.8, 9,180);
                    sleep(300);
                    drivetrain.turnPD(180,.8,.7,3000);
                }
                else if (targetZone == "B"){
                    sleep(500);
                    drivetrain.moveGyro(-.8,1,-90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.turnPD(180,.7,.4,4000);
                    sleep(300);
                    drivetrain.moveGyro(-.8, 33,180);
                    sleep(300);
                    drivetrain.turnPD(180,.8,.7,3000);
                }
                else{
                    sleep(500);
                    drivetrain.moveGyro(-.5,23.5,-90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.turnPD(180,.6,.5,4000);
                    sleep(300);
                    drivetrain.moveGyro(-.6, 72,180);
                    sleep(300);
                    drivetrain.turnPD(180,.8,.7,3000);
                }
            }

            // ----------------------------------------------- Drop Wobble Goal 2 -----------------------------------------------
            else{
                if (targetZone == "A"){
                    drivetrain.moveGyro(-.6, 6.3, -90);
                    drivetrain.turnPD(-45, .8, .7, 2500);
                    sleep(300);
                    drivetrain.moveGyro(.6, 42, -45);
                    sleep(300);
                    drivetrain.turnPD(0, .75,.65, 2500);
                    drivetrain.strafeLeftInches(.6, 9);
                    sleep(250);
                    drivetrain.moveGyro(.7, 34, 0);
                    drivetrain.strafeRightInches(.6, 2);
                    sleep(250);
                    drivetrain.moveGyro(.7, 2.5, 0);

                    wobbleGoal.grab();
                    sleep(300);
                    wobbleGoal.motorPivot.setPower(-.375);
                    sleep(2000);


                }
                else if (targetZone == "B"){

                }
                else{

                }
            }


        }


    }
}
