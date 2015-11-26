package com.grs.product.smartflatAdmin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.response.Response;

public class ActivateFlatOwnerTask extends AsyncTask<Void, Void, SmartFlatAdminError>{

	private static final String TAG = SaveSocietyOwnerCredentialTask.class.getName();
	final Context mContext;
	private AsyncTaskCompleteListener<Response> listener = null;
	Response mLoginStatus;
	private String flatOwnerCode;
	
	public ActivateFlatOwnerTask(Context mContext,
			AsyncTaskCompleteListener<Response> listener,
			String flatOwnerCode) {
		
		this.mContext = mContext;
		this.listener = listener;
		this.flatOwnerCode = flatOwnerCode;
	}
	
	@Override
	protected void onPreExecute() {
		if(listener!=null)
			listener.onStarted();
	}
	
	@Override
	protected SmartFlatAdminError doInBackground(Void... params) {
		SmartFlatAdminAPI smartFlatAdminAPI = new SmartFlatAdminAPI(mContext);		
		try 
		{			
			mLoginStatus = smartFlatAdminAPI.activateFlatOwner(flatOwnerCode);
			
		} catch (SmartFlatAdminError e) {
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
