package com.grs.product.smartflatAdmin.activities;

import java.util.Calendar;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class NewRegisteredUsersDetails extends Activity{
	
	private Bundle extras;
	private String mFlatOwnerCode;
	private FlatOwnerDetails mFlatOwnerDetails;
	private EditText mEditTextName, mEditTextDOB, mEditTextContactNo, mEditTextEmailId, mEditTextFlatNo;
	private Spinner mSpinnerBuildingName, mSpinnerFloorNo;
	private Button mButtonEdit, mButtonActivate;
	private RadioButton mRadioButtonMale, mRadioButtonFemale;
	private RadioGroup mRadioGroupGender;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registered_user_details);
		extras = getIntent().getExtras();
		mFlatOwnerCode = extras.getString("flatOwnerCode");
		getFlatOwnerData(mFlatOwnerCode);
		initialiseUI();
		showData();
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
/*		if(mFlatOwnerDetails.get.equalsIgnoreCase("Male")){
			mRadioButtonMale.setChecked(true);
		}else{
			mRadioButtonFemale.setChecked(true);
		}*/
		
		
	}
	
	
	private void getFlatOwnerData(String flatOwnerCode){

		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		Cursor details = objManager.getSingleFlatOwnerData(flatOwnerCode);
		if(details!=null&& details.getCount()>0){
			details.moveToFirst();
				mFlatOwnerDetails.setmFlatOwnerName(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_NAME)));
				mFlatOwnerDetails.setmFlatOwnerContactNo(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CONTACT_NO)));
				mFlatOwnerDetails.setmFlatOwnerEmailId(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_EMAIL_ID)));
				mFlatOwnerDetails.setmBuildingName(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_BUILDING_NAME)));
				mFlatOwnerDetails.setmFloorNo(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLOOR_NO)));
				mFlatOwnerDetails.setmFlatno(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_NO)));
				mFlatOwnerDetails.setmFlatOwnerCreatedDateTime(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CREATED_DATETIME)));
				mFlatOwnerDetails.setmFlatOwnerCode(details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CODE)));
				mFlatOwnerDetails.setActive(Boolean.parseBoolean(details.getString(details.getColumnIndex(TableFlatOwnerDetails.IS_ACTIVE))));
		}
	}

}
