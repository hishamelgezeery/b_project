package processing.test.working_now;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import android.bluetooth.BluetoothAdapter; 
import android.bluetooth.BluetoothDevice; 
import android.bluetooth.BluetoothSocket; 
import android.content.BroadcastReceiver; 
import android.content.Context; 
import android.content.Intent; 
import android.content.IntentFilter; 
import java.util.ArrayList; 
import java.util.UUID; 
import java.util.Scanner; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.lang.reflect.Method; 
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
import java.text.DecimalFormat; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class working_now extends PApplet {







  










private static final int REQUEST_ENABLE_BT = 3;
ArrayList devices;
ArrayList<String> data = new ArrayList<String>();
BluetoothAdapter adapter;
BluetoothDevice device;
BluetoothSocket socket;
InputStream ins;
Scanner sc;
OutputStream ons;
boolean registered = false;
PFont f1;
PFont f2;
int status;
int count = 0;
int rows_to_write = 40;
String error;
char value;
char[] buffer = new char[1024];
int bytes;
int num_lines = 0;
int counter = 0;
//// - fillbar variables
int RectX = 450; //constant x position for progress bar
int RectY = 300; //constant y position for progress bar
int iWidth = 50; //max width for progress bar
int iHeight = 500; //height of progress bar
float initiallevel = 1; //initial value of integer level
float level = initiallevel; //integer to record progress
int Max = 1000; //max value that level can take
float initialn = 2.55f; //initial colour value
float n = initialn; //changing colour value
float pos; //width of progress bar
String s; //text to display the progress onscreen
//// - end fillbar variables
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//BroadcastReceiver receptor = new BroadcastReceiver()
//{
//    public void onReceive(Context context, Intent intent)
//    {
//        println("onReceive");
//        String action = intent.getAction();
// 
//        if (BluetoothDevice.ACTION_FOUND.equals(action))
//        {
//            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//            println(device.getName() + " " + device.getAddress());
//            devices.add(device);
//        }
//        else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
//        {
//          status = 0;
//          println("searching...");
//        }
//        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
//        {
//          status = 1;
//          println("search ended.");
//        }
// 
//    }
//};
// 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void setup() {
  //size(320,480);
  frameRate(25);
  f1 = createFont("Arial",20,true);
  f2 = createFont("Arial",15,true);
  stroke(255);
  
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void draw() {
  switch(status)
  {
//    case -1:
//      welcomeScreen();
    case 0:
      devicesList("Looking for devices", color(255, 0, 0));
      break;
    case 1:
      devicesList("Choose Device", color(0, 255, 0));
      break;
    case 2:
      connectDevice();
      break;
    case 3:
      showData();
      showFillBar();
      break;
    case 4:
      showError();
      break;
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void onStart()
{
  super.onStart();
  println("onStart");
  adapter = BluetoothAdapter.getDefaultAdapter();
  if (adapter != null)
  {
    if (!adapter.isEnabled())
    {
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }
    else
    {
      begin();
    }
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void onStop()
{
  println("onStop");
  /*
  if(registrado)
  {
    unregisterReceiver(receptor);
  }
  */
 
  if(socket != null)
  {
    try
    {
      socket.close();
    }
    catch(IOException ex)
    {
      println(ex);
    }
  }
  super.onStop();
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void onActivityResult (int requestCode, int resultCode, Intent data)
{
  println("onActivityResult");
  if(resultCode == RESULT_OK)
  {
    println("RESULT_OK");
    begin();
  }
  else
  {
    println("RESULT_CANCELED");
    status = 4;
    error = "bluetooth is not activated";
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void mouseReleased()
{
  checkDisconnectButton();
  switch(status)
  {
    case 0:
      /*
      if(registrado)
      {
        adaptador.cancelDiscovery();
      }
      */
      break;
    case 1:
      checkSelected();
      checkDisconnectButton();
      break;
    case 3:
      checkButton();
      break;
      
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void begin()
{
    devices = new ArrayList();
    /*
    registerReceiver(receptor, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    registerReceiver(receptor, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
    registerReceiver(receptor, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    registrado = true;
    adaptador.startDiscovery();
    */
    for (BluetoothDevice device : adapter.getBondedDevices())
    {
        devices.add(device);
    }
    status = 1;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void devicesList(String text, int c)
{
  background(0);
  textFont(f1);
  fill(c);
  text(text,0, 20);
  if(devices != null)
  {
    for(int index = 0; index < devices.size(); index++)
    {
      BluetoothDevice device = (BluetoothDevice) devices.get(index);
      fill(255,255,0);
      int position = 50 + (index * 55);
      if(device.getName() != null)
      {
        text(device.getName(),0, position);
      }
      fill(180,180,255);
      text(device.getAddress(),0, position + 20);
      fill(255);
      line(0, position + 30, 319, position + 30);
    }
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void checkSelected()
{
  int selected = (mouseY - 50) / 55;
  if(selected < devices.size())   
  {     
    device = (BluetoothDevice) devices.get(selected);     
    println(device.getName());     
    status = 2;   
  } 
} 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
public void connectDevice() 
{   
  try   
  {     
    socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
    /*     
      Method m = dispositivo.getClass().getMethod("createRfcommSocket", new Class[] { int.class });     
      socket = (BluetoothSocket) m.invoke(dispositivo, 1);             
    */     
    socket.connect();     
    ins = socket.getInputStream();     
    ons = socket.getOutputStream();     
    sc = new Scanner(ins);
    status = 3;   
  }   
  catch(Exception ex)   
  {     
    status = 4;     
    error = ex.toString();     
    println(error);   
  } 
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
public void showData() 
{   
  String temp ="";
  background(0);
  fill(255);
//---read lines and display
try{
  if(ins.available()>0){
    temp = sc.nextLine();
    data.add(temp);
    Matcher matcher = Pattern.compile("\\d+").matcher(temp);
    matcher.find();
    try{
    int i = Integer.valueOf(matcher.group());
    counter = i;
    }
    catch (Exception e){
      
    }
  }
}
catch (Exception e) {
    status = 4;
    error = e.toString();
    println(error);
}
  //-- end of reading lines
  stroke(255, 255, 0);
  fill(255, 0, 0);
  rect(120, 900, 80, 40);
  fill(255, 255, 0);
  text("Send", 135, 925);
  rect(320, 900, 140, 40);
  fill(0, 0, 0);
  text("Disconnect", 335, 925);
  
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void checkButton()
{
  if(mouseX > 120 && mouseX < 200 && mouseY > 900 && mouseY < 940)
  {
    try
    {
        ons.write('0');
    }
    catch(Exception ex)
    {
      status = 4;
      error = ex.toString();
      println(error);
    }
  }
}

public void checkDisconnectButton()
{
  println("got here ");
  if(mouseX > 320 && mouseX < 360 && mouseY > 900 && mouseY < 940)
  {
        if (socket != null) {
                try {socket.close();} catch (Exception e) {}
                socket = null;
        }
        if (ins != null) {
                try {ins.close();} catch (Exception e) {}
                ins = null;
        }
        if (ons != null) {
                try {ons.close();} catch (Exception e) {}
                ons = null;
        }
        data = new ArrayList<String>();
        device = null;
        counter = 0; n = initialn; level = 1;
        onStart();
                
        
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void showFillBar() {
  level = counter%1000;
  if (level >= Max) { //if level is or greater than Max
    n=initialn; //reset n to its initial value
    level = 1;
    counter = 1;
    //reset level to its initial value
  }
  if (level < Max) { //if level is less than Max
    n=n+initialn; //increment n by its original value
  }
  pos = (level/Max)*iHeight; //take the original and share it by the max value level can take before multiplying it by level to get a width value
  DecimalFormat df = new DecimalFormat();
  df.setMaximumFractionDigits(2);
  s = "Progress: " + df.format(pos/5) + " %"; //string detailing the progress
  fill(255); //white
  text(s, 430, 280); //writes text to the screen using string s
  rect(RectX, RectY, iWidth, iHeight); //background bar of progress bar
  fill(0, n, 0); //constantly changing shade based on progress
  rect(RectX, RectY+500, iWidth, -pos); //pos is used to display the progress

}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void showError()
{
  background(255, 0, 0);
  fill(255, 255, 0);
  textFont(f2);
  textAlign(CENTER);
  translate(width / 2, height / 2);
  rotate(3 * PI / 2);
  text(error, 0, 0);
}
////////////////////////////////////////////////////////////////


}
