/******************************
*   Mobile Robotics			  *
*	Assignement 1			  *
*	Student Name: Minhui Chen *
*	Student No: D17125347	  *
*	Student Name: Jianyu He   *
*	Student No: D17124591	  *
*******************************/
package ev3;

import lejos.hardware.motor.*;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import library.*;
import lejos.robotics.Color;

public class Robotics {
	
	static UltraSonicSensor uss = new UltraSonicSensor(SensorPort.S4);
    static GyroSensor gyro = new GyroSensor(SensorPort.S2);
	static ColorSensor color = new ColorSensor(SensorPort.S3);
	static TouchSensor touch = new TouchSensor(SensorPort.S1);
	// create two motor objects to control the motors.
	static EV3LargeRegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
	
	public static void rotate(int degree)
	{
		int angle = degree;
		// set gyro angle to 0
    	gyro.reset();
	    while(Math.abs(gyro.getAngle())<=angle)
		{
	    	Lcd.clear(4);
	    	Lcd.print(4, "angle=%d ", gyro.getAngle());
		    motorA.forward();
			motorB.backward();
			// set slow motor speed to get satisfy rotate
			motorA.setSpeed(100);
			motorB.setSpeed(100);
			// adjust time to get a rotate
			Delay.msDelay(50);
		}
	    //set speed back to 500
		motorA.setSpeed(500);
		motorB.setSpeed(500);
		//when finish rotate stop motor
	    motorA.stop(true);
        motorB.stop(true);
	}
	
	public static void main(String []args)
	{
    	//set some variables
        float range;
        int unit = 90;
        int angle = 90;

    	color.setColorIdMode();
		
        //Task1
        Lcd.print(1, "Assignment 1");
        //Task2	
        Lcd.print(2, "Press any key to start");
        Button.LEDPattern(4);    // flash green led and
        Sound.beepSequenceUp();    // make sound when ready.
        Button.waitForAnyPress();
        //Task3
        //clear screen
        Lcd.clear(1);
        Lcd.clear(2);
        
        //Task4
        //using UltraSonicSensor to check range of obstacle
        range = uss.getRange();
        Lcd.print(4, "angle=%d ", gyro.getAngle());
        //set speed
		motorA.setSpeed(500);
		motorB.setSpeed(500);
        // run until we find an obstacle within 1/4 of a meter.
        while (range > .25)
        {
            motorA.forward();
            motorB.forward();
            //print range to screen
            Lcd.clear(3);
            Lcd.print(3, "range= %.3f", range);
            Delay.msDelay(500);
            range = uss.getRange();
        }
        //stop motor to get time for robotics response for next command
        motorA.stop(true);
        motorB.stop(true);
		
        //Task5
        //using LCD to print range 
        Lcd.clear(3);
        Lcd.print(3, "range= %.3f", range);
        // rotate 180 degree
    	rotate(180);

	    //Task 6
		//move 20 units
	    int distance = unit * 20;
		//set TachoCount to 0
		motorA.resetTachoCount();
		motorB.resetTachoCount();
		//to check distance if not move 20 units then keep running
		while(motorA.getTachoCount() <= distance && motorB.getTachoCount() <= distance){
	        Lcd.clear(5);
	        Lcd.print(5,"distance=%d", motorA.getTachoCount());
			motorA.forward();
			motorB.forward(); 
		}
		//print move distance
        Lcd.clear(5);
        Lcd.print(5,"distance=%d", motorA.getTachoCount());
        //stop motor
	    motorA.stop(true);
        motorB.stop(true);
        
		//Task 7
		//rotate 90 degree
        rotate(90);
        
		//Task 8
        //move forward until meet white color
	    while(!ColorSensor.colorName(color.getColorID()).equals("White"))
	    {
			motorA.forward();
		    motorB.forward();
		    //print color to screen
			Lcd.clear(6);
		    Lcd.print(6, "%s", ColorSensor.colorName(color.getColorID()));
	    }
		Lcd.clear(6);
	    Lcd.print(6, "%s", ColorSensor.colorName(color.getColorID()));
	    
		motorA.stop(true);
        motorB.stop(true);

	    //rotate 90 degree 
        rotate(90);
	    //Task 9
        //move backward until touch something
	    while(!touch.isTouched())
	    {
	    	motorA.backward();
	    	motorB.backward();
	    }
		motorA.stop(true);
		motorB.stop(true);
		
        // free up resources.
	    motorA.stop();
		motorB.stop();
	    uss.close();
        gyro.close();
        color.close();
        touch.close();
        
        Sound.beepSequence();    // we are done.
		
	}

}
