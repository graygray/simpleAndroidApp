package com.example.simpleandroidapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import android.os.SystemProperties;

public class SimpleAndroidApp extends Activity {
	
	public static final String PREFS_NAME = "SAA_PrefsFile";
	
	Handler handler;
	boolean isStopWhile;
	public static String inputDevicePath;
	public static final int MSG_UPDATEEDITTEXT = 9000;
	Message m;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple_android_app);
		
		// Restore preferences
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		inputDevicePath = settings.getString("inputDevicePath", "/dev/input/event0");
		
		handler = new Handler() {

			public void handleMessage(Message msg) {
				switch (msg.what) {
				case MSG_UPDATEEDITTEXT:
					setResultText((String)msg.obj);
					break;

				}
				super.handleMessage(msg);
			}

		};
		
		// Toast
		final Button button_toast = (Button) findViewById(R.id.button_toast);
        button_toast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	Toast.makeText(getApplicationContext(),"Toast test~!", Toast.LENGTH_LONG).show();
        		final EditText editText_result = (EditText) findViewById(R.id.editText_result);
        		editText_result.setText("");
        		
            }
        });
        
		// stop
		final Button button_stop = (Button) findViewById(R.id.button_stop);
		button_stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
        		isStopWhile = true;
            }
        });
        
        // JNI
		final Button button_jni = (Button) findViewById(R.id.button_jni);
        button_jni.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	isStopWhile = false;
            	
            	try {
            		System.loadLibrary("JNIinterface");
            	 	String temp = "use JNI to read " + inputDevicePath + "\n";
    				m = new Message();
    				m.what = MSG_UPDATEEDITTEXT;
    				m.obj = temp;
    				handler.sendMessage(m);
				} catch (Exception e) {
					String temp = "load JNI to fail\n";
    				m = new Message();
    				m.what = MSG_UPDATEEDITTEXT;
    				m.obj = temp;
    				handler.sendMessage(m);
				}
                
           	 new Thread(new Runnable() {

          		 public void run() {

						while (!isStopWhile) {

							try {
								String temps = "\n===================\n : " + readDevice(inputDevicePath);
					            	
 								m = new Message();
  								m.what = MSG_UPDATEEDITTEXT;
  								m.obj = temps;
  								handler.sendMessage(m);
  								
								Thread.sleep(100);

							} catch (InterruptedException e) {
								Log.e("gray", "SimpleAndroidApp.java: " + "InterruptedException:" + e);
								throw new RuntimeException(e);
							}

						}
					}

				}).start();

            }
        });
    
        // superuser
		final Button button_su = (Button) findViewById(R.id.button_su);
        button_su.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
               	Process p;  
               	String temp = "test su\n";
				m = new Message();
				m.what = MSG_UPDATEEDITTEXT;
				
            	try {  
            	   // Perform su to get root privilege  
            	   p = Runtime.getRuntime().exec("/system/xbin/su");   
            	  
            	   // write a file to root dir   
            	   DataOutputStream os = new DataOutputStream(p.getOutputStream());  
            	   os.writeBytes("mount -o rw,remount /\n");
            	   os.writeBytes("ls /system > /zzz\n");
            	  
            	   // Close the terminal  
            	   os.writeBytes("exit\n");  
            	   os.flush();  
            	   
            	   try {  
            	      p.waitFor();  
            	           if (p.exitValue() != 255) {  
            	        	   // TODO Code to run on success  
            	        	   Log.e("gray", "SimpleAndroidApp.java: " + "root:" + p.exitValue());
            	        	   temp += "root\n";
            	           }  
            	           else {  
            	        	   // TODO Code to run on unsuccessful
            	        	   temp += "not root\n";
            	        	   Log.e("gray", "SimpleAndroidApp.java: " + "not root" + p.exitValue());
            	           }  
            	   } catch (InterruptedException e) {  
            	      // TODO Code to run in interrupted exception  
            		   temp += "not root2\n";
    	        	   Log.e("gray", "SimpleAndroidApp.java: " + "not root");
            	   }  
            	} catch (IOException e) {  
            	   // TODO Code to run in input/output exception  
            		temp += "not root3\n";
 	        	   Log.e("gray", "SimpleAndroidApp.java: " + "not root");
            	}  
            	
            	m.obj = temp;
				handler.sendMessage(m);
 
            }
        });

        // start service in init.rc
        // need to build new image
		final Button button_services = (Button) findViewById(R.id.button_services);
        button_services.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
            	String temp = "start service in init.rc\n";
				m = new Message();
				m.what = MSG_UPDATEEDITTEXT;
				m.obj = temp;
				handler.sendMessage(m);
				
//            	SystemProperties.set("ctl.start", "p2pifdown");
            	
            }
        });
       
        // power off
		final Button button_poweroff = (Button) findViewById(R.id.button_poweroff);
		button_poweroff.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
//				Intent intent = new Intent(Intent.ACTION_REQUEST_SHUTDOWN);
//				intent.putExtra(Intent.EXTRA_KEY_CONFIRM, false);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
				
				Intent i = new Intent(Intent.ACTION_REBOOT);
				i.putExtra("nowait", 1);
				i.putExtra("interval", 1);
				i.putExtra("window", 0);
				sendBroadcast(i);	
				
            }
        });
        
		// goto setting page
 		final Button button_settings = (Button) findViewById(R.id.button_settings);
 		button_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
            	Intent intent = new Intent(); 
            	intent.setClass(SimpleAndroidApp.this, SettingsActivity.class); 
            	startActivity(intent); 
            	
            }
        });
 		
 		// goto WiFi page
 		final Button button_wifi = (Button) findViewById(R.id.button_wifi);
 		button_wifi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
            	Intent intent = new Intent(); 
            	intent.setClass(SimpleAndroidApp.this, WIFIActivity.class); 
            	startActivity(intent); 
            	
            }
        });
         
 		// goto BT page
 		final Button button_bt = (Button) findViewById(R.id.button_bt);
 		button_bt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
            	Intent intent = new Intent(); 
            	intent.setClass(SimpleAndroidApp.this, BTActivity.class); 
            	startActivity(intent); 
            	
            }
        });
 		
 		// goto power manager page
 		final Button button_pm = (Button) findViewById(R.id.button_pm);
 		button_pm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
            	Intent intent = new Intent(); 
            	intent.setClass(SimpleAndroidApp.this, PowerManngerActivity.class); 
            	startActivity(intent); 
            	
            }
        });
 		
  		// getevent
  		final Button button_getevent = (Button) findViewById(R.id.button_getevent);
  		button_getevent.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {

            	  isStopWhile = false;
            	  
            	  new Thread(new Runnable() {

            		  public void run() {

						Process process = null;
						String temp, line;
						
						temp = "getevent -tdl " + inputDevicePath + "\n";
						m = new Message();
						m.what = MSG_UPDATEEDITTEXT;
						m.obj = temp;
						handler.sendMessage(m);
						
						try {
								process = Runtime.getRuntime().exec("/system/xbin/su");
//								process = Runtime.getRuntime().exec("getevent "+ inputDevicePath );
								// write a file to root dir
								
//								DataOutputStream os = new DataOutputStream(process.getOutputStream());
////								os.writeBytes("getevent -tdl" + inputDevicePath + " \n");
//								os.writeBytes("getevent " + inputDevicePath + " \n");
//								os.write(cmd.getBytes());
								
								// Close the terminal
//								os.writeBytes("exit\n");
//								os.flush();
								
								OutputStream out = process.getOutputStream();
//								String cmd = "getevent " + "/dev/input/event0" + " \n";
//								String cmd = "getevent " + inputDevicePath + " \n";
								String cmd = "getevent \n";
								cmd += "exit \n";
								out.write(cmd.getBytes());
								
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
						
						try {
							while ((line = reader.readLine()) != null) {
								Log.e("gray", line);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						InputStreamReader errstreamreader = new InputStreamReader(
//								process.getErrorStream());
//						BufferedReader errReader = new BufferedReader(
//								errstreamreader);
//						errReader.read();
//						while ((line = errReader.readLine()) != null) {
//							Log.i("MyApp", line);
//						}
					  
//						try {
//							while ((line = reader.readLine()) != null && !isStopWhile) {
//								Log.e("gray", line);
//								line += "\n";
//								m = new Message();
//								m.what = MSG_UPDATEEDITTEXT;
//								m.obj = line;
//								handler.sendMessage(m);
//							}
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					  
// 						while (!isStopWhile) {
// 							
//							try {
//								temp = reader.readLine();
//								if (temp != null) {
//									
//									if (temp.isEmpty()) {
//										Thread.sleep(100);
//										continue;
//									} else {
//										temp += "\n";
//									}
//								} else {
//									temp = "null \n";
//								}
//								m = new Message();
//								m.what = MSG_UPDATEEDITTEXT;
//								m.obj = temp;
//								handler.sendMessage(m);
//
//								Thread.sleep(100);
//							} catch (InterruptedException e) {
//								throw new RuntimeException(e);
//							} catch (IOException e1) {
//								e1.printStackTrace();
//							}
// 						}
 						
						try {
							
							temp = "getevent stop!\n";
							m = new Message();
							m.what = MSG_UPDATEEDITTEXT;
							m.obj = temp;
							handler.sendMessage(m);
							
							process = Runtime.getRuntime().exec("busybox killall getevent");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
 					}
 				}).start();

              }
          });
  		
 		// hidRead
  		final Button button_hidread = (Button) findViewById(R.id.button_hidread);
  		button_hidread.setOnClickListener(new View.OnClickListener() {
              public void onClick(View v) {

            	  isStopWhile = false;
            	  
            	  new Thread(new Runnable() {

            		  public void run() {

						Process process = null;
						String temp;
						
						temp = "hidRead " + inputDevicePath + "\n";
						m = new Message();
						m.what = MSG_UPDATEEDITTEXT;
						m.obj = temp;
						handler.sendMessage(m);
						
 						while (!isStopWhile) {

  							try {
  								process = Runtime.getRuntime().exec("hidRead " + inputDevicePath);

  								BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
  								int read;
  								char[] buffer = new char[2048];
  								StringBuffer output = new StringBuffer();
  								while ((read = reader.read(buffer)) > 0) {
  									output.append(buffer, 0, read);
  								}
  								reader.close();
  								// Waits for the command to finish.
  								process.waitFor();
  								Message m = new Message();
  								m.what = MSG_UPDATEEDITTEXT;
  								m.obj = output.toString();
  								handler.sendMessage(m);
  								Thread.sleep(100);
  								
  							} catch (IOException e) {
  								Log.e("gray", "SimpleAndroidApp.java: "	+ "IOException:" + e);
  								throw new RuntimeException(e);
  							} catch (InterruptedException e) {
  								Log.e("gray", "SimpleAndroidApp.java: " + "InterruptedException:" + e);
  								throw new RuntimeException(e);
  							}

  						} 
 						
 						temp =  "hidRead stop!\n";
						m = new Message();
						m.what = MSG_UPDATEEDITTEXT;
						m.obj = temp;
						handler.sendMessage(m);

 					}
 				}).start();

              }
          });
  		
  		// test shell
 		final Button button_shell = (Button) findViewById(R.id.button_shell);
 		button_shell.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
				String temp = "go sh,\nex:sh /system/bin/goShell\n";
				
				m = new Message();
				m.what = MSG_UPDATEEDITTEXT;
				m.obj = temp;
				handler.sendMessage(m);
				
               	try {
             		Process process;
             	    process = Runtime.getRuntime().exec("sh /download/myShell.sh");
             		
             	    BufferedReader reader = new BufferedReader(
             	            new InputStreamReader(process.getInputStream()));
             	    int read;
             	    char[] buffer = new char[8192];
             	    StringBuffer output = new StringBuffer();
             	    while ((read = reader.read(buffer)) > 0) {
             	        output.append(buffer, 0, read);
             	    }
             	    reader.close();
             
             	    // Waits for the command to finish.
             	    process.waitFor();
             	    
					Log.e("gray", "SimpleAndroidApp.java: process.exitValue():"  + process.exitValue());
             	    
             	} catch (IOException e) {
             		Log.e("gray", "SimpleAndroidApp.java: " + "IOException:" + e);
             	    throw new RuntimeException(e);
             	} catch (InterruptedException e) {
             		Log.e("gray", "SimpleAndroidApp.java: " + "InterruptedException:" + e);
             		throw new RuntimeException(e);
             	}
            	
            }
        }); 	
 		
 		// keyevent
		final Button button_keyevent = (Button) findViewById(R.id.button_keyevent);
		button_keyevent.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
 
            	Intent intent = new Intent(); 
            	intent.setClass(SimpleAndroidApp.this, KeyEventActivity.class); 
            	startActivity(intent);
            	
            }
        });
 		 
  		
	}
	
	public void setResultText(String s){
		
		if (s == null) {
			return;
		}
		
		final EditText editText_result = (EditText) findViewById(R.id.editText_result);
		
		// append to end
		editText_result.append(s);
	}
	
	public void showAlertDialog(String title, String message) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(SimpleAndroidApp.this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
	}
	
	@Override
	public void onPause() {
		
		isStopWhile = true;
		
		super.onPause();
	}
	
	// JNI function
	public native int test(int  x);
	public native String readDevice(String path);

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_simple_android_app, menu);
		return true;
	}

}
