package de.dhbw.ui;

import java.io.File;
import java.util.List;

import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MyLocationOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.PathOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.SimpleLocationOverlay;

import de.dhbw.R;

import de.dhbw.gpx.GpxReader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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
public class MapFragment extends Fragment {
	private MapView mapView;
	private MapController mapController;
	private LocationManager locManager;
	private LocationListener listener;
	Location currentLocation;
	OnPathSelectedListener mListener;

	private PathOverlay trackOverlay;
	private ScaleBarOverlay myScaleBarOverlay;
	private MyLocationOverlay myLocation;
	private List<Overlay> mapOverlays;
	private List<Location> points = null;
	private boolean trackDrawn;
	// private ArrayList<GeoPoint> gpxPoints;

	private boolean setCenter = false;
	private static final String TAG = "Schlag";

	public MapFragment() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Interface to be implemented by parent activity for exchange of data
	 */
	public interface OnPathSelectedListener {
		public void onPathSelectedRecieved(List<Location> points);
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

		// //////////////
		// /////////////
		mapView = (MapView) rootView.findViewById(R.id.mapview);
		mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
		mapView.setBuiltInZoomControls(true);
		mapView.setMultiTouchControls(true);
		mapView.setClickable(false);
		mapView.setOnTouchListener(mapClickedListener);

		setupLocation();
		mapOverlays = mapView.getOverlays();
		// Scalebar overlay
		myScaleBarOverlay = new ScaleBarOverlay(this.getActivity());
		myScaleBarOverlay.setScaleBarOffset(0, 500);
		myScaleBarOverlay.setImperial();
		mapOverlays.add(myScaleBarOverlay);

		myLocation = new MyLocationOverlay(getActivity(), mapView);
		mapOverlays.add(myLocation);
		myLocation.enableMyLocation();

		trackOverlay = new PathOverlay(Color.BLUE, getActivity());
		Paint pPaint = trackOverlay.getPaint();
		pPaint.setStrokeWidth(5);
		trackOverlay.setPaint(pPaint);

		GeoPoint startPoint = new GeoPoint(48700000, 9200000);
		mapController = (MapController) mapView.getController();
		mapController.setZoom(12);
		mapController.setCenter(startPoint);

		final Button PositionButton = (Button) rootView
				.findViewById(R.id.Position);
		PositionButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				GeoPoint position = myLocation.getMyLocation();
				if (position != null) {
					mapController.animateTo(position);
					setCenter = true;
				} else {
					Toast.makeText(v.getContext(), "Location not yet known!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		final Button TrackButton = (Button) rootView.findViewById(R.id.Track);
		TrackButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				drawTrack();
			}
		});

		Log.e(TAG, "Create_GPS");
		// /////////////

		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnPathSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnPathSelectedListener");
		}
	}

	protected void drawTrack() {
		if (trackDrawn) {
			mapView.getOverlays().remove(trackOverlay);
			trackOverlay = new PathOverlay(Color.BLUE, getActivity());
		}
			String youFilePath = Environment.getExternalStorageDirectory()
					.toString() + "/file.gpx";
			File gpxFile = new File(youFilePath);
		
		points = GpxReader.getPoints(gpxFile);
		if(points == null){
			Toast.makeText(getActivity(), "file.gpx track not found!",
			Toast.LENGTH_SHORT).show();
			return;
	}
		mListener.onPathSelectedRecieved(points);

		for (int i = 0; i < points.size(); i++) {
			Location point = points.get(i);
			trackOverlay.addPoint(new GeoPoint(point.getLatitude(), point
					.getLongitude()));
		}
		mapController.setZoom(14);
		mapController.animateTo(new GeoPoint(points.get(0).getLatitude(),
				points.get(0).getLongitude()));
		mapView.getOverlays().add(trackOverlay);
		trackDrawn = true;
	}

	private void setupLocation() {
		locManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);

		listener = new LocationListener() {
			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

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
				int lat = (int) (location.getLatitude() * 1E6);
				int lng = (int) (location.getLongitude() * 1E6);

				// neuer Koordinatenpunkt
				GeoPoint point = new GeoPoint(lat, lng);
				mapController.setCenter(point);
			}
		};

	}

	private OnTouchListener mapClickedListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			setCenter = false;
			return false;
		}
	};

}