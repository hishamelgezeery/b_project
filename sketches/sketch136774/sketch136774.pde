/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/136774*@* */
/* !do not delete the line above, required for linking your tweak if you upload again */
ArrayList<Points> poop = new ArrayList();
int counter;
void setup() {
  size(500, 200);
}

void draw() {
  background(-1);
  noFill();
  stroke(0);
  beginShape();
  for (int i=0;i<poop.size();i++) {
    Points P = (Points)poop.get(i);
    vertex(P.x, P.y);
    if (P.x<0)poop.remove(i);
    P.x--;
  }
  endShape();
  keyPressed();
  counter++;
}

void keyPressed() {
  float t = random(0, height-20);
  Points P = new Points(width, counter);
  poop.add(P);
}

class Points {
  float x, y;
  Points(float x, float y) {
    this.x = x;
    this.y = y;
  }
}
