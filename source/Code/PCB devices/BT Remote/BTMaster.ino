#include <SoftwareSerial.h>
#define RxPin 10
#define TxPin 11

SoftwareSerial BTSerial(RxPin, TxPin);

int button1 = 2;
int button2 = 5;
int button3 = 3;
int button4 = 4;

int button1State;
int button2State;
int button3State;
int button4State;

void setup() {

  BTSerial.begin(38400);
  pinMode(button1, INPUT);
  pinMode(button2, INPUT);
  pinMode(button3, INPUT);
  pinMode(button4, INPUT);
  digitalWrite(button1, HIGH);
  digitalWrite(button2, HIGH);
  digitalWrite(button3, HIGH);
  digitalWrite(button4, HIGH);

}

void loop() {
  button1State = digitalRead(button1);
  button2State = digitalRead(button2);
  button3State = digitalRead(button3);
  button4State = digitalRead(button4);

  if (button1State) {
    BTSerial.write('a');
    delay(10);
  }

  if (button2State) {
    BTSerial.write('b');
    delay(10);
  }
  if (button3State) {
    BTSerial.write('c');
    delay(10);
  }
  if (button4State) {
    BTSerial.write('d');
    delay(10);
  }
}
