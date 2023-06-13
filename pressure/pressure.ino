int analog_pin = 0;

void setup() {
  Serial.begin(9600);
}

void loop() {
  int ain = analogRead(analog_pin);
  Serial.println(ain);
  delay(1000);
}
