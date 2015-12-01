package com.grs.product.smartflatAdmin.fragments;

import com.grs.product.smartflatAdmin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewVisitorFragment extends Fragment{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.fragment_new_visitor, container, false);	
/*		initialiseUI(rootView);
		addListeners();*/
		
        return rootView;
		}

}
