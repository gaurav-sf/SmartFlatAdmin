package com.grs.product.smartflatAdmin.activities;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.activities.SocietyRegistrationStep2.SaveSocietyOwnerCredentialTaskListener;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.LoginTask;
import com.grs.product.smartflatAdmin.asynctasks.SaveSocietyOwnerCredentialTask;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity{

	private EditText mEditTextUsername, mEditTextPassword;
	private Button mButtonLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initializeUI();
		addListeners();
	}

	private void initializeUI()
	{
		mEditTextUsername = (EditText) findViewById(R.id.editTextUsername); 
		mEditTextUsername.setText(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences());
		mEditTextPassword = (EditText) findViewById(R.id.editTextPassword); 
		mButtonLogin = (Button) findViewById(R.id.buttonLogin);	
	}

	private void addListeners(){

		mButtonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Intent i = new Intent(LoginActivity.this,DashBoardActivity.class);
				//startActivity(i);
				getLoginCall();
			}
		});
	}
	
	private void getLoginCall(){

		if (NetworkDetector.init(getApplicationContext()).isNetworkAvailable()) 
		{
			new LoginTask(LoginActivity.this, new LoginTaskListener(), mEditTextUsername.getText().toString(), mEditTextPassword.getText().toString())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(LoginActivity.this,"Error", "Please check your Internet");
		}			
	
	}

	public class LoginTaskListener implements AsyncTaskCompleteListener<Response>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(LoginActivity.this, "", false);
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
					SmartFlatAdminApplication.saveSocietyOwnerAccessCodeInSharedPreferences("TEMP");
					gotoNextActivity();
				}
				else if (result.getStatus().equalsIgnoreCase("failure"))
				{
					Utilities.ShowAlertBox(LoginActivity.this, "Message", result.getMessage());
				}
				else
				{
					Utilities.ShowAlertBox(LoginActivity.this, "Error", "Server Error. Please try after some time.");				
				}
			}	
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			CustomProgressDialog.removeDialog();
			Utilities.ShowAlertBox(LoginActivity.this, "Error", "Server Error please try later");
		}

	}

	private void gotoNextActivity(){
		Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
		startActivity(intentDashboard);
		finish();
	}
}