public class Gauge {
  private int x;
  private int y;
  private int radius;
  private float value;
  private int min;
  private int max;
  private final float ratio;

  public Gauge(float counter, int radius, int min, int max, int x, int y) {
    this. min = min;
    this.max = max;
    this.radius = radius;
    this.x = x;
    this.y = y;
    this.value = counter;
    smooth();
    ratio = (float)radius/210;
  }

  void drawGauge() {

    pushStyle();
    fill(0, 0, 0);
    text("km/h", x-10, y+30);
    stroke(0, 0, 0);

    for ( int i=0; i<=10; i++) {
      float a = radians(130 + 28*i);
      float r1 = 100*ratio;
      float r2 = 90*ratio;
      line( x+ r1*cos(a), y+r1*sin(a), x+r2*cos(a), y+r2*sin(a));
      if (i<4)
        text( (int)(min +(((float)max-min)/10)*i), x+r2*cos(a)+2, y+r2*sin(a)+6);
      else if (i<7)
        text( (int)(min +(((float)max-min)/10)*i), x+r2*cos(a)-10, y+r2*sin(a)+18);
      else if (i<10)
        text( (int)(min +(((float)max-min)/10)*i), x+r2*cos(a)-24, y+r2*sin(a)+8);
      else
        text( (int)(min +(((float)max-min)/10)*i), x+r2*cos(a)-30, y+r2*sin(a)-2);
    }
    popStyle();
    float b = radians( 130 + ((((float)value%max)/max)*280));
    pushStyle();
    fill(238, 233, 233);  
    stroke( 205, 201, 201);
    ellipse(0, 0, 10, 10); 
    stroke(255, 0, 0);
    line( x-10*cos(b), y-10*sin(b), x+100 * cos(b)*ratio, y+100 * sin(b)*ratio);
    popStyle();
  }

  void draw() {
    pushStyle();
    fill(255, 255, 255);  
    stroke( 205, 201, 201);
    strokeWeight(2);
    ellipse(x, y, radius, radius); 
    drawGauge();
    popStyle();
  }

 public void update(int counter)
  {
    value = counter;
    value %= 100;
  }
}

