public class LineGraph {
  private int x;  //represents the 0 x-coordinate of the graph
  private int y;  //represents the 0 y-coordinate of the graph
  private int sideLength;  //represents the length of the sides of the graph -- min_length is 300px
  private String firstParameter;
  private int fPmin;  // represents the first parameter's min value
  private int fPmax;  // represents the first parameter's max value
  private String secondParameter;
  private int sPmin;  // represents the second parameter's min value
  private int sPmax;  // represents the second parameter's max value
  private ArrayList<Point> points = new ArrayList<Point>();   // points representing the graph at the current time
  private int pointCounter;  // a counter used to in the implementation of the points to be drawn on the graph
  private int counter;  // counter to be displayed on the graph
  private boolean move;  //this is a boolean which is set to true only when the graph has more values than the graph width, which now makes it a moving graph
  private int wide;  // Width of window
  private int high;  // Height of window
  private float border = 40;  // Size of border
  private float lb;  // Left border
  private float rb;  // Right border
  private float bb;  // Bottom border
  private float tb;  // Top borders
  private int actualLength;  // Length of side minus borderlength

    public LineGraph(int counter, int x, int y, int sideLength, String firstParameter, int fPmin, int fPmax, String secondParameter, int sPmin, int sPmax) {
    this.counter = counter;
    this.x = x;
    this.y = y;
    this.sideLength = sideLength;
    this.firstParameter = firstParameter;
    this.fPmin = fPmin;
    this.fPmax = fPmax;
    this.secondParameter = secondParameter;
    this.sPmin = sPmin;
    this.sPmax = sPmax;
    if (this.sideLength<300)
      this.sideLength = 300;
    wide = this.sideLength;
    high = this.sideLength; 
    lb = border;         
    rb = (int)(wide - border);  
    bb = high - border;  
    tb = border;         
    actualLength = this.sideLength - 80;
  }

  public void drawGraph() {
    xAxis(firstParameter, lb, bb, rb);
    yAxis(secondParameter, lb, bb, tb);
    pointsReady();
    plot();
    shift();
  }

  void xAxis (String axisTitle, float xl, float y, float xr) {
    pushStyle();
    stroke(250);
    strokeWeight(2);
    line(this.x+xl, this.y+y, this.x+xr, this.y+y);
    //lines 
    for (int i=1;i<=5;i++) {
      line(this.x+xl-i*((xl-xr)/5), this.y+y, this.x+xl-i*((xl-xr)/5), this.y+y+10);
      text(i*((fPmax-fPmin)/5), this.x+xl-i*((xl-xr)/5)-15, this.y+y+30);
    }
    fill(250);
    textSize(20);
    textAlign(CENTER, CENTER);
    text(axisTitle, this.x+wide-10, this.y + bb);
    popStyle();
  }

  void yAxis (String axisTitle, float x, float yb, float yt) {
    pushStyle();
    stroke(250);
    strokeWeight(2);
    line(this.x+x, this.y+yb, this.x+x, this.y+yt);
    // lines
    for (int i=1;i<=5;i++) {
      line(this.x+x, this.y+yb-i*((yb-yt)/5), this.x+x-10, this.y+yb-i*((yb-yt)/5));
      if ((i*((sPmax-sPmin)/5))/10 >=10)
        text(i*((sPmax-sPmin)/5), this.x+x-45, this.y+yb-i*((yb-yt)/5)+5);
      else
        text(i*((sPmax-sPmin)/5), this.x+x-35, this.y+yb-i*((yb-yt)/5)+5);
    }
    fill(250);
    textSize(20);
    textAlign(RIGHT, CENTER);
    text(axisTitle, this.x+lb+5, this.y+tb-20);
    popStyle();
  }

  public void plot() {
    stroke(255);
    strokeWeight(2);
    if (points.size()>2)
      for (int i=0;i<points.size();i++) {
        if (points.size()>i+1) {  
          Point first = points.get(i);
          Point second = points.get(i+1);
          line(first.x, first.y, second.x, second.y);
        }
      }
  }

  public void shift() {
    if (move)
      for (int i=0;i<points.size();i++) {
        Point temp = points.get(i);
        temp.x--;
        points.set(i, temp);
      }
  }

  public void updateCounter(int counter) {
    this.counter = counter;
    if (this.counter>100)
      this.counter%=100;
  }

  public void pointsReady() {
    if (move)
      points.remove(0);
    Point temp = new Point(this.x+lb+pointCounter, this.y+bb-2*counter);

    if (temp.x-x == actualLength+40) {
      move = true;
    }
    else {
      pointCounter++;
    }
    points.add(temp);
  }

  public class Point {
    private float x, y;

    Point() {
      this.x=-1;
      this.y=-1;
    }

    Point(float x, float y) {
      this.x = x;
      this.y = y;
    }
  }
}

