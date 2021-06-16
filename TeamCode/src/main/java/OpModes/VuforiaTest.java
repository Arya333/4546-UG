package OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Library.VuforiaBitmap;

//@Autonomous(name = "VuforiaBitmapTest", group = "4546")
public class VuforiaTest extends LinearOpMode{

    private VuforiaBitmap sample;

    @Override
    public void runOpMode() throws InterruptedException{
        VuforiaBitmap sample = new VuforiaBitmap(this);
        String targetZone = "A";

        while (!isStarted()){
            telemetry.addData("Target Zone: ", sample.getStack());
            telemetry.update();
            targetZone = sample.getStack();
        }
        waitForStart();

        telemetry.addData("Target Zone: ", targetZone);
        telemetry.update();
    }
}
