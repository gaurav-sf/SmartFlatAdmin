package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.NewRegisteredUserListAdapter;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.GetFlatUsersTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NewRegisteredUsersFragment  extends Fragment{
	
	private final String LOG = NewRegisteredUsersFragment.class.getName();
	private List<FlatOwnerDetails> mListNewRegisterUser;
	private NewRegisteredUserListAdapter mNewRegisteredUserListAdapter;
	private ListView mListViewNewRegisterUser;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_new_registered_users, container, false);
		initialiseUI(rootView);
		getUserDetails();
		//initialiseUI(rootView);
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
			Utilities.ShowAlertBox(getActivity(),"Message", "You are offline. Data shown is as per last sync details.");
			showOfflineData();
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
				showOfflineData();
				//showDataInList(mListNewRegisterUser);
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
				Log.e(LOG, "Flat Owner Details Insertion Successful");
			}
		}		
	}
	
	private void showDataInList(){
		if(mListNewRegisterUser.size()>0){
			mNewRegisteredUserListAdapter = new NewRegisteredUserListAdapter(getActivity(), mListNewRegisterUser);
			mListViewNewRegisterUser.setAdapter(mNewRegisteredUserListAdapter);		}else
		{
			Utilities.ShowAlertBox(getActivity(), "Message", "There is no user to display");
		}

	}
	
	public void showOfflineData(){
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		Cursor deails = objManager.getAllFlatOwnerDetails("0");
	//	 List<FlatOwnerDetails> listNewRegisterUser = new ArrayList<FlatOwnerDetails>();
		mListNewRegisterUser.clear();
		for(int i = 0; i<=deails.getCount();i++){
			boolean isdata = deails.moveToPosition(i);
			if(isdata)
			{
				FlatOwnerDetails flatOwnerDetails = new FlatOwnerDetails();
				flatOwnerDetails.setmFlatOwnerName(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_NAME)));
				flatOwnerDetails.setmFlatOwnerContactNo(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CONTACT_NO)));
				flatOwnerDetails.setmFlatOwnerEmailId(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_EMAIL_ID)));
				flatOwnerDetails.setmBuildingName(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLAT_BUILDING_NAME)));
				flatOwnerDetails.setmFloorNo(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLOOR_NO)));
				flatOwnerDetails.setmFlatno(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLAT_NO)));
				flatOwnerDetails.setmFlatOwnerCreatedDateTime(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CREATED_DATETIME)));
				flatOwnerDetails.setmFlatOwnerCode(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.FLAT_OWNER_CODE)));
				flatOwnerDetails.setActive(Boolean.parseBoolean(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.IS_ACTIVE))));
				mListNewRegisterUser.add(flatOwnerDetails);
			}
		}
		showDataInList();
	}
	
	public  void deleteUserFromList(int position){
		mListNewRegisterUser.remove(position);
		mNewRegisteredUserListAdapter.notifyDataSetChanged();
	}
	

}
