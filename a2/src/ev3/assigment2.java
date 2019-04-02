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
	
	static UltraSonicSensor uss = new UltraSonicSensor(SensorPort.S4);
    static GyroSensor gyro = new GyroSensor(SensorPort.S2);
	static ColorSensor color = new ColorSensor(SensorPort.S3);
	static TouchSensor touch = new TouchSensor(SensorPort.S1);
	// create two motor objects to control the motors.
	static EV3LargeRegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);
	static EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);
	static EV3MediumRegulatedMotor servoMotor = new EV3MediumRegulatedMotor(MotorPort.C);

	
	
	public static void main(String []args)
	{
		Lcd.print(1, "Assignment 2");
        //Task2	
        Lcd.print(2, "Press any key to start");
        Button.LEDPattern(4);    // flash green led and
        Sound.beepSequenceUp();    // make sound when ready.
        Button.waitForAnyPress();
        //Task3
        //clear screen
        Lcd.clear(1);
        Lcd.clear(2);
        
        servoMotor.rotate(160);
        
        motorA.setSpeed(500);
		motorB.setSpeed(500);
		
		while(!touch.isTouched())
		{
			motorA.forward();
		    motorB.forward();
		    
		}
		motorA.stop(true);
		motorB.stop(true);
		
		motorA.backward();
	    motorB.backward();
		motorA.setSpeed(100);
		motorB.setSpeed(100);
		
		Delay.msDelay(2000);
		
		rotate(90);
		
		motorA.setSpeed(200);
		motorB.setSpeed(200);
		Delay.msDelay(2000);
		
		rotate(-90);
		
		motorA.setSpeed(200);
		motorB.setSpeed(200);
		Delay.msDelay(5000);
		
		rotate(-90);
		motorA.setSpeed(200);
		motorB.setSpeed(200);
		Delay.msDelay(2000);
		
		rotate(90);
		
		float range = uss.getRange();
		
		motorA.setSpeed(500);
		motorB.setSpeed(500);
		
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
			Delay.msDelay(5000);
		motorA.stop();
		motorB.stop();
	    uss.close();
        gyro.close();
        color.close();
        touch.close();
        
        Sound.beepSequence();  
	
		
	}
	
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
	