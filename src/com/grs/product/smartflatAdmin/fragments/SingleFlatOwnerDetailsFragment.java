package com.grs.product.smartflatAdmin.fragments;

import com.grs.product.smartflatAdmin.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SingleFlatOwnerDetailsFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container, Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_single_flat_owner_details, container, false);
        return rootView;
	}

}
