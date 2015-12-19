package com.grs.product.smartflatAdmin.activities;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.LoginTask;
import com.grs.product.smartflatAdmin.asynctasks.SendPushTokenToServerTask;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Param;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class LoginActivity extends Activity{

	private EditText mEditTextUsername, mEditTextPassword;
	private Button mButtonLogin;
	private TextView mTextViewCreateAccount, mTextViewForgotPassword;
	private GoogleCloudMessaging gcmObj;
	String regId = "";

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
		mTextViewForgotPassword = (TextView) findViewById(R.id.textViewForgetPassword);
		mTextViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
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
		
		mTextViewCreateAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
        		Intent goToRegistration = new Intent(LoginActivity.this,SocietyOwnerRegistration.class);
        		startActivity(goToRegistration);
        		finish();
			}
		});
		
		mTextViewForgotPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {/*
        		Intent goToRegistration = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
        		startActivity(goToRegistration);
        		finish();	
			*/}
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
					//SmartFlatAdminApplication.saveSocietyOwnerAccessCodeInSharedPreferences("TEMP");
					getPushTokenFromServer(mEditTextUsername.getText().toString());
					//gotoNextActivity();
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

	private void gotoNextActivity()
	{
		/*
		Intent intentDashboard = new Intent(LoginActivity.this, DashBoardActivity.class);
		startActivity(intentDashboard);
		finish();*/
		
		Intent intentDataCheckActivity = new Intent(LoginActivity.this, PreviousDataCheckActivity.class);
		intentDataCheckActivity.putExtra("societyCode", mEditTextUsername.getText().toString());
		startActivity(intentDataCheckActivity);
		finish();			
	}
	
	private void getPushTokenFromServer(String flatOwnerCode){

		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcmObj == null) {
						gcmObj = GoogleCloudMessaging
								.getInstance(getApplicationContext());
					}
					regId = gcmObj
							.register(Param.GOOGLE_PROJ_ID);
					msg = "Registration ID :" + regId;

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				if (!TextUtils.isEmpty(regId)) {
					SmartFlatAdminApplication.saveSocietyOwnerPushTokenInSharedPreferences(regId);
					sendPushTokenToServer();
					Log.e("Push Token Success", msg);
				} else {
					Log.e("Push Token Failure", msg);
				}
			}
		}.execute(null, null, null);
}
	
	private void sendPushTokenToServer(){


		if (NetworkDetector.init(getApplicationContext()).isNetworkAvailable()) 
		{
			new SendPushTokenToServerTask(getApplicationContext(), new SendPushTokenToServerTaskListener(),regId,mEditTextUsername.getText().toString() )
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(LoginActivity.this,"Error", "Please check your Internet");
		}		
	}
	
	public class SendPushTokenToServerTaskListener implements AsyncTaskCompleteListener<Response> {

		@Override
		public void onStarted() {
		//	CustomProgressDialog.showProgressDialog(LoginActivity.this, "", false);		
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
					gotoNextActivity();
					
				}else{
					Utilities.ShowAlertBox(LoginActivity.this,"Error",result.getMessage());		
				}
			}	
		}

		@Override
		public void onStoped() {
		//	CustomProgressDialog.removeDialog();	
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			Utilities.ShowAlertBox(LoginActivity.this,"Error",e.getMessage());		
		//	CustomProgressDialog.removeDialog();	
		}
		
	}
}
