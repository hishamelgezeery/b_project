int bars = 8;

// Scrollbars and buttons
VScrollbar vScrollbar;

void setup()
{
  size  (400,500);

  // Scrollbar/slider colors
  color scrEdgdeCol = color(0,0,0);
  color scrBgCol    = color(100,100,100);
  color sliderColor = color(0,150,200);
  color scrHoverCol = color(100,200,200);
  color scrPressCol = color(100,255,255);
  

  // The scrollbars (or sliders) and buttons array initialization
  vScrollbar = new VScrollbar(50, 20,  16, 200, 4, scrEdgdeCol, scrBgCol, sliderColor, scrHoverCol, scrPressCol);
  
  

  // Set some initial slider values for fun
  vScrollbar.setValue(1-0.66*cos((float)(i+1)/(bars*4)*2*PI));
  
}

void draw()
{
  background(192,192,192);
  fill(0,0,0);
  
  text("Sliders and buttons test 0.12",10,10);



    // Scrollbars/sliders and buttons updates and displaying
    VScrollbar.update();
    VScrollbar.display();

}
