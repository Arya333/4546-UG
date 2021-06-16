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

@Autonomous(name = "RedShootOnly", group = "4546")
public class RedShootOnly extends LinearOpMode{

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Intake intake;
    private Shooter shooter;
    private WobbleGoal wobbleGoal;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        boolean closeToWall = true;
        boolean selectionMade = false;
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        intake = new Intake(this);
        shooter = new Shooter(this);
        wobbleGoal = new WobbleGoal(this);

        wobbleGoal.grab();
        wobbleGoal.motorPivot.setPower(-.1);

        while (!isStarted()){
            telemetry.addData("Close to Wall? ", closeToWall);
            telemetry.update();
            if (gamepad1.y) {
                closeToWall = false;
                selectionMade = true;
                telemetry.addData("Close to Wall? ", closeToWall);
                telemetry.update();
            } else if (gamepad1.a) {
                closeToWall = true;
                selectionMade = true;
                telemetry.addData("Close to Wall? ", closeToWall);
                telemetry.update();
            }

            if (selectionMade){
                telemetry.addData("Close to Wall? ", closeToWall);
                telemetry.update();
                sleep(500);
            }
        }

        waitForStart();

        if (!isStopRequested() && opModeIsActive()){

            if (closeToWall){
                sleep(20000);
                drivetrain.moveGyro(-.95, 66.9, 0);
                sleep(350);
                drivetrain.turnPD(0, 0.8, 0.7, 1000);
                //shooter.shootOutHighGoal();
                shooter.motorShooter.setPower(.362);
                shooter.motorShooter2.setPower(.367);
                sleep(400);
                drivetrain.strafeRightInches(.85, 19.1);
                sleep(350);
                drivetrain.turnPD(2.6, 0.86, 0.55, 1000);
                intake.intakeOutSlow();
                sleep(1600);
                shooter.flickPos();
                sleep(1250);
                shooter.initPos();

                shooter.motorShooter.setPower(.361);
                shooter.motorShooter2.setPower(.369);
                sleep(1650);
                shooter.flickPos();
                sleep(850);
                shooter.initPos();

                shooter.motorShooter.setPower(.362);
                shooter.motorShooter2.setPower(.367);
                sleep(1650);
                shooter.flickPos();
                sleep(850);
                shooter.initPos();
                shooter.stopShooter();
                intake.intakeStop();

                drivetrain.turnPD(0, .8, .5, 700);
                sleep(250);
                drivetrain.moveGyro(-.95, 10, 0);
            }
            else{
                sleep(20000);
                drivetrain.moveGyro(-.8, 74, -1.9);
                sleep(350);
                drivetrain.turnPD(0, 0.8, 0.7, 1000);
                //shooter.shootOutHighGoal();
                shooter.motorShooter.setPower(.361);
                shooter.motorShooter2.setPower(.3645);
                sleep(1600);

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

                drivetrain.turnPD(0, .85, .4, 700);
                sleep(250);
                drivetrain.moveGyro(-.95, 6, 0);
            }

        }
    }

}
