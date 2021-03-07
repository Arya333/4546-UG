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

@Autonomous(name = "RedHighGoalFast", group = "4546")
public class RedHighGoalFast extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    //private Intake intake;
    private Shooter shooter;
    private WobbleGoal wobbleGoal;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // ----------------------------------------------- Init -----------------------------------------------
        VuforiaBitmap sample = new VuforiaBitmap(this);
        String targetZone = "C";
        boolean secondWobbleGoal = true; // Do we go for another wobble goal in auto?
        boolean selectionMade = false;
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        //intake = new Intake(this);
        shooter = new Shooter(this);
        wobbleGoal = new WobbleGoal(this);

        wobbleGoal.grab();
        wobbleGoal.motorPivot.setPower(-.1);


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

        waitForStart();

        // ----------------------------------------------- Auto -----------------------------------------------
        //drivetrain.turnPI(90,.35,.07,10000);  ---- Not using PI, too much overshoot

        //drivetrain.turnPD(90,.6,.5,5000); ---- Constants for 90 degree turn
        //drivetrain.turnPD(45, .7, .6, 5000); ---- Constants for 45 degree turn

        if (!isStopRequested() && opModeIsActive()){
            // ----------------------------------------------- Drive to Launch Line -----------------------------------------------

            drivetrain.moveGyro(-.95, 68.7, 0);
            sleep(350);
            drivetrain.turnPD(0, 0.8, 0.7, 1000);
            shooter.shootOutHighGoal();
            sleep(400);
            drivetrain.strafeRightInches(.95, 22.5);
            sleep(200);
            drivetrain.turnPD(0, 0.86, 0.55, 1000);

            // ----------------------------------------------- Shoot High Goal -----------------------------------------------

            sleep(1000);
            shooter.flickPos();
            sleep(1900);
            shooter.initPos();

            sleep(900);
            shooter.flickPos();
            sleep(1900);
            shooter.initPos();

            sleep(900);
            shooter.flickPos();
            sleep(1200);
            shooter.initPos();
            shooter.stopShooter();

            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A"){

                drivetrain.turnPD(-90, .7, .4, 2000);
                sleep(250);
                drivetrain.strafeLeftInches(.95,31.4);
                sleep(250);
                drivetrain.moveGyro(.95, 11.85, -90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(700,.5);
                sleep(150);
                wobbleGoal.release();

            }
            else if (targetZone == "B"){
                drivetrain.turnPD(180, .7,.4, 3000);
                sleep(100);
                drivetrain.strafeLeftInches(.95, 12.5);
                sleep(250);
                drivetrain.turnPD(180, .9,.4,1500);
                sleep(100);
                drivetrain.turnPD(180, .8,.5,1000);
                sleep(100);
                drivetrain.moveGyro(.95, 30.35, 180);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(700,.5);
                sleep(150);
                wobbleGoal.release();

            }
            else {
                drivetrain.turnPD(-163, .7, .4, 3000);
                sleep(250);
                drivetrain.moveGyro(.95, 63.75, -163);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(700,.5);
                sleep(150);
                wobbleGoal.release();
                sleep(250);
            }

            // ----------------------------------------------- Park -----------------------------------------------
            if (!secondWobbleGoal){
                if (targetZone == "A"){
                    drivetrain.moveGyro(-.95,8.75,-90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(100);
                    drivetrain.strafeRightInches(.95, 4);
                    sleep(200);
                    drivetrain.turnPD(180, .8, .5,2000);
                    sleep(100);
                    drivetrain.moveGyro(-.95, 7.35, 180);

                }
                else if (targetZone == "B"){
                    drivetrain.moveGyro(-.95, 17.85, 180);
                    sleep(250);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.turnPD(180,.7,.5,1000);

                }
                else{
                    drivetrain.moveGyro(-.95, 3,-163);
                    sleep(250);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.turnPD(180,.7,.5,1000);
                    sleep(250);
                    drivetrain.moveGyro(-.95, 43,180);
                }
            }

            // ----------------------------------------------- Drop Wobble Goal 2 -----------------------------------------------
            else{
                if (targetZone == "A"){
                    drivetrain.moveGyro(-.95, 21.9, -90);
                    sleep(250);
                    drivetrain.turnPD(0, .85, .4, 1800);
                    sleep(200);
                    drivetrain.turnPD(0, .86, .5, 1000);
                    sleep(250);
                    drivetrain.moveGyro(.78, 65, 0);
                    sleep(1000);

                    wobbleGoal.grab();
                    sleep(500);
                    wobbleGoal.motorPivot.setPower(-.455);

                    drivetrain.moveGyro(-.95, 57.5, 0);
                    sleep(250);
                    drivetrain.turnPD(-90, .8, .4, 1800);
                    sleep(250);
                    drivetrain.moveGyro(.95, 6, -90);

                    wobbleGoal.motorPivot.setPower(0);
                    wobbleGoal.rotateTime(700,.5);
                    sleep(250);
                    wobbleGoal.release();

                    sleep(200);
                    drivetrain.moveGyro(-.9, 5, -90);

                }
                else if (targetZone == "B"){
                    sleep(200);
                    drivetrain.moveGyro(-.95, 30, 180);
                    sleep(200);
                    drivetrain.turnPD(0, .85, .5, 2500);
                    sleep(350);
                    drivetrain.strafeRightInches(.8, 14.35);
                    sleep(200);
                    drivetrain.moveGyro(.8, 15, 0);
                    sleep(200);
                    drivetrain.strafeLeftInches(.8, 14.3);
                    sleep(250);
                    drivetrain.turnPD(0, .86, .55, 1000);
                    sleep(200);
                    drivetrain.moveGyro(.79, 19.4, 0);
                    sleep(850);

                    wobbleGoal.grab();
                    sleep(500);
                    wobbleGoal.motorPivot.setPower(-.465);

                    drivetrain.moveGyro(-.95, 30, 0);
                    sleep(200);
                    drivetrain.turnPD(180, .85, .5, 2500);
                    sleep(250);
                    drivetrain.turnPD(180, .86, .55, 1000);
                    sleep(200);
                    drivetrain.strafeLeftInches(.8, 6.9);
                    sleep(200);
                    drivetrain.moveGyro(.95, 24.2, 180);

                    wobbleGoal.motorPivot.setPower(0);
                    wobbleGoal.rotateTime(700,.5);
                    sleep(250);
                    wobbleGoal.release();

                    drivetrain.moveGyro(-.9, 1, 180);
                    sleep(250);
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(200);
                    drivetrain.moveGyro(-.95, 8, 180);

                }
                else{
                    sleep(200);
                    drivetrain.strafeLeftInches(.95, 17);
                    sleep(175);
                    drivetrain.turnPD(-163, .86, .5, 750);
                    sleep(175);
                    drivetrain.moveGyro(-.95, 102.8, -163);
                    sleep(200);
                    drivetrain.turnPD(-42,.84,.45, 2000);
                    sleep(100);
                    drivetrain.turnPD(-42,.85,.45, 2000);
                    sleep(200);
                    drivetrain.moveGyro(.95,10.15, -42);
                    sleep(800);

                    wobbleGoal.grab();
                    sleep(500);
                    wobbleGoal.motorPivot.setPower(-.465);
                    sleep(350);

                    drivetrain.moveGyro(-.95, 8.6, -42);
                    sleep(200);
                    drivetrain.turnPD(-157, .8, .4, 2000);
                    sleep(200);
                    drivetrain.moveGyro(.95, 103, -157);

                    wobbleGoal.motorPivot.setPower(0);
                    wobbleGoal.rotateTime(700,.5);
                    sleep(250);
                    wobbleGoal.release();


                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(200);
                    drivetrain.moveGyro(-.95, 46, -157);

                }
            }


        }


    }
}