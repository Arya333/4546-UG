package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import Library.Drivetrain;
import Library.Intake;
import Library.Sensors;
import Library.Shooter;
import Library.WobbleGoal;

@Autonomous(name = "Test Auto", group = "4546")
public class TestAuto extends LinearOpMode{

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

        if (!isStopRequested()){
            drivetrain.moveGyro(.5,15,0);
        }

    }
}
