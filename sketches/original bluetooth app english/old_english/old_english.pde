import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.ArrayList;
import java.util.UUID;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
 
private static final int REQUEST_ENABLE_BT = 3;
ArrayList devices;
BluetoothAdapter adapter;
BluetoothDevice device;
BluetoothSocket socket;
InputStream ins;
OutputStream ons;
boolean registered = false;
PFont f1;
PFont f2;
int status;
String error;
byte value;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
BroadcastReceiver receptor = new BroadcastReceiver()
{
    public void onReceive(Context context, Intent intent)
    {
        println("onReceive");
        String action = intent.getAction();
 
        if (BluetoothDevice.ACTION_FOUND.equals(action))
        {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            println(device.getName() + " " + device.getAddress());
            devices.add(device);
        }
        else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action))
        {
          status = 0;
          println("searching...");
        }
        else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
        {
          status = 1;
          println("searching...");
        }
 
    }
};
 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void setup() {
  //size(320,480);
  frameRate(25);
  f1 = createFont("Arial",20,true);
  f2 = createFont("Arial",15,true);
  stroke(255);
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void draw() {
  switch(status)
  {
    case 0:
      deviceList("searching for devices", color(255, 0, 0));
      break;
    case 1:
      deviceList("choose device", color(0, 255, 0));
      break;
    case 2:
      connectDevice();
      break;
    case 3
      sampleData();
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
      start();
    }
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void onStop()
{
  println("onStop");
  /*
  if(registered)
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
void onActivityResult (int requestCode, int resultCode, Intent data)
{
  println("onActivityResult");
  if(resultCode == RESULT_OK)
  {
    println("RESULT_OK");
    start();
  }
  else
  {
    println("RESULT_CANCELED");
    status = 4;
    error = "bluetooth is not enabled";
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void mouseReleased()
{
  switch(status)
  {
    case 0:
      /*
      if(registered)
      {
        adapter.cancelDiscovery();
      }
      */
      break;
    case 1:
      checkSelection();
      break;
    case 3:
      checkButton();
      break;
  }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void start()
{
    devices = new ArrayList();
    /*
    registerReceiver(receptor, new IntentFilter(BluetoothDevice.ACTION_FOUND));
    registerReceiver(receptor, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
    registerReceiver(receptor, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
    registered = true;
    adapter.startDiscovery();
    */
    for (BluetoothDevice device : adapter.getBondedDevices())
    {
        devices.add(device);
    }
    status = 1;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void deviceList(String text, color c)
{
  background(0);
  textFont(f1);
  fill(c);
  text(text,0, 20);
  if(devices != null)
  {
    for(int index = 0; indice < devices.size(); index++)
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
void checkSelection()
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
void connectDevice() 
{   
  try   
  {     
    socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
    /*     
      Method m = device.getClass().getMethod("createRfcommSocket", new Class[] { int.class });     
      socket = (BluetoothSocket) m.invoke(device, 1);             
    */     
    socket.connect();     
    ins = socket.getInputStream();     
    ons = socket.getOutputStream();     
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
  try
  {     
    while(ins.available() > 0)
    {
      value = (byte)ins.read();
    }
  }
  catch(Exception ex)
  {
    status = 4;
    error = ex.toString();
    println(error);
  }
  background(0);
  fill(255);
  text(value, width / 2, height / 2);
  stroke(255, 255, 0);
  fill(255, 0, 0);
  rect(120, 400, 80, 40);
  fill(255, 255, 0);
  text("Button", 135, 425);
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void checkButton()
{
  if(mouseX > 120 && mouseX < 200 && mouseY > 400 && mouseY < 440)
  {
    try
    {
        ons.write(0);
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
void showError()
{
  background(255, 0, 0);
  fill(255, 255, 0);
  textFont(f2);
  textAlign(CENTER);
  translate(width / 2, height / 2);
  rotate(3 * PI / 2);
  text(error, 0, 0);
}
