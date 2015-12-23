package com.grs.product.smartflatAdmin.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyNotices;
import com.grs.product.smartflatAdmin.models.NoticeDetails;

public class NoticeDetailsActivity extends Activity{
	
	private TextView mTextViewNoticeNumber,mTextViewNoticeSubject, mTextViewNoticeMessage, mTextViewNoticeDateTime;
	private NoticeDetails mNoticeDetails;
	private Bundle extras;
	private String mNoticeNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		extras = getIntent().getExtras();
		mNoticeNumber = extras.getString("noticeNumber");
		initialiseUI();
		getNoticeDetails(mNoticeNumber);
		showData();
	}
	
	private void initialiseUI(){
		mTextViewNoticeNumber = (TextView) findViewById(R.id.textViewNoticeNumber);
		mTextViewNoticeSubject = (TextView) findViewById(R.id.textViewNoticeSubject);
		mTextViewNoticeMessage = (TextView) findViewById(R.id.textViewNoticeMessage);
		mTextViewNoticeDateTime = (TextView) findViewById(R.id.textViewNoticeDateTime);
	}
	
	private void getNoticeDetails(String noticeNumber)
	{
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		Cursor cursorDetails = dbManager.getNoticeDetails(noticeNumber);
		if (cursorDetails!=null&& cursorDetails.getCount()>0) 
		{
			mNoticeDetails = new NoticeDetails();
			mNoticeDetails.setmNoticeNumber(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_NUMBER)));
			mNoticeDetails.setmNoticeSubject(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_SUBJECT)));
			mNoticeDetails.setmNoticeMessage(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_MESSAGE)));
			mNoticeDetails.setmNoticeDateTime(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_DATETIME)));
			mNoticeDetails.setmNoticeTo(cursorDetails.getString(cursorDetails.getColumnIndex(TableSocietyNotices.NOTICE_TO)));	
		}
	}
	
	private void showData(){
		mTextViewNoticeNumber.setText(mNoticeDetails.getmNoticeNumber());
		mTextViewNoticeSubject.setText(mNoticeDetails.getmNoticeSubject());
		mTextViewNoticeMessage.setText(mNoticeDetails.getmNoticeMessage());
		mTextViewNoticeDateTime.setText(mNoticeDetails.getmNoticeDateTime());
	}

}
