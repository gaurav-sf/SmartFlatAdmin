package com.grs.product.smartflatAdmin.asynctasks;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SendMessageTask extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = LoginTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	String message,requestNumber,flatOwnerCode;
	Response mMessageStatus;
	
	public SendMessageTask(Context ctx, AsyncTaskCompleteListener<Response> listener, String message, String requestNumber, String flatOwnerCode) 
	{
		this.context = ctx;
		this.listener = listener;
		this.message = message;
		this.requestNumber = requestNumber;
		this.flatOwnerCode = flatOwnerCode;
	}
	
	@Override
	protected void onPreExecute() {
		if(listener!=null)
			listener.onStarted();
	}
	
	@Override
	protected SmartFlatAdminError doInBackground(Void... params) {
		
		SmartFlatAdminAPI smartFlatAPI = new SmartFlatAdminAPI(context);
		try 
		{
			mMessageStatus =  smartFlatAPI.sendMesssage(message, requestNumber, flatOwnerCode);
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
		
		if(mMessageStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mMessageStatus);
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