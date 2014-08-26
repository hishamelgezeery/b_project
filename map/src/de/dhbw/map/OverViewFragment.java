package de.dhbw.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.dhbw.R;
import de.dhbw.bluetooth.DeviceListActivity;

/**
 * A main fragment displayed on the start of the application.
 */
public  class OverViewFragment extends Fragment {
	
	// Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
	private Button searchButton;
	OverViewFragmentListener mCallback;
	

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
	public interface OverViewFragmentListener {
        public void onConnectionRequest();
    }
	public void setTextView(String obj) {
		// TODO Auto-generated method stub
	}

	public OverViewFragment() {
	}
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OverViewFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OverviewFragmentListener");
        }
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		searchButton = (Button) rootView.findViewById(R.id.search);
		searchButton.setOnClickListener(new View.OnClickListener() {
       	 public void onClick(View v) {
     		mCallback.onConnectionRequest();
     			
            }
       });
		return rootView;
	}

	public void updateData(String obj) {
		
	}
}