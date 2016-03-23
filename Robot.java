package org.usfirst.frc.team6213.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot { // Prep everything
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    CameraServer camera; // For Webcam
    VictorSP left; // Left Victor
    VictorSP right; // Right Victor
    RobotDrive move; // Drive train Motors
    Joystick xbox; // Xbox Controller
	CANTalon ballWheel ; // Talon to control the ball wheel motor
	Timer timer; // Timer
	public static double maxSpeed; // Used to set speed of robot
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() { // Initialize everything
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        timer = new Timer();
        left = new VictorSP(0);
        right = new VictorSP(1);
        move = new RobotDrive(left,right);
        ballWheel = new CANTalon(1);
        xbox = new Joystick(0);
        camera = CameraServer.getInstance();
        camera.setQuality(50);
        camera.startAutomaticCapture("cam0");
        maxSpeed = 0.25;
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() { // More ignore :D
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    		autoMove(left,right); // Comment This to disable autonomous
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	double Fmove = xbox.getRawAxis(3); // Right trigger, forwards speed
    	double Rmove = xbox.getRawAxis(2); // Left trigger, backwards speed
    	double Turn = xbox.getRawAxis(0); // Left stick X, turning
    	boolean bButton = xbox.getRawButton(2); // B button status
    	boolean  yButton = xbox.getRawButton(4); // Y button status
    	boolean aButton = xbox.getRawButton(1); // A button status
    	ballWheel.enable(); // Enable the CAN Talon
    	
    	if(aButton){ // Checks max speed of robot, changes it
    		if(maxSpeed == 0.25){
    			maxSpeed = 1;
    			timer.delay(0.25);
    		}
    		else{
    			maxSpeed = 0.25;
    			timer.delay(0.25);
    		}
    	}
    	
    	if(Fmove > 0){ // Moving Forwards
    		move.drive(Fmove * -maxSpeed , Turn);
    	}
    	
    	else if(Rmove > 0){ // Moving Backwards
    		move.drive(Rmove * maxSpeed, Turn);
    	}
    	
    	if(bButton){ // B button down, moves towards robot, intake
        	ballWheel.set(0.55);
    	}
    	
    	else if(yButton){ // Y button, moves away from robot, shoots
        	ballWheel.set(-1);
    	}
    	else{ // Keep ball motor still by default
    		ballWheel.set(0);
    	}
    }
 
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    public void autoMove(VictorSP leftSide, VictorSP rightSide){
    	left = leftSide;
    	right = rightSide;
    	left.set(0.25);
    	right.set(-0.25);
    }
    
}
