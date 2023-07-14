#include <SoftwareSerial.h>
#include<EEPROM.h>
#define RxPin 10
#define TxPin 11

SoftwareSerial blueToothSerial(RxPin, TxPin);

int serialState = 0;

uint8_t servoAddress;
int index = 0;
String msg = "\n";

int RedLED = 8;
int GreenLED = 9;

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

void setup()
{
  Serial.begin(38400);
  blueToothSerial.begin(38400);
  pinMode(RedLED, OUTPUT);
  pinMode(GreenLED, OUTPUT);

}

void loop() {
  while (blueToothSerial.available())
  {
    // Checks whether data is comming from the serial port
    serialState = blueToothSerial.read(); // Reads the data from the serial port

    if (serialState == 'z')
    {
      if (GreenLEDState == false) // currently off
      {
        digitalWrite(GreenLED, HIGH);
        GreenLEDState = true;
        msg = "Green LED ON";
      }
      else // currently on
      {
        digitalWrite(GreenLED, LOW);
        GreenLEDState = false;
        msg = "Green LED OFF";
      }
      SendToPi(msg);
      return;
    }
    if (serialState == 'y')
    {
      if (RedLEDState == false) // currently off
      {
        digitalWrite(RedLED, HIGH);
        RedLEDState = true;
        msg = "Red LED ON";
      }
      else // currently on
      {
        digitalWrite(RedLED, LOW);
        RedLEDState = false;
        msg = "Red LED OFF";
      }
      SendToPi(msg);
      return;
    }

    else
    {
      msg = "Wrong message";
      SendToPi(msg);
      return;
    }
    serialState = 0;
  }
}

