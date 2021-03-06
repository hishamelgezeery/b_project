package com.example.dhbw_app;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import com.example.gps.GpxReader;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class GPS_Activity extends Activity {
	
	private MapView mapView;
	private MapController mapController;
	private LocationManager locManager;
	private LocationListener listener;
	Location currentLocation;
	
	private PathOverlay trackOverlay;
	private MyLocationOverlay myLocation;
	private ScaleBarOverlay myScaleBarOverlay;
	private List<Overlay> mapOverlays;
	private List<Location> points = null;
	//private ArrayList<GeoPoint> gpxPoints;
	
	
	private boolean setCenter = false;
	
	private static final String TAG = "Schlag";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gps);
	
		locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		
		listener = new LocationListener() {
        	@Override
        	public void onStatusChanged(String provider,int status,Bundle extras) {

        	}
        	
        	@Override
        	public void onProviderEnabled(String provider) {

        	}
        	
        	@Override
        	public void onProviderDisabled(String provider) {

        	}
        	
        	@Override
        	public void onLocationChanged(Location location) {
        		// Koordinaten umwandeln
        		currentLocation = location;
        		int lat = (int)(location.getLatitude() * 1E6);
        		int lng = (int)(location.getLongitude() * 1E6);
        		
        		// neuer Koordinatenpunkt
        		GeoPoint point = new GeoPoint(lat, lng);
        		mapController.setCenter(point); 
        	}
        };
        
        mapView = (MapView) this.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setClickable(false);
        mapView.setOnTouchListener(mapClickedListener);
        
        GeoPoint startPoint = new GeoPoint(48700000, 9200000);
        mapController = mapView.getController();
        mapController.setZoom(12);
        mapController.setCenter(startPoint);

        mapOverlays = mapView.getOverlays();
        
        // Scalebar overlay
        myScaleBarOverlay = new ScaleBarOverlay(this);
        myScaleBarOverlay.setScaleBarOffset(380, 850);
        myScaleBarOverlay.setLineWidth(5);
        myScaleBarOverlay.setTextSize(18);
        mapOverlays.add(myScaleBarOverlay);
        
        myLocation = new MyLocationOverlay(this, mapView);
        mapOverlays.add(myLocation);
        myLocation.enableMyLocation();

        trackOverlay = new PathOverlay(Color.BLACK, this);
        //mapOverlays.add(trackOverlay);
        
        final Button PositionButton = (Button) findViewById(R.id.Position);
        PositionButton.setOnClickListener(new View.OnClickListener() {
        	 public void onClick(View v) {
        		 GeoPoint position = myLocation.getMyLocation();
     			if (position!=null)
     			{
     				mapController.animateTo(position);
     				setCenter = true;
     			}
     			else{
     				Toast.makeText(getApplicationContext(), "Location not yet known!" ,Toast.LENGTH_SHORT).show();
     			}
             }
        });
    
        final Button TrackButton = (Button) findViewById(R.id.Track);
        TrackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//            	String youFilePath = Environment.getExternalStorageDirectory().toString()+"/file.gpx";
//        		File gpxFile=new File(youFilePath);
//        		points = GpxReader.getPoints(gpxFile);
//        		for(int i=0;i<points.size();i++){
//        			Location point = points.get(i);
//        			trackOverlay.addPoint(new GeoPoint(point.getLatitude(),point.getLongitude()));
//        		}
//        		mapController.animateTo(new GeoPoint(points.get(0).getLatitude(),points.get(0).getLongitude()));
//        		mapView.getOverlays().add(trackOverlay);
            }
        });
        
		Log.e(TAG, "Create_GPS");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.verlauf, menu);
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
    
	@Override
	protected void onStart() {
		super.onStart();
		locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0,  listener);
		Log.e(TAG, "Start_GPS");
	}
	
    @Override
    protected void onResume() {
    	super.onResume();
    	locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0,  listener);
    	Log.e(TAG, "Resume_GPS"); 
    }
    
	@Override
	protected void onPause() {
		super.onPause();
		locManager.removeUpdates(listener);
		
		Log.e(TAG, "Pause_GPS");
	}
	
    @Override
    protected void onStop() {
 	   super.onPause();
 	   locManager.removeUpdates(listener);
    }
    
	private OnTouchListener mapClickedListener = new OnTouchListener(){
		public boolean onTouch(View v, MotionEvent event) {
				setCenter = false;
			return false;
		}
	};
}
