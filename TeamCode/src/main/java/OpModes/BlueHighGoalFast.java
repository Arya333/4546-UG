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

@Autonomous(name = "BlueHighGoalFast", group = "4546")
public class BlueHighGoalFast extends LinearOpMode{

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
        boolean secondWobbleGoal = false; // Do we go for another wobble goal in auto?
        boolean selectionMade = false;
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        intake = new Intake(this);
        shooter = new Shooter(this);
        wobbleGoal = new WobbleGoal(this);

        wobbleGoal.grab();
        wobbleGoal.motorPivot.setPower(-.1);


        while (!isStarted()) {
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

            if (selectionMade) {
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

        if (!isStopRequested() && opModeIsActive()) {
            // ----------------------------------------------- Drive to Launch Line -----------------------------------------------

            drivetrain.moveGyro(-.95, 66.9, 0);
            sleep(350);
            drivetrain.turnPD(0, 0.8, 0.7, 1000);
            //shooter.shootOutHighGoal();
            shooter.motorShooter.setPower(.363);
            shooter.motorShooter2.setPower(.367);
            sleep(500);
            drivetrain.strafeLeftInches(.85, 21.5);
            sleep(350);
            drivetrain.turnPD(1.9, 0.86, 0.55, 1000);

            // ----------------------------------------------- Shoot High Goal -----------------------------------------------
            intake.intakeOutSlow();
            sleep(1550);
            shooter.flickPos();
            sleep(1250);
            shooter.initPos();

            shooter.motorShooter.setPower(.364);
            shooter.motorShooter2.setPower(.369);
            sleep(1650);
            shooter.flickPos();
            sleep(850);
            shooter.initPos();

            shooter.motorShooter.setPower(.363);
            shooter.motorShooter2.setPower(.367);
            sleep(1550);
            shooter.flickPos();
            sleep(850);
            shooter.initPos();
            shooter.stopShooter();
            intake.intakeStop();
            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A") {

                drivetrain.turnPD(90, .7, .4, 2000);
                sleep(250);
                drivetrain.strafeRightInches(.9, 16);
                sleep(150);
                drivetrain.turnPD(90, .85, .4, 500);
                sleep(250);
                drivetrain.moveGyro(.95, 20, 90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(600, .4);
                sleep(150);
                wobbleGoal.release();
                sleep(250);

            } else if (targetZone == "B") {
                drivetrain.turnPD(180, .7, .4, 3000);
                sleep(250);
                drivetrain.strafeLeftInches(.8, 14.75);
                sleep(250);
                drivetrain.turnPD(179, .9, .4, 850);
                sleep(100);
                drivetrain.moveGyro(.95, 27, 179);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(700, .4);
                sleep(150);
                wobbleGoal.release();
                sleep(250);

            } else {
                drivetrain.moveGyro(-.9, 2, 0);
                sleep(250);
                drivetrain.turnPD(144, .82, .4, 3000);
                sleep(450);
                drivetrain.moveGyro(.95, 69, 144);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(500, .4);
                sleep(150);
                wobbleGoal.release();
                sleep(250);

            }

            // ----------------------------------------------- Park -----------------------------------------------
            if (!secondWobbleGoal) {
                if (targetZone == "A") {
                    drivetrain.moveGyro(-.95, 16.85, 90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(100);
                    drivetrain.strafeLeftInches(.95, 4);
                    sleep(200);
                    drivetrain.turnPD(180, .8, .5, 2000);
                    sleep(100);
                    drivetrain.moveGyro(-.95, 21.5, 180);
                    sleep(4000);
                    drivetrain.turnPD(180, .8, .5, 750);
                    sleep(150);
                    drivetrain.moveGyro(.9, 25.25, 180);

                } else if (targetZone == "B") {
                    drivetrain.moveGyro(-.95, 3, 180);
                    sleep(200);
                    drivetrain.strafeLeftInches(.9, 29);
                    sleep(200);
                    drivetrain.turnPD(180, .8, .5, 750);
                    sleep(250);
                    drivetrain.moveGyro(-.95, 11.5, 180);
                    sleep(250);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.turnPD(180, .7, .5, 1000);

                } else {
                    drivetrain.moveGyro(-.95, 3, 144);
                    sleep(250);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.strafeLeftInches(.9, 6);
                    sleep(200);
                    drivetrain.turnPD(180, .7, .5, 1000);
                    sleep(250);
                    drivetrain.strafeLeftInches(.9, 2.5);
                    sleep(200);
                    drivetrain.moveGyro(-.95, 39, 180);
                }
            }
        }
    }
}
