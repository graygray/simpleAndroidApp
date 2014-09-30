package com.example.simpleandroidapp;

import java.util.List;

import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class WIFIActivity extends Activity implements OnClickListener {

	protected static final int WIFI_STATE_DISABLED = 0x00000001;
	protected static final int WIFI_STATE_DISABLING = 0x00000000;
	protected static final int WIFI_STATE_ENABLED = 0x00000003;
	protected static final int WIFI_STATE_ENABLING = 0x00000002;
	protected static final int WIFI_STATE_UNKNOWN = 0x00000004;
	
	BroadcastReceiver receiver;
	Handler handler = new Handler();
	
	//wifi variables
	WifiManager wifi;
	WifiInfo wifiInfo;
	List<WifiConfiguration> configs;
	DhcpInfo dhcpInfo;
	List<ScanResult> scanResults;
	
	//wifiP2P variables
	WifiP2pManager p2pManager;
	Channel p2pChannel;

	TextView textStatus;
	Button button_wifi_info;
	Button button_p2p_info;
	Button button_wifi_scan;
	ToggleButton toggleButton_wifi_switch;
	
	int buttonCount = 0;
	int previousState = 0;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifi);

		// set timer method
		handler.removeCallbacks(updateTimer);
		// set timer delay
		handler.postDelayed(updateTimer, 500);
		
		// Setup UI
		textStatus = (TextView) findViewById(R.id.editText_wifi);
		button_wifi_info = (Button) findViewById(R.id.button_wifi_info);
		button_wifi_info.setOnClickListener(this);
		button_p2p_info = (Button) findViewById(R.id.button_p2p_info);
		button_p2p_info.setOnClickListener(this);	
		button_wifi_scan = (Button) findViewById(R.id.button_wifi_scan);
		button_wifi_scan.setOnClickListener(this);	
		toggleButton_wifi_switch = (ToggleButton)findViewById(R.id.toggleButton_wifi_switch);
		
		// Setup WiFi/P2P services
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		p2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);

		toggleButton_wifi_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    	
				String tempS = "";
				
	    		switch (wifi.getWifiState()) {
					case WIFI_STATE_DISABLED:
						tempS += "\nwifi state : WIFI_STATE_DISABLED";
				        toggleButton_wifi_switch.setChecked(false);

						break;
					case WIFI_STATE_DISABLING:
						tempS += "\nwifi state : WIFI_STATE_DISABLING";
						toggleButton_wifi_switch.setChecked(true);
						
						break;
					case WIFI_STATE_ENABLED:
						tempS += "\nwifi state : WIFI_STATE_ENABLED";
				        toggleButton_wifi_switch.setChecked(true);		
				        
						break;
					case WIFI_STATE_ENABLING:
						tempS += "\nwifi state : WIFI_STATE_ENABLING";
						toggleButton_wifi_switch.setChecked(false);	
						
						break;
					case WIFI_STATE_UNKNOWN:
						tempS += "\nwifi state : WIFI_STATE_UNKNOWN";
				        toggleButton_wifi_switch.setChecked(false);
				        
						break;

					default:
						break;
				}
	    		
		    	if (isChecked) {
		    		tempS += "\nwifi is turning on : "+ buttonCount;
		        	wifi.setWifiEnabled(true);
				} else {
					tempS += "\nwifi is turning off : "+ buttonCount;
		        	wifi.setWifiEnabled(false);
				}
		    	
		    	textStatus.setText(tempS);
		    	
		    	buttonCount++;
		    }
		});
		
		// Register Broadcast Receiver
//		if (receiver == null) {
//			receiver = new WiFiScanReceiver(this);
//			registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//		}
	}

//	@Override
//	public void onStop() {
//		unregisterReceiver(receiver);
//	}

	public void onClick(View view) {
//		Toast.makeText(this, "hello!!!",Toast.LENGTH_LONG).show();

		String tempS = "";
		
		switch (view.getId()) {
			
			case R.id.button_wifi_info:
	    		
				if (wifi.getWifiState() == WIFI_STATE_DISABLED) {
					textStatus.setText("WiFi disabled");
					return;
				}
				
				
				
				tempS += "WiFi Info : " + buttonCount;
	    
	    		// Get WiFi status
	    		wifiInfo = wifi.getConnectionInfo();
	    		tempS += "\n\ngetConnectionInfo : \n" + wifiInfo.toString();

	    		// List available networks
	    		configs = wifi.getConfiguredNetworks();
	    		for (WifiConfiguration config : configs) {
	    			tempS += "\n\ngetConfiguredNetworks : \n" + config.toString();
	    		}
	    		
	    		dhcpInfo = wifi.getDhcpInfo();
	    		tempS += "\n\ndhcpInfo : \n" + dhcpInfo.toString();
	    		
	    		tempS += "\n";
	    		tempS += "\n" + "getWifiState : " + wifi.getWifiState();
	    		tempS += "\n" + "isWifiEnabled : " + wifi.isWifiEnabled();
	    		tempS += "\n" + "pingSupplicant : " + wifi.pingSupplicant();
	    		
	    		scanResults = wifi.getScanResults();
	    		if(scanResults != null){
	    			tempS += "\n";
	    			tempS += "\nscanResults : total " + scanResults.size();
	        	    for (int i=0 ; i<scanResults.size() ; i++) {
	        	    	tempS += "\nLIST--------------------------" + i;
	        	    	tempS += "\n" + scanResults.get(i).toString();
	        	      }      	   			
	    		}
	    		
	    		textStatus.setText(tempS);
	    
	    		buttonCount++;				
				break;
	
			case R.id.button_p2p_info:
				
				tempS += "P2P Info : " + buttonCount;
        		textStatus.setText(tempS);
        		buttonCount++;        
				break;

			case R.id.button_wifi_scan:
				
				if (wifi.getWifiState() == WIFI_STATE_DISABLED) {
					textStatus.setText("WiFi disabled");
					return;
				}
				
				tempS += "P2P Info : " + buttonCount;
				tempS += "\n" + "startScan : " + wifi.startScan();
        		textStatus.setText(tempS);
 
//	    		SystemClock.sleep(5000);
        		buttonCount++;        
				break;
				
			default:
				break;
		}
		
//		if (view.getId() == R.id.button_wifi_info) {
//			Log.d(TAG, "onClick() wifi.startScan()");
//			wifi.startScan();
//		}
	}
	
	public Runnable updateTimer = new Runnable() {
	        public void run() {
	        	
	        	if (wifi.getWifiState() != previousState) {
					
	        		switch (wifi.getWifiState()) {
	        		case WIFI_STATE_DISABLED:
	        		case WIFI_STATE_ENABLING:
	        		case WIFI_STATE_UNKNOWN:
	        			
	        			toggleButton_wifi_switch.setChecked(false);
	        			previousState = wifi.getWifiState();
	        			break;
	        			
	        		case WIFI_STATE_ENABLED:
	        		case WIFI_STATE_DISABLING:
	        			
	        			toggleButton_wifi_switch.setChecked(true);
	        			previousState = wifi.getWifiState();
	        			break;
	        			
	        		default:
	        			break;
	        		}
				
	        	}
	        	// set timer delay
	        	handler.postDelayed(this, 500);
	        }
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_wifi, menu);
		return true;
	}

}


class WiFiScanReceiver extends BroadcastReceiver {
	  WIFIActivity wifiDemo;

	  public WiFiScanReceiver(WIFIActivity wifiDemo) {
	    super();
	    this.wifiDemo = wifiDemo;
	  }

	  @Override
	  public void onReceive(Context c, Intent intent) {
//	    List<ScanResult> results = wifiDemo.wifi.getScanResults();
//	    ScanResult bestSignal = null;
//	    for (ScanResult result : results) {
//	      if (bestSignal == null
//	          || WifiManager.compareSignalLevel(bestSignal.level, result.level) < 0)
//	        bestSignal = result;
//	    }
	//
//	    String message = String.format("%s networks found. %s is the strongest.",
//	        results.size(), bestSignal.SSID);
//	    Toast.makeText(wifiDemo, message, Toast.LENGTH_LONG).show();
	//
//	    Log.d(TAG, "onReceive() message: " + message);
	  }

	}
