package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import Library.Drivetrain;
import Library.Intake;
import Library.Sensors;
import Library.Shooter;
import Library.WobbleGoal;

@Autonomous(name = "SampleAuto", group = "4546")
public class SampleAuto extends LinearOpMode{

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Intake intake;
    private Shooter shooter;
    private WobbleGoal wobbleGoal;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        shooter = new Shooter(this);
        wobbleGoal = new WobbleGoal(this);
        //intake = new Intake(this);
        //shooter = new Shooter(this);

        waitForStart();

        // ----------------------------------------------- Auto -----------------------------------------------
        sleep(1000);
        //drivetrain.turnPI(90,.35,.07,10000);  ---- Not using PI, too much overshoot

        //drivetrain.turnPD(90,.6,.5,5000); ---- Constants for 90 degree turn
        //drivetrain.turnPD(45, .7, .6, 5000); ---- Constants for 45 degree turn

        wobbleGoal.rotateToEncoderPos(170,.25);
        sleep(3000);

    }
}
