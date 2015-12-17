package com.grs.product.smartflatAdmin.adapter;

import java.util.ArrayList;
import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.adapter.NewRegisteredUserListAdapter.ActivateFlatOwnerTaskListener;
import com.grs.product.smartflatAdmin.apicall.AsyncTaskCompleteListener;
import com.grs.product.smartflatAdmin.asynctasks.ActivateFlatOwnerTask;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.CustomProgressDialog;
import com.grs.product.smartflatAdmin.utils.NetworkDetector;
import com.grs.product.smartflatAdmin.utils.Utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ActivatedUsersListAdapter extends BaseAdapter {
	private Context context;
	private List<FlatOwnerDetails> listFlatOwnerDetails  = new ArrayList<FlatOwnerDetails>();
	public String flatOwnerCode;
	
	public ActivatedUsersListAdapter(Context context,
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
			rowView = infalInflater.inflate(R.layout.activated_user_list_item, null);
		}
		final FlatOwnerDetails temp = listFlatOwnerDetails.get(position);
		 TextView mUserName = (TextView) rowView.findViewById(R.id.textViewUserName);
		 mUserName.setText(temp.getmFlatOwnerName());
		 TextView flatNo = (TextView) rowView.findViewById(R.id.textViewFlatNo);
		 flatNo.setText(temp.getmBuildingName()+ "--"+temp.getmFloorNo()+"--"+temp.getmFlatno());
		 TextView contactNo = (TextView) rowView.findViewById(R.id.textViewContactNo);
		 contactNo.setText(temp.getmFlatOwnerContactNo());
		 TextView datetime = (TextView) rowView.findViewById(R.id.textViewDatetime);
		 datetime.setText(temp.getmFlatOwnerCreatedDateTime());

		return rowView;
	}
	
}
