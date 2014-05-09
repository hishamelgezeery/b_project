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
import java.nio.charset.Charset;
//------------------imports------------------//

private static final int REQUEST_ENABLE_BT = 3;
ArrayList devices;
BluetoothAdapter adapter;
BluetoothDevice device;
BluetoothSocket socket;
InputStream ins;
Scanner sc;
OutputStream ons;
boolean registered = false;
PFont f1;
PFont f2;
PFont f3;
int status;
int count = 0;
String error;
char value;
int counter = 0;
Gauge gauge;
ProgressBar progressBar;
VScrollbar vScrollbar;
LineGraph graph;
//------------------variables------------------//

void setup() {  
  frameRate(25);
  f1 = createFont("Arial", 20, true);
  f2 = createFont("Arial", 15, true);
  f3 = createFont("Arial", 30, true);
  stroke(255);
  smooth();
  progressBar = new ProgressBar(counter, 50, 400, 0, 1000, 50, -350, 600);
  gauge = new Gauge(counter, 250, 0, 1000, 450, 250);
  vScrollbar = new VScrollbar(50, 20, 40, 300, 4, 0, 100, color(255, 255, 255), color(225, 225, 225), color(245, 0, 0), color(225, 0, 0), color(195, 0, 0));
  graph = new LineGraph(counter, -300, 200, 400, "Time", 0, 50, "Counter", 0, 200);
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void draw() {
  counter++;
  switch(status)
  {
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
    vScrollbar.update();
    vScrollbar.display();
    gauge.draw();
    progressBar.draw();
    graph.updateCounter(counter);
    graph.drawGraph();
    if (vScrollbar.ready) {
      sendValue();
    }
    break;
  case 4:
    showError();
    break;
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void onStart()
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
void onStop()
{
  println("onStop");

  if (socket != null)
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
void onActivityResult (int requestCode, int resultCode, Intent data)
{
  println("onActivityResult");
  if (resultCode == RESULT_OK)
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
void mouseReleased()
{
  checkDisconnectButton();
  switch(status)
  {
  case 0:
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
void begin()
{
  devices = new ArrayList();

  for (BluetoothDevice device : adapter.getBondedDevices())
  {
    devices.add(device);
  }
  status = 1;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void devicesList(String text, color c)
{
  background(0);
  textFont(f1);
  fill(c);
  text(text, 0, 20);
  if (devices != null)
  {
    for (int index = 0; index < devices.size(); index++)
    {
      BluetoothDevice device = (BluetoothDevice) devices.get(index);
      fill(255, 255, 0);
      int position = 50 + (index * 55);
      if (device.getName() != null)
      {
        text(device.getName(), 0, position);
      }
      fill(180, 180, 255);
      text(device.getAddress(), 0, position + 20);
      fill(255);
      line(0, position + 30, 319, position + 30);
    }
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void checkSelected()
{
  int selected = (mouseY - 50) / 55;
  if (selected < devices.size())   
  {     
    device = (BluetoothDevice) devices.get(selected);     
    println(device.getName());     
    status = 2;
  }
} 
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
void connectDevice() 
{   
  try   
  {     
    socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
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
void showData() 
{   
  background(0);
  pushStyle();
  textFont(f3);
  fill(255, 255, 255);
  text("Connected to: " + device.getName(), 100, 100);  
  popStyle();
  pushStyle();
  fill(255);
  //---read lines and display
  try {
    if (ins.available()>0) {
      String temp = sc.nextLine();
      Matcher matcher = Pattern.compile("\\d+").matcher(temp);
      matcher.find();
      try {
        int i = Integer.valueOf(matcher.group());
        if ( i > counter)
          counter = i;
        else
          counter++;
      }
      catch (Exception e) {
      }
    }
  }
  catch (Exception e) {
    status = 4;
    error = e.toString();
    println(error);
  }
  //-- end of reading lines
  pushStyle();
  textFont(f1);
  stroke(255, 255, 0);
  fill(255, 0, 0);
  rect(120, 900, 80, 40);
  fill(255, 255, 0);
  text("Send", 135, 925);
  rect(320, 900, 140, 40);
  fill(0, 0, 0);
  text("Disconnect", 335, 925);
  popStyle();
  popStyle();
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void sendValue() {
  try
  {
    String string = "$SDPM, " + vScrollbar.getValue()+",";
    ons.write(string.getBytes(Charset.forName("UTF-8")));
    vScrollbar.ready = false;
    //ons.write('0');
  }
  catch(Exception ex)
  {
    status = 4;
    error = ex.toString();
    println(error);
  }
}
void checkButton()
{
  if (mouseX > 120 && mouseX < 200 && mouseY > 900 && mouseY < 940)
  {
    try
    {
      String string = "$SDPM, " + progressBar.getValue()+",";
      ons.write(string.getBytes(Charset.forName("UTF-8")));
      //ons.write('0');
    }
    catch(Exception ex)
    {
      status = 4;
      error = ex.toString();
      println(error);
    }
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void checkDisconnectButton()
{
  println("got here ");
  if (mouseX > 320 && mouseX < 360 && mouseY > 900 && mouseY < 940)
  {
    if (socket != null) {
      try {
        socket.close();
      } 
      catch (Exception e) {
      }
      socket = null;
    }
    if (ins != null) {
      try {
        ins.close();
      } 
      catch (Exception e) {
      }
      ins = null;
    }
    if (ons != null) {
      try {
        ons.close();
      } 
      catch (Exception e) {
      }
      ons = null;
    }
    device = null;
    counter = 0;
    status = 1;
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void showError()
{
  background(255, 0, 0);
  fill(255, 255, 0);
  textFont(f2);
  textAlign(CENTER);
  translate(width / 2, height / 2);
  rotate(3 * PI / 2);
  text(error, 0, 0);
  if (socket != null) {
    try {
      socket.close();
    } 
    catch (Exception e) {
    }
    socket = null;
  }
  if (ins != null) {
    try {
      ins.close();
    } 
    catch (Exception e) {
    }
    ins = null;
  }
  if (ons != null) {
    try {
      ons.close();
    } 
    catch (Exception e) {
    }
    ons = null;
  }
  device = null;
  counter = 0;
}

