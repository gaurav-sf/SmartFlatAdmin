package com.grs.product.smartflatAdmin;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.grs.product.smartflatAdmin.database.SmartFlatAdminDatabase;

public class SmartFlatAdminApplication extends Application{
	
	private static SmartFlatAdminApplication singleton;
	private static SharedPreferences sharedpreferences;
	private static String SMARTFLATADMINSHAREDPREFERENCES = "SmartFlatAdminSP";
	
	public static SmartFlatAdminApplication getInstance() {
		return singleton;
	}
	
	protected void init(){
		singleton = this;
		sharedpreferences = getSharedPreferences(SMARTFLATADMINSHAREDPREFERENCES, Context.MODE_PRIVATE);
	}
	
	@Override
	public void onCreate() {
		this.init();
		SmartFlatAdminDatabase.getInstance();
		super.onCreate();
	}
	
	public static void saveSocietyCodeInSharedPreferences(String societycode){
		   SharedPreferences.Editor editor = sharedpreferences.edit();           
           editor.putString("SocietyCode", societycode);
           editor.commit();	
	}
	
	public static String getSocietyCodeFromSharedPreferences(){	
		
		String restoredText = sharedpreferences.getString("SocietyCode", null);		
		return restoredText;	
	}
		
	public static void saveSocietyOwnerAccessCodeInSharedPreferences(String flatOwnerCode){
		SharedPreferences.Editor editor = sharedpreferences.edit();           
        editor.putString("SocietyOwnerAccessCode", flatOwnerCode);
        editor.commit();	
	}
	
	public static String getSocietyOwnerAccessCodeFromSharedPreferences(){	
		
		String restoredText = sharedpreferences.getString("SocietyOwnerAccessCode", null);		
		return restoredText;	
	}

	public static void saveUsersLastSyncTime(String time){
		SharedPreferences.Editor editor = sharedpreferences.edit();           
        editor.putString("UserLastSyncTime", time);
        editor.commit();	
	}
	
	public static String getUsersLastSyncTime(){	
		
		String restoredText = sharedpreferences.getString("UserLastSyncTime", null);		
		return restoredText;	
	}
	
	public static void saveRequestLastSyncTime(String time){
		SharedPreferences.Editor editor = sharedpreferences.edit();           
        editor.putString("RequestLastSyncTime", time);
        editor.commit();	
	}
	
	public static String getRequestLastSyncTime(){	
		
		String restoredText = sharedpreferences.getString("RequestLastSyncTime", null);		
		return restoredText;	
	}
	
	public static void saveLVisitorLastSyncTime(String time){
		SharedPreferences.Editor editor = sharedpreferences.edit();           
        editor.putString("VisitorLastSyncTime", time);
        editor.commit();	
	}
	
	public static String getVisitorLastSyncTime(){	
		
		String restoredText = sharedpreferences.getString("VisitorLastSyncTime", null);		
		return restoredText;	
	}
	
	public static void saveSocietyOwnerPushTokenInSharedPreferences(String flatOwnerCode){
		SharedPreferences.Editor editor = sharedpreferences.edit();           
        editor.putString("SocietyOwnerPushToken", flatOwnerCode);
        editor.commit();	
	}
	
	public static String getSocietyOwnerPushTokenFromSharedPreferences(){		
		String restoredText = sharedpreferences.getString("SocietyOwnerPushToken", null);		
		return restoredText;	
	}

}
