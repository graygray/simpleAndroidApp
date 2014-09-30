package com.example.simpleandroidapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class KeyEventActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_key_event);
		
 		final Button button_ke_back = (Button) findViewById(R.id.button_ke_back);
 		button_ke_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Intent intent = new Intent(); 
            	intent.setClass(KeyEventActivity.this, SimpleAndroidApp.class); 
            	startActivity(intent);
            	
            }
        });
		
 		final Button button_ke_clean = (Button) findViewById(R.id.button_ke_clean);
 		button_ke_clean.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
        		final EditText editText_result = (EditText) findViewById(R.id.editText_keyevent);
        		editText_result.setText("");
            	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_key_event, menu);
		return true;
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {

//		Log.e("gray", "KeyEventActivity.java: event.toString():" + event.toString());
		setResultText(">>>> \n" + event.toString() + "\n");
		
//		return false;
 		return true;
	}

	public void setResultText(String s){
		
		final EditText editText_result = (EditText) findViewById(R.id.editText_keyevent);
		
		// append to end
		editText_result.append(s);
	}
}
