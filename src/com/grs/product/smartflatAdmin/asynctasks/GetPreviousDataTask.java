package com.grs.product.smartflatAdmin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;

public class GetPreviousDataTask  extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = GetPreviousDataTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	String societyCode;
	Response mStatus;
	
	public GetPreviousDataTask(Context ctx, AsyncTaskCompleteListener<Response> listener, String societyCode) 
	{
		this.context = ctx;
		this.listener = listener;
		this.societyCode = societyCode;
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
			mStatus =  smartFlatAdminAPI.getPreviousData(societyCode);
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
		
		if(mStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mStatus);
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
