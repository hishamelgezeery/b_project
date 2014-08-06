package de.dhbw.map;


import java.util.Random;

import de.dhbw.ui.GaugeView;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
