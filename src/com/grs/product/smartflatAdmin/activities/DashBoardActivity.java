package com.grs.product.smartflatAdmin.activities;


import java.util.ArrayList;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.adapter.NavDrawerItem;
import com.grs.product.smartflatAdmin.adapter.NavDrawerListAdapter;
import com.grs.product.smartflatAdmin.fragments.HomeFragment;
import com.grs.product.smartflatAdmin.fragments.MainContactsFragment;
import com.grs.product.smartflatAdmin.fragments.MainNoticeFragment;
import com.grs.product.smartflatAdmin.fragments.MainRequestFragment;
import com.grs.product.smartflatAdmin.fragments.MainUsersFragment;
import com.grs.product.smartflatAdmin.fragments.MainVisirtorFragment;
import com.grs.product.smartflatAdmin.fragments.SocietyDetailsFragment;
import com.grs.product.smartflatAdmin.utils.Utilities;


@SuppressWarnings("deprecation")
public class DashBoardActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		//Utilities.addCustomActionBar(this);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
		// Users
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Society
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));		
		// Requests
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(4, -1)));
		// Visitors
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));		
		//Notices
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(6, -1)));
		//Contacts
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
		//About App
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(6, -1)));
		//Help
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(6, -1)));
		//Sign Out
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.activity_screen_slide, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return false;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Displaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		String status = "";
		switch (position) {
		//Home
		case 0:
			status = "created";
			HomeFragment homeFragment = new HomeFragment();
	        getSupportFragmentManager().beginTransaction()
	        .replace(R.id.fragment_container, homeFragment).commit();
			break;
			
		//Users	
		case 1:
			status = "created";
			MainUsersFragment mMainUsersFragment = new MainUsersFragment();
	        getSupportFragmentManager().beginTransaction()
	        .replace(R.id.fragment_container, mMainUsersFragment).commit();
			break;
			
		//Society	
		case 2:
			status = "created";
			SocietyDetailsFragment societyDetailsFragment = new SocietyDetailsFragment();
	        getSupportFragmentManager().beginTransaction()
	        .replace(R.id.fragment_container, societyDetailsFragment).commit();
			break;
			
		//Request/Complaint	
		case 3:
			status = "created";
			MainRequestFragment mainRequestFragment = new MainRequestFragment();
	        getSupportFragmentManager().beginTransaction()
	        .replace(R.id.fragment_container, mainRequestFragment).commit();
			break;	
		//Visitor	
		case 4:
			status = "created";
			MainVisirtorFragment mainVisirtorFragment = new MainVisirtorFragment();
	        getSupportFragmentManager().beginTransaction()
	        .replace(R.id.fragment_container, mainVisirtorFragment).commit();
			break;
			
		//Notice	
		case 5:
			status = "created";
			MainNoticeFragment mainNoticeFragment = new MainNoticeFragment();
	        getSupportFragmentManager().beginTransaction()
	        .replace(R.id.fragment_container, mainNoticeFragment).commit();
			break;
		
		//Contacts	
		case 6:
			status = "created";
			MainContactsFragment contactsFragment = new MainContactsFragment();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.fragment_container, contactsFragment).commit();
			break;
			
		//	AboutApp
		case 7:
/*			status = "created";
			AboutAppFragment aboutAppFragment = new AboutAppFragment();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.fragment_container, aboutAppFragment).commit();*/
			break;
			
		//Help	
		case 8:
/*			status = "created";
			HelpFragment helpFragment = new HelpFragment();
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.fragment_container, helpFragment).commit();*/
			break;
			
		//Sign Out	
		case 9:
			status = "created";
    		overridePendingTransition(R.animator.slide_in_bottom, R.animator.slide_out_bottom);
    		SmartFlatAdminApplication.saveSocietyOwnerAccessCodeInSharedPreferences(null);
			finish();
			break;
			
		default:
			break;
		}

		if (!status.equals("")) 
		{
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}

