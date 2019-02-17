package com.example.terranaut;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teranaut.R;

public class MainActivity extends Activity implements SensorEventListener,Handler.Callback 
{
    private static final String TAG = "BluetoothTest";
    private BluetoothAdapter bluetoothAdapter = null;
    private static long back_pressed;
    // list of BT devices that have paired with this phone
    private Spinner foundDevicesSpinner;
    private TextView sensorValTv;
    private TextView messageTv;
    //private TextView countTv;
   // private static final int REQUEST_ENABLE_BT = 666;
    private ArrayAdapter<String> foundDevices;
    // Create a BroadcastReceiver for ACTION_FOUND
    private BroadcastReceiver bluetoothReceiver;
    private HashMap<String, BluetoothDevice> btDeviceMap 
            = new HashMap<String, BluetoothDevice>();
    private HashMap<String, BtConnectionThread> btConnThreadMap 
            = new HashMap<String, BtConnectionThread>();
    private HashMap<Integer, BtConnectionThread> btConnThreadLookup 
            = new HashMap<Integer, BtConnectionThread>();
    // well known SPP UUID
    private static final UUID MY_UUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private BluetoothSocket bluetoothSocket;
    //private boolean message=true;
    //private int ch1=0,ch2=0;
    String test="abcd";
    
    Button front, back ,arm1up, arm1down, arm2up, arm2down ;
    Boolean f=false,b=false,a1u=false,a1d=false,a2u=false,a2d=false, rxx=false;
    int count=0 ,scount=0;
    long lastUpdate=0;
    long curTime=0;
     //a TextView
  	private TextView tvx,tvy,tvz;
  	//the Sensor Manager
  	private SensorManager sManager;
  	BtConnectionThread btConnThread;
  	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        Log.d(TAG, "onCreate: bt test");
        foundDevicesSpinner = (Spinner) findViewById(R.id.foundSpin);
        sensorValTv = (TextView) findViewById(R.id.sensorVal);
        messageTv = (TextView) findViewById(R.id.msgLabel);
        //get the TextView from the layout file
        tvx = (TextView) findViewById(R.id.tvx); 
        tvy = (TextView) findViewById(R.id.tvy);
        tvz = (TextView) findViewById(R.id.tvz);
        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        configureBluetooth();       
        front = (Button)findViewById(R.id.front);
		back = (Button)findViewById(R.id.back);
		arm1up = (Button)findViewById(R.id.a1up);
		arm1down = (Button)findViewById(R.id.a1down);
		arm2up = (Button)findViewById(R.id.a2up);
		arm2down = (Button)findViewById(R.id.a2down);		
    }

    
  public void evaluate()
    {	  
	  try
	   {
	  	btConnThread = getSelectedConnThread();   
	    //Forward
    	if(rxx && f && !b && !a1u && !a1d && !a2u && !a2d )
    	{
    	//	messageTv.setText("Sending y*1#");
    		
    		if(btConnThread!=null)
    		{		       			  
    		   	btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("1"+"#").getBytes());
    		   	rxx=false;
    		 }
			
    	}
    	
    	else if(rxx && f && !b && a1u && !a1d && !a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*2#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("2"+"#").getBytes());
    		   	rxx=false;
    		 }
    		
    	}
    	else if(rxx && f && !b && a1u && !a1d && a2u && !a2d)
    	{
    		rxx=false;
    	//	messageTv.setText("Sending y*3#");
    	//	if(btConnThread!=null)
    	//	{
    		//	btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		//   	btConnThread.write(("3"+"#").getBytes());
    		//   	rxx=false;
    	//	 }
    		
    	}
    	else if(rxx && f && !b && a1u && !a1d && !a2u && a2d)
    	{
    		rxx=false;
    	//	messageTv.setText("Sending y*4#");
//    		if(btConnThread!=null)
 //   		{
   // 			btConnThread.write((tvy.getText().toString()+"*").getBytes());
   // 		   	btConnThread.write(("4"+"#").getBytes());
   // 		   	rxx=false;
   // 		 }
    		
    	}
    	else if(rxx && f && !b && !a1u && a1d && !a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*5#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("5"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	else if(rxx && f && !b && !a1u && a1d && a2u && !a2d)
    	{
    		rxx=false;
    	//	messageTv.setText("Sending y*6#");
  //  		if(btConnThread!=null)
  //  		{
  //  			btConnThread.write((tvy.getText().toString()+"*").getBytes());
  //  		   	btConnThread.write(("6"+"#").getBytes());
  //  		   	rxx=false;
  // 		 }
    	}
    	else if(rxx && f && !b && !a1u && a1d && !a2u && a2d)
    	{
    		rxx=false;
    	//	messageTv.setText("Sending y*7#");
 //    		if(btConnThread!=null)
 //   		{
 //   			btConnThread.write((tvy.getText().toString()+"*").getBytes());
 //   		   	btConnThread.write(("7"+"#").getBytes());
 //   		   	rxx=false;
 //   		 }
    	}
    	else if(rxx && f && !b && !a1u && !a1d && a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*8#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("8"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	else if(rxx && f && !b && !a1u && !a1d && !a2u && a2d)
    	{
    	//	messageTv.setText("Sending y*9#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("9"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	
    	//Backward
    	else if(rxx && !f && b && !a1u && !a1d && !a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*10#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("10"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	
    	else if(rxx && !f && b && a1u && !a1d && !a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*11#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("11"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	else if(rxx && !f && b && a1u && !a1d && a2u && !a2d)
    	{
    		rxx=false;
    	//	messageTv.setText("Sending y*12#");
 //   		if(btConnThread!=null)
 //   		{
 //   			btConnThread.write((tvy.getText().toString()+"*").getBytes());
 //   		   	btConnThread.write(("12"+"#").getBytes());
 //   		   	rxx=false;
 //   		 }
    	}
    	else if(rxx && !f && b && a1u && !a1d && !a2u && a2d)
    	{
    		rxx=false;
    	//	messageTv.setText("Sending y*13#");
  //  		if(btConnThread!=null)
  //  		{
  //  			btConnThread.write((tvy.getText().toString()+"*").getBytes());
  //  		   	btConnThread.write(("13"+"#").getBytes());
  //  		   	rxx=false;
  //  		 }
    	}
    	else if(rxx && !f && b && !a1u && a1d && !a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*14#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("14"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	else if(rxx && !f && b && !a1u && a1d && a2u && !a2d)
    	{
    		rxx=false;
    	//	messageTv.setText("Sending y*15#");
//    		if(btConnThread!=null)
//    		{
//    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
//    		   	btConnThread.write(("15"+"#").getBytes());
 //   		   	rxx=false;
 //   		 }
    	}
    	else if(rxx && !f && b && !a1u && a1d && !a2u && a2d)
    	{
    		rxx=false;
    		//messageTv.setText("Sending y*16#");
 //   		if(btConnThread!=null)
 //   		{
 //   			btConnThread.write((tvy.getText().toString()+"*").getBytes());
 //   		   	btConnThread.write(("16"+"#").getBytes());
  //  		   	rxx=false;
  //  		 }
    	}
    	else if(rxx && !f && b && !a1u && !a1d && a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*17#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("17"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	else if(rxx && !f && b && !a1u && !a1d && !a2u && a2d)
    	{
    //		messageTv.setText("Sending y*18#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write((tvy.getText().toString()+"*").getBytes());
    		   	btConnThread.write(("18"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	
    	//ARM 1 UP
    	else if(rxx && !f && !b && a1u && !a1d && !a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*19#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("19"+"#").getBytes());
    		   	rxx=false;
    		 }
    		
    	}
    	else if(rxx && !f && !b && a1u && !a1d && a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*20#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("20"+"#").getBytes());
    		   	rxx=false;
    		 }
    		
    	}
    	else if(rxx && !f && !b && a1u && !a1d && !a2u && a2d)
    	{
    	//	messageTv.setText("Sending y*21#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("21"+"#").getBytes());
    		   	rxx=false;
    		 }
    		
    	}
    	
    	//ARM 1 Down
    	else if(rxx && !f && !b && !a1u && a1d && !a2u && !a2d)
    	{
    		//messageTv.setText("Sending y*22#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("22"+"#").getBytes());
    		   	rxx=false;
    		 }    		
    	}
    	else if(rxx && !f && !b && !a1u && a1d && a2u && !a2d)
    	{
    	//	messageTv.setText("Sending y*23#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("23"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	else if(rxx && !f && !b && !a1u && a1d && !a2u && a2d)
    	{
    	//	messageTv.setText("Sending y*24#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("24"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	
    	//ARM 2 up
    	else if(rxx && !f && !b && !a1u && !a1d && a2u && !a2d)
    	{
    		//messageTv.setText("Sending y*25#");
    		if(btConnThread!=null)
    		{
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("25"+"#").getBytes());
    		   	rxx=false;
    		 }
    		
    	}
    	//ARM 2 down
    	else if(rxx && !f && !b && !a1u && !a1d && !a2u && a2d)
    	{
    	//	messageTv.setText("Sending y*26#");
    		if(btConnThread!=null)
    		{
    		//btConnThread.write((tvy.getText().toString()+"*").getBytes());
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("26"+"#").getBytes());
    		   	rxx=false;
    		 }    		
    	}   	
    	else
    	{	 		
    		if(btConnThread!=null && rxx)
    		{	
    		//	messageTv.setText("DEFAULT --  sending x*y*z*0#");
    			btConnThread.write(("0"+"*").getBytes());
    		   	btConnThread.write(("0"+"#").getBytes());
    		   	rxx=false;
    		 }
    	}
    	
    	
    }catch(Exception e)
    {
    }
    		  	    	  	
    }
    
    //when this Activity starts
    @Override
	protected void onResume() 
	{
    	//v= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		super.onResume();
		/*register the sensor listener to listen to the gyroscope sensor, use the 
		 * callbacks defined in this class, and gather the sensor information as  
		 * quick as possible*/
		sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_FASTEST);		
		listener();
		 
	}
        
    void listener()
	{		
		front.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {         	 
           	 
                if (event.getAction() == MotionEvent.ACTION_DOWN ) 
                {
               	    front.setTextColor(Color.WHITE);
                    front.setBackgroundColor(Color.parseColor("#3339FF"));
                    f=true;
                    evaluate();   
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE ) 
                {
                    front.setTextColor(Color.WHITE);
                    front.setBackgroundColor(Color.parseColor("#3339FF"));
                    f=true;
                    evaluate();
                    return true;
                } 
                 else if (event.getAction() == MotionEvent.ACTION_UP ) 
                 {
                     front.setTextColor(Color.WHITE);               
                     front.setBackgroundColor(Color.parseColor("#ff91070a")); 
                     f=false;
                   //  evaluate();
                   //  messageTv.setText("DEFAULT --  sending x*y*z*0#");
             		if(btConnThread!=null)
             		{			
             			btConnThread.write(("0"+"*").getBytes());
             		   	btConnThread.write(("0"+"#").getBytes());
             		 }
                 }                 
                return false;
            }
        }); 
		 
		 back.setOnTouchListener(new OnTouchListener() 
		 {			
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           	 
                if (event.getAction() == MotionEvent.ACTION_DOWN ) 
                {
               	 back.setTextColor(Color.WHITE);
                    back.setBackgroundColor(Color.parseColor("#3339FF"));
                    b=true;
                    evaluate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE ) 
                {
                    back.setTextColor(Color.WHITE);
                    back.setBackgroundColor(Color.parseColor("#3339FF"));
                    b=true;
                    evaluate();
                    return true;
                }
                 else if (event.getAction() == MotionEvent.ACTION_UP ) 
                 {
                     back.setTextColor(Color.WHITE);
                     back.setBackgroundColor(Color.parseColor("#ff91070a"));
                     b=false;
                  //   evaluate();
                   //  messageTv.setText("DEFAULT --  sending x*y*z*0#");
              		if(btConnThread!=null)
              		{			
              			btConnThread.write(("0"+"*").getBytes());
              		   	btConnThread.write(("0"+"#").getBytes());
              		 }
                 } 
                return false;
            }		 
        }); 
		 
		 arm1up.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           	 
                if (event.getAction() == MotionEvent.ACTION_DOWN ) 
                {
               	 arm1up.setTextColor(Color.WHITE);
               	 arm1up.setBackgroundColor(Color.parseColor("#3334FF"));
               	 a1u=true;
               	 evaluate();
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE ) 
                {
               	 arm1up.setTextColor(Color.WHITE);
               	 arm1up.setBackgroundColor(Color.parseColor("#3334FF"));
               	 a1u=true;
               	 evaluate();
                    return true;
                } 
                 else if (event.getAction() == MotionEvent.ACTION_UP ) 
                 {
               	  arm1up.setTextColor(Color.WHITE);               
               	  arm1up.setBackgroundColor(Color.parseColor("#ff333333"));
               	  a1u=false;
               //	  evaluate();
               //	  messageTv.setText("DEFAULT --  sending x*y*z*0#");
           		if(btConnThread!=null)
           		{			
           			btConnThread.write(("0"+"*").getBytes());
           		   	btConnThread.write(("0"+"#").getBytes());
           		 }
                 }                
                return false;
            }
        }); 
		 
		 arm1down.setOnTouchListener(new OnTouchListener() 
		 {			
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           	 
                if (event.getAction() == MotionEvent.ACTION_DOWN ) 
                {
                	 arm1down.setTextColor(Color.WHITE);
                    arm1down.setBackgroundColor(Color.parseColor("#3334FF"));
                    a1d=true;
                   evaluate();   			        
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE ) 
                {
                    arm1down.setTextColor(Color.WHITE);
                    arm1down.setBackgroundColor(Color.parseColor("#3339FF"));
                    a1d=true;
                   evaluate();
                    return true;
                }
                 else if (event.getAction() == MotionEvent.ACTION_UP ) 
                 {
                     arm1down.setTextColor(Color.WHITE);
                     arm1down.setBackgroundColor(Color.parseColor("#ff333333"));
                     a1d=false;
                   //  evaluate();
                   //  messageTv.setText("DEFAULT --  sending x*y*z*0#");
              		if(btConnThread!=null)
              		{			
              			btConnThread.write(("0"+"*").getBytes());
              		   	btConnThread.write(("0"+"#").getBytes());
              		 }
                     }
                return false;
            }
			 
        }); 
		 
		 arm2up.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           	 
                if (event.getAction() == MotionEvent.ACTION_DOWN ) 
                {
               	 arm2up.setTextColor(Color.WHITE);
                    arm2up.setBackgroundColor(Color.parseColor("#3334FF"));
                    a2u=true;
                    evaluate();	    
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE ) 
                {
                    arm2up.setTextColor(Color.WHITE);
                    arm2up.setBackgroundColor(Color.parseColor("#3339FF"));
                    a2u=true;
                    evaluate();		 
                    return true;
                } 
                 else if (event.getAction() == MotionEvent.ACTION_UP ) 
                 {
                     arm2up.setTextColor(Color.WHITE);               
                     arm2up.setBackgroundColor(Color.parseColor("#ff333333"));
                     a2u=false;
                    // evaluate(); 
                   //  messageTv.setText("DEFAULT --  sending x*y*z*0#");
              		if(btConnThread!=null)
              		{			
              			btConnThread.write(("0"+"*").getBytes());
              		   	btConnThread.write(("0"+"#").getBytes());
              		 }
                 }                
                return false;
            }
        }); 
		 
		 arm2down.setOnTouchListener(new OnTouchListener() 
		 {			
            @Override
            public boolean onTouch(View v, MotionEvent event) {          	 
                if (event.getAction() == MotionEvent.ACTION_DOWN ) 
                {
               	 arm2down.setTextColor(Color.WHITE);
                    arm2down.setBackgroundColor(Color.parseColor("#3334FF"));
                    a2d=true;
                 evaluate();     
                    return true;
                }
                else if (event.getAction() == MotionEvent.ACTION_MOVE ) 
                {
                    arm2down.setTextColor(Color.WHITE);
                    arm2down.setBackgroundColor(Color.parseColor("#3334FF"));
                    a2d=true;
                   evaluate();	     
                    return true;
                }
                 else if (event.getAction() == MotionEvent.ACTION_UP ) 
                 {
                     arm2down.setTextColor(Color.WHITE);
                     arm2down.setBackgroundColor(Color.parseColor("#ff333333"));
                     a2d=false;
                  // evaluate(); 
                //   messageTv.setText("DEFAULT --  sending x*y*z*0#");
            		if(btConnThread!=null)
            		{			
            			btConnThread.write(("0"+"*").getBytes());
            		   	btConnThread.write(("0"+"#").getBytes());
            	    }
            	 }
                return false;
            }
			 
        }); 
	}   
    
  //When this Activity isn't visible anymore
  	@Override
  	protected void onStop() 
  	{
  		//unregister the sensor listener
  		sManager.unregisterListener(this);
  		super.onStop();
  	}
    
  	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) 
	{
		//Do nothing
	}

	@Override
	public void onSensorChanged(SensorEvent event) 
	{
		
		//if sensor is unreliable, return void
		if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
		{
			return;
		}		        	         
		//else it will output the Roll, Pitch and Yawn values
		//tv.setText("Orientation X (Roll) :"+ Integer.toString((int) event.values[2]) +"\n"+
		//	"Orientation Y (Pitch) :"+ Integer.toString((int) event.values[1]) +"\n"+
		// "Orientation Z (Yaw) :"+ Integer.toString((int) event.values[0]));
		//	tvx.setText(Integer.toString((int) ((event.values[2])*10)));				
		//	tvz.setText(Integer.toString((int) ((event.values[0])*10)));
		//tvy.setText(Integer.toString((int) ((event.values[1])*10)));
		
	//	int x=(int)((event.values[2])*10);
		int y=(int) ((event.values[1])*10);
	//	int z=(int) ((event.values[0])*10);
		
	    curTime=System.currentTimeMillis();
		
		if((curTime-lastUpdate) > 100)
		{
		//	long diffTime=(curTime-lastUpdate);
			lastUpdate=curTime;
	//		tvx.setText(Integer.toString(x));
   		   tvy.setText(Integer.toString(y));
   	//	  tvz.setText(Integer.toString(z));
		}
		
	}
  	
    @Override
    protected void onDestroy() 
    {
        super.onPause();
        Log.d(TAG, "onDestroy: unregistering");
        unregisterReceiver(bluetoothReceiver);
        disconnectAll();
        sManager.unregisterListener(this);
        finish();
    }

    private void configureBluetooth() 
    {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) 
        {
            Log.d(TAG, "configureBluetooth 1: bluetoothAdapter is null");
            return;
        }
        //Log.d(TAG, "configureBluetooth 2: GOT BT ADAPTER!");
        else if (!bluetoothAdapter.isEnabled()) 
        {
            /*Log.d(TAG, "configureBluetooth 3: NOT enabled - enabling");
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);*/
        	bluetoothAdapter.enable();
        } 
        else 
        {
           Log.d(TAG, "configureBluetooth 4: bluetooth already enabled ");
        }

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        foundDevices = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        
        if (pairedDevices.size() > 0) 
        {
            String key;
            for (BluetoothDevice device : pairedDevices) 
            {
                key = device.getName() + "  " + device.getAddress();
                btDeviceMap.put(key, device);
                foundDevices.add(key);
            }
            foundDevicesSpinner.setAdapter(foundDevices);
        } 
        else 
        {
            Log.d(TAG, "configureBluetooth 5: no paired devices to show");
        }
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        bluetoothReceiver = new BluetoothReceiver();
        registerReceiver(bluetoothReceiver, filter);
        test="abcd";
    }

    void connect() 
    {
        // discovery is a heavyweight process so
        // disable while making a connection
        bluetoothAdapter.cancelDiscovery();
        String addy = (String) foundDevicesSpinner.getSelectedItem();
        Log.d(TAG, "connect: address to connect:" + addy);
        BluetoothDevice btDevice = btDeviceMap.get(addy);
        if (btDevice == null) {
            Log.w(TAG, "connect: no bt device to connect to");
            return;
        }
        try {
            bluetoothSocket = btDevice
                    .createRfcommSocketToServiceRecord(MY_UUID);
            Log.d(TAG, "connect 0: socket:" + bluetoothSocket);
        } catch (IOException ioe) {
            String err = "Problem creating BT socket for " + addy;
            showMessage(Log.ERROR, err);
            Log.e(TAG, err);
            ioe.printStackTrace();
            return;
        }
        try 
        {
            bluetoothSocket.connect();
            showMessage(Log.INFO, "Successfully connected to " + addy);
        } catch (IOException ioe) {
            String err = "Problem connecting to " + addy;
            showMessage(Log.ERROR, err);
            Log.e(TAG, err);
            ioe.printStackTrace();
            return;
        }
        int threadIndex = btConnThreadLookup.size();
        BtConnectionThread btConnThread = new BtConnectionThread(
                bluetoothSocket, new Handler(this), threadIndex);
        btConnThread.start();
        btConnThreadMap.put(addy, btConnThread);
        btConnThreadLookup.put(threadIndex, btConnThread);
        Log.d(TAG, "number of connection threads:" + btConnThreadMap.size());
    }

    void disconnect() {
        BtConnectionThread btConnThread = getSelectedConnThread();
        if (btConnThread != null) {
            showMessage(Log.INFO,
                    "Disconnecting from " + btConnThread.getDeviceDisplayVal());
            btConnThread.disconnect();
            removeSelectedConnThread();
        }
    }

    private void disconnectAll() {
        for (BtConnectionThread btConnThread : btConnThreadMap.values()) {
            btConnThread.disconnect();
        }
        showMessage(Log.INFO, "Disconnecting from all devices");
        //countTv.setText("0");
    }
    
    public void showMessage(int msgType, String message) {
        if (msgType == Log.ERROR) {
            messageTv.setTextColor(Color.rgb(200, 0, 0));
        } else if (msgType == Log.WARN) {
            messageTv.setTextColor(Color.rgb(255, 255, 0));
        } else {
            messageTv.setTextColor(Color.parseColor("#00B700"));
        }
        messageTv.setText(message);
    }

    /**
     * Remove the selected thread from the map
     */
    private void removeSelectedConnThread() {
        String addy = (String) foundDevicesSpinner.getSelectedItem();
        BtConnectionThread btct = btConnThreadMap.remove(addy);
        if (btct != null) {
        }
    }

    /**
     * Get the connection thread for the currently-selected device
     * 
     * @return relevant thread
     */
    private BtConnectionThread getSelectedConnThread() 
    {
        String addy = (String) foundDevicesSpinner.getSelectedItem();
        return btConnThreadMap.get(addy);
    }
    
    void findDevices() 
    {
    	if(!bluetoothAdapter.isEnabled())
    	{
    		bluetoothAdapter.enable();
    	}
        foundDevices.clear();
        bluetoothAdapter.startDiscovery();
       Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        foundDevices = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        if (pairedDevices.size() > 0) 
        {
            String key;
            for (BluetoothDevice device : pairedDevices) 
            {
                key = device.getName() + "  " + device.getAddress();
                btDeviceMap.put(key, device);
                foundDevices.add(key);
            }
        } 
        else 
        {
            Log.d(TAG, "configureBluetooth 5: no paired devices to show");
        }
        foundDevicesSpinner.setAdapter(foundDevices);
        showMessage(Log.INFO, "");
    }
    
    class BluetoothReceiver extends BroadcastReceiver 
    {

        @Override
        public void onReceive(Context context, Intent intent) 
        {
            Log.d(TAG, "onreceive 0");
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                Log.d(TAG, "onreceive 1: found BT device");
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String key = device.getName() + "  " + device.getAddress();
                btDeviceMap.put(key, device);
                foundDevices.add(key);
                showMessage(Log.INFO, "Found Device:" + key);
            } 
            else 
            {
                Log.d(TAG, "onreceive 2: different bt action rxx");
            }
        }
    }

    @Override
    public boolean handleMessage(Message msg) 
    {
    	byte[] writeBuf = (byte[]) msg.obj;
    	int begin = (int)msg.arg1;
    	int end = (int)msg.arg2;
    	//BtConnectionThread btConnThread = getSelectedConnThread();
    	
    	switch(msg.what) 
    	{
    	case 1:	
    	String writeMessage = new String(writeBuf);
    	writeMessage = writeMessage.substring(begin, end);
    	sensorValTv.setText(writeMessage);
  	
    	//btConnThread.write((tvx.getText().toString()+"*").getBytes());
      	//btConnThread.write((tvy.getText().toString()+"*").getBytes());
       	//btConnThread.write((tvz.getText().toString()+"*").getBytes());
       	//btConnThread.write(("0"+"0"+"#").getBytes());
       	rxx=true;
       	
    	break;
    	}
    	return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) 
        { 
        
        case R.id.findbtdevices:
        	findDevices();
        	break;
        
        case R.id.conn:
        	connect();
        	break;
        	
        case R.id.discon:
        	disconnect();
        	break;
        
        case R.id.about:
        	Intent intent1 = new Intent(this, About.class);
            startActivity(intent1);
        	break;
        	
        case R.id.exit:
            Log.d(TAG, "onOptionsItemSelected: exiting via menu item");
            disconnectAll();
            bluetoothAdapter.disable();
            finish();
            break;
        }

         return super.onOptionsItemSelected(item);
    }

	@Override
	public void onBackPressed() 
	{
		 if (back_pressed + 2000 > System.currentTimeMillis())
		 {
		        super.onBackPressed();
		        disconnectAll();
	            bluetoothAdapter.disable();
	            finish();
		  }
		    else
		    {
		     Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
		     back_pressed = System.currentTimeMillis();
		    }
	}
	
}

