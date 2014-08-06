package de.dhbw.map;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import de.dhbw.R;


public class NewDataFragment extends Fragment {

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
		return rootView;
	}
	
	
}
