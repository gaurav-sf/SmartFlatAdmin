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
import com.grs.product.smartflatAdmin.fragments.SingleFlatOwnerDetailsFragment;
import com.grs.product.smartflatAdmin.fragments.SingleFlatOwnerNoticeDetailsFragment;
import com.grs.product.smartflatAdmin.fragments.SingleFlatOwnerRequestDetails;
import com.grs.product.smartflatAdmin.fragments.SingleFlatOwnerVehicleDetailsFragment;
import com.grs.product.smartflatAdmin.fragments.SingleFlatOwnerVisitorDetailsFragment;

public class ActivatedUserDetails extends FragmentActivity{
	private FragmentTabHost mTabHost;
	TabWidget widget;
	HorizontalScrollView hs;
	public static String flatOwnerCode = "";
	private Bundle extras;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activated_user_details);
		extras = getIntent().getExtras();
		flatOwnerCode = extras.getString("flatOwnerCode");
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

		mTabHost.addTab(
				mTabHost.newTabSpec("tab1").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.text_user_details)),
				SingleFlatOwnerDetailsFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab2").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.text_vehicle)),
				SingleFlatOwnerVehicleDetailsFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab3").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.text_request)),
				SingleFlatOwnerRequestDetails.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab4").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.text_visitors)),
				SingleFlatOwnerVisitorDetailsFragment.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec("tab5").setIndicator(getTabIndicator(mTabHost.getContext(), R.string.text_notice)),
				SingleFlatOwnerNoticeDetailsFragment.class, null);
	}

	private View getTabIndicator(Context context, int title) {
		View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
		TextView tv = (TextView) view.findViewById(R.id.textView);
		tv.setText(title);
		return view;
	}
}
