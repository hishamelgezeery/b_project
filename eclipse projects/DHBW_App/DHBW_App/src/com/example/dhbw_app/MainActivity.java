package com.example.dhbw_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	    

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

	        setContentView(R.layout.activity_main);

	         final Button VerlaufButton = (Button) findViewById(R.id.Verlauf);
	         VerlaufButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 Intent intent = new Intent(getApplicationContext(), Verlauf.class);
	            	 startActivity(intent);
	             }
	         });
	     
	         final Button GPSButton = (Button) findViewById(R.id.GPS);
	         GPSButton.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 Intent intent = new Intent(getApplicationContext(), GPS_Activity.class);
	            	 startActivity(intent);
	             }
	         });
	 
			Log.e("Schlag", "Creat_Main");
		}

		@Override
		public void onStart() {
			super.onStart();
			
			Log.e("Schlag", "Start_Main");
		}

		@Override
		public synchronized void onResume() {
			super.onResume();
			
			Log.e("Schlag", "Resume_Main");
		}

		@Override
		public synchronized void onPause() {
			super.onPause();

			Log.e("Schlag", "Pause_Main");
		}

		@Override
		public void onStop() {
			super.onStop();

			Log.e("Schlag", "Stop_Main");
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			
			Log.e("Schlag", "Destroy_Main");
		}

	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.action_settings:
	        	Toast.makeText(getApplicationContext(), R.string.hello_world ,Toast.LENGTH_SHORT).show();
	        }
	        return false;
	    }
}


