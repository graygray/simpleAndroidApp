package com.example.simpleandroidapp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsActivity extends ListActivity {

	Handler handler;
	public static final int MSG_UPDATEADAPTER = 9033;
	
	static final String setting1 = "InputPath:";
	static final String setting2 = "setting2-";
	static final String setting3 = "list file-";
	static final String setting4 = "select true/false-";
	static final String setting5 = "input text-";
	
	static final String[] setingList = new String[] { 
		setting1 + SimpleAndroidApp.inputDevicePath, 
		setting2, setting3, setting4, setting5 };
	
	static final int devicesListLen = 20;
	static int devListCount;
	String[] devicesList= new String[devicesListLen];

	ArrayAdapter<String> adapter;
	File root;
	List<String> fileList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		handler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_UPDATEADAPTER:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}

		};
		
//		setContentView(R.layout.activity_settings);
		adapter = new ArrayAdapter<String>(this, R.layout.activity_settings,setingList);
		setListAdapter(adapter);
		 
	    devListCount = 0;
 	    for (int i = 0; i < devicesListLen; i++) {
 	    	devicesList[i] = "none";
		}

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);
 
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Log.e("gray", "SettingsActivity.java, position:" + position);
				Log.e("gray", "SettingsActivity.java, id:" + id);

				switch (position) {
				case 0:
					
					fileList.clear();

					root = new File("/dev/input");
					ListDir(root, null);
					root = new File("/dev");
					ListDir(root, "hid");
					
					final String[] devicesList = fileList.toArray(new String[fileList.size()]);
				    
					new AlertDialog.Builder(SettingsActivity.this)
							.setTitle("Choose Input Device Path")
							.setItems(devicesList,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,int which) {

											SimpleAndroidApp.inputDevicePath = devicesList[which];

											setingList[0] = setting1 + devicesList[which];

											Message m = new Message();
											m.what = MSG_UPDATEADAPTER;
											handler.sendMessage(m);
											
											// save setting data
											SharedPreferences settings = getSharedPreferences(SimpleAndroidApp.PREFS_NAME, 0);
											SharedPreferences.Editor editor = settings.edit();
											editor.putString("inputDevicePath", devicesList[which]);
											// Commit the edits!
											editor.commit();

										}
									}
							).show();

					break;
				case 1:
					
	
					break;
				case 2:
					
					fileList.clear();

					root = new File("/dev");
					ListDir(root, null);
				    
					final String[] fileLists = fileList.toArray(new String[fileList.size()]);
					new AlertDialog.Builder(SettingsActivity.this).setTitle("????")
					.setItems(fileLists, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								
							}
				    }).show();
					
					break;
				case 3:
					
					new AlertDialog.Builder(SettingsActivity.this).setMessage("?????")
		               .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
								
		                   }
		               })
		               .setNegativeButton("No", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
								
		                   }
		               }).show();
					
					break;
				case 4:
					
					final EditText input = new EditText(SettingsActivity.this);
					input.setText("sample");
					new AlertDialog.Builder(SettingsActivity.this).setTitle("?????").setIcon(
						     android.R.drawable.ic_dialog_info).setView(input).setPositiveButton("Confirm",
						    	new DialogInterface.OnClickListener() { 
						    	 	public void onClick(DialogInterface dialog, int whichButton) {
						    	 		
						    	 	}
						    	 }
						    ).setNegativeButton("Cancel", null).show();
					
					break;
				default:
					break;
				}

			}
		});
		
	}

	public void ListDir(File f, String filter){
		
		try {
			
			File[] files = f.listFiles();
//			fileList.clear();
			for (File file : files){
				if (filter != null) {
			    	 if(file.getName().contains(filter) ){
						fileList.add(file.getPath());  
			    	 }
				} else {
					fileList.add(file.getPath());  
				}
			}
			
		} catch (Exception e) {
			Log.e("gray", "SettingsActivity.java:ListDir, Exception:" + e.toString());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}

}
