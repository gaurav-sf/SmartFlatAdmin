package com.grs.product.smartflatAdmin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grs.product.smartflatAdmin.R;

public class SingleFlatOwnerNoticeDetailsFragment  extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_sent_notice, container, false);
        return rootView;
	}

}
