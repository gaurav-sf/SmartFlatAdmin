package com.grs.product.smartflatAdmin.fragments;

import com.grs.product.smartflatAdmin.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SentNoticeFragment extends Fragment{
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_sent_notice, container, false);
		//getUserDetails();
		//initialiseUI(rootView);
        return rootView;	}

}
