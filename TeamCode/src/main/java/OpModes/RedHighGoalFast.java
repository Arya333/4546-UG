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
    private Intake intake;
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
        intake = new Intake(this);
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
            sleep(300);
            drivetrain.strafeRightInches(.95, 19.25);
            sleep(200);
            drivetrain.turnPD(1, 0.86, 0.55, 1000);

            // ----------------------------------------------- Shoot High Goal -----------------------------------------------
            intake.intakeIn();
            sleep(1100);
            shooter.flickPos();
            sleep(1150);
            shooter.initPos();

            sleep(1450);
            shooter.flickPos();
            sleep(850);
            shooter.initPos();

            sleep(1400);
            shooter.flickPos();
            sleep(850);
            shooter.initPos();
            shooter.stopShooter();
            intake.intakeStop();
            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A"){

                drivetrain.turnPD(-90, .7, .4, 2000);
                sleep(250);
                drivetrain.strafeLeftInches(.95,29);
                sleep(250);
                drivetrain.moveGyro(.95, 10.9, -90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(700,.5);
                sleep(150);
                wobbleGoal.release();

            }
            else if (targetZone == "B"){
                drivetrain.turnPD(180, .7,.4, 3000);
                sleep(100);
                drivetrain.strafeLeftInches(.95, 18.85);
                sleep(250);
                drivetrain.turnPD(180, .86,.4,1500);
                sleep(100);
                drivetrain.turnPD(180, .83,.5,1000);
                sleep(100);
                drivetrain.moveGyro(.95, 30.35, 180);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(700,.5);
                sleep(150);
                wobbleGoal.release();

            }
            else {
                drivetrain.turnPD(-165, .7, .4, 3000);
                sleep(250);
                drivetrain.moveGyro(.95, 65.75, -165);

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
                    drivetrain.moveGyro(-.95, 18.6, -90);
                    sleep(250);
                    drivetrain.turnPD(0, .88, .4, 1600);
                    sleep(200);
                    drivetrain.turnPD(0, .86, .42, 400);
                    sleep(250);
                    drivetrain.moveGyro(.6, 65.3, 0);
                    sleep(2500);

                    wobbleGoal.grab();
                    sleep(650);
                    wobbleGoal.motorPivot.setPower(-.455);

                    drivetrain.moveGyro(-.95, 59, 0);
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

                }/*
                else if (targetZone == "B"){
                    sleep(200);
                    drivetrain.moveGyro(-.95, 30, 180);
                    sleep(200);
                    drivetrain.turnPD(-2, .86, .38, 2500);
                    sleep(350);
                    drivetrain.strafeRightInches(.8, 10.75);
                    sleep(200);
                    drivetrain.moveGyro(.8, 15, -2);
                    sleep(200);
                    drivetrain.strafeLeftInches(.8, 14.63);
                    sleep(250);
                    drivetrain.turnPD(-2, .88, .4, 1000);
                    sleep(200);
                    drivetrain.moveGyro(.79, 18.53, -2);
                    sleep(1000);

                    wobbleGoal.grab();
                    sleep(500);
                    wobbleGoal.motorPivot.setPower(-.465);

                    drivetrain.moveGyro(-.95, 30, 0);
                    sleep(200);
                    drivetrain.turnPD(180, .86, .4, 2500);
                    sleep(250);
                    drivetrain.turnPD(180, .87, .4, 1000);
                    sleep(200);
                    drivetrain.strafeLeftInches(.8, 17);
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

                }*/
                else if (targetZone == "B"){
                    sleep(200);
                    drivetrain.moveGyro(-.95, 5, 180);
                    sleep(200);
                    drivetrain.turnPD(-160, .86, .45, 1500);
                    sleep(200);
                    drivetrain.moveGyro(-.95, 48, -160);
                    sleep(200);

                    drivetrain.turnPD(-43, .85, .4, 1000);
                    sleep(200);
                    drivetrain.moveGyro(.85, 16.2, -43);
                    sleep(1500);

                    wobbleGoal.grab();
                    sleep(650);
                    wobbleGoal.motorPivot.setPower(-.465);
                    sleep(250);

                    drivetrain.moveGyro(-.95, 16.5, -43);
                    sleep(200);
                    drivetrain.turnPD(-170, .86, .43, 1400);
                    sleep(200);
                    drivetrain.moveGyro(.95, 51.5, -170);

                    wobbleGoal.motorPivot.setPower(0);
                    wobbleGoal.rotateTime(700,.5);
                    sleep(250);
                    wobbleGoal.release();

                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(200);
                    drivetrain.moveGyro(-.95, 10, -170);
                }
                else{
                    sleep(200);
                    drivetrain.strafeLeftInches(.95, 17);
                    sleep(175);
                    drivetrain.turnPD(-163, .86, .5, 750);
                    sleep(175);
                    drivetrain.moveGyro(-.95, 103.5, -163);
                    sleep(200);
                    drivetrain.turnPD(-52,.87,.4, 2000);
                    sleep(200);
                    drivetrain.moveGyro(.85,7.15, -52);
                    sleep(1000);

                    wobbleGoal.grab();
                    sleep(500);
                    wobbleGoal.motorPivot.setPower(-.465);
                    sleep(350);

                    drivetrain.moveGyro(-.95, 11.6, -52);
                    sleep(200);
                    drivetrain.turnPD(-157, .8, .4, 2000);
                    sleep(200);
                    drivetrain.moveGyro(.95, 105.5, -157);

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