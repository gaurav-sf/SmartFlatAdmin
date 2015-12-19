package com.grs.product.smartflatAdmin.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.GetPreviousDataTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class PreviousDataCheckActivity extends Activity {
	
	private Bundle extras;
	private String societyCode;
	private SmartFlatAdminDBManager dbManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_previous_data_check);
		dbManager = new SmartFlatAdminDBManager();
		extras = getIntent().getExtras();
		societyCode = extras.getString("societyCode");
		checkIfUserExists();
	}
	
	private void checkIfUserExists()
	{
		//First time user is logging in
		if(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()==null
				||SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences().equals(null))
		{
			dbManager.deleteDataFromAllTables();
			getSocietyData();	
		}
		else if(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences().equals(societyCode))
		{
			gotoDashBoard();
		}else{
			dbManager.deleteDataFromAllTables();
			getSocietyData();			
		}
	}
	
	private void gotoDashBoard(){
		Intent intentDashboard = new Intent(PreviousDataCheckActivity.this, DashBoardActivity.class);
		startActivity(intentDashboard);
		finish();
	}
	
	private void getSocietyData(){
		if (NetworkDetector.init(getApplicationContext()).isNetworkAvailable()) 
		{
			new GetPreviousDataTask(PreviousDataCheckActivity.this, new GetPreviousDataTaskListener(), societyCode)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(PreviousDataCheckActivity.this,"Error", "Please check your Internet");
		}			
	}
	
	public class GetPreviousDataTaskListener implements AsyncTaskCompleteListener<Response> {

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(PreviousDataCheckActivity.this, "", false);		
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
					SmartFlatAdminApplication.saveSocietyCodeInSharedPreferences(societyCode);
					gotoDashBoard();					
				}else{
					
				}
			}	
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();	
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			Utilities.ShowAlertBox(PreviousDataCheckActivity.this,"Error",e.getMessage());		
			CustomProgressDialog.removeDialog();	
		}
		
	}

}
