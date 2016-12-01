/* Copyright (c) 2015 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package org.firstinspires.ftc.robotcontroller.external.samples;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 *
 * This is an example LinearOpMode that shows how to use
 * a Modern Robotics Color Sensor.
 *
 * The op mode assumes that the color sensor
 * is configured with a name of "color sensor".
 *
 * You can use the X button on gamepad1 to toggle the LED on and off.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "Sensor: MR Color", group = "Sensor")
@Disabled
public class ColorSensorTester extends LinearOpMode {

  ColorSensor colorSensorBack;    // Hardware Device Object
    public ColorSensor colorSensorFront;
  DcMotor motor1;
    DcMotor motor2;
    Servo servo1;
    Servo servo2;
  private ElapsedTime     runtime = new ElapsedTime();

  static final double     FORWARD_SPEED = 0.6;
  static final double     TURN_SPEED    = 0.5;

  @Override
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

      // bLedOn represents the state of the LED.
      boolean bLedOn = true;

      boolean active = false;

      // get a reference to our ColorSensor object.
     // colorSensorFront = hardwareMap.colorSensor.get("cs1");
      colorSensorFront = hardwareMap.colorSensor.get("front color sensor");
      colorSensorBack = hardwareMap.colorSensor.get("back color sensor");

      motor1 = hardwareMap.dcMotor.get("motor 1 power");
      motor2 = hardwareMap.dcMotor.get("motor 2 power");


      // Set the LED in the beginning
      colorSensorFront.enableLed(bLedOn);
      colorSensorBack.enableLed(bLedOn);
      //colorSensorBack.enableLed(bLedOn);


      // wait for the start button to be pressed
      waitForStart();

      // while the op mode is active, loop and read the RGB data.
      // Note we use opModeIsActive() as our loop condition because it is an interruptible method.
      while (opModeIsActive()) {

          //sensoring color red

          // update previous state variable.

              // button is transitioning to a pressed state. So Toggle LED
         // colorSensor.enableLed(bLedOn);
          //colorSensor.enableLed(bLedOn);
         // colorSensorBack.enableLed(bLedOn);


          // update previous state variable.
          bPrevState = bCurrState;

          // convert the RGB values to HSV values.
          Color.RGBToHSV(colorSensorFront.red() * 8, colorSensorFront.green() * 8, colorSensorFront.blue() * 8, hsvValues);
          Color.RGBToHSV(colorSensorBack.red() * 8, colorSensorBack.green() * 8, colorSensorBack.blue() * 8, hsvValues);

          // Color.RGBToHSV(colorSensorBack.red() * 8, colorSensorBack.green() * 8, colorSensorBack.blue() * 8, hsvValues);


          // send the info back to driver station using telemetry function.
          telemetry.addData("Alpha Front", colorSensorFront.alpha());
          telemetry.addData("Alpha Back", colorSensorBack.alpha());
          // program back color sensor
          //telemetry.addData("Blue Two  ", colorSensorBack.blue());

          //motor1.setPower(0.7);
          //motor2.setPower(0.6);

          //telemetry.addData("Clear", colorSensor.alpha());
          if (colorSensorFront.alpha() < 10 && colorSensorBack.alpha() < 10) {

                  motor1.setPower(1.0);
                  motor2.setPower(1.0);


          }
          else if (colorSensorFront.alpha() > 10 && colorSensorBack.alpha() > 10) {

              motor1.setPower(0.0);
              motor2.setPower(0.0);
          }

          else {

          }


          //telemetry.addData("Green", colorSensor.green());
          //telemetry.addData("Hue", hsvValues[0]);

          // change the background color to match the color detected by the RGB sensor.
          // pass a reference to the hue, saturation, and value array as an argument
          // to the HSVToColor method.
          relativeLayout.post(new Runnable() {
              public void run() {
                  relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
              }
          });

          telemetry.update();
          idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
      }
  }
}
