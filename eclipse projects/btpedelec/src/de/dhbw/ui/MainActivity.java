package de.dhbw.ui;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import de.dhbw.R;
import de.dhbw.bluetooth.BluetoothCommandService;
import de.dhbw.bluetooth.DeviceListActivity;
import de.dhbw.ui.DataFragment.DataFragmentListener;
import de.dhbw.ui.MapFragment.OnPathSelectedListener;
import de.dhbw.ui.OverViewFragment.OverViewFragmentListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener, OverViewFragmentListener, OnPathSelectedListener, DataFragmentListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	CustomViewPager mViewPager;
	/**
	 * The variables handling bluetooth activity
	 */
	// Layout view

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	private static final int REQUEST_ENABLE_BT = 2;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Fragments for use
	OverViewFragment mainFragment;
	TextView mv;

	// Key names received from the BluetoothCommandService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	public static final String READ = "read";

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for Bluetooth Command Service
	private BluetoothCommandService mCommandService = null;
	// Used for updating Bluetooth status
	private boolean deviceConnected;
	private Button connectButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (CustomViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	@Override
	protected void onStart() {
		super.onStart();

		// If BT is not on, request that it be enabled.
		// setupCommand() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
		}
		// otherwise set up the command service
		else {
			if (mCommandService == null)
				setupCommand();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
		if (mCommandService != null) {
			if (mCommandService.getState() == BluetoothCommandService.STATE_NONE) {
				mCommandService.start();
			}
		}
	}

	private void setupCommand() {
		// Initialize the BluetoothChatService to perform bluetooth connections
		mCommandService = new BluetoothCommandService(this, mHandler);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mCommandService != null)
			mCommandService.stop();
	}

	private void ensureDiscoverable() {
		if (mBluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(
					BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				switch (msg.arg1) {
				case BluetoothCommandService.STATE_CONNECTED:
					changeValuesConnected();
					break;
				case BluetoothCommandService.STATE_CONNECTING:
					break;
				case BluetoothCommandService.STATE_LISTEN:
				case BluetoothCommandService.STATE_NONE:
					break;
				}
				break;
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(),
						"Connected to " + mConnectedDeviceName,
						Toast.LENGTH_SHORT).show();
				deviceConnected = true;
				changeValuesConnected();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(),
						msg.getData().getString(TOAST), Toast.LENGTH_SHORT)
						.show();
				break;
			case MESSAGE_READ:
				String obj = "";
				try {
					obj = (String) new String((byte[]) msg.obj, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
				// send recieved String to corresponding Fragments
				sendToFragment(obj);
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				// Get the device MAC address
				String address = data.getExtras().getString(
						DeviceListActivity.EXTRA_DEVICE_ADDRESS);
				// Get the BLuetoothDevice object
				BluetoothDevice device = mBluetoothAdapter
						.getRemoteDevice(address);
				// Attempt to connect to the device
				mCommandService.connect(device);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupCommand();
			} else {
				// User did not enable Bluetooth or an error occured
				Toast.makeText(this, R.string.bt_not_enabled_leaving,
						Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	// method to update button and textview after successful bluetooth
	// connection
	protected void changeValuesConnected() {
		connectButton = (Button) findViewById(R.id.search);
		connectButton.setText(R.string.disconnect);

		TextView deviceStatus = (TextView) findViewById(R.id.deviceStatus);
		deviceStatus.setText("Status: Connected to " + mConnectedDeviceName);

	}

	// method to update button and textview after successful bluetooth
	// disconnection
	protected void changeValuesDisconnected() {
		connectButton = (Button) findViewById(R.id.search);
		connectButton.setText(R.string.connect);

		TextView deviceStatus = (TextView) findViewById(R.id.deviceStatus);
		deviceStatus.setText(R.string.Text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.discoverable:
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			return true;
		}
		return false;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		if (tab.getPosition() == 1)
			mViewPager.setPagingEnabled(false);
		else
			mViewPager.setPagingEnabled(true);
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Returns one of the five fragments depending on the position
			switch (position) {
			case 0:
				return OverViewFragment.newInstance();
			case 1:
				return MapFragment.newInstance();
			case 2:
				return DataFragment.newInstance();
			case 3:
				return NewDataFragment.newInstance();
			case 4:
				return GraphDataFragment.newInstance();
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 5 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			}
			return null;
		}
	}

	public void sendToFragment(String obj) {
		// checks to see if the graphDataFragment and NewDataFragment are
		// loaded,
		// and if so sends the data received from Bluetooth
		NewDataFragment newDataFrag = NewDataFragment.newInstance();
		GraphDataFragment graphDataFrag = GraphDataFragment.newInstance();
		boolean newDataFragmentFound = false;
		boolean graphFragmentFound = false;
		int fragments = getSupportFragmentManager().getFragments().size();
		for (int i = 0; i < fragments; i++) {
			Fragment currentFragment = getSupportFragmentManager()
					.getFragments().get(i);
			if (currentFragment.getClass() == NewDataFragment.class) {
				newDataFrag = (NewDataFragment) currentFragment;
				newDataFragmentFound = true;
			} else {
				if (currentFragment.getClass() == GraphDataFragment.class) {
					graphDataFrag = (GraphDataFragment) currentFragment;
					graphFragmentFound = true;
				}
			}
		}
		// send the data if the Fragment has been instantiated
		if (graphFragmentFound && newDataFragmentFound) {
			graphDataFrag.updateData(obj);
			newDataFrag.updateData(obj);
		} else if (graphFragmentFound) {
			graphDataFrag.updateData(obj);
		} else if (newDataFragmentFound) {
			newDataFrag.updateData(obj);
		}
	}

	@Override
	public void onPathSelectedRecieved(List<Location> points) {
		DataFragment dataFrag = DataFragment.newInstance();
		boolean found = false;
		int fragments = getSupportFragmentManager().getFragments().size();
		for (int i = 0; i < fragments; i++) {
			Fragment currentFragment = getSupportFragmentManager()
					.getFragments().get(i);
			if (currentFragment.getClass() == DataFragment.class) {
				dataFrag = (DataFragment) currentFragment;
				found = true;
			}
		}
		if (found)
			dataFrag.populateGraphView(points);

	}

	@Override
	// this method is called from the Overview Fragment. The user clicks the
	// "connect"
	// button and this calles this method to start the DeviceListActivity
	public void onConnectionRequest() {
		if (!deviceConnected) {
			Intent serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
		} else {
			mCommandService.stop();
			mCommandService.start();
			changeValuesDisconnected();
			deviceConnected = false;
		}
	}

	@Override
	public void OverviewFragmentUpdate() {
		if (deviceConnected) {
			changeValuesConnected();
		}
	}

	@Override
	public void onDataReceiveRequest(String textData) {
		byte[] data = textData.getBytes();
		mCommandService.write(data);
		
	}

}
