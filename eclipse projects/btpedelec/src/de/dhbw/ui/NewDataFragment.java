package de.dhbw.ui;

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
	private String[] data;
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
		int diameter = (int) screenWidth / 2 - 20;
		// creating gauges and specifying end values and then adding them to
		// linear layout
		mGaugeView1 = new GaugeView(rootView.getContext(), 400);
		mGaugeView2 = new GaugeView(rootView.getContext(), 40);
		LinearLayout l1 = (LinearLayout) rootView
				.findViewById(R.id.gaugesLayout);
		l1.setPadding(20, 20, 20, 0);
		l1.addView(mGaugeView1, diameter, diameter);
		l1.addView(mGaugeView2, diameter, diameter);

		// setting time fields
		TextView timeTextView = (TextView) rootView
				.findViewById(R.id.timeValue1);
		TextView timeTextView2 = (TextView) rootView
				.findViewById(R.id.timeValue2);
		timeTextView.setText(DateUtils.formatDateTime(getActivity(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_12HOUR));
		timeTextView2.setText(DateUtils.formatDateTime(getActivity(),
				System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_12HOUR));

		// getting progress bars from view
		p1 = (ProgressBar) rootView.findViewById(R.id.vertical_progressbar);
		p2 = (ProgressBar) rootView.findViewById(R.id.vertical_progressbar2);
		p3 = (ProgressBar) rootView.findViewById(R.id.vertical_progressbar3);

		// example of setting the value
		p1.setProgress(50); // 50%
		p2.setProgress(70);
		mTimer.start();
		return rootView;
	}

	// timer used for testing values
	private final CountDownTimer mTimer = new CountDownTimer(30000, 1000) {

		@Override
		public void onTick(final long millisUntilFinished) {
			// example of updating gauge values
			mGaugeView1.setTargetValue(RAND.nextInt(401));
			mGaugeView2.setTargetValue(RAND.nextInt(41));

		}

		@Override
		public void onFinish() {
		}
	};

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
