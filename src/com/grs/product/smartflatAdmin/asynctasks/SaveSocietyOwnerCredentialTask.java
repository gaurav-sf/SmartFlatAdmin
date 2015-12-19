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

public class SaveSocietyOwnerCredentialTask  extends AsyncTask<Void, Void, SmartFlatAdminError>{

	private static final String TAG = SaveSocietyOwnerCredentialTask.class.getName();
	final Context mContext;
	private AsyncTaskCompleteListener<Response> listener = null;
	Response mResponseStatus;
	private SocietyOwnerDetails mSocietyDetails;
	
	public SaveSocietyOwnerCredentialTask(Context mContext,
			AsyncTaskCompleteListener<Response> listener,
			SocietyOwnerDetails mSocietyDetails) {
		
		this.mContext = mContext;
		this.listener = listener;
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
			
			mResponseStatus = smartFlatAdminAPI.getSaveSocietyOwnerCredential(mSocietyDetails);
			
		} catch (SmartFlatAdminError e) {
			Log.e(TAG, e.toString());
			return e;	
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(SmartFlatAdminError error) {
		
		if(mResponseStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(mResponseStatus);
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
