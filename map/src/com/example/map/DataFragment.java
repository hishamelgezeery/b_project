package com.example.map;

import com.example.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataFragment extends Fragment {

	/**
	 * Returns a new instance of this fragment for the given section number.
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
}
