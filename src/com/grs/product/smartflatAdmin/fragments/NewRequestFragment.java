package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.activities.RequestDetailsActivity;
import com.grs.product.smartflatAdmin.adapter.NewRequestAndComplaintAdapter;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.GetMessagesTask;
import com.grs.product.smartflatAdmin.asynctasks.GetRequestAndComplaintTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableRequestDetails;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class NewRequestFragment extends Fragment {

	private final String LOG = NewRequestFragment.class.getName();
	private List<RequestDetails> mListRequestDetails;
	private ListView mListViewNewRequestAndCompliant;
	private NewRequestAndComplaintAdapter mNewRequestAndComplaintAdapter;
	private Spinner mSpinnertSorting;
	SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();

	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_new_request, container, false);	
		initialiseUI(rootView);
		getRequestAndComplaintDetails();
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
	
	private void getRequestAndComplaintDetails(){
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new GetRequestAndComplaintTask(getActivity(), new GetRequestAndComplaintTaskListener())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			showDataInListView();
		}				
	
	}
	
	public class GetRequestAndComplaintTaskListener implements AsyncTaskCompleteListener<List<RequestDetails>>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(getActivity(), "", false);
		}

		@Override
		public void onTaskComplete(List<RequestDetails> result) {
			if (result!=null) {
				mListRequestDetails = result;
				saveRequestAndComplaintInDB();
				getMessagesFromServer();
				showDataInListView();			
			}
			
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();
			
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			CustomProgressDialog.removeDialog();
			if (e.getMessage().equalsIgnoreCase("No Requests to process for a day")) {
				Utilities.ShowAlertBox(getActivity(), "Message", "No Requests to process for a day");
				mSpinnertSorting.setVisibility(View.INVISIBLE);
			}else{
				Utilities.ShowAlertBox(getActivity(), "Error", "Server error occured please try later");
				//showDataInListView();			
			}
		}
		
	}
	
	private void saveRequestAndComplaintInDB(){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < mListRequestDetails.size(); i++)
		{
			boolean result = objManager.saveRequestDetails(mListRequestDetails.get(i));
			if(result)
			{
				Log.e(LOG, " Insertion Successful");
			}
		}		
	}
	
	private void showDataInListView(){
		mNewRequestAndComplaintAdapter = new NewRequestAndComplaintAdapter(getActivity(),mListRequestDetails);
		mListViewNewRequestAndCompliant.setAdapter(mNewRequestAndComplaintAdapter);
		sortListByDate();
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
				// TODO Auto-generated method stub

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
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetails();
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
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByType();
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
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByCategory();
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
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByPriorityHtoL();
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
		Cursor requestDetailsCursor = objManager.getRaisedRequestDetailsByPriorityLtoH();
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
	
	private void getMessagesFromServer(){
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new GetMessagesTask(getActivity(), new GetMessagesTaskListener())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(getActivity(),"Error", "Please check your Internet");
		}						
	}
	
	public class GetMessagesTaskListener implements AsyncTaskCompleteListener<List<RequestMessages>>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(getActivity(), "", false);			
		}

		@Override
		public void onTaskComplete(List<RequestMessages> result) {
			if(result!=null){
				saveMessagesInDB(result);
			}
			
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			CustomProgressDialog.removeDialog();
			
		}
		
	}
	
	private void saveMessagesInDB(List<RequestMessages> listMessages){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listMessages.size(); i++)
		{
			boolean result = objManager.saveMessage(listMessages.get(i));
			if(result)
			{
				Log.e(LOG, " Insertion Successful");
			}
		}		
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
		// TODO Auto-generated method stub
		super.onResume();
		if(mListRequestDetails!=null){
			showDataInListView();
		}
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

}
