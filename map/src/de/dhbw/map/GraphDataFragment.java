package de.dhbw.map;

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
	
		/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static GraphDataFragment newInstance() {
		GraphDataFragment fragment = new GraphDataFragment();
		return fragment;
	}

		private String[] data;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_graph, container,
				false);
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		          new GraphViewData(1, 2.0d) 
		          , new GraphViewData(2, 1.5d)
		          , new GraphViewData(3, 3.5d)
		          , new GraphViewData(4, 4.0d)
		          , new GraphViewData(4, 0.0d)
		          , new GraphViewData(4, 1.0d)
		          , new GraphViewData(4, 2.0d),
		          new GraphViewData(4, 6.0d)
		});

		GraphView graphView = new LineGraphView(
		      this.getActivity() // context
		      , "GraphViewDemo" // heading
		);
		graphView.addSeries(exampleSeries); // data
		
		GraphView graphView2 = new LineGraphView(
			      this.getActivity() // context
			      , "GraphViewDemo" // heading
		);
		graphView2.addSeries(exampleSeries); // data
			
		GraphView graphView3 = new LineGraphView(
			      this.getActivity() // context
			      , "GraphViewDemo" // heading
		);
		graphView3.addSeries(exampleSeries); // data
		LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.graphLayout);
		//adding graphs to fragment
		float screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
		float screenHeight = getActivity().getResources().getDisplayMetrics().heightPixels;
		int width = (int) screenWidth/4;
		int height = (int) screenHeight/4;
		layout.addView(graphView,width, height);
		layout.addView(graphView2,width, height);
		layout.addView(graphView3,width, height);
		
		
		return rootView;
	}

	public void updateData(String obj) {
		data = obj.split(";");
	}
	
}
