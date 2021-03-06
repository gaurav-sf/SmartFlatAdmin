package com.grs.product.smartflatAdmin.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.google.android.gms.wearable.internal.GetDataItemResponse;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyDetails;
import com.grs.product.smartflatAdmin.models.SocietyDetails;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Utilities {

	public static SocietyDetails getSocietyDetails(){
		SocietyDetails societyDetails = null;
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		Cursor cursorDeatils = dbManager.getSocietyDetails();
		if(cursorDeatils!=null && cursorDeatils.getCount()>0){
			societyDetails = new SocietyDetails();
			societyDetails.setmSocietyCode(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.SOCIETY_CODE)));
			societyDetails.setmSocietyName(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.SOCIETY_NAME)));
			societyDetails.setmBuildingName(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.BUILDING_NAME)));
			societyDetails.setmTotalFloorNumber(cursorDeatils.getInt(cursorDeatils.getColumnIndex(TableSocietyDetails.TOTAL_FLOOR_NUMBER)));
			societyDetails.setmSocietyAddressLine1(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.SOCIETY_ADDRESS_LINE1)));
			societyDetails.setmSocietyAddressLine2(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.SOCIETY_ADDRESS_LINE2)));
			societyDetails.setmSocietyAddressCity(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.SOCIETY_ADDRESS_CITY)));
			societyDetails.setmSocietyAddressState(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.SOCIETY_ADDRESS_STATE)));
			societyDetails.setmSocietyAddressPIN(cursorDeatils.getString(cursorDeatils.getColumnIndex(TableSocietyDetails.SOCIETY_ADDRESS_PIN)));
		}		
		return societyDetails;
	}
	
	public static String getUTCDateTime() {

		String dateTime="";
		Date date = new Date();		
		DateFormat converter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		converter.setTimeZone(TimeZone.getTimeZone("UTC"));
		dateTime=converter.format(date);
		
		return dateTime;
	}
	
	public static void ShowAlertBox(final Context context, String title,
			String message) {
		final Dialog mDialog = new Dialog(context,
				android.R.style.Theme_Translucent_NoTitleBar);
		View layout = LayoutInflater.from(context)
				.inflate(R.layout.alert, null);
		TextView tvAlert = (TextView) layout.findViewById(R.id.tvAlert);
		TextView tvAlertMsg = (TextView) layout.findViewById(R.id.tvAlertMsg);
		tvAlertMsg.setText(message);
		tvAlert.setText(title);
		mDialog.setContentView(layout);
		Button btnOk = (Button) layout.findViewById(R.id.btnOk);
		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(final View view) {
				mDialog.dismiss();
			}
		});
		mDialog.show();
	}
	
	public static String getCurrentDateTime(){
		String dateTime="";
		Date date = new Date();		
		DateFormat converter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		DateFormat converter1 = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		DateFormat converter2 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
		DateFormat converter3 = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);

		DateFormat converter4 = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	
	Log.e("Locale Short", converter1.format(date));
	Log.e("Locale Medium", converter2.format(date));
	Log.e("Locale Long", converter3.format(date));
	Log.e("Locale Full", converter4.format(date));
	//	converter.setTimeZone(TimeZone.getTimeZone("UTC"));
		dateTime=converter.format(date);
		
		return dateTime;
	}
	
	public static void addCustomActionBar(Activity activity){
		ActionBar actionBar = activity.getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater inflater = LayoutInflater.from(activity);
		View mCustomView = inflater.inflate(R.layout.actionbar, null);
		actionBar.setCustomView(mCustomView);
		actionBar.setDisplayShowCustomEnabled(true);
	}
	
	public static boolean isValidEmail(String email) {
	        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	    }
	public static String[] getAllStatesName(){
		String[] state_name = {
				"Andhra Pradesh (AP)","Arunachal Pradesh (AR)","Assam (AS)",
				"Bihar (BR)","Chhattisgarh (CG)","Goa (GA)","Goa (GA)","Haryana (HR)",
				"Himachal Pradesh (HP)","Jammu and Kashmir (JK)","Jharkhand (JH)",
				"Karnataka (KA)","Kerala (KL)","Madhya Pradesh (MP)","Maharashtra (MH)",
				"Manipur (MN)","Meghalaya (ML)","Mizoram (MZ)","Nagaland (NL)","Odisha(OR)",
				"Punjab (PB)","Rajasthan (RJ)","Sikkim (SK)","Tamil Nadu (TN)","Tripura (TR)",
				"Uttar Pradesh (UP)","Uttarakhand (UK)","West Bengal (WB)","Telangana",

				};
		return state_name;
	}

}
