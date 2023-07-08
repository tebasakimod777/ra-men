#include <Wire.h>

int analog_pin1 = 0;
int analog_pin2 = 1;
int analog_pin3 = 2;
int analog_pin4 = 3;

byte sendByte[2];

void setup() {
//  pinMode(MISO, OUTPUT);
  Serial.begin(9600);
  Wire.begin(0x8);
  Wire.onReceive(recPress);
  Wire.onRequest(sendPress);
}

void recPress() {
  while(Wire.available()) {
    char c = Wire.read();
    int ain1 = analogRead(analog_pin1);
    int ain2 = analogRead(analog_pin2);
    int ain3 = analogRead(analog_pin3);
    int ain4 = analogRead(analog_pin4);
    int sum = ain1 + ain2 + ain3 + ain4;
    sendByte[1] = (uint8_t)(sum >> 8);
    sendByte[0] = (uint8_t)sum;
  }
}

void sendPress() {
  Serial.print("send: ");
  Serial.print(sendByte[0]);
  Serial.print(" ");
  Serial.println(sendByte[1]);
  Wire.write(sendByte, 2);
}

void loop() {
  int ain1 = analogRead(analog_pin1);
  int ain2 = analogRead(analog_pin2);
  int ain3 = analogRead(analog_pin3);
  int ain4 = analogRead(analog_pin4);

  Serial.println(ain1);
  Serial.println(ain2);
  Serial.println(ain3);
  Serial.println(ain4);
  Serial.println(ain1 + ain2 + ain3 + ain4);

  delay(1000);
}
