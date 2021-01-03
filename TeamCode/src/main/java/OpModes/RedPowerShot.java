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
        String targetZone = "A";
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        intake = new Intake(this);
        shooter = new Shooter(this);
        wobbleGoal = new WobbleGoal(this);

        wobbleGoal.grab();
        wobbleGoal.motorPivot.setPower(-.1);

        /*while (!isStarted()){
            telemetry.addData("Target Zone: ", sample.getStack());
            telemetry.update();
            targetZone = sample.getStack();
            sleep(100);
        }*/

        waitForStart();

        // ----------------------------------------------- Auto -----------------------------------------------
        //drivetrain.turnPI(90,.35,.07,10000);  ---- Not using PI, too much overshoot

        //drivetrain.turnPD(90,.6,.5,5000); ---- Constants for 90 degree turn
        //drivetrain.turnPD(45, .7, .6, 5000); ---- Constants for 45 degree turn

        if (!isStopRequested()){
            // ----------------------------------------------- Drive to Launch Line -----------------------------------------------
            drivetrain.moveGyro(-.6,30,0);
            sleep(750);
            drivetrain.strafeRightInches(.6,15.5);
            sleep(250);
            drivetrain.turnPD(0,.8,.7,2000);
            drivetrain.moveGyro(-.6,44, 0);
            sleep(300);
            drivetrain.turnPD(4.1,.75,.65,2000);
            sleep(250);


            // ----------------------------------------------- Shoot Power Goals -----------------------------------------------
            shooter.shootOutPowerShot();
            sleep(1600);
            shooter.flickPos();
            sleep(1200);
            shooter.initPos();

            drivetrain.turnPD(0,.8,.7,2000);
            sleep(500);
            drivetrain.strafeRightInches(.4, 4.35);
            sleep(100);
            drivetrain.turnPD(0,.8,.7,2000);
            shooter.flickPos();
            sleep(1200);
            shooter.initPos();

            drivetrain.turnPD(-6.1,.75,.65,2000);
            sleep(500);
            shooter.flickPos();
            sleep(1200);
            shooter.initPos();
            sleep(500);
            drivetrain.turnPD(0,.8,.7,2000);
            shooter.stopShooter();

            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A"){
                drivetrain.moveGyro(-.6, 18, 0);
                sleep(500);
                drivetrain.turnPD(-90,.6,.5,5000);
                sleep(300);
                drivetrain.moveGyro(.6,26.5,-90);
            }
            else if (targetZone == "B"){

            }
            else{

            }



        }


    }
}
