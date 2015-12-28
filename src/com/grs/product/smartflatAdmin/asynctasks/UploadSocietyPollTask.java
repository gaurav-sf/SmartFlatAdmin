package com.grs.product.smartflatAdmin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.SocietyPollDetails;
import com.grs.product.smartflatAdmin.response.Response;

public class UploadSocietyPollTask extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = LoginTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	private SocietyPollDetails mSocietyPollDetails;
	Response mSocietyPollDetailsStatus;
	
	public UploadSocietyPollTask(Context ctx, AsyncTaskCompleteListener<Response> listener, SocietyPollDetails societyPollDetails) 
	{
		this.context = ctx;
		this.listener = listener;
		this.mSocietyPollDetails = societyPollDetails;
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
			mSocietyPollDetailsStatus =  smartFlatAPI.getUploadSocietyPoll(mSocietyPollDetails);
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
		
		if(mSocietyPollDetailsStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mSocietyPollDetailsStatus);
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