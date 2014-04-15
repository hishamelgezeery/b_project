
public class Gauge {
  private int x;
  private int y;
  private int radius;
  private float value;
  private int min;
  private int max;
  private final float ratio;

  public Gauge(int radius, int min, int max, int x, int y) {
    this. min = min;
    this.max = max;
    this.radius = radius;
    this.x = x;
    this.y = y;
    this.value = min;
    smooth();
    ratio = (float)radius/210;
  }

  void drawGauge() {

    pushStyle();
    fill(0, 0, 0);
    text("km/h", -10, 30);
    stroke(0, 0, 0);

    for ( int i=0; i<=10; i++) {
      float a = radians(130 + 28*i);
      float r1 = 100*ratio;
      float r2 = 90*ratio;
      line( r1*cos(a), r1*sin(a), r2*cos(a), r2*sin(a));
      if (i<4)
        text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)+2, r2*sin(a)+4);
      else if (i<7)
        text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)-8, r2*sin(a)+14);
      else if (i<10)
        text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)-20, r2*sin(a)+4);
      else
        text( (int)(min +(((float)max-min)/10)*i), r2*cos(a)-25, r2*sin(a)+2);
    }
    popStyle();
    float b = radians( 130 + ((((float)value%max)/max)*280));
    pushStyle();
    fill(238, 233, 233);  
    stroke( 205, 201, 201);
    ellipse(0, 0, 10, 10); 
    stroke(255, 0, 0);
    line( -10*cos(b), -10*sin(b), 100 * cos(b)*ratio, 100 * sin(b)*ratio);
    popStyle();
  }
  
  void draw() {
    background(255, 255, 255);
    pushStyle();
    translate(x, y);
    fill(255, 255, 255);  
    stroke( 205, 201, 201);
    strokeWeight(2);
    ellipse(0, 0, radius, radius); 
    drawGauge();
    popStyle();
  }

public void incrementValue() {
    if ( value <= this.max ) {
      this.value+=0.3;
    }
    else {
      println("progress finished.");
    }
  }
  
}
