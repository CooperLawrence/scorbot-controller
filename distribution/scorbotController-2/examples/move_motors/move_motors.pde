
import processing.serial.*; //Import the Processing serial library
import scorbot.*; //Import the Scorbot library

ScorbotER3 scorbot; //Scorbot object

void setup() {
  size(400, 200);

  //Initialize the library on a specific serial port, at a set baud rate
  scorbot = new ScorbotER3(this, "COM1", 9600);
}

void draw() {
  //Needed to run the key events
}

//Key events
void keyReleased() {
  if (key == 'z') {
    scorbot.moveClaw(-300);
  }

  if (key == 'x') {
    scorbot.moveClaw(300);
  }

  if (key == 'q') {
    scorbot.rotateWrist(50);
  }

  if (key == 'a') {
    scorbot.rotateWrist(-50);
  }

  if (key == 'w') {
    scorbot.moveWrist(-50);
  }

  if (key == 's') {
    scorbot.moveWrist(50);
  }

  if (key == 'e') {
    scorbot.moveElbow(100);
  }

  if (key == 'd') {
    scorbot.moveElbow(-100);
  }

  if (key == 'r') {
    scorbot.moveShoulder(-100);
  }

  if (key == 'f') {
    scorbot.moveShoulder(100);
  }

  if (key == 't') {
    scorbot.rotateBase(100);
  }

  if (key == 'g') {
    scorbot.rotateBase(-100);
  }

  if (key == 'v') {
    scorbot.moveMotor(6, -100);
  }

  if (key == 'b') {
    scorbot.moveMotor(6, 100);
  }

  if (key == 'n') {
    scorbot.moveMotor(7, -100);
  }

  if (key == 'm') {
    scorbot.moveMotor(7, 100);
  }
}

