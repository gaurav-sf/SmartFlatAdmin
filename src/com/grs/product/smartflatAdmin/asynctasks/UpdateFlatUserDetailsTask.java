package com.grs.product.smartflatAdmin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class UpdateFlatUserDetailsTask   extends AsyncTask<Void, Void, SmartFlatAdminError> {

	private static final String TAG = LoginTask.class.getName();
	final Context context;
	private AsyncTaskCompleteListener<Response> listener = null;
	private FlatOwnerDetails mFlatOwnerDetails;
	Response mRegistrationStatus;
	
	public UpdateFlatUserDetailsTask(Context ctx, AsyncTaskCompleteListener<Response> listener, FlatOwnerDetails mFlatOwnerDetails) 
	{
		this.context = ctx;
		this.listener = listener;
		this.mFlatOwnerDetails=mFlatOwnerDetails;
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
			mRegistrationStatus =  smartFlatAPI.updateFlatOwnerDetails(mFlatOwnerDetails);
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
		
		if(mRegistrationStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mRegistrationStatus);
				listener = null;
			}
		}
		else 
		{		
			if(listener!=null)
			{
				if(error!=null)
				{
					Utilities.ShowAlertBox(context, error.errorType, error.errorMessage);
					listener.onStopedWithError(error);
				}
				
				listener = null;
			}
			
		}

	}

}
