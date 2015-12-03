package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.VisitedVisitorsListAdapter;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableVisitorDetails;
import com.grs.product.smartflatAdmin.models.VisitorDetails;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class VisitedVisitorFrgament extends Fragment{

	private ListView mListViewVisitedVisitors;
	private List<VisitorDetails> mListVisitors;
	private VisitedVisitorsListAdapter mVisitedVisitorsListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.fragment_visited_visitors, container, false);	
		initialiseUI(rootView);
		getVisitorDetails();
		addListeners();

		return rootView;
	}

	private void initialiseUI(View rootView){
		mListViewVisitedVisitors = (ListView) rootView.findViewById(R.id.listViewVisitedVisitors);
		mListVisitors = new ArrayList<VisitorDetails>();
		mListVisitors = getVisitorDetails();		
		mVisitedVisitorsListAdapter = new VisitedVisitorsListAdapter(getActivity(), mListVisitors);
	}

	private void addListeners(){

	}

	private List<VisitorDetails> getVisitorDetails(){
		List<VisitorDetails> tempListVisitors =  new ArrayList<VisitorDetails>();
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		Cursor cursorVisitors = dbManager.getVisitors();
		if(cursorVisitors!=null && cursorVisitors.getCount()>0){
			for (int i = 0; i < cursorVisitors.getCount(); i++) {
				boolean isdata = cursorVisitors.moveToPosition(i);
				if(isdata)
				{
					VisitorDetails tempDetails = new VisitorDetails();
					tempDetails.setmVisitorName(cursorVisitors.getString(cursorVisitors.getColumnIndex(TableVisitorDetails.VISITOR_NAME)));
					tempDetails.setmNoofVisitors(cursorVisitors.getString(cursorVisitors.getColumnIndex(TableVisitorDetails.NO_OF_VISITORS)));
					tempDetails.setmVisitorInTime(cursorVisitors.getString(cursorVisitors.getColumnIndex(TableVisitorDetails.VISITOR_IN_TIME)));
					tempDetails.setmVisitorContacNo(cursorVisitors.getString(cursorVisitors.getColumnIndex(TableVisitorDetails.VISITOR_CONTACT_NO)));
					tempDetails.setmVisitorVehicleNo(cursorVisitors.getString(cursorVisitors.getColumnIndex(TableVisitorDetails.VISITOR_VEHICLE_NO)));
					tempDetails.setmVisitPurpose(cursorVisitors.getString(cursorVisitors.getColumnIndex(TableVisitorDetails.VISIT_PURPOSE)));
					tempDetails.setmFlatOwnerCode(cursorVisitors.getString(cursorVisitors.getColumnIndex(TableVisitorDetails.FLAT_OWNER_CODE)));

					tempListVisitors.add(tempDetails);
				}			
			}
		}
		return tempListVisitors;
	}

}
