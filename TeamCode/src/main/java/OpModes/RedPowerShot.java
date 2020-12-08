package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import Library.Drivetrain;
import Library.Intake;
import Library.Sensors;
import Library.Shooter;
import Library.VuforiaBitmap;

@Autonomous(name = "RedPowerShot", group = "4546")
public class RedPowerShot extends LinearOpMode {

    private Drivetrain drivetrain;
    private Sensors sensors;
    private Intake intake;
    private Shooter shooter;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {

        // ----------------------------------------------- Init -----------------------------------------------
        VuforiaBitmap sample = new VuforiaBitmap(this);
        String targetZone = "A";
        drivetrain = new Drivetrain(this);
        sensors = new Sensors(this);
        //intake = new Intake(this);
        //shooter = new Shooter(this);

        while (!isStarted()){
            telemetry.addData("Target Zone: ", sample.getStack());
            telemetry.update();
            targetZone = sample.getStack();
        }
        waitForStart();

        telemetry.addData("Target Zone: ", targetZone);
        telemetry.update();

        waitForStart();

        // ----------------------------------------------- Auto -----------------------------------------------
        sleep(1000);
        //drivetrain.turnPI(90,.35,.07,10000);  ---- Not using PI, too much overshoot

        //drivetrain.turnPD(90,.6,.5,5000); ---- Constants for 90 degree turn
        //drivetrain.turnPD(45, .7, .6, 5000); ---- Constants for 45 degree turn

        drivetrain.turnPD(-45, .7, .6, 5000);
        telemetry.update();

    }
}
