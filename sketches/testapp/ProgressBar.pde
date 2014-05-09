
public class ProgressBar
{
  private int x; 
  private int y;
  private int barWidth;
  private int barHeight;
  private int heightProgress;
  private float value;
  private int minValue;
  private int maxValue;
  private PFont font;
  private color backgroundColor;
  private color progressColor;
  private int sizeFont;

  public ProgressBar(float counter, int barWidth, int barHeight, int minValue, int maxValue, int heightProgress, int x, int y)
  {
    this.barWidth=barWidth;
    this.barHeight=barHeight; 
    this.minValue=minValue;
    this.maxValue=maxValue;
    this.heightProgress=heightProgress;
    this.x=x;
    this.y=y;
    this.value=counter;
    this.backgroundColor=color(255, 255, 255);
    this.progressColor=color(216, 0, 0);
    smooth();
  }

  public void setBarWidth(int barWidth)
  {
    this.barWidth=barWidth;
  }

  public void setBarHeight(int barHeight)
  {
    this.barHeight=barHeight;
  }

  public void setHeightProgress(int heightProgress)
  {
    this.heightProgress=heightProgress;
  }

  public void setX(int x)
  {
    this.x=x;
  }

  public void setY(int y)
  {
    this.y=y;
  }

  public void setBackgroundColor(color backgroundColor)
  {
    this.backgroundColor=backgroundColor;
  }


  public void setProgressColor(color progressColor)
  {
    this.progressColor=progressColor;
  }


  public void setFontSize(int fontSize)
  {
    this.sizeFont=sizeFont;
  }

  public int percentage()
  { 
    return (int)(((value/maxValue)*100)%100) ;
  }


  public void incrementValue()
  {
    if ( value <= this.maxValue )
    {
      this.value+=0.3;
    }
    else
    {
      println("progress finished.");
    }
  }

  public void drawLines() {
    for (int i=0;i<=10;i++) {
      strokeWeight(2);
      if (i%2==0) {
        if (i!=10) {
          line(x-barWidth/2-5, y-(((float)i/10)*barHeight)-2, (x-barWidth/2)-15, y-(((float)i/10)*barHeight)-2);
          text((int)(((float)i/10)*(maxValue-minValue)), (x-barWidth/2)-30, y-(((float)i/10)*barHeight)-4);
        }
        else {
          line(x-barWidth/2-5, y-(((float)i/10)*barHeight)+2, (x-barWidth/2)-15, y-(((float)i/10)*barHeight)+2);
          text((int)(((float)i/10)*(maxValue-minValue)), (x-barWidth/2)-30, y-(((float)i/10)*barHeight)+2);
        }
      }
      else {
        line(x-barWidth/2-5, y-(((float)i/10)*barHeight)-2, (x-barWidth/2)-8, y-(((float)i/10)*barHeight)-2);
      }
    }
  }

  public void draw() {
    drawLines();
    float currentValue = (value%maxValue)/maxValue;
    pushStyle();
    textSize(this.sizeFont);       
    strokeCap(SQUARE);
    stroke(this.backgroundColor);
    strokeWeight(barWidth);
    line(this.x, this.y, this.x, this.y-this.barHeight);
    stroke(this.progressColor);
    strokeWeight(barWidth);
    line(this.x, this.y, this.x, this.y-(currentValue*barHeight));
    popStyle();
  }
  int getValue() {
    return (int)(value%maxValue)/maxValue;
  }
}

