package com.grs.product.smartflatAdmin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;

public class SignOutTask  extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = SignOutTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	Response mSignOutStatus;
	
	public SignOutTask(Context ctx, AsyncTaskCompleteListener<Response> listener) 
	{
		this.context = ctx;
		this.listener = listener;
	}
	
	@Override
	protected void onPreExecute() {
		if(listener!=null)
			listener.onStarted();
	}
	
	@Override
	protected SmartFlatAdminError doInBackground(Void... params) {
		
		SmartFlatAdminAPI smartFlaAdmintAPI = new SmartFlatAdminAPI(context);
		try 
		{
			mSignOutStatus =  smartFlaAdmintAPI.getSignOut();
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
		
		if(mSignOutStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mSignOutStatus);
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
