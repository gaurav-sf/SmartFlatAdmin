package com.grs.product.smartflatAdmin.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.SaveSocietyOwnerCredentialTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class SocietyRegistrationStep2 extends Activity {
	
	private EditText mEditTextUsername, mEditTextPassword, mEditTextAnswer;
	private Spinner mSpinnerSecurityQue ;
	private Button buttonSubmit;
	private Bundle extras;
	String[] security_Questions = {
			"What is your nick name?",
			"What is your birth place?",
			"Who is your favorite cricket player?",
			"What is your favorite color",

			};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_society_registration_step2);
		 extras = getIntent().getExtras();
		initializeUI();
		addListeners();
		createSpinnerData();
	}
	
	private void initializeUI()
	{
		mEditTextUsername = (EditText) findViewById(R.id.editTextUsername);
		mEditTextUsername.setText(extras.getString("societyCode"));
		mEditTextPassword = (EditText) findViewById(R.id.editTextPassword); 
		mSpinnerSecurityQue =  (Spinner) findViewById(R.id.spinnerSecurityQue); 		mEditTextAnswer =  (EditText) findViewById(R.id.editTextAnswer);
		buttonSubmit = (Button) findViewById(R.id.buttonSubmit);	
	}
	
	private void addListeners(){
		buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(isValidateUiEntries()){
					saveUserData();
					sendDataToServer();			
				}
			}
		});
	}
	
	private boolean isValidateUiEntries()
	{
		if(mEditTextPassword.getText().toString().equals("")){
			mEditTextPassword.setError("Please enter password");
			return false;
		}
		if(mEditTextAnswer.getText().toString().equals("")){
			mEditTextAnswer.setError("Please enter answer");
			return false;
		}

		return true;	
	}
	
	private void sendDataToServer(){

		if (NetworkDetector.init(getApplicationContext()).isNetworkAvailable()) 
		{
			new SaveSocietyOwnerCredentialTask(getApplicationContext(), 
					new SaveSocietyOwnerCredentialTaskListener(), 
					SocietyRegistrationStep1.mSocietyDetails)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(SocietyRegistrationStep2.this,"Error", "Please check your Internet");
		}			
	
	}
	
	private void saveUserData()
	{
		SocietyRegistrationStep1.mSocietyDetails.setmUsername(mEditTextUsername.getText().toString());
		SocietyRegistrationStep1.mSocietyDetails.setmPassword(mEditTextPassword.getText().toString());
		SocietyRegistrationStep1.mSocietyDetails.setmSecurityQuestion(mSpinnerSecurityQue.getSelectedItem().toString());
		SocietyRegistrationStep1.mSocietyDetails.setmAnswer(mEditTextAnswer.getText().toString());
	}
	
	public class SaveSocietyOwnerCredentialTaskListener implements AsyncTaskCompleteListener<Response>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(SocietyRegistrationStep2.this, "", false);			
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
				//	SmartFlatAdminApplication.saveSocietyCodeInSharedPreferences(extras.getString("societyCode"));
				//	saveDataInDB();
					gotoNextActivity();
					
				}else{
					Utilities.ShowAlertBox(SocietyRegistrationStep2.this, "Error", "Server Error. Please try after some time.");				
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
			Utilities.ShowAlertBox(SocietyRegistrationStep2.this, "Error", "Server Error. Please try after some time.");
		}
		
	}
	
	private void gotoNextActivity(){
		Intent intentLogin = new Intent(SocietyRegistrationStep2.this, LoginActivity.class);
		startActivity(intentLogin);
		finish();
	}
	
	private void saveDataInDB(){
		SmartFlatAdminDBManager objDbManager = new SmartFlatAdminDBManager();
		boolean isAdded = objDbManager.saveSocietyOwnerDetails(SocietyOwnerRegistration.mSocietyOwnerDetails);
		if(isAdded){
			Log.e("Society Owner Details", "Inserted Successfully");
		}
		isAdded = objDbManager.saveSocietyDetails(SocietyRegistrationStep1.mSocietyDetails);
		if(isAdded){
			Log.e("Society Details", "Inserted Successfully");
		}
	}
	
	private void createSpinnerData(){
		//Later the values for the spinner will come from the database. 
		//The values which we are going to save after validation of society code
		List<String> listSecurityQuestion = new ArrayList<String>();
		for (int i = 0; i < security_Questions.length; i++) {
			listSecurityQuestion.add(security_Questions[i]);
			
		}
		ArrayAdapter<String> securityQuestion = new ArrayAdapter<String>
		(this,R.layout.spinner_item, listSecurityQuestion);
		securityQuestion.setDropDownViewResource(R.layout.spinner_item);		
		mSpinnerSecurityQue.setAdapter(securityQuestion);

}

}
