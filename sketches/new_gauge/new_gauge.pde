/**
 a simple gauge simulator by <a href="http://www.local-guru.net/blog">guru</a>
 */
int counter = 1;
void setup() {
  size(300, 300);
  smooth();
}

void draw() {
  background(255,255,255);
  counter++;
  pushMatrix();
  fill(255, 255, 255);  
  stroke( 205, 201, 201);
  strokeWeight(2);
  ellipse(150, 150, 210, 210);
  popMatrix();
  pushMatrix();  
  translate( 150, 150);
  drawGauge(counter,0,100);
  popMatrix();
}

void drawGauge( int counter, int min, int max) {
  fill(0, 0,0);
   text("km/h", -10, 30);
  pushMatrix();
  stroke(0,0,0);

  for ( int i=0; i<=10; i++) {
    float a = radians(130 + 28*i);
    float r1 = 100;
    float r2 = 90;
//    r2 = i % 5 == 0 ? 85 : r2;
//    r2 = i % 10 == 0 ? 80 : r2;
    line( r1*cos(a), r1*sin(a), r2*cos(a), r2*sin(a));
    if(i<4)
    text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)+2, r2*sin(a)+4);
    else if(i<7)
    text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)-8, r2*sin(a)+14);
    else if(i<10)
    text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)-20, r2*sin(a)+4);
    else
    text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)-25, r2*sin(a)+2);
  }
  popMatrix();
  
  float b = radians( 130 + ((((float)counter%1000)/1000)*280));
  pushMatrix();
  fill(238, 233, 233);  
  stroke( 205, 201, 201);
  ellipse(0, 0, 10, 10);
  popMatrix();
  stroke(255,0,0);
  line( -10*cos(b), -10*sin(b), 100 * cos(b), 100 * sin(b));
}

