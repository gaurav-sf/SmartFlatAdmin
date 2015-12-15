package com.grs.product.smartflatAdmin;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.android.gcm.GCMBaseIntentService;
import com.grs.product.smartflatAdmin.activities.RequestDetailsActivity;
import com.grs.product.smartflatAdmin.apicall.JSONSingleObjectDecode;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.fragments.MainUsersFragment;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
import com.grs.product.smartflatAdmin.utils.Param;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GCMIntentService extends GCMBaseIntentService {
	
	public static String registerid="";
	private static int uniqueId = 0;

	
	public GCMIntentService() {
		super(Param.GOOGLE_PROJ_ID);
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String message = intent.getExtras().getString("message");
		if(message.contains("New User Registered"))
		{
			JSONObject json;
			try {
				json = new JSONObject(intent.getExtras().getString("jsonData"));
				JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
				saveUserDataInDB(objectjson.getFlatUsers(json));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(message.contains("New Request"))
		{
			JSONObject json;
			try {
				json = new JSONObject(intent.getExtras().getString("jsonData"));
				JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
				saveRequestAndComplaintInDB(objectjson.getRequestAndComplaint(json));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(message.contains("New Message")){
			JSONObject json;
			try {
				json = new JSONObject(intent.getExtras().getString("jsonData"));
				JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
				saveMessageInDB(objectjson.getMessages(json));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//Log.e(TAG, "new JSON= "+json);
		Log.i(TAG, "message from server= "+message);
		generateNotification(context, message);
		
	}

	@Override
	protected void onRegistered(Context arg0, String registeredid) {
		// TODO Auto-generated method stub
		Log.d("----Device registered: regId-----",registeredid);
		registerid = registeredid;
		
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// TODO Auto-generated method stub
		return super.onRecoverableError(context, errorId);
	}
	
	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {
		
		Intent notificationIntent = null;
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		// Play default notification sound
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.defaults |= Notification.DEFAULT_VIBRATE;
		  
		String title = context.getString(R.string.app_name);
		if(message.contains("New Message")){
			 notificationIntent = new Intent(context, RequestDetailsActivity.class);
			 notificationIntent.putExtra("requestno", message.split("-")[1].trim());
			 Intent intent = new Intent(Param.DISPLAY_MESSAGE_ACTION);
				intent.putExtra("Message", "newMessage");
				context.sendBroadcast(intent);
		}
		else if(message.equalsIgnoreCase("New User Registered")){
			notificationIntent = new Intent(context, MainUsersFragment.class);
		}
		else if(message.contains("New Request")){
			 notificationIntent = new Intent(context, RequestDetailsActivity.class);
			 notificationIntent.putExtra("requestno", message.split("-")[1].trim());
		}
		    
		
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent,PendingIntent.FLAG_CANCEL_CURRENT);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		uniqueId++;
		notificationManager.notify(0, notification);
	}
	
	private void saveUserDataInDB(List<FlatOwnerDetails> listDetails){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listDetails.size(); i++)
		{
			boolean result = objManager.saveFlatOwnerDeatils(listDetails.get(i));
			if(result)
			{
				Log.e("From Service", "Flat Owner Details Insertion Successful");
			}
		}		
	}
	
	private void saveMessageInDB(List<RequestMessages> listMessages){
		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listMessages.size(); i++)
		{
			boolean result = objManager.saveMessage(listMessages.get(i));
			if(result)
			{
				Log.e("Message Details", " Insertion Successful");
			}
		}		
	
	}
	
	private void saveRequestAndComplaintInDB(List<RequestDetails> mListRequestDetails){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < mListRequestDetails.size(); i++)
		{
			boolean result = objManager.saveRequestDetails(mListRequestDetails.get(i));
			if(result)
			{
				Log.e("From GCM Receiver", "Request Insertion Successful");
			}
		}		
	}

}
