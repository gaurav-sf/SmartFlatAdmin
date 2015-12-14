package com.grs.product.smartflatAdmin.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.Tab;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.SentNoticeListAdapter;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyNotices;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.NoticeDetails;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class SentNoticeFragment extends Fragment{
	private List<NoticeDetails> mListNoticeDetails;
	private ListView mListViewNoticeDetails;
	private SentNoticeListAdapter mNoticeListAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_sent_notice, container, false);
		//getUserDetails();
		initialiseUI(rootView);
		return rootView;	      
	}

	private void initialiseUI(View rootView){
		mListViewNoticeDetails = (ListView) rootView.findViewById(R.id.listViewNotice);
		mListNoticeDetails = new ArrayList<NoticeDetails>();
		
		getNoticesFromDB();
	}

	private void getNoticesFromDB()
	{
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		Cursor cursorDetails = dbManager.getAllNotice();
		if (cursorDetails!=null && cursorDetails.getCount()>0)
		{
			for(int i = 0; i<=cursorDetails.getCount();i++)
			{
				boolean isdata = cursorDetails.moveToPosition(i);
				if(isdata)
				{
					NoticeDetails noticeDetails = new NoticeDetails();
					noticeDetails.setmNoticeNumber(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_NUMBER)));
					noticeDetails.setmNoticeTo(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_TO)));
					noticeDetails.setmNoticeSubject(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_SUBJECT)));
					noticeDetails.setmNoticeMessage(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_MESSAGE)));
					noticeDetails.setmNoticeDateTime(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_DATETIME)));
					mListNoticeDetails.add(noticeDetails);
				}
			}
		}
		if(mListNoticeDetails.size()==0){
			Utilities.ShowAlertBox(getActivity(), "Message", "There is no notice to display");
		}else{
			mNoticeListAdapter = new SentNoticeListAdapter(getActivity(), mListNoticeDetails);
			mListViewNoticeDetails.setAdapter(mNoticeListAdapter);
		}
	}

}
