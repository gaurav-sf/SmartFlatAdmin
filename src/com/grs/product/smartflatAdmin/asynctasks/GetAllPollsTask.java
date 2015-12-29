package com.grs.product.smartflatAdmin.asynctasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.SocietyPollDetails;

public class GetAllPollsTask extends AsyncTask<Void, Void, SmartFlatAdminError>{

	private static final String TAG = SaveSocietyOwnerCredentialTask.class.getName();
	final Context mContext;
	private AsyncTaskCompleteListener<List<SocietyPollDetails>> listener = null;
	private List<SocietyPollDetails> listSocietyPollDetails;
	
	public GetAllPollsTask(Context mContext, 
			AsyncTaskCompleteListener<List<SocietyPollDetails>> listener) 
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
			
			listSocietyPollDetails = smartFlatAdminAPI.getAllPolls();
			if (listSocietyPollDetails==null) {
				return new SmartFlatAdminError("No Polls to Display");				
			}
			
		} catch (SmartFlatAdminError e) {
			Log.e(TAG, e.toString());
			return e;	
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(SmartFlatAdminError error) {
		
		if(listSocietyPollDetails!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(listSocietyPollDetails);
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