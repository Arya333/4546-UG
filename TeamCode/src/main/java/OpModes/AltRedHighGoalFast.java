package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import Library.Drivetrain;
import Library.Intake;
import Library.Sensors;
import Library.Shooter;
import Library.VuforiaBitmap;
import Library.WobbleGoal;

@Autonomous(name = "AltRedHighGoalFast", group = "4546")
public class AltRedHighGoalFast extends LinearOpMode{

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
            drivetrain.moveGyro(-.8, 74, -1.9);
            sleep(350);
            drivetrain.turnPD(0, 0.8, 0.7, 1000);
            //shooter.shootOutHighGoal();
            shooter.motorShooter.setPower(.361);
            shooter.motorShooter2.setPower(.3645);
            sleep(1600);

            // ----------------------------------------------- Shoot High Goal -----------------------------------------------
            intake.intakeOutSlow();
            sleep(250);
            drivetrain.strafeRightInches(.8, 11);
            sleep(200);
            drivetrain.turnPD(0, .8, .4, 400);
            sleep(200);
            drivetrain.turnPD(18.35,.84, .38, 1000);
            sleep(250);
            shooter.flickPos();
            sleep(1300);
            shooter.initPos();

            shooter.motorShooter.setPower(.362);
            shooter.motorShooter2.setPower(.366);
            sleep(1650);
            shooter.flickPos();
            sleep(1300);
            shooter.initPos();

            shooter.motorShooter.setPower(.361);
            shooter.motorShooter2.setPower(.3645);
            sleep(1650);
            shooter.flickPos();
            sleep(1300);
            shooter.initPos();
            shooter.stopShooter();
            intake.intakeStop();

            // ----------------------------------------------- Drop Wobble Goal 1 -----------------------------------------------
            if (targetZone == "A"){
                sleep(200);
                drivetrain.turnPD(0, .8, .4, 800);
                sleep(200);
                drivetrain.moveGyro(-.95, 26, 0);
                sleep(300);
                drivetrain.turnPD(-90,.84,.4,2600);
                sleep(300);
                drivetrain.moveGyro(.95,28.75,-90);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(600,.43);
                sleep(150);
                wobbleGoal.release();
                sleep(250);
            }
            else if (targetZone == "B"){
                sleep(200);
                drivetrain.turnPD(0, .8, .4, 800);
                sleep(200);
                drivetrain.moveGyro(-.95, 58.4, 0);
                sleep(300);
                drivetrain.turnPD(-90,.84,.4,2600);
                sleep(150);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(600,.43);
                sleep(150);
                wobbleGoal.release();
                sleep(250);
            }
            else{
                sleep(200);
                drivetrain.turnPD(0, .8, .4, 800);
                sleep(200);
                drivetrain.moveGyro(-.95, 80, 0);
                sleep(300);
                drivetrain.turnPD(-92.5,.84,.4,2600);
                sleep(300);
                drivetrain.moveGyro(.95,25.8,-93.5);

                wobbleGoal.motorPivot.setPower(0);
                wobbleGoal.rotateTime(600,.43);
                sleep(250);
                wobbleGoal.release();
                sleep(350);
            }


            // ----------------------------------------------- Park -----------------------------------------------
            if (!secondWobbleGoal){
                if (targetZone == "A"){
                    sleep(150);
                    drivetrain.moveGyro(-.95,29.5,-90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.25);
                    sleep(250);
                    drivetrain.turnPD(180,.84,.4,2500);
                    sleep(300);
                    drivetrain.moveGyro(-.95, 16,180);
                    sleep(300);
                    drivetrain.turnPD(180,.8,.5,700);
                }
                else if (targetZone == "B"){
                    drivetrain.moveGyro(-.95,3,-90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.turnPD(180,.84,.4,2600);
                    sleep(300);
                    drivetrain.moveGyro(-.95, 46.5,180);
                    sleep(300);
                    drivetrain.turnPD(180,.8,.4,3000);
                }
                else{
                    sleep(500);
                    drivetrain.moveGyro(-.95,24,-90);
                    sleep(200);
                    wobbleGoal.grab();
                    wobbleGoal.motorPivot.setPower(-.2);
                    sleep(250);
                    drivetrain.strafeRightInches(.8,5);
                    drivetrain.turnPD(180,.84,.4,2600);
                    sleep(250);
                    drivetrain.moveGyro(-.95, 63.5,180);
                    sleep(250);
                    drivetrain.turnPD(180,.8,.5,700);
                }
            }


        }
    }
}
