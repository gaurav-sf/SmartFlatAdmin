package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.utils.Utilities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class NewVisitorFragment extends Fragment{
	
	private EditText mEditTextVisitorName, mEditTextVisitorInTime, mEditTextVisitPurpose,
					 mEditTextFlatNo, mEditTextVisitorContactNo,mEditTextVisitorVehicleNo;
	private Spinner mSpinnerNoOfVisitors, mSpinnerBuildingName, mSpinnerFloorNo;
	private RadioGroup mRadioGroupFlatType;
	private RadioButton mRadioButtonFlatNo, mRadioButtonOwnerName;
	private Button mButtonAddVisitor;
	
	
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
	}
	
	private void addListeners(){
		mButtonAddVisitor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
			listFloorNo.add(String.valueOf(i));	
		}
		ArrayAdapter<String> adapterNoOfVisitors = new ArrayAdapter<String>
		(getActivity(), android.R.layout.simple_dropdown_item_1line, listNoOfVisitors);
		adapterFloorNo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);		
		mSpinnerNoOfVisitors.setAdapter(adapterNoOfVisitors);	
	}

}
