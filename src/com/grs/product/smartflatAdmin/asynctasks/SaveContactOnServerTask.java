package com.grs.product.smartflatAdmin.asynctasks;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.ContactDetails;
import com.grs.product.smartflatAdmin.models.VisitorDetails;
import com.grs.product.smartflatAdmin.response.Response;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SaveContactOnServerTask  extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = LoginTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	Response mContactStatus;
	private ContactDetails mContactDetails;
	
	public SaveContactOnServerTask(Context ctx, AsyncTaskCompleteListener<Response> listener, ContactDetails details) 
	{
		this.context = ctx;
		this.listener = listener;
		this.mContactDetails = details;
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
			mContactStatus =  smartFlatAPI.saveContact(mContactDetails);
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
		
		if(mContactStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mContactStatus);
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