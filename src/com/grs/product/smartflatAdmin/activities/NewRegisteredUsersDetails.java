package com.grs.product.smartflatAdmin.activities;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.ActivateFlatOwnerTask;
import com.grs.product.smartflatAdmin.asynctasks.UpdateFlatUserDetailsTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyDetails;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class NewRegisteredUsersDetails extends Activity{
	
	private Bundle extras;
	private String mFlatOwnerCode;
	private FlatOwnerDetails mFlatOwnerDetails;
	private EditText mEditTextName, mEditTextDOB, mEditTextContactNo, mEditTextEmailId, mEditTextFlatNo;
	private Spinner mSpinnerBuildingName, mSpinnerFloorNo;
	private Button mButtonEdit, mButtonActivate;
	private RadioButton mRadioButtonMale, mRadioButtonFemale;
	private RadioGroup mRadioGroupGender;
	private SocietyDetails mSocietyDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registered_user_details);
		extras = getIntent().getExtras();
		mFlatOwnerCode = extras.getString("flatOwnerCode");
		getFlatOwnerData(mFlatOwnerCode);	
		initialiseUI();
		getSocietyDetails();
		showData();
		addListener();
	}
	
	private void initialiseUI(){
		mEditTextName = (EditText) findViewById(R.id.editTextName);
		mEditTextDOB = (EditText) findViewById(R.id.editTextDOB);
		mEditTextContactNo = (EditText) findViewById(R.id.editTextContactNo);
		mEditTextEmailId =  (EditText) findViewById(R.id.editTextEmailId);
		mEditTextFlatNo = (EditText) findViewById(R.id.editTextFlatNo);
		mSpinnerBuildingName = (Spinner) findViewById(R.id.spinnertBuildingName);
		mSpinnerFloorNo = (Spinner) findViewById(R.id.spinnerTextFloorNo);
		mButtonEdit = (Button) findViewById(R.id.buttonEdit);
		mButtonActivate = (Button) findViewById(R.id.buttonActivate);
		mRadioGroupGender = (RadioGroup) findViewById(R.id.RadioGroupGender);
		mRadioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
		mRadioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
	}
	
	private void showData(){
		mEditTextName.setText(mFlatOwnerDetails.getmFlatOwnerName());
		mEditTextDOB.setText(mFlatOwnerDetails.getmFlatOwnerDOB());
		mEditTextContactNo.setText(mFlatOwnerDetails.getmFlatOwnerContactNo());
		mEditTextEmailId.setText(mFlatOwnerDetails.getmFlatOwnerEmailId());
		mEditTextFlatNo.setText(mFlatOwnerDetails.getmFlatno());
		if(mFlatOwnerDetails.getmGender().equalsIgnoreCase("Male")){
			mRadioButtonMale.setChecked(true);
		}else{
			mRadioButtonFemale.setChecked(true);
		}		
	}
	
	private void addListener(){
		mButtonEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mButtonEdit.getText().toString().equalsIgnoreCase("EDIT")){
					mButtonEdit.setText("UPDATE");
					enableAllUIFields();		
				}else{
					getUpdatedUserDetails();
					updateUserData();
				}
						
			}
		});
		
		mButtonActivate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				activateFlatOwnerCall(mFlatOwnerCode);				
			}
		});
	}
	
	
	private void getFlatOwnerData(String flatOwnerCode){

		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		Cursor details = objManager.getSingleFlatOwnerData(flatOwnerCode);
		if(details!=null&& details.getCount()>0){
			details.moveToFirst();
			    mFlatOwnerDetails = new FlatOwnerDetails();
				mFlatOwnerDetails.setmFlatOwnerName(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_NAME)));
				mFlatOwnerDetails.setmFlatOwnerContactNo(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CONTACT_NO)));
				mFlatOwnerDetails.setmFlatOwnerEmailId(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_EMAIL_ID)));
				mFlatOwnerDetails.setmFlatOwnerDOB(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_DOB)));
				mFlatOwnerDetails.setmBuildingName(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_BUILDING_NAME)));
				mFlatOwnerDetails.setmFloorNo(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLOOR_NO)));
				mFlatOwnerDetails.setmFlatno(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_NO)));
				mFlatOwnerDetails.setmFlatOwnerCreatedDateTime(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CREATED_DATETIME)));
				mFlatOwnerDetails.setmFlatOwnerCode(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CODE)));
				mFlatOwnerDetails.setmGender(details.getString(details.getColumnIndex(TableFlatOwnerDetails.GENDER)));
				mFlatOwnerDetails.setActive(Boolean.parseBoolean(details.getString(details.getColumnIndex(TableFlatOwnerDetails.IS_ACTIVE))));
		}
	}
	
	private void getSocietyDetails()
	{
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		Cursor details = objManager.getSocietyDetails();
		if(details!=null&& details.getCount()>0)
		{
			details.moveToFirst();
			mSocietyDetails = new SocietyDetails();
			mSocietyDetails.setmBuildingName(details.getString(details.getColumnIndex(TableSocietyDetails.BUILDING_NAME)));
			mSocietyDetails.setmTotalFloorNumber(details.getInt(details.getColumnIndex(TableSocietyDetails.TOTAL_FLOOR_NUMBER)));
			//mSocietyDetails.setmBuildingName(details.getString(details.getColumnIndex(TableSocietyDetails.BUILDING_NAME)));
			//mSocietyDetails.setmBuildingName(details.getString(details.getColumnIndex(TableSocietyDetails.BUILDING_NAME)));
			
		}
		createSpinnerData();
	}
	
	private void createSpinnerData(){
		List<String> listBuilidingName = new ArrayList<String>();
		String[] buildingName = mSocietyDetails.getmBuildingName().split("@");
		for(int i=0; i<buildingName.length;i++){
			listBuilidingName.add(buildingName[i]);	
		}
		
		//Create Adapter and set data to spinner building name
		ArrayAdapter<String> adapterBuildingName = new ArrayAdapter<String>
		(this, R.layout.spinner_item, listBuilidingName);
		adapterBuildingName.setDropDownViewResource(R.layout.spinner_item);		
		mSpinnerBuildingName.setAdapter(adapterBuildingName);
		mSpinnerBuildingName.setSelection(adapterBuildingName.getPosition(mFlatOwnerDetails.getmBuildingName()));
		//Create Adapter and set data to spinner floor no
		List<String> listFloorNo = new ArrayList<String>();
		for(int i =1; i<=mSocietyDetails.getmTotalFloorNumber();i++){
			listFloorNo.add(String.valueOf(i));	
		}
		ArrayAdapter<String> adapterFloorNo = new ArrayAdapter<String>
		(this, R.layout.spinner_item, listFloorNo);
		adapterFloorNo.setDropDownViewResource(R.layout.spinner_item);		
		mSpinnerFloorNo.setAdapter(adapterFloorNo);		
		mSpinnerFloorNo.setSelection(adapterBuildingName.getPosition(mFlatOwnerDetails.getmFloorNo()));
	}
	
	private void enableAllUIFields()
	{
		mEditTextName.setEnabled(true);
		mEditTextName.setFocusable(true);
		mEditTextDOB.setEnabled(true);
		mEditTextContactNo.setEnabled(true);
		mEditTextEmailId.setEnabled(true);
		mEditTextFlatNo.setEnabled(true);
		mSpinnerBuildingName.setEnabled(true);
		mSpinnerFloorNo.setEnabled(true);
		mRadioGroupGender.setEnabled(true);
		mRadioButtonMale.setClickable(true);
		mRadioButtonFemale.setClickable(true);
	}
	
	private void activateFlatOwnerCall(String flatOwnerCode){

		if (NetworkDetector.init(NewRegisteredUsersDetails.this).isNetworkAvailable()) 
		{
			new ActivateFlatOwnerTask(NewRegisteredUsersDetails.this, new ActivateFlatOwnerTaskListener(), flatOwnerCode)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(NewRegisteredUsersDetails.this,"Error", "Please check your Internet");
		}				
	}
	
	public class ActivateFlatOwnerTaskListener implements AsyncTaskCompleteListener<Response>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(NewRegisteredUsersDetails.this, "", false);
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result!=null) {
				if (result.getStatus().equals("success"))
				{
					Utilities.ShowAlertBox(NewRegisteredUsersDetails.this, "Message", result.getMessage());
					updateInDB(mFlatOwnerCode);
				}else if (result.getStatus().equals("failure")){
					Utilities.ShowAlertBox(NewRegisteredUsersDetails.this, "Message", result.getMessage());
				}else{
					Utilities.ShowAlertBox(NewRegisteredUsersDetails.this, "Message", "Something went wrong please try later");
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
			Utilities.ShowAlertBox(NewRegisteredUsersDetails.this, "Error", "Something went wrong please try later");
			
		}
		
	}
		
	private void updateInDB(String flatOwnerCode){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		dbManager.UpdateActivationFlag(flatOwnerCode);
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
	
	private void getUpdatedUserDetails(){
			mFlatOwnerDetails.setmFlatOwnerName(mEditTextName.getText().toString());
			mFlatOwnerDetails.setmFlatOwnerContactNo(mEditTextContactNo.getText().toString());
			mFlatOwnerDetails.setmFlatOwnerEmailId(mEditTextEmailId.getText().toString());
			mFlatOwnerDetails.setmBuildingName(mSpinnerBuildingName.getSelectedItem().toString());
			mFlatOwnerDetails.setmFloorNo(mSpinnerFloorNo.getSelectedItem().toString());
			mFlatOwnerDetails.setmFlatno(mEditTextName.getText().toString());
			String gender = "";
			if (mRadioButtonFemale.isSelected())
			{
				gender = "Female";
			}else{
				gender = "Male";
			}					
			mFlatOwnerDetails.setmGender(gender);
	
	}
	
	private void updateUserData()
	{
		if (NetworkDetector.init(getApplicationContext()).isNetworkAvailable()) 
		{
			new UpdateFlatUserDetailsTask(NewRegisteredUsersDetails.this, new UpdateFlatUserDetailsTaskListener(), mFlatOwnerDetails)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(NewRegisteredUsersDetails.this,"Error", "Please check your Internet");
		}				
	}
	
	public class UpdateFlatUserDetailsTaskListener implements AsyncTaskCompleteListener<Response> {

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(NewRegisteredUsersDetails.this, "", false);		
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
					Utilities.ShowAlertBox(NewRegisteredUsersDetails.this,"Message","User details updated successfully");
				}else{
					Utilities.ShowAlertBox(NewRegisteredUsersDetails.this,"Message",result.getMessage());	
				}
			}	
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();	
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			Utilities.ShowAlertBox(NewRegisteredUsersDetails.this,"Error",e.getMessage());		
			CustomProgressDialog.removeDialog();	
		}
		
	}

}
