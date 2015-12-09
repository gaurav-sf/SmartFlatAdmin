package com.grs.product.smartflatAdmin.activities;

import java.util.Calendar;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.models.SocietyOwnerDetails;
import com.grs.product.smartflatAdmin.utils.Utilities;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SocietyOwnerRegistration extends Activity {
	
	private EditText mEditTextName, mEditTextDOB, mEditTextAge, mEditTextContactNo, mEditTextEmailId, mEditTextAddressLine2, mEditTextAddressLine1;
	private EditText mEditTextCity,mEditTextState, mEditTextPIN;
	private Button mButtonNext;
	public static SocietyOwnerDetails mSocietyOwnerDetails;
	private RadioButton mRadioButtonMale, mRadioButtonFemale;
	private RadioGroup mRadioGroupGender;
	 private Calendar cal;
	 private int currentDay;
	 private int currentMonth;
	 private int currentYear;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_society_owner_registration);
		initializeUI();
		addListeners();
	}
	
	private void initializeUI(){
		mEditTextName = (EditText) findViewById(R.id.editTextName);
		mEditTextDOB = (EditText) findViewById(R.id.editTextDOB);
		mEditTextAge = (EditText) findViewById(R.id.editTextAge);
		mEditTextAge.setText("25"); 
		mEditTextAge.setVisibility(View.GONE);
		mEditTextContactNo = (EditText) findViewById(R.id.editTextContactNo);
		mEditTextEmailId =  (EditText) findViewById(R.id.editTextEmailId);
		mEditTextAddressLine1 = (EditText) findViewById(R.id.editTextAddressLine1);
		mEditTextAddressLine2 = (EditText) findViewById(R.id.editTextAddressLine2);
		mEditTextCity = (EditText) findViewById(R.id.editTextCity);
		mEditTextState = (EditText) findViewById(R.id.editTextState);
		mEditTextPIN = (EditText) findViewById(R.id.editTextPin);	
		mButtonNext = (Button) findViewById(R.id.buttonNext);
		mRadioGroupGender = (RadioGroup) findViewById(R.id.RadioGroupGender);
		mRadioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
		mRadioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
		mSocietyOwnerDetails = new SocietyOwnerDetails();
		  cal = Calendar.getInstance();
		  currentDay = cal.get(Calendar.DAY_OF_MONTH);
		  currentMonth = cal.get(Calendar.MONTH);
		  currentYear = cal.get(Calendar.YEAR);
	}
	
	private void addListeners(){
		
		mButtonNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(isValidateUiEntries()){					
					saveSocietyOwnerData();
					gotoNextActivity();
				}
			}
		});	
		
		mEditTextDOB.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(0);
				
			}
		});
	}
	
	private boolean isValidateUiEntries(){
		if(mEditTextName.getText().toString().equals(""))
		{
			mEditTextName.setError("Please enter your full name");
			return false;
		}
		if(mEditTextDOB.getText().toString().equals(""))
		{
			mEditTextDOB.setError("Please enter your DOB");
			return false;
		}
		if(mEditTextAge.getText().toString().equals(""))
		{
			mEditTextAge.setError("Please enter your Age");
			return false;
		}
		if(mEditTextContactNo.getText().toString().equals(""))
		{
			mEditTextContactNo.setError("Please enter your contact no");
			return false;
		}
		if(mEditTextEmailId.getText().toString().equals(""))
		{
			mEditTextEmailId.setError("Please enter your email id");
			return false;
		}else{
			if(!Utilities.isValidEmail(mEditTextEmailId.getText().toString())){
				mEditTextEmailId.setError("Please enter valid email id");	
				return false;
			}
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
		return true;		
	}
	
	private void saveSocietyOwnerData(){
		mSocietyOwnerDetails.setmSocietyOwnerName(mEditTextName.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerDOB(mEditTextDOB.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerAge(mEditTextAge.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerContactNo(mEditTextContactNo.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerEmailId(mEditTextEmailId.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerAddressLine1(mEditTextAddressLine1.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerAddressLine2(mEditTextAddressLine2.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerCity(mEditTextCity.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerState(mEditTextState.getText().toString());
		mSocietyOwnerDetails.setmSocietyOwnerPIN(mEditTextPIN.getText().toString());
		String gender = "Male";
		int id = mRadioGroupGender.getCheckedRadioButtonId();
		if(id == mRadioButtonFemale.getId()){
			gender = "Female";
		}
		mSocietyOwnerDetails.setmGender(gender);
	}
	
	private void gotoNextActivity(){
		Intent intentSocietyRegistrationStep1 = new Intent(SocietyOwnerRegistration.this, SocietyRegistrationStep1.class);
		startActivity(intentSocietyRegistrationStep1);
		finish();
	}
	
	 @Override
	 @Deprecated
	 protected Dialog onCreateDialog(int id) {
	  return new DatePickerDialog(this, datePickerListener, currentYear, currentMonth, currentDay);
	 }

	 private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
	  public void onDateSet(DatePicker view, int selectedYear,
	    int selectedMonth, int selectedDay) {
		  mEditTextDOB.setText(selectedDay + "/" + (selectedMonth + 1) + "/"
	     + selectedYear);
	  }
	 };

}
