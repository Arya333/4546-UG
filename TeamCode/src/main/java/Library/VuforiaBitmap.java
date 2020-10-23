package Library;

import android.graphics.Bitmap;

import static android.graphics.Color.red;
import static android.graphics.Color.green;
import static android.graphics.Color.blue;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Frame;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.Parameters;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;

public class VuforiaBitmap {

    LinearOpMode opMode;
    int stackHeight = 0;
    String targetZone = "A";

    private VuforiaLocalizer vuforia;
    private Parameters parameters;
    private CameraDirection CAMERA_CHOICE = CameraDirection.BACK;
    private static final String VUFORIA_KEY = "Aa6axCr/////AAABmVc2QsaPSkQJjpSMGsD9/ZosRWS7BswNiE9Sb9VLOcImnf5cWwR01zEMArJtnPJzhJ9tT7d/hf4NWBELaHUGmFmm/9YjcSO09DOuDcZC+gS2AGXGcYFTw4SDBryFvz8OY5jaUAWxnfXzt3uaQcNS/3ScAZsWXL6RS4WazWpYlfeLNyyp2SliGbDza3b7T8DaxHXewmwtX+uobcEv9SS4ivv12Lr14Id9q4Qa+P1ZSSxyMQZ7TUMlZtrb/L9kmMLdLYkBX+74pVTpW9Ftp18uJdvF86qy/Jt3b1t67q5JM2xTHGnF8ETgpwNFEXWOivJdxlSGhTF6p7DCO7yNXEHw+xFa2JkVhoFV9GyBseKP+BdV";

    private final int RED_THRESHOLD = 25;
    private final int GREEN_THRESHOLD = 25;
    private final int BLUE_THRESHOLD = 25;

    public VuforiaBitmap (LinearOpMode opMode){

        this.opMode = opMode;

        int cameraMonitorViewId = this.opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", this.opMode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        params.vuforiaLicenseKey = VUFORIA_KEY;
        params.cameraDirection = CAMERA_CHOICE;
        params.cameraName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(params);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(4);
        vuforia.enableConvertFrameToBitmap();

    }

    public Bitmap getBitmap() throws InterruptedException {

        VuforiaLocalizer.CloseableFrame picture;
        picture = vuforia.getFrameQueue().take();
        Image rgb = picture.getImage(1);

        long numImages = picture.getNumImages();

        opMode.telemetry.addData("Num images", numImages);
        opMode.telemetry.update();

        for (int i = 0; i < numImages; i++) {

            int format = picture.getImage(i).getFormat();
            if (format == PIXEL_FORMAT.RGB565) {
                rgb = picture.getImage(i);
                break;
            } else {
                opMode.telemetry.addLine("Didn't find correct RGB format");
                opMode.telemetry.update();

            }
        }

        Bitmap imageBitmap = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        imageBitmap.copyPixelsFromBuffer(rgb.getPixels());

        opMode.telemetry.addData("Image width", imageBitmap.getWidth());
        opMode.telemetry.addData("Image height", imageBitmap.getHeight());
        opMode.telemetry.update();
        opMode.sleep(500);

        picture.close();


        return imageBitmap;
    }

    public double getImageHeight() throws InterruptedException {
        Bitmap bitmap = getBitmap();
        return bitmap.getHeight();
    }

    public double getImageWidth() throws InterruptedException {
        Bitmap bitmap = getBitmap();
        return bitmap.getWidth();
    }

    public String getStack() throws InterruptedException {
        Bitmap bitmap = getBitmap();
        int yellowPixelCount = 0;
        ArrayList<Integer> xValues = new ArrayList<>();
        ArrayList<Integer> yValues = new ArrayList<>();
        for (int y = bitmap.getHeight() - 5; y > (bitmap.getHeight() / 2); y--) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                int pixel = bitmap.getPixel(x, y);
                if (red(pixel) <= RED_THRESHOLD && blue(pixel) <= BLUE_THRESHOLD && green(pixel) <= GREEN_THRESHOLD) {
                    xValues.add(x);
                    yValues.add(y);
                    yellowPixelCount++;
                }
            }
        }

        // add code for determining stack height using yellowPixelCount after testing
        if (yellowPixelCount > 500){
            stackHeight = 4;
            targetZone = "C";
        }
        else if (yellowPixelCount > 250){
            stackHeight = 1;
            targetZone = "B";
        }
        else{
            stackHeight = 0;
            targetZone = "A";
        }
        return targetZone;
    }
}
