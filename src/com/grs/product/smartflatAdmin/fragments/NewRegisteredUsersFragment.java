package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.NewRegisteredUserListAdapter;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;

public class NewRegisteredUsersFragment  extends Fragment{
	
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
		mNewRegisteredUserListAdapter = new NewRegisteredUserListAdapter(getActivity(), mListNewRegisterUser);
		mListViewNewRegisterUser.setAdapter(mNewRegisteredUserListAdapter);
	}
	
	private void getUserDetails(){
		
	}
	
	

}
