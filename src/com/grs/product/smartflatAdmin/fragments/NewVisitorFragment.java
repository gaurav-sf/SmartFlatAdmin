package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.SaveVisitorOnServerTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.fragments.NewNoticeFragment.AutoCompleteFlatOwnerNameAdapter;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.models.VisitorDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class NewVisitorFragment extends Fragment{
	
	private EditText mEditTextVisitorName, mEditTextVisitorInTime, mEditTextVisitPurpose,
					 mEditTextFlatNo, mEditTextVisitorContactNo,mEditTextVisitorVehicleNo;
	private Spinner mSpinnerNoOfVisitors, mSpinnerBuildingName, mSpinnerFloorNo;
	private RadioGroup mRadioGroupFlatType;
	private RadioButton mRadioButtonFlatNo, mRadioButtonOwnerName;
	private Button mButtonAddVisitor;
	private VisitorDetails mVisitorDetails;
	private AutoCompleteTextView mAutoCompleteTextViewFlatOwnerName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_new_visitor, container, false);	
		initialiseUI(rootView);
		createSpinnerData();
		addListeners();	
        return rootView;
		}
	
	private void initialiseUI(View rootView){
		mEditTextVisitorName = (EditText) rootView.findViewById(R.id.editTextVisitorName);
		mEditTextVisitorInTime = (EditText) rootView.findViewById(R.id.editTextVisitorInTime );
		mEditTextVisitorInTime.setText(Utilities.getCurrentDateTime());
		mEditTextVisitPurpose = (EditText) rootView.findViewById(R.id.editTextVisitPurpose);
		mEditTextFlatNo = (EditText) rootView.findViewById(R.id.editTextFlatNo);
		mEditTextVisitorContactNo = (EditText) rootView.findViewById(R.id.editTextVisitorContactNo);
		mEditTextVisitorVehicleNo = (EditText) rootView.findViewById(R.id.editTextVisitorVehicleNo);
		mSpinnerNoOfVisitors = (Spinner) rootView.findViewById(R.id.spinnerNoOfVisitors);
		mSpinnerBuildingName  = (Spinner) rootView.findViewById(R.id.spinnerBuildingName);
		mSpinnerFloorNo = (Spinner) rootView.findViewById(R.id.spinnerFloorNo);
		mRadioGroupFlatType = (RadioGroup) rootView.findViewById(R.id.radioGroupFlatType);
		mRadioButtonFlatNo = (RadioButton) rootView.findViewById(R.id.radioButtonFlatNo);
		mRadioButtonOwnerName = (RadioButton) rootView.findViewById(R.id.radioButtonOwnerName);
		mButtonAddVisitor = (Button) rootView.findViewById(R.id.buttonAddVisitor);
		mAutoCompleteTextViewFlatOwnerName = (AutoCompleteTextView) rootView.findViewById(R.id.autoCompleteTextViewFlatOwnerName);
		AutoCompleteFlatOwnerNameAdapter adapter = new AutoCompleteFlatOwnerNameAdapter();
		mAutoCompleteTextViewFlatOwnerName.setAdapter(adapter);
		mAutoCompleteTextViewFlatOwnerName.setOnItemClickListener(adapter);
	}
	
	private void addListeners(){
		mButtonAddVisitor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isValidateUiEntries()){
					mVisitorDetails = getVisitorDetails();
					saveVisitor();
				}
			}
		});
		
		mRadioGroupFlatType.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				 switch(checkedId)
                 {
                 case R.id.radioButtonFlatNo:
                     mAutoCompleteTextViewFlatOwnerName.setVisibility(View.GONE);
                	 mSpinnerBuildingName.setVisibility(View.VISIBLE);
                	 mSpinnerFloorNo.setVisibility(View.VISIBLE);
                	 mEditTextFlatNo.setVisibility(View.VISIBLE);
                     break;
                 case R.id.radioButtonOwnerName:
                	 mAutoCompleteTextViewFlatOwnerName.setVisibility(View.VISIBLE);
                	 mSpinnerBuildingName.setVisibility(View.GONE);
                	 mSpinnerFloorNo.setVisibility(View.GONE);
                	 mEditTextFlatNo.setVisibility(View.GONE);
                     break;
                 }
			}
		});
		
	}
		
	private void createSpinnerData(){
		SocietyDetails mSocietyDetails = Utilities.getSocietyDetails();
		List<String> listBuilidingName = new ArrayList<String>();
		String[] buildingName = mSocietyDetails.getmBuildingName().split("@");
		for(int i=0; i<buildingName.length;i++){
			listBuilidingName.add(buildingName[i]);	
		}	
		//Create Adapter and set data to spinner building name
		ArrayAdapter<String> adapterBuildingName = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_dropdown_item_1line, listBuilidingName);
		adapterBuildingName.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);		
		mSpinnerBuildingName.setAdapter(adapterBuildingName);		
		//Create Adapter and set data to spinner floor no
		List<String> listFloorNo = new ArrayList<String>();
		for(int i =1; i<=mSocietyDetails.getmTotalFloorNumber();i++){
			listFloorNo.add(String.valueOf(i));	
		}
		ArrayAdapter<String> adapterFloorNo = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_dropdown_item_1line, listFloorNo);
		adapterFloorNo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);		
		mSpinnerFloorNo.setAdapter(adapterFloorNo);			
		//Create adapter for no of visitor spinner
		List<String> listNoOfVisitors = new ArrayList<String>();
		for(int i =1; i<=10;i++){
			listNoOfVisitors.add(String.valueOf(i));	
		}
		ArrayAdapter<String> adapterNoOfVisitors = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_dropdown_item_1line, listNoOfVisitors);
		adapterFloorNo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);		
		mSpinnerNoOfVisitors.setAdapter(adapterNoOfVisitors);	
	}
	
	private boolean isValidateUiEntries(){
		
		if(mEditTextVisitorName.getText().toString().equals(""))
		{
			mEditTextVisitorName.setError("Please enter visitor Name");
			return false;
		}
		
		if(mEditTextFlatNo.getText().toString().equals(""))
		{
			mEditTextFlatNo.setError("Please enter flat number");
			return false;
		}
		
		if(mEditTextVisitPurpose.getText().toString().equals(""))
		{
			mEditTextVisitPurpose.setError("Please enter visit purpose");
			return false;
		}
		
		if(mEditTextVisitorContactNo.getText().toString().equals(""))
		{
			mEditTextVisitorContactNo.setError("Please enter visitor contact no");
			return false;
		}

		return true;
	}
	
	private VisitorDetails getVisitorDetails(){
		 VisitorDetails tempDetails = new VisitorDetails();
		 tempDetails.setmVisitorName(mEditTextVisitorName.getText().toString());
		 tempDetails.setmNoofVisitors(mSpinnerNoOfVisitors.getSelectedItem().toString());
		 tempDetails.setmVisitorInTime(mEditTextVisitorInTime.getText().toString());
		 tempDetails.setmVisitorContacNo(mEditTextVisitorContactNo.getText().toString());
		 tempDetails.setmVisitorVehicleNo(mEditTextVisitorVehicleNo.getText().toString());
		 tempDetails.setmVisitPurpose(mEditTextVisitPurpose.getText().toString());
		 
		 String buildingName = mSpinnerBuildingName.getSelectedItem().toString();
		 String floorNo = mSpinnerFloorNo.getSelectedItem().toString();
		 String flatNo = mEditTextFlatNo.getText().toString();
		 String flatOwnerCode = getFlatOwnerCode(buildingName+floorNo+"@"+flatNo);
		 if(flatOwnerCode.equals("")){
			 tempDetails.setmFlatOwnerCode(buildingName+floorNo+"@"+flatNo);	 
		 }else{
			 tempDetails.setmFlatOwnerCode(flatOwnerCode);	 
		 }
			
		return tempDetails;
	}
	
	private String getFlatOwnerCode(String searchValue){
		String flatOwnerCode = "";
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		Cursor details = dbManager.getFlatOwnerCode(searchValue);
		if(details!=null && details.getCount()>0){
			details.moveToFirst();
			flatOwnerCode = details.getString(details.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CODE));
		}
		return flatOwnerCode;
	}
	
	private void saveVisitor(){	
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new SaveVisitorOnServerTask(getActivity(), new SaveVisitorOnServerTaskListener() , mVisitorDetails)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			mVisitorDetails.setmIsOfflineEntry(true);
			saveVisitorInLocalDB("TEMP");
		}				
	}
	
	public class SaveVisitorOnServerTaskListener implements AsyncTaskCompleteListener<Response>{

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
					mVisitorDetails.setmIsOfflineEntry(false);
					saveVisitorInLocalDB(result.getMessage());
				}else{
					mVisitorDetails.setmIsOfflineEntry(true);
					saveVisitorInLocalDB("");
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
	
	private void saveVisitorInLocalDB(String visitorCode){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		mVisitorDetails.setmVisitorCode(visitorCode);
		boolean isAdded = dbManager.saveVisitor(mVisitorDetails);
		if (isAdded) {
			Log.e("Visitor Details", "Inserted Successfully");
		}
		clearUIentries();
	}
	
	private void clearUIentries(){
		mEditTextVisitorName.setText("");
		createSpinnerData();
		mEditTextFlatNo.setText("");
		mEditTextVisitorInTime.setText(Utilities.getCurrentDateTime());
		mEditTextVisitPurpose.setText("");
		mEditTextVisitorContactNo.setText("");
		mEditTextVisitorVehicleNo.setText("");	
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
	        
	        mAutoCompleteTextViewFlatOwnerName.setText(mAutoCompleteTextViewFlatOwnerName.getText().toString()+";"+flatOwnerCode);
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

}
