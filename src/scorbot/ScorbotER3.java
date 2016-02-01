/**
 * ##library.name##
 * ##library.sentence##
 * ##library.url##
 *
 * Copyright ##copyright## ##author##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      ##author##
 * @modified    ##date##
 * @version     ##library.prettyVersion## (##library.version##)
 */

package scorbot; //Package name

//Required processing libraries
import processing.core.*;
import processing.serial.*;

public class ScorbotER3 {
	/**
	 * Parent PApplet.
	 */
	PApplet myParent;

	/**
	 * Serial object to communicate with the Scorbot controller.
	 */
	Serial scorbotPort; // Serial object used to communicate

	/**
	 * Location of motors 1 to 8, ignoring array index 0.
	 */
	public int[] motorLocation = new int[9];

	/**
	 * Description.
	 */
	public int[][] homePosition = new int[9][9];

	/**
	 * Initializes the library.
	 * 
	 * @param _myParent
	 *            Parent PApplet.
	 * @param _portName
	 *            Name of the serial port connected to the Scorbot control box.
	 * @param _baudRate
	 *            Serial port baud rate.
	 */
	public ScorbotER3(PApplet _myParent, String _portName, int _baudRate) {
		myParent = _myParent;

		// Attempt to create the serial interface
		if (Serial.list().length > 0) {
			scorbotPort = new Serial(myParent, _portName, _baudRate); // rate
		} else {
			System.out.println("Unable to open the selected serial interface! It probably doesn't exist.");
		}

		// Display some output on the controller when initializing the class
		for (int i = 1; i <= 8; i++) {
			scorbotPort.write(Integer.toString(i));
			delay(10);
			scorbotPort.write("S");
			delay(10);
			scorbotPort.write("\r\n");
			delay(100);
		}

		for (int i = 1; i <= 8; i++) {
			scorbotPort.write(Integer.toString(i));
			delay(10);
			scorbotPort.write("R");
			delay(10);
			scorbotPort.write("\r\n");
			delay(100);
		}
	}

	/**
	 * Delays the main thread.
	 * 
	 * @param _milliseconds
	 *            Time in milliseconds.
	 */
	private void delay(int _milliseconds) {
		try {
			Thread.sleep(_milliseconds);
		} catch (InterruptedException ie) {
		}
	}

	/**
	 * Opens and closes the claw.
	 * 
	 * @param _encoderPulses
	 *            Amount of rotation in digital encoder pulses, from -8000 to
	 *            8000.
	 */
	public void moveClaw(int _encoderPulses) {
		if (_encoderPulses >= -8000 && _encoderPulses <= 8000) {
			moveMotor(8, _encoderPulses);
			delay(10);
		}
	}

	/**
	 * Rotates the wrist.
	 * 
	 * @param _encoderPulses
	 *            Amount of rotation in digital encoder pulses, from -8000 to
	 *            8000.
	 */
	public void rotateWrist(int _encoderPulses) {
		if (_encoderPulses >= -8000 && _encoderPulses <= 8000) {
			moveMotor(4, _encoderPulses);
			delay(10);
			moveMotor(5, _encoderPulses);
			delay(10);
		}
	}

	/**
	 * Moves the wrist up and down.
	 * 
	 * @param _encoderPulses
	 *            Amount of rotation in digital encoder pulses, from -8000 to
	 *            8000.
	 */
	public void moveWrist(int _encoderPulses) {
		if (_encoderPulses >= -8000 && _encoderPulses <= 8000) {
			moveMotor(4, -_encoderPulses);
			delay(10);
			moveMotor(5, _encoderPulses);
			delay(10);
		}
	}

	/**
	 * Moves the elbow up and down.
	 * 
	 * @param _encoderPulses
	 *            Amount of rotation in digital encoder pulses, from -8000 to
	 *            8000.
	 */
	public void moveElbow(int _encoderPulses) {
		if (_encoderPulses >= -8000 && _encoderPulses <= 8000) {
			moveMotor(3, _encoderPulses);
			delay(10);
		}
	}

	/**
	 * Moves the shoulder up and down.
	 * 
	 * @param _encoderPulses
	 *            Amount of rotation in digital encoder pulses, from -8000 to
	 *            8000.
	 */
	public void moveShoulder(int _encoderPulses) {
		if (_encoderPulses >= -8000 && _encoderPulses <= 8000) {
			moveMotor(2, _encoderPulses);
			delay(10);
		}
	}

	/**
	 * Rotates the base.
	 * 
	 * @param _encoderPulses
	 *            Amount of rotation in digital encoder pulses, from -8000 to
	 *            8000.
	 */
	public void rotateBase(int _encoderPulses) {
		if (_encoderPulses >= -8000 && _encoderPulses <= 8000) {
			moveMotor(1, _encoderPulses);
			delay(10);
		}
	}

	/**
	 * Moves a specific motor (shouldn't be used to move a joint, as some rely
	 * on multiple motors).
	 * 
	 * @param _motorNumber
	 *            Motor number, from 1 to 8.
	 * @param _encoderPulses
	 *            Amount of rotation in digital encoder pulses, from -8000 to
	 *            8000.
	 */
	public void moveMotor(int _motorNumber, int _encoderPulses) {
		if (_motorNumber >= 1 && _motorNumber <= 8) {
			if (_encoderPulses >= -8000 && _encoderPulses <= 8000) {
				// Old command
				scorbotPort.write(Integer.toString(_motorNumber));
				delay(10);
				scorbotPort.write("M");
				delay(10);
				for (int i = 0; i < Integer.toString(_encoderPulses).length(); i++) {
					scorbotPort.write(Integer.toString(_encoderPulses).charAt(i));
					delay(10);
				}
				delay(10);
				scorbotPort.write("\r");
				delay(10);
				scorbotPort.write("\r\n");
				delay(10);

				// Update the motor's position
				motorLocation[_motorNumber] += _encoderPulses;
			}
		}
	}

	/**
	 * Stops a specific motor.
	 * 
	 * @param _motorNumber
	 *            Motor number, from 1 to 8.
	 */
	public void stopMotor(int _motorNumber) {
		if (_motorNumber >= 1 && _motorNumber <= 8) {
			scorbotPort.write(Integer.toString(_motorNumber));
			delay(10);
			scorbotPort.write("P");
			delay(10);
			scorbotPort.write("\r\n");
			delay(10);
		}
	}

	/**
	 * Sets a specific motor's speed.
	 * 
	 * @param _motorNumber
	 *            Motor number, from 1 to 8.
	 * @param _motorSpeed
	 *            Motor speed, from 1 (slowest) to 9, or 0 (fastest).
	 */
	public void motorSpeed(int _motorNumber, int _motorSpeed) {
		if (_motorNumber >= 1 && _motorNumber <= 8) {
			if (_motorSpeed >= 0 && _motorSpeed <= 9) {
				scorbotPort.write(Integer.toString(_motorNumber));
				delay(10);
				scorbotPort.write("V");
				delay(10);
				scorbotPort.write(Integer.toString(_motorSpeed));
				delay(10);
				scorbotPort.write("\r\n");
				delay(10);
			}
		}
	}

	/**
	 * Enables a specific output.
	 * 
	 * @param _outputNumber
	 *            Output Number, from 1 to 8.
	 */
	public void setOutput(int _outputNumber) {
		if (_outputNumber >= 1 && _outputNumber <= 8) {
			scorbotPort.write(Integer.toString(_outputNumber));
			delay(10);
			scorbotPort.write("S");
			delay(10);
			scorbotPort.write("\r\n");
			delay(10);
		}
	}

	/**
	 * Disables a specific output.
	 * 
	 * @param _outputNumber
	 *            Output Number, from 1 to 8.
	 */
	public void resetOutput(int _outputNumber) {
		if (_outputNumber >= 1 && _outputNumber <= 8) {
			scorbotPort.write(Integer.toString(_outputNumber));
			delay(10);
			scorbotPort.write("R");
			delay(10);
			scorbotPort.write("\r\n");
			delay(10);
		}
	}

	/**
	 * Emergency brake all motors (requires hitting the reset button on the
	 * control box).
	 */
	public void emergencyBrake() {
		scorbotPort.write("B");
		delay(10);
		scorbotPort.write("\r\n");
		delay(10);
	}
}
