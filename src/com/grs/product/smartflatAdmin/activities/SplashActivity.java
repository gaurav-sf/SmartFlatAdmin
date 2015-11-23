package com.grs.product.smartflatAdmin.activities;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.TextView;

public class SplashActivity extends Activity {

	private Animation mZoomIn;
	private TextView mTextViewAppName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/** Hiding Title bar of this activity screen */
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);

		/** Making this activity, full screen */
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_splash);
		mTextViewAppName = (TextView) findViewById(R.id.tv_title);
		mZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.animator.zoom_in);
		addListner();
		mTextViewAppName.setAnimation(mZoomIn);
		// goToNextActivity();
	}

	private void addListner(){
		mZoomIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				goToNextActivity();
			}
		});
	}

	private void goToNextActivity(){	
		
		if(SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()==null || 
				SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences().equals(null))
		{
			Intent goToLoginScreen = new Intent(SplashActivity.this,SocietyOwnerRegistration.class);
			startActivity(goToLoginScreen);
			finish();
		}
		else if(SmartFlatAdminApplication.getSocietyOwnerAccessCodeFromSharedPreferences()==null || 
				SmartFlatAdminApplication.getSocietyOwnerAccessCodeFromSharedPreferences().equals(null)){
			Intent goToLoginScreen = new Intent(SplashActivity.this,LoginActivity.class);
			startActivity(goToLoginScreen);
			finish();

		}else{
			Intent goToDashboardScreen = new Intent(SplashActivity.this,DashBoardActivity.class);
			startActivity(goToDashboardScreen);
			finish();

		}


	}
}
