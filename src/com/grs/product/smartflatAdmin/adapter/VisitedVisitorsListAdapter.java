package com.grs.product.smartflatAdmin.adapter;

import java.util.ArrayList;
import java.util.List;
import com.grs.product.smartflatAdmin.R;
import com.grs.product.smartflatAdmin.models.VisitorDetails;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class VisitedVisitorsListAdapter  extends BaseAdapter {
	private Context context;
	private List<VisitorDetails> listVisitorDetails  = new ArrayList<VisitorDetails>();
	
	public VisitedVisitorsListAdapter(Context context,
			List<VisitorDetails> listFlatOwnerDetails) {
		super();
		this.context = context;
		this.listVisitorDetails = listFlatOwnerDetails;
	}
	

	@Override
	public int getCount() {
		return listVisitorDetails.size();
	}

	@Override
	public Object getItem(int position) {
		return listVisitorDetails.get(position);
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
			rowView = infalInflater.inflate(R.layout.visited_visitor_list_item, null);
		}
		 VisitorDetails temp = listVisitorDetails.get(position);
		 TextView visitorName = (TextView) rowView.findViewById(R.id.textViewVisitorName);
		 visitorName.setText(temp.getmVisitorName()+"\n"+temp.getmVisitorContacNo());

		return rowView;
	}
	
}
