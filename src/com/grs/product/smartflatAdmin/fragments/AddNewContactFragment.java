package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.SaveContactOnServerTask;
import com.grs.product.smartflatAdmin.asynctasks.SaveVisitorOnServerTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.fragments.NewVisitorFragment.SaveVisitorOnServerTaskListener;
import com.grs.product.smartflatAdmin.models.ContactDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddNewContactFragment extends Fragment{
	
	private EditText mEditTextContactName, mEditTextContactNumber, mEditTextContactEmailId, mEditTextOccupation;
	private Spinner mSpinnerOccupation;
	private Button mButtonAddContact;
	private ContactDetails mContactDetails;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_add_new_contact, container, false);
		initialiseUI(rootView);
		createSpinnerData();
		addListeners();
        return rootView;   
	}
	
	private void initialiseUI(View rootView){
		mEditTextContactName = (EditText) rootView.findViewById(R.id.editTextContactName);
		mEditTextContactNumber = (EditText) rootView.findViewById(R.id.editTextContactNo);
		mEditTextContactEmailId = (EditText) rootView.findViewById(R.id.editTextEmailId);
		mEditTextOccupation = (EditText) rootView.findViewById(R.id.editTextOccupation);
		mSpinnerOccupation = (Spinner) rootView.findViewById(R.id.spinnerOccupation);
		mButtonAddContact = (Button) rootView.findViewById(R.id.buttonAddContact);
	}
	
	private void createSpinnerData(){
		List<String> listOccupationType = new ArrayList<String>();
		listOccupationType.add("Doctor");
		listOccupationType.add("Security Manager");
		listOccupationType.add("Laundry");
		listOccupationType.add("Courier");
		listOccupationType.add("Electrician");
		listOccupationType.add("Gas Connection");
		listOccupationType.add("Key Maker");
		listOccupationType.add("Plumber");
		listOccupationType.add("Other");
		ArrayAdapter<String> adapterOccupationName = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_dropdown_item_1line, listOccupationType);
		adapterOccupationName.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);		
		mSpinnerOccupation.setAdapter(adapterOccupationName);	
	}
	
	private void addListeners(){
		mButtonAddContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (validateUI()) {
					mContactDetails = getContactDetails();
					saveContactOnServer();
				}
			}
		});
		
		mSpinnerOccupation.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (parent.getSelectedItem().toString().equals("Other")) {
					mEditTextOccupation.setVisibility(View.VISIBLE);
				}else{
					mEditTextOccupation.setVisibility(View.GONE);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});		
	}
	
	private boolean validateUI(){
		
		if(mEditTextContactName.getText().toString().equals(""))
		{
			mEditTextContactName.setError("Please enter Name");
			return false;
		}
		
		if(mEditTextContactNumber.getText().toString().equals(""))
		{
			mEditTextContactNumber.setError("Please enter Contact Number");
			return false;
		}
		if(!mEditTextContactEmailId.getText().toString().equals(""))
		{
			if(!Utilities.isValidEmail(mEditTextContactEmailId.getText().toString())){
				mEditTextContactEmailId.setError("Please enter valid email id");	
				return false;
			}
		}
		
		return true;
	}
	
	private ContactDetails getContactDetails(){
		ContactDetails temp = new ContactDetails();
		temp.setmContactName(mEditTextContactName.getText().toString());
		temp.setmContactNumber(mEditTextContactNumber.getText().toString());
		temp.setmContactEmailId(mEditTextContactEmailId.getText().toString());
		if(mEditTextOccupation.getVisibility()==View.GONE){
			temp.setmContactOccupation(mSpinnerOccupation.getSelectedItem().toString());
		}else{
			temp.setmContactOccupation(mEditTextOccupation.getText().toString());
		}
		return temp;
	}
	
	private void saveContactOnServer(){	
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new SaveContactOnServerTask(getActivity(), new SaveContactOnServerTaskListener(), mContactDetails)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(getActivity(), "Message", "Please check your Internet");
		}					
	}
	
	public class SaveContactOnServerTaskListener implements AsyncTaskCompleteListener<Response>{

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
					saveContactInDB();
					Utilities.ShowAlertBox(getActivity(), "Message", "Contact Added Successfully");
					clearUIEntries();
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
	
	private void saveContactInDB(){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		boolean isAdded = dbManager.saveContact(mContactDetails);
		if (isAdded) {
			Log.e("Contact Details", "Inserted Successfully");
		}
	}
	
	private void clearUIEntries(){
		mEditTextContactName.setText("");
		mEditTextContactNumber.setText("");
		mEditTextContactEmailId.setText("");
		mEditTextOccupation.setVisibility(View.GONE);
		mEditTextOccupation.setText("");
		createSpinnerData();
	}

}
