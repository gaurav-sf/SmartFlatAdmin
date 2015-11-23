package com.grs.product.smartflatAdmin.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;

public class NewRegisteredUserListAdapter extends BaseAdapter {
	private Context context;
	private List<FlatOwnerDetails> listFlatOwnerDetails  = new ArrayList<FlatOwnerDetails>();
	
	public NewRegisteredUserListAdapter(Context context,
			List<FlatOwnerDetails> listFlatOwnerDetails) {
		super();
		this.context = context;
		this.listFlatOwnerDetails = listFlatOwnerDetails;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFlatOwnerDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listFlatOwnerDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
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
		 TextView mUserName;
		 Button mButtonActivate;
		return null;
	}

}
