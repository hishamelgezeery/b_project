LineGraph graph;
void setup()
{
 size(640,640);
 background(0,0,0);
 this.graph = new LineGraph(0, 0, 500, "Speed", 0, 50, "Time", 0, 100);
  
} 
void draw()
{
  this.graph.incrementCounter();
  this.graph.drawGraph();
}


