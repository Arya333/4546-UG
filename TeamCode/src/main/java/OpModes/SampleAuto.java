package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import Library.Drivetrain;
import Library.Intake;
import Library.Sensors;
import Library.Shooter;

@Autonomous(name = "SampleAuto", group = "4546")
public class SampleAuto extends LinearOpMode{

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Intake intake;
    private Shooter shooter;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        intake = new Intake(this);
        shooter = new Shooter(this);

        waitForStart();

        // ----------------------------------------------- Auto -----------------------------------------------


    }
}
