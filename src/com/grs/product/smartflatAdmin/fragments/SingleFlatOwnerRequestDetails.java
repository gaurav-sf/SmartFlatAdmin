package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.activities.ActivatedUserDetails;
import com.grs.product.smartflatAdmin.activities.RequestDetailsActivity;
import com.grs.product.smartflatAdmin.adapter.NewRequestAndComplaintAdapter;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableRequestDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;

public class SingleFlatOwnerRequestDetails extends Fragment{
	
	//private final String LOG = SingleFlatOwnerRequestDetails.class.getName();
	private List<RequestDetails> mListRequestDetails;
	private ListView mListViewNewRequestAndCompliant;
	private NewRequestAndComplaintAdapter mNewRequestAndComplaintAdapter;
	private Spinner mSpinnertSorting;
	SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();


	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_new_request, container, false);
		initialiseUI(rootView);
		showDataInListView();
		addListeners();
        return rootView;
	}
	
	private void initialiseUI(View rootView){
		mListRequestDetails = new ArrayList<RequestDetails>();
		mListViewNewRequestAndCompliant = (ListView) rootView.findViewById(R.id.listViewNewRequestAndCompliant);
		mSpinnertSorting = (Spinner) rootView.findViewById(R.id.spinnertSorting);
		mNewRequestAndComplaintAdapter = new NewRequestAndComplaintAdapter(getActivity(),mListRequestDetails);
		createSortingSpinnerData();
	}
	
	private void createSortingSpinnerData(){
		List<String> listRequestType = new ArrayList<String>();
		listRequestType.add("By Date");
		listRequestType.add("By Type");
		listRequestType.add("By Category (A-Z)");
		listRequestType.add("By Priority High to Low");
		listRequestType.add("By Priority Low to High");
		ArrayAdapter<String> adapterBuildingName = new ArrayAdapter<String>
		(getActivity(), R.layout.spinner_item, listRequestType);
		adapterBuildingName.setDropDownViewResource(R.layout.spinner_item);		
		mSpinnertSorting.setAdapter(adapterBuildingName);
	}
	
	private void showDataInListView(){
		mNewRequestAndComplaintAdapter = new NewRequestAndComplaintAdapter(getActivity(),mListRequestDetails);
		mListViewNewRequestAndCompliant.setAdapter(mNewRequestAndComplaintAdapter);
		sortListByDate();
	}
	
	private void addListeners(){
		mSpinnertSorting.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				//Sort By date
				case 0:
					sortListByDate();
					break;

					//By Type
				case 1: 
					sortListByType();
					break;

					//By Category
				case 2:
					sortListByCategory();
					break;

					//By Priority High to Low
				case 3:
					sortListByPriorityHtoL();
					break;

					//By Priority Low to High
				case 4:
					sortListByPriorityLtoH();
					break;

				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		
		mListViewNewRequestAndCompliant.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent singleRequestDetails = new Intent(getActivity(), RequestDetailsActivity.class);
				singleRequestDetails.putExtra("requestno", mListRequestDetails.get(position).getmRequestNumber());
				startActivity(singleRequestDetails);

			}
		});
	}
	
	private void sortListByDate(){
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsForFlatOwner(ActivatedUserDetails.flatOwnerCode);
		mListRequestDetails.clear();
		for(int i = 0; i<=requestDetailsCursor.getCount();i++){
			boolean isdata = requestDetailsCursor.moveToPosition(i);
			if(isdata)
			{
				RequestDetails tempRequestDetails = new RequestDetails();				
				tempRequestDetails.setmRequestNumber(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER)));
				tempRequestDetails.setmRequestCategory(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_CATEGORY)));
				tempRequestDetails.setmRequestDetails(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DETAILS)));
				tempRequestDetails.setmRequestPriority(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_PRIORITY)));
				tempRequestDetails.setmRequestType(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_TYPE)));
				tempRequestDetails.setmRequestStatus(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_STATUS)));
				tempRequestDetails.setmRequestDateTime(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DATETIME)));
				tempRequestDetails.setmUnreadMessageCount(getUnreadMessageCount(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER))));
				mListRequestDetails.add(tempRequestDetails);
			}
		}
		mNewRequestAndComplaintAdapter.notifyDataSetChanged();
	}

	private void sortListByType(){
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByTypeForFlatOwner(ActivatedUserDetails.flatOwnerCode);
		mListRequestDetails.clear();
		for(int i = 0; i<=requestDetailsCursor.getCount();i++){
			boolean isdata = requestDetailsCursor.moveToPosition(i);
			if(isdata)
			{
				RequestDetails tempRequestDetails = new RequestDetails();				
				tempRequestDetails.setmRequestNumber(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER)));
				tempRequestDetails.setmRequestCategory(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_CATEGORY)));
				tempRequestDetails.setmRequestDetails(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DETAILS)));
				tempRequestDetails.setmRequestPriority(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_PRIORITY)));
				tempRequestDetails.setmRequestType(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_TYPE)));
				tempRequestDetails.setmRequestStatus(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_STATUS)));
				tempRequestDetails.setmRequestDateTime(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DATETIME)));
				tempRequestDetails.setmUnreadMessageCount(getUnreadMessageCount(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER))));
				mListRequestDetails.add(tempRequestDetails);
			}
		}	
		mNewRequestAndComplaintAdapter.notifyDataSetChanged();
	}

	private void sortListByCategory(){
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByCategoryForFlatOwner(ActivatedUserDetails.flatOwnerCode);
		mListRequestDetails.clear();
		for(int i = 0; i<=requestDetailsCursor.getCount();i++){
			boolean isdata = requestDetailsCursor.moveToPosition(i);
			if(isdata)
			{
				RequestDetails tempRequestDetails = new RequestDetails();				
				tempRequestDetails.setmRequestNumber(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER)));
				tempRequestDetails.setmRequestCategory(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_CATEGORY)));
				tempRequestDetails.setmRequestDetails(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DETAILS)));
				tempRequestDetails.setmRequestPriority(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_PRIORITY)));
				tempRequestDetails.setmRequestType(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_TYPE)));
				tempRequestDetails.setmRequestStatus(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_STATUS)));
				tempRequestDetails.setmRequestDateTime(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DATETIME)));
				tempRequestDetails.setmUnreadMessageCount(getUnreadMessageCount(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER))));
				mListRequestDetails.add(tempRequestDetails);
			}
		}
		mNewRequestAndComplaintAdapter.notifyDataSetChanged();
	}

	private void sortListByPriorityHtoL(){
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByPriorityHtoLForFlatOwner(ActivatedUserDetails.flatOwnerCode);
		mListRequestDetails.clear();
		for(int i = 0; i<=requestDetailsCursor.getCount();i++){
			boolean isdata = requestDetailsCursor.moveToPosition(i);
			if(isdata)
			{
				RequestDetails tempRequestDetails = new RequestDetails();				
				tempRequestDetails.setmRequestNumber(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER)));
				tempRequestDetails.setmRequestCategory(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_CATEGORY)));
				tempRequestDetails.setmRequestDetails(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DETAILS)));
				tempRequestDetails.setmRequestPriority(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_PRIORITY)));
				tempRequestDetails.setmRequestType(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_TYPE)));
				tempRequestDetails.setmRequestStatus(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_STATUS)));
				tempRequestDetails.setmRequestDateTime(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DATETIME)));
				tempRequestDetails.setmUnreadMessageCount(getUnreadMessageCount(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER))));
				mListRequestDetails.add(tempRequestDetails);
			}
		}	
		mNewRequestAndComplaintAdapter.notifyDataSetChanged();
	}

	private void sortListByPriorityLtoH(){
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByPriorityLtoHForFlatOwner(ActivatedUserDetails.flatOwnerCode);
		mListRequestDetails.clear();
		for(int i = 0; i<=requestDetailsCursor.getCount();i++){
			boolean isdata = requestDetailsCursor.moveToPosition(i);
			if(isdata)
			{
				RequestDetails tempRequestDetails = new RequestDetails();				
				tempRequestDetails.setmRequestNumber(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER)));
				tempRequestDetails.setmRequestCategory(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_CATEGORY)));
				tempRequestDetails.setmRequestDetails(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DETAILS)));
				tempRequestDetails.setmRequestPriority(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_PRIORITY)));
				tempRequestDetails.setmRequestType(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_TYPE)));
				tempRequestDetails.setmRequestStatus(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_STATUS)));
				tempRequestDetails.setmRequestDateTime(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_DATETIME)));
				tempRequestDetails.setmUnreadMessageCount(getUnreadMessageCount(requestDetailsCursor.getString(requestDetailsCursor.getColumnIndex(TableRequestDetails.REQUEST_NUMBER))));
				mListRequestDetails.add(tempRequestDetails);
			}
		}		
		mNewRequestAndComplaintAdapter.notifyDataSetChanged();
	}
	
	private int getUnreadMessageCount(String requestNumber){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		Cursor details = dbManager.getUnreadMessageCountForRequest(requestNumber);
		if (details!=null && details.getCount()>0) {
			return details.getCount();			
		}
		return 0;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(mListRequestDetails!=null){
			showDataInListView();
		}
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}

}
