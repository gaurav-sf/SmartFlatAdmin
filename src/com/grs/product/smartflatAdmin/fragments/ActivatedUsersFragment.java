package com.grs.product.smartflatAdmin.fragments;

import com.grs.product.smartflatAdmin.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActivatedUsersFragment  extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_activated_users, container, false);
		//getUserDetails();
		//initialiseUI(rootView);
        return rootView;
	}

}
