package com.grs.product.smartflatAdmin.activities;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.RegistrationTask;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class SocietyRegistrationStep1 extends Activity {
	
	private EditText mEditTextName, mEditTextAddressLine1, mEditTextAddressLine2, mEditTextBuildingName, mEditTextTotalFloors;
	private EditText mEditTextCity,mEditTextState, mEditTextPIN;
	private Button mButtonNext, mButtonAddEditText;
	private List<EditText> mListAllBuildingNameEdittext;
	public static SocietyDetails mSocietyDetails;
	private LinearLayout mEdittextContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_society_registration_step1);
		initializeUI();
		addListeners();
	}
	
	private void initializeUI(){
		mEditTextName = (EditText) findViewById(R.id.editTextSocietyName);
		mEditTextAddressLine1 = (EditText) findViewById(R.id.editTextAddressLine1);
		mEditTextAddressLine2 = (EditText) findViewById(R.id.editTextAddressLine2);
		mEditTextCity = (EditText) findViewById(R.id.editTextCity);
		mEditTextState = (EditText) findViewById(R.id.editTextState);
		mEditTextPIN = (EditText) findViewById(R.id.editTextPin);	
		mEditTextBuildingName = (EditText) findViewById(R.id.editTextBuildingName);	
		mEditTextTotalFloors = (EditText) findViewById(R.id.editTextTotalFloors);	
		mButtonNext = (Button) findViewById(R.id.buttonNext);
		mButtonAddEditText = (Button) findViewById(R.id.buttonAddEditText);
		mListAllBuildingNameEdittext = new ArrayList<EditText>();
		mListAllBuildingNameEdittext.add(mEditTextBuildingName);
		mEdittextContainer = (LinearLayout) findViewById(R.id.layoutEditTextContainer);
		mSocietyDetails = new SocietyDetails();
	}
	
	private void addListeners(){
		
		mButtonNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(isValidateUiEntries()){					
					saveSocietyData();
					sendDataToServer();
				}
			}
		});	
		
		mButtonAddEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText temp = createNewEditText();
				mEdittextContainer.addView(temp);
				mListAllBuildingNameEdittext.add(temp);
			}
		});
	}
	
	private boolean isValidateUiEntries(){
		if(mEditTextName.getText().toString().equals(""))
		{
			mEditTextName.setError("Please enter your full name");
			return false;
		}
		if(mEditTextAddressLine1.getText().toString().equals(""))
		{
			mEditTextAddressLine1.setError("Please enter address line 1");
			return false;
		}
		if(mEditTextCity.getText().toString().equals(""))
		{
			mEditTextCity.setError("Please enter city");
			return false;
		}
		if(mEditTextState.getText().toString().equals(""))
		{
			mEditTextState.setError("Please enter state");
			return false;
		}
		if(mEditTextPIN.getText().toString().equals(""))
		{
			mEditTextPIN.setError("Please enter PIN code");
			return false;
		}
		if(mEditTextBuildingName.getText().toString().equals(""))
		{
			mEditTextBuildingName.setError("Please enter building name");
			return false;
		}
		if(mEditTextTotalFloors.getText().toString().equals(""))
		{
			mEditTextTotalFloors.setError("Please enter total floors");
			return false;
		}
		return true;		
	}
	
	private void saveSocietyData(){
		mSocietyDetails.setmSocietyName(mEditTextName.getText().toString());
		mSocietyDetails.setmSocietyAddressLine1(mEditTextAddressLine1.getText().toString());
		mSocietyDetails.setmSocietyAddressLine2(mEditTextAddressLine2.getText().toString());
		mSocietyDetails.setmSocietyAddressCity(mEditTextCity.getText().toString());
		mSocietyDetails.setmSocietyAddressState(mEditTextState.getText().toString());
		mSocietyDetails.setmSocietyAddressPIN(mEditTextPIN.getText().toString());
		String buildingNames = "";
		for (int i = 0; i < mListAllBuildingNameEdittext.size(); i++) {
			if (i==0) {
				buildingNames = mListAllBuildingNameEdittext.get(i).getText().toString();	
			}else{
				buildingNames = buildingNames + "@" +mListAllBuildingNameEdittext.get(i).getText().toString();
			}
		}
		mSocietyDetails.setmBuildingName(buildingNames.trim());
		mSocietyDetails.setmTotalFloorNumber(Integer.parseInt(mEditTextTotalFloors.getText().toString()));		
	}
	
	private void gotoNextActivity(String societyCode){
		Intent intentSocietyRegistrationStep2 = new Intent(SocietyRegistrationStep1.this, SocietyRegistrationStep2.class);
		intentSocietyRegistrationStep2.putExtra("societyCode", societyCode);
		startActivity(intentSocietyRegistrationStep2);
		finish();
	}
	
	private void sendDataToServer(){

		if (NetworkDetector.init(getApplicationContext()).isNetworkAvailable()) 
		{
			new RegistrationTask(getApplicationContext(), new RegistrationTaskListener(),
					SocietyOwnerRegistration.mSocietyOwnerDetails, 
					SocietyRegistrationStep1.mSocietyDetails)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(SocietyRegistrationStep1.this,"Error", "No internet connection");
		}			
	
	}
	
	public class RegistrationTaskListener implements AsyncTaskCompleteListener<Response>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(SocietyRegistrationStep1.this, "", false);
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
				//	SmartFlatAdminApplication.saveSocietyCodeInSharedPreferences(result.getSocietycode());
					gotoNextActivity(result.getSocietycode());
					
				}else
				{
					Utilities.ShowAlertBox(SocietyRegistrationStep1.this, "Error", "Server Error. Please try after some time.");				
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
			if(e!=null){
				Utilities.ShowAlertBox(SocietyRegistrationStep1.this, "Error", "Server Error. Please try after some time.");							
			}
		}	
	}
	
	private EditText createNewEditText() {
	    final LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    lparams.setMargins(0, 10, 0, 0);
	    final EditText edittext = new EditText(this);    
	    edittext.setLayoutParams(lparams);
	    edittext.setBackgroundResource(R.drawable.trans_edittext_bg);
	    edittext.setHintTextColor(getResources().getColor(R.color.text_hint_color));
	    edittext.setEms(10);
	    edittext.setPadding(5, 5, 5, 5);
	    
	    edittext.setHint("Building Name");
	    return edittext;
	}
}
