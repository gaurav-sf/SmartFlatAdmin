package com.grs.product.smartflatAdmin.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.fragments.NewNoticeFragment;
import com.grs.product.smartflatAdmin.fragments.SentNoticeFragment;

public class ActivatedUserDetails extends FragmentActivity{
	private FragmentTabHost mTabHost;
	  TabWidget widget;
	    HorizontalScrollView hs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activated_user_details);
/*	       mTabHost = (FragmentTabHost)findViewById(R.id.tabhost);
	     //  mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
	       
	        //hs = (HorizontalScrollView)findViewById(R.id.horizontalScrollView);
	        //mTabHost.setup(ActivatedUserDetails.this, getSupportFragmentManager(), android.R.id.tabcontent);
	       mTabHost.setup(ActivatedUserDetails.this, getSupportFragmentManager(), R.id.fragmentContent);
*/	 
		  mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		    mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

	        mTabHost.addTab(
	                mTabHost.newTabSpec("tab1").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.new_notice)),
	                NewNoticeFragment.class, null);
	        mTabHost.addTab(
	                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.sent_notice)),
	                SentNoticeFragment.class, null);
	        mTabHost.addTab(
	                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.app_name)),
	                SentNoticeFragment.class, null);
	        mTabHost.addTab(
	                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.activated_users)),
	                SentNoticeFragment.class, null);
	        mTabHost.addTab(
	                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.visited_visitor)),
	                SentNoticeFragment.class, null);
	        mTabHost.addTab(
	                mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.sent_notice)),
	                SentNoticeFragment.class, null);
	        

	}
	
    private View getTabIndicator(Context context, int title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }

}
