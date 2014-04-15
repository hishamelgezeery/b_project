/**
a simple gauge simulator by <a href="http://www.local-guru.net/blog">guru</a>
*/
int counter = 1;
void setup() {
  size(300,300);
  smooth();
}

void draw() {
  background(1);
  counter++;
  pushMatrix();
  translate( 100,100);
  drawGauge(counter);
  popMatrix();

}

void drawGauge( int counter ) {
  stroke(0);

  for( int i=0; i<31; i++) {
    float a = radians(210 + i * 120 / 30);
    float r1 = 100;
    float r2 = 90;
    r2 = i % 5 == 0 ? 85 : r2;
    r2 = i % 10 == 0 ? 80 : r2;

    line( r1*cos(a), r1*sin(a), r2*cos(a), r2*sin(a));
    stroke(255,255,255);  
  }
  stroke( 255,0,0 );
  println((((counter%1000)/1000)*120));
  float b = radians( 210 + ((((float)counter%1000)/1000)*120));
  fill(255,255,255);
  ellipse(0,0,10,10);
  line( -10*cos(b),-10*sin(b),100 * cos(b), 100 * sin(b));
}
