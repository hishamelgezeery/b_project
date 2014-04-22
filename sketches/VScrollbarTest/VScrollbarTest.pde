
VScrollbar vScrollbar;

void setup()
{
  size  (400,500);

  // Scrollbar/slider colors
  color scrEdgdeCol = color(255,255,255);
  color scrBgCol    = color(225,225,225);
  color sliderColor = color(245,0,0);
  color scrHoverCol = color(225,0,0);
  color scrPressCol = color(195,0,0);
  
  vScrollbar = new VScrollbar(50, 20,  40, 300, 4, 0, 100, scrEdgdeCol, scrBgCol, sliderColor, scrHoverCol, scrPressCol);
  
  vScrollbar.setValue(0.5);
  
}

void draw()
{
  background(0);

  vScrollbar.update();
  vScrollbar.display();

}
