package de.dhbw.map;


import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


public class NewDataFragment extends Fragment {
	
	private GaugeView mGaugeView1;
	private GaugeView mGaugeView2;
	private ProgressBar p1;
	private ProgressBar p2;
	private ProgressBar p3;
	int counter = 0;
	private String [] data;
	private final Random RAND = new Random();
	TextView t2;
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
		float screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels;
		int diameter = (int)screenWidth/2 - 20;
		mGaugeView1 = new GaugeView(rootView.getContext(), 400);
		mGaugeView2 = new GaugeView(rootView.getContext(), 40);
		LinearLayout l1 = (LinearLayout) rootView.findViewById(R.id.linear_layout1);
		l1.setPadding(20, 20,20,0);
		l1.addView(mGaugeView1, diameter, diameter);
		l1.addView(mGaugeView2, diameter, diameter);
		///21spt2 = (TextView) rootView.findViewById(R.id.textView2);
		TextView timeTextView = (TextView) rootView.findViewById(R.id.textView12);
		timeTextView.setText(DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_12HOUR));
		p1 = (ProgressBar) rootView.findViewById(R.id.vertical_progressbar);
		p2 = (ProgressBar) rootView.findViewById(R.id.vertical_progressbar2);
		p3 = (ProgressBar) rootView.findViewById(R.id.vertical_progressbar3);
		mTimer.start();
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
//		LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.graphLayout);
//		if ((getResources().getConfiguration().screenLayout & 
//			    Configuration.SCREENLAYOUT_SIZE_MASK) == 
//			        Configuration.SCREENLAYOUT_SIZE_LARGE) {
//			layout.addView(graphView,500,150);
//			layout.addView(graphView2,500,150);
//			layout.addView(graphView3,500,150);
//
//			}
//		else {
//			layout.addView(graphView,800,250);
//			layout.addView(graphView2,800,250);
//			layout.addView(graphView3,800,250);
//		}
		 //int orientation = getResources().getConfiguration().orientation;
		
		
		return rootView;
	}
	
	
	private final CountDownTimer mTimer = new CountDownTimer(30000, 1000) {

		@Override
		public void onTick(final long millisUntilFinished) {
			mGaugeView2.setTargetValue(RAND.nextInt(41));
			
		}

		@Override
		public void onFinish() {}
	};
	public void updateData(String obj) {
		data = obj.split(";");
		Log.e("Data", obj);
		//counter = Integer.parseInt(data[0]);
//		p1.setProgress(counter);
//		p2.setProgress(counter);
//		p3.setProgress(counter);
//		mGaugeView1.setTargetValue(counter);
	}
	
}
