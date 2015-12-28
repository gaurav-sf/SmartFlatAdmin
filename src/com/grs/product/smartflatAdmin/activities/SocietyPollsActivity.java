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
import com.grs.product.smartflatAdmin.fragments.AddNewPollFragment;
import com.grs.product.smartflatAdmin.fragments.AllPollDetailsFragment;

public class SocietyPollsActivity  extends FragmentActivity{
	private FragmentTabHost mTabHost;
	TabWidget widget;
	HorizontalScrollView hs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_society_polls);
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab1").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.text_polls)),
				AllPollDetailsFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.text_add_new)),
				AddNewPollFragment.class, null);	}

	private View getTabIndicator(Context context, int title) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
		TextView tv = (TextView) view.findViewById(R.id.textView);
		tv.setText(title);
		return view;
	}
}
