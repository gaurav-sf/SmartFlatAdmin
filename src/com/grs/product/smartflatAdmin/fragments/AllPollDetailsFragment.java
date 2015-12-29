package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.SocietyPollListAdapter;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.GetAllPollsTask;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.SocietyPollDetails;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class AllPollDetailsFragment  extends Fragment{
	
	private SocietyPollDetails mSocietyPollDetails;
	private List<SocietyPollDetails> mListSocietyPollDetails;
	private ListView mListViewSocietyPollDetails;
	private SocietyPollListAdapter mSocietyPollListAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_all_polls, container, false);	
		initialiseUI(rootView);
		getPollDetails();
		//addListeners();	
        return rootView;
		}
	
	private void initialiseUI(View rootView){
		mSocietyPollDetails = new SocietyPollDetails();
		mListSocietyPollDetails = new ArrayList<SocietyPollDetails>();
		mListViewSocietyPollDetails = (ListView) rootView.findViewById(R.id.listViewAllPolls);
	}
	
	private void getPollDetails(){
		
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new GetAllPollsTask(getActivity(),new getAllPollsTaskListener() )
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(getActivity(), "Message", "Please check your Internet");		
		}					
	
	}
	
	public class getAllPollsTaskListener implements AsyncTaskCompleteListener<List<SocietyPollDetails>>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(getActivity(), "", false);			
		}

		@Override
		public void onTaskComplete(List<SocietyPollDetails> result) {
			if(result!=null){
				mListSocietyPollDetails = result;
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
			if (e!=null)
			Utilities.ShowAlertBox(getActivity(), "Error", e.getMessage());	
		}	
	}
	private void showDataInListView(){
		mSocietyPollListAdapter = new SocietyPollListAdapter(getActivity(), mListSocietyPollDetails);
		mListViewSocietyPollDetails.setAdapter(mSocietyPollListAdapter);
	}

}
