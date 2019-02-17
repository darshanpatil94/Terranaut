package com.example.terranaut;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.teranaut.R;

public class About extends Activity
{
	private TextView sTextView;
	private String str1,str2,str3;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		sTextView = (TextView)findViewById(R.id.arm1);
		str1="Android app developed to control the bot via bluetooth. ";
		str2="Developed by: " + '\n'+ "Darshan Patil"+ '\n' + "Department of Electronics and Communication";
		str3="B. V. Bhoomaraddi College of Engineering and Technology";
		
		sTextView.setText("About:" + '\n'+'\n' +"TERRANAUT 7.0"
		+ '\n'+'\n' + str1 + '\n'+'\n' + str2 +'\n' + str3);	
	}

	/*
	@Override
	protected void onStart() 
	{
		super.onStart();
		sTextView = (TextView)findViewById(R.id.textView1);
		str1="This is an bluetooth controlled android app "
				+ "developed by EC department 5th sem students;";
		str2="Darshan Kamran Ajay Akhil. ";
		sTextView.setText("About:" + str1 + str2); 
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		/*sTextView = (TextView)findViewById(R.id.textView1);
		str1="This is an bluetooth controlled android app developed by EC department 5th sem students;";
		str2='\n'+ " Darshan" + '\n'+ " Kamran"+'\n' + "Ajay"+ '\n' + "Akhil";
		sTextView.setText("About:"+ '\n' + str1 + str2);
		
	}*/

	@Override
	public void onBackPressed() 
	{
		//super.onBackPressed();
		finish();
	}
	
	@Override
	protected void onStop() 
	{
		super.onStop();
		finish();
	}
	@Override
	protected void onDestroy() 
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
   
}
