package com.grs.product.smartflatAdmin.asynctasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;

public class GetRequestAndComplaintTask  extends AsyncTask<Void, Void, SmartFlatAdminError>{

	private static final String TAG = GetRequestAndComplaintTask.class.getName();
	final Context mContext;
	private AsyncTaskCompleteListener<List<RequestDetails>> listener = null;
	List<RequestDetails> listRequestDetails;
	
	public GetRequestAndComplaintTask(Context mContext, 
			AsyncTaskCompleteListener<List<RequestDetails>> listener) 
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
			
			listRequestDetails = smartFlatAdminAPI.getRequestAndComplaint();
			if (listRequestDetails==null) {
				return new SmartFlatAdminError("No new users registered");				
			}
			
		} catch (SmartFlatAdminError e) {
			Log.e(TAG, e.toString());
			return e;	
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(SmartFlatAdminError error) {
		
		if(listRequestDetails!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(listRequestDetails);
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