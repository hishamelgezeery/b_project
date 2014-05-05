LineGraph graph;
void setup()
{
 size(640,640);
 background(0,0,0);
 this.graph = new LineGraph(0, 200, 400, "Time", 0, 50, "Counter", 0, 200);
  
} 
void draw()
{
  background(0,0,0);
  this.graph.incrementCounter();
  this.graph.drawGraph();
}


