package com.example.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;

/**
 * A main fragment displayed on the start of the application.
 */
public  class OverViewFragment extends Fragment {
	
	private TextView mTitle;
	OnDataListener mCallback;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static OverViewFragment newInstance() {
		OverViewFragment fragment = new OverViewFragment();
		return fragment;
	}
	/**
	 * Interface to be implemented by parent activity for exchange of data
	 */
	public interface OnDataListener {
        public void onDataRecieved();
    }
	public void setTextView(String obj) {
		// TODO Auto-generated method stub
		mTitle.setText(obj);
	}

	public OverViewFragment() {
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnDataListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDataListener");
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mTitle = (TextView) rootView.findViewById(R.id.textView1);
		return rootView;
	}

	public void updateData(String obj) {
		mTitle.setText(obj);
		
	}
}