package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.ActivatedUsersListAdapter;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.utils.Utilities;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ActivatedUsersFragment  extends Fragment{
	
	private List<FlatOwnerDetails> mListNewRegisterUser;
	private ListView mListViewNewRegisterUser;
	private ActivatedUsersListAdapter mActivatedUsersListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_activated_users, container, false);
		initialiseUI(rootView);
		getUserDetails();		
        return rootView;
	}
	
	private void initialiseUI(View rootView){
		mListViewNewRegisterUser = (ListView) rootView.findViewById(R.id.listViewNewRegisterUser);
		mListNewRegisterUser = new ArrayList<FlatOwnerDetails>();
	}
	
	private void showDataInList(List<FlatOwnerDetails> listDetails){
		mActivatedUsersListAdapter = new ActivatedUsersListAdapter(getActivity(), listDetails);
		mListViewNewRegisterUser.setAdapter(mActivatedUsersListAdapter);
	}
	
	public void getUserDetails(){
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		Cursor deails = objManager.getAllFlatOwnerDetails("1");
		 List<FlatOwnerDetails> listNewRegisterUser = new ArrayList<FlatOwnerDetails>();
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
				listNewRegisterUser.add(flatOwnerDetails);
			}
		}
		if(listNewRegisterUser.size()>0){
			showDataInList(listNewRegisterUser);
		}else
		{
			Utilities.ShowAlertBox(getActivity(), "Message", "Ther is no user to display");
		}
	}

}
