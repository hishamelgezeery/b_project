public class VScrollbar
{
  private int barWidth, barHeight; // width and height of bar. NOTE: barWidth also used as slider button height.
  private int Xpos, Ypos;          // upper-left position of bar
  private float Spos, newSpos;     // y (upper) position of slider
  private int SposMin, SposMax;    // max and min values of slider
  private int min, max;            // max and min values of the measurement
  private int loose;               // how loose/heavy
  private boolean over;            // True if hovering over the scrollbar
  private boolean locked;          // True if a mouse button is pressed while on the scrollbar
  private boolean ready;           // true if mouse button has been released so that value can be updated
  private int currentValue;        // the current value as an integer
  private color barOutlineCol;     // the color of the bar outline
  private color barFillCol;        // the color of the bar filling
  private color barHoverCol;       // the color of the bar while hovering
  private color sliderFillCol;     // the color of the slider filling
  private color sliderPressCol;    // the color of the slider while being pressed

  public VScrollbar (int X_start, int Y_start, int bar_width, int bar_height, int loosiness, int min, int max, 
  color bar_outline, color bar_background, color slider_bg, color barHover, color slider_press) {
    barWidth = bar_width;
    barHeight = bar_height;
    Xpos = X_start;
    Ypos = Y_start;
    Spos = Ypos + barHeight/2 - barWidth/2; // center it initially
    newSpos = Spos;
    SposMin = Ypos;
    SposMax = Ypos + barHeight - barWidth;
    this.min = min;
    this.max = max;
    loose = loosiness;
    if (loose < 1) loose = 1;
    barOutlineCol  = bar_outline;
    barFillCol     = bar_background;
    sliderFillCol  = slider_bg;
    barHoverCol    = barHover;
    sliderPressCol = slider_press;
  }

  void update() {
    over = over();
    if (mousePressed && over) locked = true; 
    else locked = false;

    if (locked) {
      newSpos = constrain(mouseY-barWidth/2, SposMin, SposMax);
    }
    if (abs(newSpos - Spos) > 0) {
      Spos = Spos + (newSpos-Spos)/loose;
    }
  }

  int constrain(int val, int minv, int maxv) {
    return min(max(val, minv), maxv);
  }

  boolean over() {
    if (mouseX > Xpos && mouseX < Xpos+barWidth &&
      mouseY > Ypos && mouseY < Ypos+barHeight) {
      return true;
    } 
    else {
      return false;
    }
  }

  void display() {
    stroke(barOutlineCol);
    fill(barFillCol);
    rect(Xpos, Ypos, barWidth, barHeight);
    if (over) {
      fill(barHoverCol);
    } 
    if (locked) {
      fill(sliderPressCol);
    }
    if (!over && !locked) {
      fill (sliderFillCol);
    }
    if (abs(Spos-newSpos)>0.1) fill (sliderPressCol);
    rect(Xpos, Spos, barWidth, barWidth+1);
    float currValue = Math.round((value()*(max-min))+min);
    fill(255, 255, 255);
    text("Current value:"+(int)currValue, Xpos-20, barHeight+Ypos+20);
    this.currentValue = (int)currValue;
    ready = true;
  }

  float value() {
    // Convert slider position Spos to a value between 0 and 1
    return (Spos-Ypos) / (barHeight-barWidth);
  }

  int getValue() {
    return (int)(Math.round((value()*(max-min))+min));
  }

  void setValue(float value) {
    // convert a value (0 to 1) to slider position Spos
    if (value<0) value=0;
    if (value>1) value=1;
    Spos = Ypos + ((barHeight-barWidth)*value);
    newSpos = Spos;
  }
}

