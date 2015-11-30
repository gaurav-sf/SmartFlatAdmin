package com.grs.product.smartflatAdmin.activities;


import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableRequestDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;

public class RequestDetailsActivity extends Activity{
	private Bundle extras;
	private TextView mTextViewRequestNo, mTextViewPriorityType, mTextViewCategory,mTextViewDetails, mTextViewManagementReply;
	private ImageButton mButtonClose;
	private String mRequestNumber;
	private RequestDetails mRequestDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_request_details);
		initializeUI();
		mRequestDetails = getRequestDataFromDB(mRequestNumber);
		setUIData();
		addListeners();
	}
	
	private void initializeUI(){
		extras = getIntent().getExtras();
		mRequestNumber = extras.getString("requestno");
		mTextViewRequestNo = (TextView) findViewById(R.id.textViewRequestNumber);
		mTextViewPriorityType = (TextView) findViewById(R.id.textViewPriorityType);
		mTextViewCategory = (TextView) findViewById(R.id.textViewCategory);
		mTextViewDetails = (TextView) findViewById(R.id.textViewDetails);
		mTextViewManagementReply = (TextView) findViewById(R.id.textViewManagementReply);
		mButtonClose = (ImageButton) findViewById(R.id.buttonClose);
	}
	
	private void addListeners(){
		mButtonClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}
	
	private RequestDetails getRequestDataFromDB(String requestNumber){
		SmartFlatAdminDBManager objDbManager = new SmartFlatAdminDBManager();
		RequestDetails tempRequestDetails = new RequestDetails();
		Cursor requestDetailsCursor = objDbManager.getSinbleRequestDetails(requestNumber);
			boolean isdata = requestDetailsCursor.moveToFirst();
			if(isdata)
			{
				tempRequestDetails.setmRequestNumber(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER)));
				tempRequestDetails.setmRequestCategory(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_CATEGORY)));
				tempRequestDetails.setmRequestDetails(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DETAILS)));
				tempRequestDetails.setmRequestPriority(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_PRIORITY)));
				tempRequestDetails.setmRequestType(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_TYPE)));
				tempRequestDetails.setmRequestStatus(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_STATUS)));
				tempRequestDetails.setmRequestDateTime(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DATETIME)));
				
				//Write Method to get all messages and store it in list
			}			
			return tempRequestDetails;	
	}
	
	private void setUIData(){
		mTextViewRequestNo.setText(mRequestDetails.getmRequestNumber());
		mTextViewCategory.setText("Category :- "+mRequestDetails.getmRequestCategory());
		mTextViewDetails.setText("Details :- "+mRequestDetails.getmRequestDetails());
		//mTextViewManagementReply.setText(mRequestDetails.getm);
		if(mRequestDetails.getmRequestPriority().equalsIgnoreCase("1")){
			mTextViewPriorityType.setTextColor(Color.RED);
			mTextViewPriorityType.setText("High");	
		}else if(mRequestDetails.getmRequestPriority().equalsIgnoreCase("2")){
			mTextViewPriorityType.setTextColor(Color.BLUE);
			mTextViewPriorityType.setText("Medium");	
		}else{
			mTextViewPriorityType.setTextColor(Color.GREEN);
			mTextViewPriorityType.setText("Low");	
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
}
