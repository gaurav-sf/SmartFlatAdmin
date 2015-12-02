package com.grs.product.smartflatAdmin.asynctasks;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.VisitorDetails;
import com.grs.product.smartflatAdmin.response.Response;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SaveVisitorOnServerTask extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = LoginTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	Response mVisitorStatus;
	private VisitorDetails mVisitorDetails;
	
	public SaveVisitorOnServerTask(Context ctx, AsyncTaskCompleteListener<Response> listener, VisitorDetails details) 
	{
		this.context = ctx;
		this.listener = listener;
		this.mVisitorDetails = details;
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
			mVisitorStatus =  smartFlatAPI.saveVisitor(mVisitorDetails);
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
		
		if(mVisitorStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mVisitorStatus);
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