package com.grs.product.smartflatAdmin.activities;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableVisitorDetails;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.VisitorDetails;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class VisitorDetailsActivity extends Activity{
	
	private VisitorDetails mVisitorDetails;
	private FlatOwnerDetails mFlatOwnerDetails = null;
	private String visitorNumber;
	private String flatOwnerCode;
	private Bundle extras;
	private EditText mTextViewOwnername,mTextViewFlatDetail,mTextViewVisitorName,mTextViewNoOfVisitors,
	mTextViewVehicleNo,mTextViewVisitPurpose,mTextViewVisitorInTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visitor_detail);
		extras = getIntent().getExtras();
		visitorNumber = extras.getString("visitorCode");
		flatOwnerCode =  extras.getString("flatOwnerCode");
		mVisitorDetails = getVisitorDetails();
		initialiseUI();
		showData();
	}
	
	private VisitorDetails getVisitorDetails(){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		VisitorDetails temp = new VisitorDetails();
		Cursor details = dbManager.getSingleVisitorDetails(visitorNumber);
		if(details!=null && details.getCount()>0)
		{
			details.moveToFirst();
			temp.setmVisitorCode(details.getString(details.getColumnIndex(TableVisitorDetails.VISITOR_CODE)));
			temp.setmVisitorName(details.getString(details.getColumnIndex(TableVisitorDetails.VISITOR_NAME)));
			temp.setmVisitorContacNo(details.getString(details.getColumnIndex(TableVisitorDetails.VISITOR_CONTACT_NO)));
			temp.setmVisitorInTime(details.getString(details.getColumnIndex(TableVisitorDetails.VISITOR_IN_TIME)));
			temp.setmVisitPurpose(details.getString(details.getColumnIndex(TableVisitorDetails.VISIT_PURPOSE)));
			temp.setmNoofVisitors(details.getString(details.getColumnIndex(TableVisitorDetails.NO_OF_VISITORS)));
			temp.setmFlatOwnerCode(details.getString(details.getColumnIndex(TableVisitorDetails.FLAT_OWNER_CODE)));
			getFlatOwnerData(details.getString(details.getColumnIndex(TableVisitorDetails.FLAT_OWNER_CODE)));
		}
		return temp;		
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
	
	private void initialiseUI(){
		mTextViewOwnername = (EditText) findViewById(R.id.textViewOwnername);
		mTextViewFlatDetail = (EditText) findViewById(R.id.textViewFlatDetail);
		mTextViewVisitorName = (EditText) findViewById(R.id.textViewVisitorName);
		mTextViewNoOfVisitors = (EditText) findViewById(R.id.textViewNoOfVisitors);
		mTextViewVehicleNo = (EditText) findViewById(R.id.textViewVehicleNo);
		mTextViewVisitPurpose = (EditText) findViewById(R.id.textViewVisitPurpose);
		mTextViewVisitorInTime = (EditText) findViewById(R.id.textViewVisitorInTime);
	}
	
	private void showData(){
		if(mFlatOwnerDetails!=null){
			mTextViewOwnername.setText(mFlatOwnerDetails.getmFlatOwnerName());
			mTextViewFlatDetail.setText(mFlatOwnerDetails.getmBuildingName()+"-"+mFlatOwnerDetails.getmFloorNo()+""+mFlatOwnerDetails.getmFlatno());
		}else{
			mTextViewOwnername.setVisibility(View.INVISIBLE);
			mTextViewFlatDetail.setText(flatOwnerCode);
		}
		mTextViewVisitorName.setText(mVisitorDetails.getmVisitorName());
		mTextViewNoOfVisitors.setText(mVisitorDetails.getmNoofVisitors());
		mTextViewVehicleNo.setText(mVisitorDetails.getmVisitorVehicleNo());
		mTextViewVisitPurpose.setText(mVisitorDetails.getmVisitPurpose());
		mTextViewVisitorInTime.setText(mVisitorDetails.getmVisitorInTime());
	}

}
