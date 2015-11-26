package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.NewRequestAndComplaintAdapter;
import com.grs.product.smartflatAdmin.adapter.NewRegisteredUserListAdapter.ActivateFlatOwnerTaskListener;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.ActivateFlatOwnerTask;
import com.grs.product.smartflatAdmin.asynctasks.GetRequestAndComplaintTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.RequestDetails;
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
import android.view.ViewGroup;
import android.widget.ListView;

public class NewRequestFragment extends Fragment {

	private final String LOG = NewRequestFragment.class.getName();
	private List<RequestDetails> mListRequestDetails;
	private ListView mListViewNewRequestAndCompliant;
	private NewRequestAndComplaintAdapter mNewRequestAndComplaintAdapter;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_new_request, container, false);	
		initialiseUI(rootView);
		getRequestAndComplaintDetails();
        return rootView;
	}
	
	private void initialiseUI(View rootView){
		mListRequestDetails = new ArrayList<RequestDetails>();
		mListViewNewRequestAndCompliant = (ListView) rootView.findViewById(R.id.listViewNewRequestAndCompliant);
	}
	
	private void getRequestAndComplaintDetails(){


		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new GetRequestAndComplaintTask(getActivity(), new GetRequestAndComplaintTaskListener())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(getActivity(),"Error", "Please check your Internet");
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
			Utilities.ShowAlertBox(getActivity(), "Error", "Server error occured please try later");
			
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
	}

}
