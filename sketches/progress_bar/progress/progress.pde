/* OpenProcessing Tweak of *@*http://www.openprocessing.org/sketch/58777*@* */
/* !do not delete the line above, required for linking your tweak if you upload again */
/*
  AUTOR: RICARDO JOSE MONTES RODRIGUEZ
  DESCRIPCION: Barra de progreso para mostrar el porcentaje de carga cierto proceso
  CORREO: ricardomontesrodriguez@gmail.com
*/

TProgressBar  progress;
void setup()
{
 size(400,400);
 this.progress=new TProgressBar(100,300,0,100,50,150,350);
  
}
 
 
void draw()
{
  
  background(137,137,137);
  this.progress.incrementValue();
  this.progress.draw();
  
}
  
