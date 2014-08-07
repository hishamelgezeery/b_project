package de.dhbw.map;


import java.util.Random;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;

import de.dhbw.ui.GaugeView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import de.dhbw.R;


public class NewDataFragment extends Fragment {
	
	private GaugeView mGaugeView1;
	private GaugeView mGaugeView2;
	private final Random RAND = new Random();
	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static NewDataFragment newInstance() {
		NewDataFragment fragment = new NewDataFragment();
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_data_2, container,
				false);
		mGaugeView1 = (GaugeView) rootView.findViewById(R.id.gauge_view1);
		mGaugeView2 = (GaugeView) rootView.findViewById(R.id.gauge_view2);
		mTimer.start();
		// init example series data
		GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
		          new GraphViewData(1, 2.0d)
		          , new GraphViewData(2, 1.5d)
		          , new GraphViewData(3, 2.5d)
		          , new GraphViewData(4, 1.0d)
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
		layout.addView(graphView,800,250);
		layout.addView(graphView2,800,250);
		layout.addView(graphView3,800,250);
		return rootView;
	}
	
	private final CountDownTimer mTimer = new CountDownTimer(30000, 1000) {

		@Override
		public void onTick(final long millisUntilFinished) {
			mGaugeView1.setTargetValue(RAND.nextInt(101));
			mGaugeView2.setTargetValue(RAND.nextInt(101));
		}

		@Override
		public void onFinish() {}
	};
	
}
