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

@Autonomous(name = "BlueShootOnly", group = "4546")
public class BlueShootOnly extends LinearOpMode{

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
                shooter.motorShooter.setPower(.363);
                shooter.motorShooter2.setPower(.367);
                sleep(500);
                drivetrain.strafeLeftInches(.85, 21.5);
                sleep(350);
                drivetrain.turnPD(1.9, 0.86, 0.55, 1000);

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

                drivetrain.turnPD(0, .8, .5, 700);
                sleep(250);
                drivetrain.moveGyro(-.95, 10, 0);
            }
            else{
                sleep(20000);
                drivetrain.moveGyro(-.8, 75.8, 0);
                sleep(350);
                drivetrain.turnPD(0, 0.8, 0.45, 1000);
                //shooter.shootOutHighGoal();
                shooter.motorShooter.setPower(.361);
                shooter.motorShooter2.setPower(.366);
                sleep(1500);

                intake.intakeOutSlow();
                sleep(250);
                drivetrain.strafeLeftInches(.8, 12.9);
                sleep(200);
                drivetrain.turnPD(0, .82, .39, 500);
                sleep(200);
                drivetrain.turnPD(-14.3,.85, .39, 1000);
                sleep(250);
                shooter.flickPos();
                sleep(1300);
                shooter.initPos();

                shooter.motorShooter.setPower(.362);
                shooter.motorShooter2.setPower(.366);
                sleep(1400);
                shooter.flickPos();
                sleep(1300);
                shooter.initPos();

                shooter.motorShooter.setPower(.361);
                shooter.motorShooter2.setPower(.366);
                sleep(1400);
                shooter.flickPos();
                sleep(1300);
                shooter.initPos();
                shooter.stopShooter();
                intake.intakeStop();
                drivetrain.turnPD(0,.85,.38,700);
                sleep(250);
                drivetrain.moveGyro(-.95, 6, 0);
            }

        }
    }
}
