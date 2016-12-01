package org.firstinspires.ftc.robotcontroller.external.samples;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;



public class ColorTester2 extends LinearOpMode{
    double currenttime = 0;
    long start;
    boolean fail = false;
    float hsvlineleft[] = {0F,0F,0F};
    float hsvlineright[] = {0F,0F,0F};

    DcMotor left = null;
    DcMotor right = null;
    ColorSensor lineleft;
    ColorSensor lineright;
    ColorSensor frontright;
    ColorSensor frontleft;
    ModernRoboticsI2cRangeSensor rangeSensor;


    public void runOpMode() throws InterruptedException {
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F, 0F, 0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(R.id.RelativeLayout);

        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;

        //bLedOn represents the state of the LED.
        boolean bLedOn = true;

        boolean active = false;



        // wait for the start button to be pressed
        waitForStart();


        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        lineleft = hardwareMap.colorSensor.get("line left");
        lineright = hardwareMap.colorSensor.get("line right");
        frontright = hardwareMap.colorSensor.get("front right");
        frontleft = hardwareMap.colorSensor.get("front left");


        left.setDirection(DcMotor.Direction.FORWARD);
        right.setDirection(DcMotor.Direction.FORWARD);

        //left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //lineleft.enableLed(true);
        lineright.enableLed(true);



        //This program is for going in a straight line
        //right.setPower(0.5); //sets the power to 0.5
        //left.setPower(0.5); //side note, 4262's autonomous is "way faster" - KJ
        Stopwatch();
        currenttime = 0;
        while(opModeIsActive())
        {
            Color.RGBToHSV(lineright.red() * 8, lineright.green() * 8, lineright.blue() * 8, hsvlineright); // get color value in HSV
            Color.RGBToHSV(lineleft.red() * 8, lineleft.green() * 8, lineright.blue() * 8, hsvlineright); // get color value in HSV
            currenttime = elapsedTime();
            //detect distance
            //stop if distance is too close
            //first go a certain amount of distance
            if(currenttime < 5) { // goes forward
                right.setPower(1);
                left.setPower(1);

            }

            if(currenttime < 5)
            {
                if(((hsvlineleft[1] < 0.5 || hsvlineleft[2] < 0.5) && (hsvlineright[1] < 0.5 || hsvlineright[2] < 0.5)) || ((hsvlineleft[1] <= 0.3 || hsvlineleft[2] >= 0.7) && (hsvlineright[1] <= 0.3 || hsvlineright[2] >= 0.7)))
                {
                    right.setPower(0.5);
                    left.setPower(0.5);
                }
                else if((hsvlineleft[1] < 0.5 || hsvlineleft[2] > 0.5) && (hsvlineright[1] < 0.5 || hsvlineright[2] < 0.5))
                {
                    right.setPower(0);
                    left.setPower(0.5);
                }
                else if(((hsvlineleft[1] < 0.5 || hsvlineleft[2] < 0.5) && (hsvlineright[1] < 0.5 || hsvlineright[2] > 0.5)))
                {
                    right.setPower(0.5);
                    left.setPower(0);
                }
                telemetry.addData("Motor Power right", right.getPower());
                telemetry.addData("Motor Power left", left.getPower());
                telemetry.addData("left color saturation", hsvlineleft[1]);
                telemetry.addData("left color value", hsvlineleft[2]);
                telemetry.addData("right color saturation", hsvlineright[1]);
                telemetry.addData("right color value", hsvlineright[2]);
                int x = 20;

                if(frontright.red() > 20 || frontleft.red() > 20 )
                {
                    right.setPower(1);
                    left.setPower(1);

                }

                else if(frontright.red() < 25 || frontleft.red() < 25)
                {

                    right.setPower(0);
                    left.setPower(0);

                }



            }

            else
            {
                fail = true;
                continue;
            }

            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });

            telemetry.update();
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
        //left.setPower(0);
        //right.setPower(0);



    }

    public void Stopwatch() {
        start = System.currentTimeMillis();
    }

    // function to read stopwatch
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }
}
