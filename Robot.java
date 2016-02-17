package org.usfirst.frc.team6213.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    RobotDrive move; // Drive train Motors
    Joystick xbox; // Xbox Controller
    JoystickButton bIn; // B button, rotates wheel towards robot
    JoystickButton yOut; // Y button, rotates wheel away from robot
	CANTalon ballWheel ; // Talon to control the ball wheel motor
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        move = new RobotDrive(0,1);
        ballWheel = new CANTalon(2);
        xbox = new Joystick(0);
        bIn = new JoystickButton(xbox,1);
        yOut = new JoystickButton(xbox,3);
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
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	double Fmove = xbox.getRawAxis(3);
    	double Rmove = xbox.getRawAxis(2);
    	double Turn = xbox.getRawAxis(0);
    	boolean wheelIn = bIn.get();
    	boolean  wheelOut= yOut.get();
    	
    	if(Fmove > 0){ // Moving Forwards
    		move.drive(Fmove * -1, Turn);
    	}
    	
    	else if(Rmove > 0){ // Moving Backwards
    		move.drive(Rmove, Turn);
    	}
    	
    	if(wheelIn){
    		ballWheel.set(1);
    	}
    	
    	else if(wheelOut){
    		ballWheel.set(-1);
    	}
       
    }
 
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
