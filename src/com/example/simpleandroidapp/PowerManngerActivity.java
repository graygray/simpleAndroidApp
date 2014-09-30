package com.example.simpleandroidapp;

import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PowerManngerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_power_mannger);

//		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
//		wl.acquire();
//		wl.release();
		
		final Button button_isScreenOn = (Button) findViewById(R.id.button_isScreenOn);
		button_isScreenOn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
        		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            	final EditText editText_pm = (EditText) findViewById(R.id.editText_pm);

            	editText_pm.setText(pm.isScreenOn()? "Screen is On" : "Screen is OFF");
            	editText_pm.append(pm.toString());
            }
        });
		   
		final Button button_goToSleep = (Button) findViewById(R.id.button_goToSleep);
		button_goToSleep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
        		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
               	final EditText editText_pm = (EditText) findViewById(R.id.editText_pm);

               	editText_pm.setText( ""+ SystemClock.uptimeMillis ());
            	pm.goToSleep( SystemClock.uptimeMillis ());
            }
        });
		
		final Button button_wakeUp = (Button) findViewById(R.id.button_wakeUp);
		button_wakeUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
//        		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        		
            }
        });
		
		final Button button_reboot = (Button) findViewById(R.id.button_reboot);
		button_reboot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
        		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

            	pm.reboot("reboot");
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_power_mannger, menu);
		return true;
	}

}
