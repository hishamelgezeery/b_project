package de.dhbw.ui;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import org.codeandmagic.android.gauge.GaugeView;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.dhbw.R;

public class GraphDataFragment extends Fragment {
	private String[] data; // String array containing seperated elements from
							// recieved bluetooth string.

	/**
	 * Returns a new instance of this fragment.
	 */
	public static GraphDataFragment newInstance() {
		GraphDataFragment fragment = new GraphDataFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_graph, container,
				false);
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(
				new GraphViewData[] { new GraphViewData(1, 2.0d),
						new GraphViewData(2, 1.5d), new GraphViewData(3, 3.5d),
						new GraphViewData(4, 4.0d), new GraphViewData(4, 0.0d),
						new GraphViewData(4, 1.0d), new GraphViewData(4, 2.0d),
						new GraphViewData(4, 6.0d) });

		GraphView graphView = new LineGraphView(this.getActivity() // context
				, "GraphViewDemo" // heading
		);
		graphView.addSeries(exampleSeries); // data

		GraphView graphView2 = new LineGraphView(this.getActivity() // context
				, "GraphViewDemo" // heading
		);
		graphView2.addSeries(exampleSeries); // data

		GraphView graphView3 = new LineGraphView(this.getActivity() // context
				, "GraphViewDemo" // heading
		);
		graphView3.addSeries(exampleSeries); // data
		LinearLayout layout = (LinearLayout) rootView
				.findViewById(R.id.graphLayout);

		// adding graphs to fragment
		float screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
		float screenHeight = getActivity().getResources().getDisplayMetrics().heightPixels;
		int width = (int) screenWidth - 20; // width of graph
		int height = (int) screenHeight / 4; // height of graph
		layout.addView(graphView, width, height);
		layout.addView(graphView2, width, height);
		layout.addView(graphView3, width, height);

		return rootView;
	}

	/**
	 * This method is called from the MainActivity. It is called when a String
	 * is received from Bluetooth. This makes it possible therefore to use the
	 * data to update the graphs after splitting the elements into the class
	 * field "data".
	 */
	public void updateData(String obj) {
		data = obj.split(";");
	}

}
