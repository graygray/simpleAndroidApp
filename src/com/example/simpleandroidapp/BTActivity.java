package com.example.simpleandroidapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.UUID;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BTActivity extends Activity {

	Handler handler;
	
	public boolean isCursorShow = false;
    public int readCount = 0;
    
    public static final int MEG_UPDATEEDITTEXT = 9527;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bt);
	
		handler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MEG_UPDATEEDITTEXT:
					setResultText((String)msg.obj);
					break;

				}
				super.handleMessage(msg);
			}

		};
		
		// test BT socket
 		final Button button_bt_socket = (Button) findViewById(R.id.button_bt_socket);
         button_bt_socket.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
  
            	   	BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                	if (mBluetoothAdapter == null) {
                		Log.e("gray", "SimpleAndroidApp.java: " + "mBluetoothAdapter == null");
                	} else {
                		Log.e("gray", "SimpleAndroidApp.java: " + "mBluetoothAdapter != null");
            		}
                	
                	if (!mBluetoothAdapter.isEnabled()) {
                		Log.e("gray", "SimpleAndroidApp.java: " + "mBluetoothAdapter.isEnabled() X");
                	} else {
                		Log.e("gray", "SimpleAndroidApp.java: " + "mBluetoothAdapter.isEnabled() O");
            		}
                	
                	Log.e("gray", "SimpleAndroidApp.java: " + "getProfileConnectionState 0x01:" + mBluetoothAdapter.getProfileConnectionState(0x01));
                	Log.e("gray", "SimpleAndroidApp.java: " + "getProfileConnectionState 0x02:" + mBluetoothAdapter.getProfileConnectionState(0x02));
                	Log.e("gray", "SimpleAndroidApp.java: " + "getProfileConnectionState 0x03:" + mBluetoothAdapter.getProfileConnectionState(0x03));
                	
                	BluetoothDevice mdevice = null;
                	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                	// If there are paired devices
                	if (pairedDevices.size() > 0) {
                	    // Loop through paired devices
                		Log.e("gray", "SimpleAndroidApp.java: " + "pairedDevices.size() > 0");
                	    for (BluetoothDevice device : pairedDevices) {
                	        // Add the name and address to an array adapter to show in a ListView
//                	    	mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                	    	mdevice = device;
                	    	Log.e("gray", "SimpleAndroidApp.java: " + "device.getName():" + device.getName());
                	    	Log.e("gray", "SimpleAndroidApp.java: " + "device.getAddress():" + device.getAddress());
                	    }
                	}
                	
                	Log.e("gray", "SimpleAndroidApp.java: " + "fetchUuidsWithSdp:" + mdevice.fetchUuidsWithSdp());
                	
                	ParcelUuid[] uuidx = mdevice.getUuids();
                	BluetoothSocket mmSocket = null;
              
            		Log.e("gray", "SimpleAndroidApp.java: " + "uuidx length: " + uuidx.length);
            		for (int i = 0; i < uuidx.length; i++) {
                		Log.e("gray", "SimpleAndroidApp.java: " + uuidx[i].toString());
            		}
            		
                	try {
                		mmSocket = mdevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                		
//                		mmSocket = mdevice.createRfcommSocketToServiceRecord(uuidx[0].getUuid());
                        
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			Log.e("gray", "SimpleAndroidApp.java: " + "createRfcommSocketToServiceRecord " + e);
            			e.printStackTrace();
            		}

            		// ************* hack method, not work
//            		Method m = null;
//            		try {
//            			m = mdevice.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
//            		} catch (NoSuchMethodException e) {
//            			// TODO Auto-generated catch block
//            			Log.e("gray", "SimpleAndroidApp.java: " + "");
//            			e.printStackTrace();
//            		}
//            		try {
//            			mmSocket = (BluetoothSocket) m.invoke(mdevice, 1);
//            		} catch (IllegalArgumentException e) {
//            			// TODO Auto-generated catch block
//            			Log.e("gray", "SimpleAndroidApp.java: " + "IllegalArgumentException");
//            			e.printStackTrace();
//            		} catch (IllegalAccessException e) {
//            			// TODO Auto-generated catch block
//            			Log.e("gray", "SimpleAndroidApp.java: " + "IllegalAccessException");
//            			e.printStackTrace();
//            		} catch (InvocationTargetException e) {
//            			// TODO Auto-generated catch block
//            			Log.e("gray", "SimpleAndroidApp.java: " + "InvocationTargetException");
//            			e.printStackTrace();
//            		}
                	// ************* hack method, not work
                       		
                	Log.e("gray", "SimpleAndroidApp.java: " + "mmSocket create ok");
                	Log.e("gray", "SimpleAndroidApp.java: " + "mmSocket.isConnected()" + mmSocket.isConnected());
                	
                	try {
            			mmSocket.connect();
            		} catch (IOException e) {
            			// TODO Auto-generated catch block
            			Log.e("gray", "SimpleAndroidApp.java: " + "connect fail: " + e);
            			e.printStackTrace();
            		}
                	Log.e("gray", "SimpleAndroidApp.java: " + "mmSocket.isConnected()" + mmSocket.isConnected());

                	//***************** server	
//                   	BluetoothServerSocket mmServerSocket = null;
//               		try {
//            			mmServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("gray_test", uuidx[0].getUuid());
//            		} catch (IOException e) {
//            			// TODO Auto-generated catch block
//            			Log.e("gray", "SimpleAndroidApp.java: " + "mmServerSocket fail:" + e);
//            			e.printStackTrace();
//            		}
//               		Log.e("gray", "SimpleAndroidApp.java: " + "mmServerSocket create ok!");
//               		
//               		try {
//               			mmSocket = mmServerSocket.accept();
//                    } catch (IOException e) {
//                    	Log.e("gray", "SimpleAndroidApp.java: " + "mmServerSocket.accept fail:" + e);
//                    
//                    }
//               		Log.e("gray", "SimpleAndroidApp.java: " + "mmServerSocket.accept OK" );
            //
//                    if (mmSocket != null) {
//                        // Do work to manage the connection (in a separate thread)
////                        manageConnectedSocket(socket);
//                        
//                        try {
//                            mmServerSocket.close();
//                        } catch (IOException e) {
//                        	Log.e("gray", "SimpleAndroidApp.java: " + "mmServerSocket.close fail:" + e);
//                        }
//                        
//                    }
                	//***************** server	
                   
                    Log.e("gray", "SimpleAndroidApp.java: " + "done!!");
             }
         });
        
         // hidWrite
         // may need to to "chmod 7777 execFile"
 		final Button button_bt_hidWriye = (Button) findViewById(R.id.button_bt_hidWrite);
         button_bt_hidWriye.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
             	
                	try {
             	    // Executes the command.
             		Log.e("gray", "SimpleAndroidApp.java: " + "enter");
             		
             		Process process;
//             	    process = Runtime.getRuntime().exec("/system/bin/ls /");
//             	    process = Runtime.getRuntime().exec("/system/xbin/hcidump");
             		
             		if (isCursorShow) {
             			process = Runtime.getRuntime().exec("hidWrite 01 44 00");
                    	    isCursorShow = false;
 					} else {
 						process = Runtime.getRuntime().exec("hidWrite 01 44 01");
                    	    isCursorShow = true;
 					}
             	    
             		Log.e("gray", "SimpleAndroidApp.java: " + "after exec");
             		
             	    // Reads stdout.
             	    // NOTE: You can write to stdin of the command using
             	    //       process.getOutputStream().
             		
             	    BufferedReader reader = new BufferedReader(
             	            new InputStreamReader(process.getInputStream()));
             	    int read;
             	    char[] buffer = new char[8192];
             	    StringBuffer output = new StringBuffer();
             	    while ((read = reader.read(buffer)) > 0) {
             	        output.append(buffer, 0, read);
             	    }
             	    reader.close();
             
             	    Log.e("gray", "SimpleAndroidApp.java: " + "reader.close");
             	    // Waits for the command to finish.
             	    
             	    process.waitFor();
             	    
//             	    Thread.sleep(5000);
//             	    process.destroy();
             	    
             	    Log.e("gray", "SimpleAndroidApp.java: " + "output.toString():" + output.toString());
             	    
             	} catch (IOException e) {
             		Log.e("gray", "SimpleAndroidApp.java: " + "IOException:" + e);
             	    throw new RuntimeException(e);
             	} catch (InterruptedException e) {
             		Log.e("gray", "SimpleAndroidApp.java: " + "InterruptedException:" + e);
             		throw new RuntimeException(e);
             	}
             	
             }
         });
     
   		// hidRead
//   		final Button button_bt_hidRead = (Button) findViewById(R.id.button_bt_hidRead);
//           button_bt_hidRead.setOnClickListener(new View.OnClickListener() {
//               public void onClick(View v) {
//
//              	 new Thread(new Runnable() {
//
//              		 public void run() {
//
//  						while (true) {
//
//  							try {
//  								// Executes the command.
//  								Process process;
//  								process = Runtime.getRuntime().exec("hidRead");
//
//  								BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//  								int read;
//  								char[] buffer = new char[2048];
//  								StringBuffer output = new StringBuffer();
//  								Log.e("gray", "BTActivity.java:run, " + "zzzz");
//  								while ((read = reader.read(buffer)) > 0) {
//  									output.append(buffer, 0, read);
//  								}
//  								Log.e("gray", "BTActivity.java:run, " + "xxxx");
//  								reader.close();
//  								// Waits for the command to finish.
//  								process.waitFor();
//  								Message m = new Message();
//  								m.what = MEG_UPDATEEDITTEXT;
//  								m.obj = output.toString();
//  								handler.sendMessage(m);
//  								Thread.sleep(50);
//  								
//  								try {
//  									process = Runtime.getRuntime().exec("busybox killall getevent");
//  								} catch (IOException e1) {
//  									e1.printStackTrace();
//  								}
//
//  							} catch (IOException e) {
//  								Log.e("gray", "SimpleAndroidApp.java: "	+ "IOException:" + e);
//  								throw new RuntimeException(e);
//  							} catch (InterruptedException e) {
//  								Log.e("gray", "SimpleAndroidApp.java: " + "InterruptedException:" + e);
//  								throw new RuntimeException(e);
//  							}
//
//  						}
//  					}
//
//  				}).start();
//
//               }
//           });
//           
   		// clean edittext
   		final Button button_bt_clean = (Button) findViewById(R.id.button_bt_clean);
   		button_bt_clean.setOnClickListener(new View.OnClickListener() {
               public void onClick(View v) {
               	
					final EditText editText_bt_result = (EditText) findViewById(R.id.editText_bt_result);
					editText_bt_result.setText("");
           		
               }
           });
         
	}
	
	public void setResultText(String s){
		
		final EditText editTextResult = (EditText) findViewById(R.id.editText_bt_result);
		
		// insert from top
//		Editable edit = editTextResult.getEditableText();
//		edit.insert(0, s);
		
		// append to end
		editTextResult.append(s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bt, menu);
		return true;
	}

}
