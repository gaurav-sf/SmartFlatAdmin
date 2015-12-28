package com.grs.product.smartflatAdmin.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.activities.LoginActivity;
import com.grs.product.smartflatAdmin.activities.SocietyPollsActivity;
import com.grs.product.smartflatAdmin.adapter.CustomGridAdapter;

public class HomeFragment extends Fragment {
	
	GridView mHomeItemsGrid;
	  //String HALLOWEEN_ORANGE="#ffffff";
	  //final CharSequence[] items = {" Engineering "," Polytechnic "," GATE "," CAT "," Aptitude "};
	  public static final String MY_PREFS_NAME = "MyPrefsFile";
	  String[] item = {
	        "User Profile",
	      "Society Polls",
	      "Request",
	      "Unread Notice"} ;
	  
	  int[] imageId = {
		      R.drawable.ic_user_icon,
		      R.drawable.ic_pending_request_icon,
		      R.drawable.ic_request_icon,
		      R.drawable.ic_notice_icon} ;
	  
	  @Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container, false);
			initialiseUI(rootView);
			addListeners();
	        return rootView;
		}
		
		
		private void initialiseUI(View rootview){		
		    mHomeItemsGrid=(GridView)rootview.findViewById(R.id.grid);
		    CustomGridAdapter cGridAdapter = new CustomGridAdapter(getActivity(), item, imageId);
	        mHomeItemsGrid.setAdapter(cGridAdapter);        
		}
		
		private void addListeners(){
			mHomeItemsGrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					switch (position){
					
					case 0:
						
						break;
					case 1:
						Intent pollIntent = new Intent(getActivity(),SocietyPollsActivity.class);
						startActivity(pollIntent);
						break;
					case 2:
						break;
					case 3:
						break;
					case 4:
						break;
					case 5:
						break;					
					}
					
				}
			});
			
		}

		
	}
