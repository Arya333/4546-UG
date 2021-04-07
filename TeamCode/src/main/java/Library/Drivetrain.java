package Library;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Drivetrain {

    public DcMotor motorFL;
    public DcMotor motorFR;
    public DcMotor motorBL;
    public DcMotor motorBR;
    public RevBlinkinLedDriver blink;

    LinearOpMode opMode;
    public double countsPerInch;
    ElapsedTime time;
    Sensors sensor;

    public Drivetrain(LinearOpMode opMode) throws InterruptedException{
        this.opMode = opMode;
        motorFL = this.opMode.hardwareMap.dcMotor.get("motorFL");
        motorFR = this.opMode.hardwareMap.dcMotor.get("motorFR");
        motorBL = this.opMode.hardwareMap.dcMotor.get("motorBL");
        motorBR = this.opMode.hardwareMap.dcMotor.get("motorBR");

        blink = this.opMode.hardwareMap.get(RevBlinkinLedDriver.class, "blink");
        blink.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);

        motorFL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBL.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        countsPerInch = EncodersPerInch(560, .5, (75/25.4)); //change parameters
        time = new ElapsedTime();
        sensor = new Sensors(opMode);

        setDTBrake();

    }

    public void startMotors(double left, double right){
        motorFL.setPower(left);
        motorFR.setPower(right);
        motorBL.setPower(left);
        motorBR.setPower(right);
    }

    public void stopMotors(){
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public double EncodersPerInch(double encoders, double gearReduction, double wheelDiameter){
        return ((encoders * gearReduction) /(wheelDiameter * Math.PI) );
    }

    public void resetEncoders(){
        motorFL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorFR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorBL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();
        motorBR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        opMode.idle();

        motorFL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorFR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorBL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
        motorBR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        opMode.idle();
    }

    public void setDTBrake(){
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opMode.idle();
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opMode.idle();
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opMode.idle();
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        opMode.idle();
    }

    public void setDTFloat(){
        motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        opMode.idle();
        motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        opMode.idle();
        motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        opMode.idle();
        motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        opMode.idle();
    }

    public double getEncoderAvg(){
        int count = 4;
        if (motorFL.getCurrentPosition() == 0){
            count--;
        }
        if (motorFR.getCurrentPosition() == 0){
            count--;
        }
        if (motorBL.getCurrentPosition() == 0){
            count--;
        }
        if (motorBR.getCurrentPosition() == 0){
            count--;
        }
        if (count == 0) count++;
        return (Math.abs(motorFL.getCurrentPosition()) +
                Math.abs(motorFR.getCurrentPosition()) +
                Math.abs(motorBL.getCurrentPosition()) +
                Math.abs(motorBR.getCurrentPosition())) / count;
    }

    public void moveInches(double inches, double power){
        resetEncoders();
        while (getEncoderAvg() < inches * countsPerInch){
            startMotors(power, power);
        }
        stopMotors();
    }

    public void moveTime(int millis, double power){
        time.reset();
        while (time.milliseconds() < millis){
            startMotors(power, power);
        }
        stopMotors();
    }

    public void strafeRightInches(double power, double inches){
        resetEncoders();
        while (getEncoderAvg() < inches * countsPerInch){
            motorBL.setPower(-power);
            motorFL.setPower(power);
            motorFR.setPower(-power);
            motorBR.setPower(power);
        }
        stopMotors();
    }

    public void strafeLeftInches(double power, double inches){
        resetEncoders();
        while (getEncoderAvg() < inches * countsPerInch){
            motorBL.setPower(power);
            motorFL.setPower(-power);
            motorFR.setPower(power);
            motorBR.setPower(-power);
        }
        stopMotors();
    }


    public void moveGyro(double power, double inches, double heading){
        setDTBrake();
        resetEncoders();
        double constant = .7; //to reduce power of one side of drivetrain
        if (power > 0){
            while (getEncoderAvg() < inches * countsPerInch && opMode.opModeIsActive()){ //while our avg encoder value is less than desired number of encoder ticks
                if (sensor.getTrueDiff(heading) > 1.6){ //if our robot is off its heading to the left (needs to turn right)
                    startMotors(power, power * constant); //apply less power to right side so we turn right to maintain our heading
                }
                else if (sensor.getTrueDiff(heading) < -2){ //if our robot is off its heading to the right (needs to turn left)
                    startMotors(power * constant, power); //apply less power to left side so we turn left to maintain our heading
                }
                else{
                    startMotors(power, power);
                }
            }
        }
        else{
            while (getEncoderAvg() < inches * countsPerInch && opMode.opModeIsActive()){ //everything is swapped when we move backwards
                if (sensor.getTrueDiff(heading) > 1.6){
                    startMotors(power * constant, power);
                }
                else if (sensor.getTrueDiff(heading) < -2){
                    startMotors(power, power * constant);
                }
                else{
                    startMotors(power, power);
                }
            }
        }
        stopMotors();
    }

    public void turnP(double angle, double p) {
        double kP = p;
        final double startPos = sensor.getGyroYaw(); // initial angle
        final double angleDiff = angle - startPos; // desired angle - initial angle = difference
        double deltaAngle = sensor.getTrueDiff(angle); // difference in current and desired angle (negative if need to turn left and positive if need to turn right)
        double changePID = 0; // the power we will apply to our motors
        while(Math.abs(deltaAngle) > .3){
            deltaAngle = sensor.getTrueDiff(angle);
            changePID = ((deltaAngle/Math.abs(angleDiff)) * kP); // As we get closer to the desired angle, the value that is multiplied with kP is decreased (we won't overshoot)
            if (changePID < 0){ // if this value is negative (need to turn left)
                startMotors(changePID - .075 , -changePID + .075); // turn method for going left (left side turns backwards and right side turns forward)
            }
            else{ // if changePID is positive (need to turn right)
                startMotors(changePID + .075, -changePID - .075); // turn method for going right (left side turns forward and right side turns backward)
            }
            // the constants that are added or subtracted with changePID in startMotors allow the motors to turn when the value of changePID becomes small
            opMode.telemetry.addData("Current position", sensor.getGyroYaw());
            opMode.telemetry.addData("Current Difference",deltaAngle);
            opMode.telemetry.addData("changePID", changePID);
            opMode.telemetry.addData("angleDiff: ", angleDiff);
            opMode.telemetry.update();
        }
        stopMotors();
    }

    public void turnPI(double angle, double p, double i, double timeout) {
        time.reset();
        double kP = p / 90;
        double kI = i / 100000;
        double currentTime = time.milliseconds();
        double pastTime = 0;
        double P = 0;
        double I = 0;
        double angleDiff = sensor.getTrueDiff(angle);
        double changePID = 0;
        while (Math.abs(angleDiff) > .5 && time.milliseconds() < timeout) {
            pastTime = currentTime;
            currentTime = time.milliseconds();
            double dT = currentTime - pastTime;
            angleDiff = sensor.getTrueDiff(angle); // error
            P = angleDiff * kP; // power is proportional to error
            I += dT * angleDiff * kI; // sum of error throughout entire duration of the turn
            changePID = P;
            changePID += I;
            opMode.telemetry.addData("PID: ", changePID);
            opMode.telemetry.addData("diff", angleDiff);
            opMode.telemetry.addData("P", P);
            opMode.telemetry.addData("I", I);
            opMode.telemetry.addData("motorBR power: ", motorBR.getPower());
            opMode.telemetry.update();
            if (changePID < 0) {
                startMotors(changePID - .1, -changePID + .1);
            } else {
                startMotors(changePID + .1, -changePID - .1);
            }
        }
        stopMotors();
    }

    public void turnPD(double angle, double p, double d, double timeout){
        time.reset();
        double kP = p / 90;
        double kD = d;
        double currentTime = time.milliseconds();
        double pastTime = 0;
        double prevAngleDiff = sensor.getTrueDiff(angle);
        double angleDiff = prevAngleDiff;
        double changePID = 0;
        while (Math.abs(angleDiff) > .5 && time.milliseconds() < timeout && opMode.opModeIsActive()) {
            pastTime = currentTime;
            currentTime = time.milliseconds();
            double dT = currentTime - pastTime;
            angleDiff = sensor.getTrueDiff(angle);
            changePID = (angleDiff * kP) + ((angleDiff - prevAngleDiff) / dT * kD); // second part is rate of change of error (derivative)
            if (changePID < 0) {
                startMotors(changePID - .10, -changePID + .10);
            } else {
                startMotors(changePID + .10, -changePID - .10);
            }
            opMode.telemetry.addData("P", (angleDiff * kP));
            opMode.telemetry.addData("D", ((Math.abs(angleDiff) - Math.abs(prevAngleDiff)) / dT * kD));
            opMode.telemetry.update();
            prevAngleDiff = angleDiff;
        }
        stopMotors();
    }


}
