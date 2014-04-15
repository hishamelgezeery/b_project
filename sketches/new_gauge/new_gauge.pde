/**
 a simple gauge simulator by <a href="http://www.local-guru.net/blog">guru</a>
 */
Gauge gauge; 
void setup()
{
 size(600,600);
 this.gauge=new Gauge(100,0,1000,250,250);
  
} 
void draw()
{
  background(137,137,137);
  this.gauge.incrementValue();
  this.gauge.draw();
}



