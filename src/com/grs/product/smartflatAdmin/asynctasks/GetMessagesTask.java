package com.grs.product.smartflatAdmin.asynctasks;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.apicall.SmartFlatAdminAPI;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;

public class GetMessagesTask   extends AsyncTask<Void, Void, SmartFlatAdminError>{

	private static final String TAG = GetMessagesTask.class.getName();
	final Context mContext;
	private AsyncTaskCompleteListener<List<RequestMessages>> listener = null;
	List<RequestMessages> listMessages;
	
	public GetMessagesTask(Context mContext, 
			AsyncTaskCompleteListener<List<RequestMessages>> listener) 
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
			
			listMessages = smartFlatAdminAPI.getMessages();
			if (listMessages==null) {
				return new SmartFlatAdminError("No Messages");				
			}
			
		} catch (SmartFlatAdminError e) {
			Log.e(TAG, e.toString());
			return e;	
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(SmartFlatAdminError error) {
		
		if(listMessages!=null)
		{
			if(listener!=null)
			{
				listener.onStoped();
				listener.onTaskComplete(listMessages);
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