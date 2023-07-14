#include <SoftwareSerial.h>
#include<Servo.h>
#include<EEPROM.h>
#define RxPin 10
#define TxPin 11

SoftwareSerial blueToothSerial(RxPin, TxPin);
Servo myServo;

int serialState = 0;
int servoAngle;
int extraAngle = 30;
uint8_t servoAddress;
uint8_t redLEDState;
uint8_t greenLEDState;
int index = 0;
String msg = "\n";

int RedLED = 6;
int GreenLED = 7;

bool RedLEDState = false;
bool GreenLEDState = false;

int GetStringLength(String S)
{
  if (S == "") return 0;
  S = S + "\n";
  int theSize = 0;
  while (S[theSize] != '\n')
  {
    theSize ++;
  }
  return theSize;
}

void SendToPi(String msg)
{
  if (msg == "")
  {
    index = 0;
    blueToothSerial.write('\n');
    return;
  }
  while (true)
  {
    blueToothSerial.write(msg[index]);
    index ++;
    if (index > GetStringLength( msg))
    {
      index = 0;
      blueToothSerial.write('\n');
      break;
    }
  }
}

void WorkingWithLEDSetup()
{
   RedLEDState = EEPROM.read(redLEDState);
   GreenLEDState = EEPROM.read(greenLEDState);
  if (RedLEDState==true)
  {
         digitalWrite(RedLED, HIGH);
  }
  else if (RedLEDState==false)
  {
         digitalWrite(RedLED, LOW);

   
  }

  if (GreenLEDState==true)
  {
        digitalWrite(GreenLED, HIGH);
    
  }
else if (GreenLEDState==0)
  {
        digitalWrite(GreenLED, LOW);
    
  }

  
}

void setup()
{
  Serial.begin(38400);
  blueToothSerial.begin(38400);
  //blueToothSerial.write('x');
  myServo.attach(5);
  servoAngle = EEPROM.read(servoAddress);
  Serial.println(EEPROM.read(servoAddress));
  myServo.write(servoAngle);
  pinMode(RedLED, OUTPUT);
  pinMode(GreenLED, OUTPUT);
WorkingWithLEDSetup();
}

void loop() {
  while (blueToothSerial.available())
  {
    // Checks whether data is comming from the serial port
    serialState = blueToothSerial.read(); // Reads the data from the serial port

    if (serialState == 'a') //holding button to open the door
    {
      if (servoAngle > 90)
      {
        break;
      }
      else 
      {
        servoAngle += 1;
        myServo.write(servoAngle);              // tell servo to go to position in variable 'pos'
        EEPROM.update(servoAddress, servoAngle);
        delay(10);
      }
    }

    if (serialState == 'b')  //holding button to close the door
    {
      if (servoAngle < 2)
      { 
        break;
      }
      else 
      {
        servoAngle -= 1;
        myServo.write(servoAngle);              // tell servo to go to position in variable 'pos'
        EEPROM.update(servoAddress, servoAngle);
        delay(10);
      }
    }

    if (serialState == 'c')  //close the door
    {
      for (int i = servoAngle; i > 2; i-- )
      {
        myServo.write(i);
        EEPROM.update(servoAddress, i);
        delay(10);
      }
      servoAngle = 2;
      msg = "Fence is closed";
      SendToPi(msg);
      msg = "\n";
      return;
    }

    if (serialState == 'd')  //open the door
    {
      for (int i = servoAngle; i < 90; i++)
      {
        myServo.write(i);
        EEPROM.update(servoAddress, i);
        delay(10);
      }
      servoAngle = 90;
      msg = "Fence is opened";
      SendToPi(msg);
      msg = "\n";
      return;
    }

    if (serialState == 'e')  //up 15%
    {
      int oldAngle = servoAngle;
      for (int i = oldAngle; i <= oldAngle + extraAngle; i++)
      {
        if (servoAngle >= 90)
        {
          msg = "Fence is already opened";
          SendToPi(msg);
          msg = "\n";
          return;
        }
        servoAngle += 1;
        myServo.write(servoAngle);              // tell servo to go to position in variable 'pos'
        EEPROM.update(servoAddress, i);
        Serial.println(servoAngle);
        delay(10);
      }
      msg = "Fence is up 15 degree";
      SendToPi(msg);
      msg = "\n";
      return;
    }

    if (serialState == 'f')  //down 15%
    {
      int oldAngle = servoAngle;
      for (int i = oldAngle; i >= oldAngle - extraAngle; i--)
      {
        if (servoAngle <= 2 )
        {
          msg = "Fence is already closed";
          SendToPi(msg);
          msg = "\n";
          return;
        }
        servoAngle -= 1;
        myServo.write(servoAngle);              // tell servo to go to position in variable 'pos'
        EEPROM.update(servoAddress, i);
        Serial.println(servoAngle);
        delay(10);
      }
      msg = "Fence is down 15 degree";
      SendToPi(msg);
      msg = "\n";
      return;
    }


    if (serialState == 'g')
    {
      if (GreenLEDState == false) // currently off
      {
        digitalWrite(GreenLED, HIGH);
        GreenLEDState = true;
       EEPROM.update(greenLEDState, GreenLEDState);

        msg = "Green LED ON";
      }
      else // currently on
      {
        digitalWrite(GreenLED, LOW);
        EEPROM.update(greenLEDState, 0);
        GreenLEDState = false;
       EEPROM.update(greenLEDState, GreenLEDState);

        msg = "Green LED OFF";
      }
      SendToPi(msg);
      msg = "\n";
      return;
    }
    if (serialState == 'h')
    {
      if (RedLEDState == false) // currently off
      {
        digitalWrite(RedLED, HIGH);
        RedLEDState = true;
        EEPROM.update(redLEDState, RedLEDState);

        msg = "Red LED ON";
      }
      else // currently on
      {
        digitalWrite(RedLED, LOW);
        EEPROM.update(redLEDState, 0);
        RedLEDState = false;
        EEPROM.update(redLEDState, RedLEDState);

        msg = "Red LED OFF";
      }
      SendToPi(msg);
      msg = "\n";
      return;
    }

    else
    {
      msg = "Wrong message";
      SendToPi(msg);
      msg = "\n";
      return;
    }
    serialState = 0;
  }
}

