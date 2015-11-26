package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.NewRegisteredUserListAdapter;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.GetFlatUsersTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class NewRegisteredUsersFragment  extends Fragment{
	
	private final String LOG = NewRegisteredUsersFragment.class.getName();
	private List<FlatOwnerDetails> mListNewRegisterUser;
	private NewRegisteredUserListAdapter mNewRegisteredUserListAdapter;
	private ListView mListViewNewRegisterUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_new_registered_users, container, false);
		getUserDetails();
		initialiseUI(rootView);
        return rootView;
	}
	
	private void initialiseUI(View rootView){
		mListViewNewRegisterUser = (ListView) rootView.findViewById(R.id.listViewNewRegisterUser);
		mListNewRegisterUser = new ArrayList<FlatOwnerDetails>();
	}
	
	private void getUserDetails(){
		if (NetworkDetector.init(getActivity()).isNetworkAvailable()) 
		{
			new GetFlatUsersTask(getActivity(), new GetFlatUsersTaskListener())
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(getActivity(),"Error", "Please check your Internet");
		}				
	}
	
	public class GetFlatUsersTaskListener implements AsyncTaskCompleteListener<List<FlatOwnerDetails>>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(getActivity(), "", false);			
		}

		@Override
		public void onTaskComplete(List<FlatOwnerDetails> result) {
			if(result!=null){
				saveDataInDB(result);
				mListNewRegisterUser = result;
				showDataInList(mListNewRegisterUser);
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
	
	private void saveDataInDB(List<FlatOwnerDetails> listDetails){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listDetails.size(); i++)
		{
			boolean result = objManager.saveFlatOwnerDeatils(listDetails.get(i));
			if(result)
			{
				Log.e(LOG, "Society Details Insertion Successful");
			}
		}		
	}
	
	private void showDataInList(List<FlatOwnerDetails> listDetails){
		mNewRegisteredUserListAdapter = new NewRegisteredUserListAdapter(getActivity(), listDetails);
		mListViewNewRegisterUser.setAdapter(mNewRegisteredUserListAdapter);
	}
	

}
