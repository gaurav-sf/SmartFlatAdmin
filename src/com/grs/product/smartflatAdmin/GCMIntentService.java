package com.grs.product.smartflatAdmin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.grs.product.smartflatAdmin.activities.DashBoardActivity;
import com.grs.product.smartflatAdmin.utils.Param;

public class GCMIntentService extends GCMBaseIntentService {
	
	public static String registerid="";

	
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
		Log.i(TAG, "new message= "+message);
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
	private static void generateNotification(Context context, String message) {
		
		boolean isHash = false, isStar = false, isnews = false;
		Intent notificationIntent;
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		
		if(message.contains("#")){
			isHash = true;			
		}else if(message.contains("*")){
			isStar = true;
		}else{
			isnews = true;
		}
		message = message.replace("#", "");
		message = message.replace("*", "");
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		// Play default notification sound
		  notification.defaults |= Notification.DEFAULT_SOUND;
		  notification.defaults |= Notification.DEFAULT_VIBRATE;
		  
		String title = context.getString(R.string.app_name);

		     notificationIntent = new Intent(context, DashBoardActivity.class);
		
		//Intent notificationIntent = new Intent(context, OrderReceivedActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notificationManager.notify(0, notification);
	}

}
