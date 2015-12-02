package com.grs.product.smartflatAdmin.activities;


import java.util.ArrayList;
import java.util.List;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.SendMessageTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableMessageDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableRequestDetails;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class RequestDetailsActivity extends Activity{
	private Bundle extras;
	private TextView mTextViewRequestNo, mTextViewPriorityType, mTextViewCategory,mTextViewDetails, mTextViewManagementReply,mTextViewMessage;
	private ImageButton mButtonClose,mButtonSendMessage;
	private EditText mEditTextMessage;
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
		mEditTextMessage = (EditText) findViewById(R.id.editTextMessage);
		mButtonSendMessage = (ImageButton) findViewById(R.id.imageButtonSendMessage);
		mTextViewMessage =  (TextView) findViewById(R.id.textViewMessage);

	}
	
	private void addListeners(){
		mButtonClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		mButtonSendMessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				sendMessage();
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
				tempRequestDetails.setmRequestRaisedBy(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_RAISED_BY)));
				//Write Method to get all messages and store it in list
				tempRequestDetails.setmMessageList(getMessagesFromDB());
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
		
		for (int i = 0; i < mRequestDetails.getmMessageList().size(); i++) {
			mTextViewMessage.setText(mTextViewMessage.getText().toString()+"\n"
		+mRequestDetails.getmMessageList().get(i).getmMessageContent())	;
		}
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
	}
	
	private List<RequestMessages> getMessagesFromDB(){
		List<RequestMessages> messageList = new ArrayList<RequestMessages>();
		SmartFlatAdminDBManager objDbManager = new SmartFlatAdminDBManager();
		Cursor messageCursor = objDbManager.getMessages(mRequestNumber);
		for(int i = 0; i<=messageCursor.getCount();i++)
		{
			boolean isdata = messageCursor.moveToPosition(i);
			if(isdata)	
			{
				RequestMessages tempMessages = new RequestMessages();
				tempMessages.setmMessageContent(messageCursor.getString(messageCursor.getColumnIndex(TableMessageDetails.MESSAGE_CONTENT)));
				tempMessages.setmIsSocietyMessage(Boolean.parseBoolean(messageCursor.getString(messageCursor.getColumnIndex(TableMessageDetails.IS_SOCIETY_MESSAGE))));
				messageList.add(tempMessages);
			}
		}
		return messageList;
	}
	
	private void sendMessage(){
		
		if (NetworkDetector.init(getApplicationContext()).isNetworkAvailable()) 
		{
			new SendMessageTask(getApplicationContext(), new SendMessageTaskListener(), mEditTextMessage.getText().toString(), mRequestNumber, mRequestDetails.getmRequestRaisedBy())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(RequestDetailsActivity.this,"Error", "Please check your Internet");
		}	
	
	}
	
	public class SendMessageTaskListener implements AsyncTaskCompleteListener<Response> {

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(RequestDetailsActivity.this, "", false);		
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
					saveMessageInDB(result.getMessage());
					mTextViewMessage.setText(mTextViewMessage.getText().toString()+"\n"
					+mEditTextMessage.getText().toString())	;
					mEditTextMessage.setText("");
				}else{
					Utilities.ShowAlertBox(RequestDetailsActivity.this,"Error",result.getMessage());		
				}
			}	
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();	
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			Utilities.ShowAlertBox(RequestDetailsActivity.this,"Error",e.getMessage());		
			CustomProgressDialog.removeDialog();	
		}
		
	}
	
	private void saveMessageInDB(String messageNumber)
	{
		RequestMessages tempMessages = new RequestMessages();
		tempMessages.setmMessageNumber(messageNumber);
		tempMessages.setmMessageContent(mEditTextMessage.getText().toString());
		tempMessages.setmRequestNumber(mRequestNumber);
		tempMessages.setmIsSocietyMessage(false);
		tempMessages.setmMessageDateTime(Utilities.getCurrentDateTime());
		
		SmartFlatAdminDBManager objDbManager = new SmartFlatAdminDBManager();
		boolean isAdded = objDbManager.saveMessage(tempMessages);
		if (isAdded) {
			Log.e("Request Message", "Inserted Successfully");
		}
	}
}
