package com.grs.product.smartflatAdmin.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.UploadSocietyPollTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.SocietyPollDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class AddNewPollFragment extends Fragment{
	
	private EditText mEditTextPollTopic,mEditTextDetails,mEditTextPollOption1,mEditTextPollOption2,mEditTextPollOption3,mEditTextPollOption4,
	mEditTextPollDuration;
	private Button mButtonAddEditText,mButtonPostPoll;
	private SocietyPollDetails mSocietyPollDetails;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_new_poll, container, false);	
		initialiseUI(rootView);
		addListeners();	
        return rootView;
		}
	
	private void initialiseUI(View rootView){
		mEditTextPollTopic = (EditText) rootView.findViewById(R.id.editTextPollTopic);
		mEditTextDetails = (EditText) rootView.findViewById(R.id.editTextDetails);
		mEditTextPollOption1 = (EditText) rootView.findViewById(R.id.editTextPollOption1);
		mEditTextPollOption2 = (EditText) rootView.findViewById(R.id.editTextPollOption2);
		mEditTextPollOption3 = (EditText) rootView.findViewById(R.id.editTextPollOption3);
		mEditTextPollOption4 = (EditText) rootView.findViewById(R.id.editTextPollOption4);
		mEditTextPollDuration = (EditText) rootView.findViewById(R.id.editTextPollDuration);		
		mButtonAddEditText = (Button) rootView.findViewById(R.id.buttonAddEditText);
		if(mEditTextPollOption3.getVisibility()==View.VISIBLE && mEditTextPollOption4.getVisibility()==View.VISIBLE){
			mButtonAddEditText.setVisibility(View.GONE);
		}
		mButtonPostPoll = (Button) rootView.findViewById(R.id.buttonPostPoll);
	}
	
	private void addListeners(){
		mButtonAddEditText.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mEditTextPollOption3.getVisibility()!=View.VISIBLE)
				{
					mEditTextPollOption3.setVisibility(View.VISIBLE);
				}else if(mEditTextPollOption4.getVisibility()!=View.VISIBLE){
					mEditTextPollOption4.setVisibility(View.VISIBLE);
					mButtonAddEditText.setVisibility(View.GONE);
				}
			}
		});
		
		mButtonPostPoll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validateUI()){
					getPollData();
					uploadPoll();
				}		
			}
		});
	
	}
	
	private boolean validateUI(){
		if(mEditTextPollTopic.getText().toString().equals("")){
			mEditTextPollTopic.setError("Please enter topic");
			return false;
		}
		if(mEditTextDetails.getText().toString().equals("")){
			mEditTextDetails.setError("Please enter details");
			return false;
		}
		if(mEditTextPollOption1.getText().toString().equals("")){
			mEditTextPollOption1.setError("Please enter option one");
			return false;
		}
		if(mEditTextPollOption2.getText().toString().equals("")){
			mEditTextPollOption2.setError("Please enter option two");
			return false;
		}
		if(mEditTextPollDuration.getText().toString().equals("")){
			mEditTextPollDuration.setError("Please poll duration");
			return false;
		}
		return true;		
	}
	
	private void getPollData()
	{
		mSocietyPollDetails = new SocietyPollDetails();
		mSocietyPollDetails.setmPollTopic(mEditTextPollTopic.getText().toString());
		mSocietyPollDetails.setmPollTopicDetails(mEditTextDetails.getText().toString());
		mSocietyPollDetails.setmPollOption1(mEditTextPollOption1.getText().toString());
		mSocietyPollDetails.setmPollOption2(mEditTextPollOption2.getText().toString());
		mSocietyPollDetails.setmPollOption3(mEditTextPollOption3.getText().toString());
		mSocietyPollDetails.setmPollOption4(mEditTextPollOption4.getText().toString());
		mSocietyPollDetails.setmPollDuration(mEditTextPollDuration.getText().toString());
		mSocietyPollDetails.setmPollCreatedDateTime(Utilities.getCurrentDateTime());
		mSocietyPollDetails.setmPollCreatedBy(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences());
	}
	
	private void uploadPoll()
	{		
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new UploadSocietyPollTask(getActivity(), new UploadSocietyPollTaskListener(), mSocietyPollDetails)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(getActivity(), "Message", "Please check your Internet");		
		}					
	}
	
	public class UploadSocietyPollTaskListener implements AsyncTaskCompleteListener<Response>{

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
					saveSocietyPollInDB(result.getMessage());				
				}else{

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
	
	private void saveSocietyPollInDB(String poll_Id){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		boolean isAdded = dbManager.saveSocietyPoll(mSocietyPollDetails);
		if (isAdded) {
			Log.e("Poll Inserted", "Successfully");
		}
	}

}

