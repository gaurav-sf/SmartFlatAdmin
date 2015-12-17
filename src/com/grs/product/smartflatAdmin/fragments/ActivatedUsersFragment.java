package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.activities.ActivatedUserDetails;
import com.grs.product.smartflatAdmin.adapter.ActivatedUsersListAdapter;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.utils.Utilities;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

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
		addListeners();
        return rootView;
	}
	
	private void initialiseUI(View rootView){
		mListViewNewRegisterUser = (ListView) rootView.findViewById(R.id.listViewActivatedUser);
		mListNewRegisterUser = new ArrayList<FlatOwnerDetails>();
	}
	
	private void showDataInList(){
		mActivatedUsersListAdapter = new ActivatedUsersListAdapter(getActivity(), mListNewRegisterUser);
		mListViewNewRegisterUser.setAdapter(mActivatedUsersListAdapter);
	}
	
	public void getUserDetails(){
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		Cursor deails = objManager.getAllFlatOwnerDetails("1");
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
				flatOwnerDetails.setmGender(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.GENDER)));
				flatOwnerDetails.setActive(Boolean.parseBoolean(deails.getString(deails.getColumnIndex(TableFlatOwnerDetails.IS_ACTIVE))));
				mListNewRegisterUser.add(flatOwnerDetails);
			}
		}
		if(mListNewRegisterUser.size()>0){
			showDataInList();
		}else
		{
			Utilities.ShowAlertBox(getActivity(), "Message", "There is no user to display");
		}
	}
	
	private void addListeners(){
		mListViewNewRegisterUser.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent gotoFlatOwnerDetails = new Intent(getActivity(),ActivatedUserDetails.class);
				gotoFlatOwnerDetails.putExtra("flatOwnerCode", mListNewRegisterUser.get(position).getmFlatOwnerCode());
				startActivity(gotoFlatOwnerDetails);
			}
		});
	}

}
