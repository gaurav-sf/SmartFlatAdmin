package com.grs.product.smartflatAdmin.asynctasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.models.SocietyOwnerDetails;
import com.grs.product.smartflatAdmin.response.Response;

public class RegistrationTask extends AsyncTask<Void, Void, SmartFlatAdminError>{

	
	private static final String TAG = RegistrationTask.class.getName();
	final Context mContext;
	private AsyncTaskCompleteListener<Response> listener = null;
	Response mRegistrationStatus;
	private SocietyOwnerDetails mSocietyOwnerDetails;
	private SocietyDetails mSocietyDetails;
	
	
	public RegistrationTask(Context mContext,
			AsyncTaskCompleteListener<Response> listener,
			SocietyOwnerDetails mSocietyOwnerDetails,
			SocietyDetails mSocietyDetails) {
		
		this.mContext = mContext;
		this.listener = listener;
		this.mSocietyOwnerDetails = mSocietyOwnerDetails;
		this.mSocietyDetails = mSocietyDetails;
	}

	@Override
	protected void onPreExecute() {
		if(listener!=null)
			listener.onStarted();
	}
	
	@Override
	protected SmartFlatAdminError doInBackground(Void... params) {
		SmartFlatAdminAPI smartFlatAdminAPI = new SmartFlatAdminAPI(mContext);
		
		try {
			
			mRegistrationStatus = smartFlatAdminAPI.getRegistration(mSocietyDetails, mSocietyOwnerDetails);
			
		} catch (SmartFlatAdminError e) {
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
					listener.onStopedWithError(error);				
				}			
				listener = null;
			}		
		}
	}

}
