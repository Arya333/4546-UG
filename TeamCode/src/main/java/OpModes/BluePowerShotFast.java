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

@Autonomous(name = "BluePowerShotFast", group = "4546")
public class BluePowerShotFast extends LinearOpMode{

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


        if (!isStopRequested() && opModeIsActive()){
            // ----------------------------------------------- Drive to Launch Line -----------------------------------------------
            drivetrain.moveGyro(-.8, 75.8, 0);
            sleep(350);
            drivetrain.turnPD(0, 0.8, 0.45, 1000);
            shooter.shootOutPowerShot();
            sleep(1600);

            // ----------------------------------------------- Shoot Power Shots -----------------------------------------------
            intake.intakeOutSlow();
            sleep(250);
            drivetrain.strafeLeftInches(.8, 19.5);
            sleep(250);
            drivetrain.turnPD(0, .8, .4, 400);
            shooter.flickPos();
            sleep(1300);
            shooter.initPos();

            drivetrain.turnPD(10, .86, .4, 400);
            shooter.motorShooter.setPower(.325);
            shooter.motorShooter2.setPower(.348);
            sleep(1300);
            shooter.flickPos();
            sleep(1300);
            shooter.initPos();

            drivetrain.turnPD(-7.6, .88, .36, 700);
            shooter.motorShooter.setPower(.325);
            shooter.motorShooter2.setPower(.347);
            sleep(1400);
            shooter.flickPos();
            sleep(1300);
            shooter.initPos();
            shooter.stopShooter();
            intake.intakeStop();
            drivetrain.turnPD(0,.85,.38,700);

            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A"){
                drivetrain.moveGyro(-.95, 13, 0);
                sleep(300);
                drivetrain.turnPD(90,.84,.4,2600);
                sleep(300);
                drivetrain.moveGyro(.95,37.2,90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(600,.45);
                sleep(150);
                wobbleGoal.release();
            }
            else if (targetZone == "B"){
                drivetrain.moveGyro(-.95, 42.5, 0);
                sleep(300);
                drivetrain.turnPD(90,.84,.4,2600);
                sleep(150);
                drivetrain.moveGyro(.95,5,90);
                sleep(150);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(600,.45);
                sleep(150);
                wobbleGoal.release();
            }
            else{
                drivetrain.moveGyro(-.95, 78, 0);
                sleep(300);
                drivetrain.turnPD(90,.84,.4,2600);
                sleep(300);
                drivetrain.moveGyro(.95,33.5,90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(600,.45);
                sleep(150);
                wobbleGoal.release();
            }


            // ----------------------------------------------- Park -----------------------------------------------
            if (!secondWobbleGoal){
                if (targetZone == "A"){
                    sleep(150);
                    drivetrain.moveGyro(-.95,34.5,90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.25);
                    sleep(250);
                    drivetrain.turnPD(180,.84,.4,2500);
                    sleep(300);
                    drivetrain.moveGyro(-.95, 1,180);
                    sleep(300);
                    drivetrain.turnPD(180,.8,.5,700);
                }
                else if (targetZone == "B"){
                    drivetrain.moveGyro(-.95,5,90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.turnPD(180,.84,.4,2600);
                    sleep(300);
                    drivetrain.moveGyro(-.95, 29,180);
                    sleep(300);
                    drivetrain.turnPD(180,.8,.4,3000);
                }
                else{
                    sleep(500);
                    drivetrain.moveGyro(-.95,33.5,90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.strafeLeftInches(.8,5);
                    drivetrain.turnPD(180,.84,.4,2600);
                    sleep(250);
                    drivetrain.moveGyro(-.95, 61.2,180);
                    sleep(250);
                    drivetrain.turnPD(180,.8,.5,700);
                }
            }


        }
    }
}
