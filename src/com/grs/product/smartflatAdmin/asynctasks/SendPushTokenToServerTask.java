package com.grs.product.smartflatAdmin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;

public class SendPushTokenToServerTask  extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = LoginTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	String pushToken;
	Response mLoginStatus;
	
	public SendPushTokenToServerTask(Context ctx, AsyncTaskCompleteListener<Response> listener, String pushToken) 
	{
		this.context = ctx;
		this.listener = listener;
		this.pushToken = pushToken;	
	}
	
	@Override
	protected void onPreExecute() {
		if(listener!=null)
			listener.onStarted();
	}
	
	@Override
	protected SmartFlatAdminError doInBackground(Void... params) {
		
		SmartFlatAdminAPI smartFlatAdminAPI = new SmartFlatAdminAPI(context);
		try 
		{
			mLoginStatus =  smartFlatAdminAPI.sendPushToken(pushToken);
		}
		catch (SmartFlatAdminError e) 
		{
			Log.e(TAG, e.toString());
			return e;		
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(SmartFlatAdminError error) {
		
		if(mLoginStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mLoginStatus);
				listener = null;
			}
		}
		else 
		{		
			if(listener!=null)
			{
				if(error!=null)
				{
					listener.onStopedWithError(error);
				}
				
				listener = null;
			}
			
		}

	}

}
