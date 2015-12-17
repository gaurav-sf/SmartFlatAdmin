package com.grs.product.smartflatAdmin.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.ActivateFlatOwnerTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class NewRegisteredUserListAdapter extends BaseAdapter {
	private Context context;
	private List<FlatOwnerDetails> listFlatOwnerDetails  = new ArrayList<FlatOwnerDetails>();
	public String flatOwnerCode;
	private int mPosition;
	
	public NewRegisteredUserListAdapter(Context context,
			List<FlatOwnerDetails> listFlatOwnerDetails) {
		super();
		this.context = context;
		this.listFlatOwnerDetails = listFlatOwnerDetails;
	}
	

	@Override
	public int getCount() {
		return listFlatOwnerDetails.size();
	}

	@Override
	public Object getItem(int position) {
		return listFlatOwnerDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = infalInflater.inflate(R.layout.new_register_user_list_item, null);
		}
		final FlatOwnerDetails temp = listFlatOwnerDetails.get(position);
		 TextView mUserName = (TextView) rowView.findViewById(R.id.textViewUserName);
		 mUserName.setText(temp.getmFlatOwnerName());
		 TextView flatNo = (TextView) rowView.findViewById(R.id.textViewFlatNo);
		 flatNo.setText(temp.getmBuildingName()+ "--"+temp.getmFloorNo()+"--"+temp.getmFlatno());
		 
		 Button mButtonActivate= (Button) rowView.findViewById(R.id.buttonActivate);
		 mPosition = position;
		 mButtonActivate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flatOwnerCode = temp.getmFlatOwnerCode();
				//activateFlatOwnerCall(flatOwnerCode);
				
			}
		});
		return rowView;
	}
	
	private void activateFlatOwnerCall(String flatOwnerCode){

		if (NetworkDetector.init(context).isNetworkAvailable()) 
		{
			new ActivateFlatOwnerTask(context, new ActivateFlatOwnerTaskListener(), flatOwnerCode)
			.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} 
		else 
		{
			Utilities.ShowAlertBox(context,"Error", "Please check your Internet");
		}				
	}
	
	public class ActivateFlatOwnerTaskListener implements AsyncTaskCompleteListener<Response>{

		@Override
		public void onStarted() {
			CustomProgressDialog.showProgressDialog(context, "", false);
		}

		@Override
		public void onTaskComplete(Response result) {
			if (result!=null) {
				if (result.getStatus().equals("success"))
				{
					Utilities.ShowAlertBox(context, "Message", result.getMessage());
					updateInDB(flatOwnerCode);
					listFlatOwnerDetails.remove(mPosition);
					notifyDataSetChanged();
				}else if (result.getStatus().equals("failure")){
					Utilities.ShowAlertBox(context, "Message", result.getMessage());
				}else{
					Utilities.ShowAlertBox(context, "Message", "Something went wrong please try later");
				}
			}
		}

		@Override
		public void onStoped() {
			CustomProgressDialog.removeDialog();
		}

		@Override
		public void onStopedWithError(SmartFlatAdminError e) {
			CustomProgressDialog.removeDialog();
			Utilities.ShowAlertBox(context, "Error", "Something went wrong please try later");
			
		}
		
	}
	
	private void updateInDB(String flatOwnerCode){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		dbManager.UpdateActivationFlag(flatOwnerCode);
	}

}
