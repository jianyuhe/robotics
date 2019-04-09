 /******************************
*   Mobile Robotics			  *
*	Assignement 2			  *
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

public class assigment2 {
	// create sensors
	static UltraSonicSensor uss = new UltraSonicSensor(SensorPort.S4);
    static GyroSensor gyro = new GyroSensor(SensorPort.S2);
	static ColorSensor color = new ColorSensor(SensorPort.S3);
	static TouchSensor touch = new TouchSensor(SensorPort.S1);
	
	static EV3LargeRegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3MediumRegulatedMotor servoMotor = new EV3MediumRegulatedMotor(MotorPort.C);

	
	
	public static void main(String []args)
	{
		Lcd.print(1, "Assignment 2");
       
        Lcd.print(2, "Press any key to start");
        Button.LEDPattern(4);    // flash green led and
        Sound.beepSequenceUp();    // make sound when ready.
        Button.waitForAnyPress();
        
        //clear screen
        Lcd.clear(1);
        Lcd.clear(2);
          //rotate servomotor up
        servoMotor.rotate(160);
        
        motorA.setSpeed(500);
		motorB.setSpeed(500);
		//if touch something then stop
		while(touch.isTouched())
		{
		
		    motorA.stop(true);
			motorB.stop(true);
			//go back 
			motorA.backward();
		    motorB.backward();
			motorA.setSpeed(100);
			motorB.setSpeed(100);
			
			Delay.msDelay(1000);
					//turn left
			rotate(90);
			
			motorA.setSpeed(400);
			motorB.setSpeed(400);
			Delay.msDelay(1000);
			//turn right
			rotate(-90);
			
			motorA.setSpeed(400);
			motorB.setSpeed(400);
			Delay.msDelay(2200);
			//turn right
			rotate(-90);
			motorA.setSpeed(400);
			motorB.setSpeed(400);
			Delay.msDelay(1000);
			//turn left
			rotate(90);
		    
		}
		motorA.forward();
	    motorB.forward();
		
		
		float range = uss.getRange();
		
		motorA.setSpeed(500);
		motorB.setSpeed(500);
		//check object is nearly then put down the servo motor
		 while (range > .07)
	        {
	            motorA.forward();
	            motorB.forward();
	            //print range to screen
	            Lcd.clear(3);
	            Lcd.print(3, "range= %.3f", range);
	            Delay.msDelay(500);
	            range = uss.getRange();
	        }
		 motorA.stop(true);
		motorB.stop(true);
		 servoMotor.rotate(-160);
		
		 motorA.forward();
         motorB.forward();
		 motorA.setSpeed(500);
			motorB.setSpeed(500);
			
			color.setColorIdMode();
			//use color sensor to check if floor is white then stop set object here
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
		        servoMotor.rotate(160);
			//go back
		        motorA.backward();
			    motorB.backward();
			    motorA.setSpeed(500);
			       motorB.setSpeed(500);
			       Delay.msDelay(1000); 
			       //rotate then go back
		        motorA.forward();
			    motorB.forward();
			motorA.setSpeed(400);
		       motorB.setSpeed(200);
		       Delay.msDelay(4000);
		       
		       
		       motorA.setSpeed(500);
		       motorB.setSpeed(500);
			   //check floor is white then stop
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
		        
		motorA.stop();
		motorB.stop();
	    uss.close();
        gyro.close();
        color.close();
        touch.close();
        
        Sound.beepSequence();  
	
		
	}
	
	//rotate method
	public static void rotate(int degree)
	{
		int angle = degree;
		// set gyro angle to 0
    	gyro.reset();
    	
    	if(angle > 0 )
    	{
    		
	    while(gyro.getAngle()<=angle)
		{
	    	Lcd.clear(4);
	    	Lcd.print(4, "angle=%d ", gyro.getAngle());
		    motorA.backward();
			motorB.forward();
			// set slow motor speed to get satisfy rotate
			motorA.setSpeed(100);
			motorB.setSpeed(100);
			// adjust time to get a rotate
			Delay.msDelay(50);
		}
    	}
    	
    	else
    	{
    		
    		while(gyro.getAngle()>=angle)
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
    	}
	    
		//when finish rotate stop motor
	    motorA.stop(true);
        motorB.stop(true);
        
        motorA.forward();
	    motorB.forward();
	}
	
}
	