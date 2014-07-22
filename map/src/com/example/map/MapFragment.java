package com.example.map;

import java.io.File;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.PathOverlay;


import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * A map fragment containing the map view.
 */
public  class MapFragment extends Fragment {
	private MapView mapView;
	private MapController mapController;
	private boolean setCenter = false;
	
	private static final String TAG = "Schlag";
	public  MapFragment() {
		// TODO Auto-generated constructor stub
	}
	
	public static MapFragment newInstance() {
		MapFragment fragment = new MapFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_map, container,
				false);
		////////////////
		mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setClickable(false);
        mapView.setOnTouchListener(mapClickedListener);
        
        GeoPoint startPoint = new GeoPoint(48700000, 9200000);
        mapController = mapView.getController();
        mapController.setZoom(12);
        mapController.setCenter(startPoint);
        
        final Button PositionButton = (Button) rootView.findViewById(R.id.Position);
        PositionButton.setOnClickListener(new View.OnClickListener() {
        	 public void onClick(View v) {
        		
     				Toast.makeText(v.getContext(), "Location not yet known!" ,Toast.LENGTH_SHORT).show();
     			
             }
        });
    
        final Button TrackButton = (Button) rootView.findViewById(R.id.Track);
        TrackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(v.getContext(), "Location not yet known!" ,Toast.LENGTH_SHORT).show();
            }
        });
        
		Log.e(TAG, "Create_GPS");
		///////////////
		return rootView;
	}
	
	private OnTouchListener mapClickedListener = new OnTouchListener(){
		public boolean onTouch(View v, MotionEvent event) {
				setCenter = false;
			return false;
		}
	};
}