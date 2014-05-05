
public class LineGraph {
  private int x; //represents the 0 x-coordinate of the graph
  private int y; //represents the 0 y-coordinate of the graph
  private int sideLength; //represents the length of the sides of the graph -- min_length is 300px
  String firstParameter;
  int fPmin; // represents the first parameter's min value
  int fPmax;
  String secondParameter;
  int sPmin;
  int sPmax;
  ArrayList xValues = new ArrayList();
  ArrayList yValues = new ArrayList();
  Point[] points;
  int pointCounter;
  int counter;
  boolean move = false;
  int wide;             // Width of window
  int high;             // Height of window
  float border = 40;    // Size of border
  float lb;             // Left border
  float rb;             // Right border
  float bb;             // Bottom border
  float tb;             // Top borders


  public LineGraph(int x, int y, int sideLength, String firstParameter, int fPmin, int fPmax, String secondParameter, int sPmin, int sPmax) {
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
    rb = wide - border;  
    bb = high - border;  
    tb = border;         
    points = new Point[sideLength];
  }
  public void drawGraph() {
    xAxis(firstParameter, lb, bb, rb);
    yAxis(secondParameter, lb, bb, tb);
    pointsReady();
    plot();
  }
  public void frame () {
    noStroke();
    pushStyle();
    fill(50);
    rect(0, 0, width, border);    // Top mask
    rect(0, bb, width, border);   // Bottom mask
    rect(0, 0, border, height);   // Left mask
    rect(rb, 0, border, height);  // Right mask
    popStyle();
  }
  void xAxis (String axisTitle, float xl, float y, float xr) {
    pushStyle();
    stroke(250);
    //strokeWeight(2);
    line(this.x+xl, this.y+y, this.x+xr, this.y+y);
    //lines 
    for (int i=1;i<=5;i++) {
      line(this.x+xl-i*((xl-xr)/5), this.y+y, this.x+xl-i*((xl-xr)/5), this.y+y+10);
      text(i*((fPmax-fPmin)/5), this.x+xl-i*((xl-xr)/5), this.y+y+25);
    }
    //fill(250);
    textSize(10);
    textAlign(CENTER, CENTER);
    text(axisTitle, this.x+wide-10, this.y + bb);
    popStyle();
  }
  public void plot() {
    stroke(255);
    for (int i=0;i<sideLength-1;i++) {
        Point first = new Point();
        Point second = new Point();
      if (points[i]!=null)
        first = (Point)points[i];
      if (points[i+1]!=null)
        second = points[i+1];
      if (first.x!=-1 && second.x!=-1) {
        line(first.x, first.y, second.x, second.y);
      }
    }
  }
  void yAxis (String axisTitle, float x, float yb, float yt) {
    pushStyle();
    stroke(250);
    //strokeWeight(2);
    line(this.x+x, this.y+yb, this.x+x, this.y+yt);
    // lines
    for (int i=1;i<=5;i++) {
      line(this.x+x, this.y+yb-i*((yb-yt)/5), this.x+x-10, this.y+yb-i*((yb-yt)/5));
      text(i*((sPmax-sPmin)/5), this.x+x-25, this.y+yb-i*((yb-yt)/5));
    }
    fill(250);
    textAlign(RIGHT, CENTER);
    text(axisTitle, this.x+lb+5, this.y+tb-15);
    popStyle();
  }
  public void incrementCounter() {
    counter++;
    if (counter>100)
      counter%=100;
  }
  public void pointsReady() {
    Point temp = new Point(x+lb+pointCounter, this.y+bb-2*counter);
    
    if(temp.x == this.x+rb){
      move = true;
      temp.x--;
    }
    else{
      pointCounter++;
    }
    if(move){
    for(int i = 0;i<sideLength-1;i++){
        points[i].x--;
        if(points[i].x<x+lb)
          points[i]=new Point();
      
    }
    }
    for (int i=sideLength-1;i>1;i--) {
      points[i-2] = points[i-1];
      points[i-1] = points[i];
//      if(points[i-2]!=null)
//      points[i-2].x--;
//      if(points[i-1]!=null)
//      points[i-1].x--;
    }
    boolean empty = false;
    for(int i =0;i<sideLength;i++){
      if (points[i]==null){
        empty = true;
        points[i] = temp;
      }
    }
    if(!empty)
    points[sideLength-1] = temp;
  }

  public class Point {
    private float x, y;
    Point(){
      this.x=-1;
      this.y=-1;
    }
    Point(float x, float y) {
      this.x = x;
      this.y = y;
    }
  }
}



