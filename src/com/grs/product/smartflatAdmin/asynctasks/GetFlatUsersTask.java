package com.grs.product.smartflatAdmin.asynctasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.response.Response;

public class GetFlatUsersTask extends AsyncTask<Void, Void, SmartFlatAdminError>{

	private static final String TAG = SaveSocietyOwnerCredentialTask.class.getName();
	final Context mContext;
	private AsyncTaskCompleteListener<List<FlatOwnerDetails>> listener = null;
	Response mUserStatus;
	List<FlatOwnerDetails> listFlatOwnerDetails;
	
	public GetFlatUsersTask(Context mContext, 
			AsyncTaskCompleteListener<List<FlatOwnerDetails>> listener) 
	{
		
		this.mContext = mContext;
		this.listener = listener;
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
			
			listFlatOwnerDetails = smartFlatAdminAPI.getFlatUsers();
			
		} catch (SmartFlatAdminError e) {
			Log.e(TAG, e.toString());
			return e;	
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(SmartFlatAdminError error) {
		
		if(mUserStatus!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(listFlatOwnerDetails);
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