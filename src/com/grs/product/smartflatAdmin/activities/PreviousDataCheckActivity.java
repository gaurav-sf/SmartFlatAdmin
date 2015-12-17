package com.grs.product.smartflatAdmin.activities;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class PreviousDataCheckActivity extends Activity {
	
	private Bundle extras;
	private String societyCode;
	private SmartFlatAdminDBManager dbManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_data_check);
		extras = getIntent().getExtras();
		societyCode = extras.getString("societyCode");
		checkIfUserExists();
	}
	
	private void checkIfUserExists()
	{
		//First time user is logging in
		if(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()!=null
				||SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences().equals(null))
		{
			
		}
		else if(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences().equals(societyCode))
		{
			gotoDashBoard();
		}else{
			
		}
	}
	
	private void gotoDashBoard(){
		Intent intentDashboard = new Intent(PreviousDataCheckActivity.this, DashBoardActivity.class);
		startActivity(intentDashboard);
		finish();
	}

}
