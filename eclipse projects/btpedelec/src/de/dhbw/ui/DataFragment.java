package de.dhbw.ui;

import java.util.List;

import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import de.dhbw.R;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DataFragment extends Fragment {

	/**
	 * This fragment is the fragment used for displaying the altitude graph for
	 * the chosen track.
	 */

	/**
	 * This method returns a new instance of the DataFragment.
	 */
	public static DataFragment newInstance() {
		DataFragment fragment = new DataFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_data, container,
				false);
		return rootView;
	}

	/**
	 * This method recieves the list of location points obtained from the track
	 * and outputs the altitude values in relation to distance covered to a
	 * graphview.
	 */
	public void populateGraphView(List<Location> points) {
		// init example series data
		TextView trackStatus = (TextView) getView().findViewById(
				R.id.trackStatus);
		if (trackStatus != null) {
			trackStatus.setText(R.string.track_loaded);
		}
		GraphViewData[] plots = new GraphViewData[points.size()];
		double distance = 0;
		for (int i = 0; i < points.size(); i++) {
			Location point = points.get(i);
			double altitude = point.getAltitude();
			if (i == 0) {
				distance = 0;
			} else {
				distance += point.distanceTo(points.get(i - 1));
			}

			plots[i] = new GraphViewData(distance, altitude);
		}
		GraphViewSeries exampleSeries = new GraphViewSeries(plots);

		LineGraphView graphView = new LineGraphView(getActivity() // context
				, "Height Profile" // heading
		);
		graphView.addSeries(exampleSeries); // data

		try {
			LinearLayout layout = (LinearLayout) getView().findViewById(
					R.id.graph1);
			layout.addView(graphView);
		} catch (NullPointerException e) {
			// something to handle the NPE.
		}
	}
}
