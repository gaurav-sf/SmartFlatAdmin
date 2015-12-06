package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.AddedContactsListAdapter;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableContactDetails;
import com.grs.product.smartflatAdmin.models.ContactDetails;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AddedContactsFragment extends Fragment{

	private List<ContactDetails> mContactList;
	private ListView mListViewContacts;
	private AddedContactsListAdapter mAddedContactsListAdapter;

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_added_contacts, container, false);
		initialiseUI(rootView);
		getContactsFromDB();
		showDataInListView();
		return rootView;	     
	}

	private void initialiseUI(View rootView){
		mContactList = new ArrayList<ContactDetails>();
		mListViewContacts = (ListView) rootView.findViewById(R.id.listViewContacts);
	}

	private void getContactsFromDB(){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		Cursor cursorDetails = dbManager.getAllContacts();
		if (cursorDetails!=null && cursorDetails.getCount()>0) 
		{
			for(int i = 0; i<=cursorDetails.getCount();i++)
			{
				boolean isdata = cursorDetails.moveToPosition(i);
				if(isdata)
				{
					ContactDetails temp = new ContactDetails();
					temp.setmContactName(cursorDetails.getString(cursorDetails.getColumnIndex(TableContactDetails.CONTACT_NAME)));
					temp.setmContactNumber(cursorDetails.getString(cursorDetails.getColumnIndex(TableContactDetails.CONTACT_NUMBER)));
					temp.setmContactEmailId(cursorDetails.getString(cursorDetails.getColumnIndex(TableContactDetails.CONTACT_EMAIL_ID)));
					temp.setmContactOccupation(cursorDetails.getString(cursorDetails.getColumnIndex(TableContactDetails.CONTACT_OCCUPATION)));
					mContactList.add(temp);
				}
			}
		}
	}
	
	private void showDataInListView(){
		mAddedContactsListAdapter = new AddedContactsListAdapter(getActivity(), mContactList);
		mListViewContacts.setAdapter(mAddedContactsListAdapter);	
	}

}
