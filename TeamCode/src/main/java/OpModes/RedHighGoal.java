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

@Autonomous(name = "RedHighGoal", group = "4546")
public class RedHighGoal extends LinearOpMode {

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

            drivetrain.moveGyro(-.8, 20, 0);
            sleep(750);
            drivetrain.strafeLeftInches(.6, 8);
            sleep(250);
            drivetrain.moveGyro(-.8, 26, 0);
            sleep(750);
            drivetrain.turnPD(0, 0.8, 0.7, 2000);
            sleep(750);
            drivetrain.strafeRightInches(.6, 28);
            sleep(1000);
            drivetrain.turnPD(0, 0.8, 0.7, 2000);

            // ----------------------------------------------- Shoot High Goal -----------------------------------------------

            shooter.shootOutHighGoal();
            sleep(1600);
            shooter.flickPos();
            sleep(1200);
            shooter.initPos();

            sleep(1000);
            shooter.shootOutHighGoal();
            sleep(1600);
            shooter.flickPos();
            sleep(1200);
            shooter.initPos();

            sleep(1000);
            shooter.shootOutHighGoal();
            sleep(1600);
            shooter.flickPos();
            sleep(1200);
            shooter.initPos();
            shooter.stopShooter();

            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A"){

                drivetrain.turnPD(-90, .7, .5, 2000);
                sleep(300);
                drivetrain.strafeLeftInches(.7,11);
                sleep(250);
                drivetrain.moveGyro(.8, 4.75, -90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(1500,.25);
                sleep(250);
                wobbleGoal.release();

            }
            else if (targetZone == "B"){
                drivetrain.turnPD(180, .78,.4, 4000);
                sleep(350);
                drivetrain.moveGyro(.8, 13.5, 180);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(1500,.25);
                sleep(250);
                wobbleGoal.release();

            }
            else {
                drivetrain.turnPD(-175, .8, .45, 3000);
                sleep(350);
                drivetrain.moveGyro(.8, 45, -175);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(1500,.25);
                sleep(250);
                wobbleGoal.release();
            }

            // ----------------------------------------------- Park -----------------------------------------------
            if (!secondWobbleGoal){
                if (targetZone == "A"){

                }
                else if (targetZone == "B"){

                }
                else{

                }
            }

            // ----------------------------------------------- Drop Wobble Goal 2 -----------------------------------------------
            else{
                if (targetZone == "A"){



                }
                else if (targetZone == "B"){

                }
                else{

                }
            }


        }


    }
}

