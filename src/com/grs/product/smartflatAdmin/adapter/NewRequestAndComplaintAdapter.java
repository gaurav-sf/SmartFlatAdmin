package com.grs.product.smartflatAdmin.adapter;

import java.util.List;

import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NewRequestAndComplaintAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<RequestDetails> mListRequestDetails;
	
	public NewRequestAndComplaintAdapter(Context context,
			List<RequestDetails> listRequestDetails) {
		super();
		this.mContext = context;
		this.mListRequestDetails = listRequestDetails;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListRequestDetails.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mListRequestDetails.get(position);
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
			LayoutInflater infalInflater = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = infalInflater.inflate(R.layout.new_request_complaint_list_item, null);
		}
		final RequestDetails temp = mListRequestDetails.get(position);
		 TextView requestNo = (TextView) rowView.findViewById(R.id.textViewUserName);
		 requestNo.setText(temp.getmRequestNumber());
		return rowView;
	}

}
