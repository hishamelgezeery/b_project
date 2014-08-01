package de.dhbw.btproject;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import de.dbhw.btproject.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class DetailsActivity extends Activity {
	private Button backBtn;
	private BluetoothAdapter adapter;
	private BluetoothDevice device;
	private BluetoothSocket socket;
	private InputStream ins;
	private OutputStream ons;
	private ArrayList<BluetoothDevice> pairedDevices;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.details_view);
		pairedDevices = new ArrayList<BluetoothDevice>();
		   for (BluetoothDevice device : BluetoothAdapter.getDefaultAdapter().getBondedDevices())
		   {
			   pairedDevices.add(device);
		   }
		backBtn = (Button) findViewById(R.id.button1);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				disconnect();
				Intent intent = new Intent(v.getContext(), MainActivity.class);
				startActivity(intent);
			}
		});
		BluetoothDevice device = pairedDevices.get(getIntent().getIntExtra("device_index", 3));
		Log.d("btdevice", device.getName());
		try{
		socket = device.createRfcommSocketToServiceRecord(UUID
				.fromString("00001101-0000-1000-8000-00805F9B34FB"));
		socket.connect();
		ins = socket.getInputStream();
		ons = socket.getOutputStream();
		Toast.makeText(getApplicationContext(), getIntent().getIntExtra("device_index", -1)+"",
          		 Toast.LENGTH_LONG).show();
		}
		catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(),"sorry not available",
           		 Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(), MainActivity.class);
			startActivity(intent);
		}
		// rest of the code
	}
	private void disconnect(){
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
	}
}
