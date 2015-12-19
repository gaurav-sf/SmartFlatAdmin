package com.grs.product.smartflatAdmin.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.activities.LoginActivity;
import com.grs.product.smartflatAdmin.activities.LoginActivity.LoginTaskListener;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.LoginTask;
import com.grs.product.smartflatAdmin.asynctasks.SendNoticeTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.NoticeDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class NewNoticeFragment extends Fragment{
	
	private EditText mEditTextSubject,mEditTextMessage;
	 private AutoCompleteTextView mAutoCompleteTextViewTo;
	 private Button mButtonSendNotice;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_new_notice, container, false);
		initialiseUI(rootView);
		addListeners();
        return rootView;	       
	}
	
	private void initialiseUI(View rootView){
		mAutoCompleteTextViewTo = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextViewTo);
		mEditTextSubject = (EditText) rootView.findViewById(R.id.editTextSubject);
		mEditTextMessage = (EditText) rootView.findViewById(R.id.editTextMessage);
		mButtonSendNotice = (Button) rootView.findViewById(R.id.buttonSendNotice);
		AutoCompleteFlatOwnerNameAdapter adapter = new AutoCompleteFlatOwnerNameAdapter();
		mAutoCompleteTextViewTo.setAdapter(adapter);
		mAutoCompleteTextViewTo.setOnItemClickListener(adapter);
	}
	
	private void addListeners(){
		mButtonSendNotice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validateUIEntries()){
					sendNotice();
				}
			}
		});
	}
	
	private boolean validateUIEntries(){
		if(mAutoCompleteTextViewTo.getText().toString().equals("")){
			mAutoCompleteTextViewTo.setError("Please enter at least one receipient");
			return false;
		} 
		if(mEditTextSubject.getText().toString().equals("")){
			mEditTextSubject.setError("Please enter subject");
			return false;
		}
		if (mEditTextMessage.getText().toString().equals("")) {
			mEditTextMessage.setError("Please enter message");
			return false;
		}
		return true;
	}
	
	private void sendNotice()
	{
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new SendNoticeTask(getActivity(), new SendNoticeTaskListener(), mAutoCompleteTextViewTo.getText().toString(), mEditTextSubject.getText().toString(), mEditTextMessage.getText().toString())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(getActivity(),"Error", "Please check your Internet");
		}				
	}
	
	public class SendNoticeTaskListener implements AsyncTaskCompleteListener<Response>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(getActivity(), "", false);
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result != null) 
			{
				if (result.getStatus().equalsIgnoreCase("success")) 
				{
					Utilities.ShowAlertBox(getActivity(), "Message", "Notice sent successfully");
					saveNoticeInDB(result.getMessage());
					clearUIEntries();
				}
				else
				{
					Utilities.ShowAlertBox(getActivity(), "Error", "Server Error. Please try after some time.");				
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
			Utilities.ShowAlertBox(getActivity(), "Error", "Server Error please try later");
		}

	}
	
	public class AutoCompleteFlatOwnerNameAdapter extends CursorAdapter
	implements android.widget.AdapterView.OnItemClickListener {		
		SmartFlatAdminDBManager dbManager;		
		public AutoCompleteFlatOwnerNameAdapter() {
			super(getActivity(), null);
			dbManager = new SmartFlatAdminDBManager();
		}
		
		@Override
		public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
	        Cursor cursor = dbManager.getFlatOwnerData(
	                (constraint != null ? constraint.toString() : null));
	        return cursor;
		}
		
		@Override
		public String convertToString(Cursor cursor) {
	        final int columnIndex = cursor.getColumnIndexOrThrow(TableFlatOwnerDetails.FLAT_OWNER_NAME);
	        final String str = cursor.getString(columnIndex);
	        return str;
		}
				

		@Override
		public void onItemClick(AdapterView<?> listView, View view, int position,
				long id) {
	        // Get the cursor, positioned to the corresponding row in the result set
	        Cursor cursor = (Cursor) listView.getItemAtPosition(position);
	        // Get the state's capital from this row in the database.
	        String flatOwnerCode = cursor.getString(cursor.getColumnIndexOrThrow(TableFlatOwnerDetails.FLAT_OWNER_CODE));
	        // Update the parent class's TextView
	        Toast.makeText(getActivity(),flatOwnerCode, Toast.LENGTH_LONG).show();
	        
	        mAutoCompleteTextViewTo.setText(mAutoCompleteTextViewTo.getText().toString()+";"+flatOwnerCode);
	    }

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
	        final LayoutInflater inflater = LayoutInflater.from(context);
	        final View view =
	                inflater.inflate(android.R.layout.simple_dropdown_item_1line,
	                        parent, false);
	       return view;
	    }

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
	        final String text = convertToString(cursor);
	        ((TextView) view).setText(text);		
		}
	}
	
	private void saveNoticeInDB(String noticeNumber){
		NoticeDetails noticeDetails = new NoticeDetails();
		noticeDetails.setmNoticeNumber(noticeNumber);
		noticeDetails.setmNoticeTo(mAutoCompleteTextViewTo.getText().toString());
		noticeDetails.setmNoticeSubject(mEditTextSubject.getText().toString());
		noticeDetails.setmNoticeMessage(mEditTextMessage.getText().toString());
		noticeDetails.setmNoticeDateTime(Utilities.getCurrentDateTime());
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		boolean isAdded = dbManager.saveNotice(noticeDetails);
		if (isAdded) {
			Log.e("Notice Details", "Inserted Successfully");
		}
	}
	private void clearUIEntries(){
		mAutoCompleteTextViewTo.setText("");
		mEditTextSubject.setText("");
		mEditTextMessage.setText("");
	}


}
