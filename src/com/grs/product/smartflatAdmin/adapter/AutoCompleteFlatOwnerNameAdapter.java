package com.grs.product.smartflatAdmin.adapter;

import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;

import android.content.Context;
import android.database.Cursor;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class AutoCompleteFlatOwnerNameAdapter extends CursorAdapter
implements android.widget.AdapterView.OnItemClickListener {
	
	SmartFlatAdminDBManager dbManager;
	
	public AutoCompleteFlatOwnerNameAdapter(Context context, Cursor c) {
		super(context, null);
		dbManager = new SmartFlatAdminDBManager();
	}
	
	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		// TODO Auto-generated method stub
        if (getFilterQueryProvider() != null) {
            return getFilterQueryProvider().runQuery(constraint);
        }
        Cursor cursor = dbManager.getFlatOwnerData(
                (constraint != null ? constraint.toString() : null));

        return cursor;
	}
	
	@Override
	public String convertToString(Cursor cursor) {
        final int columnIndex = cursor.getColumnIndexOrThrow(TableFlatOwnerDetails.FLAT_OWNER_NAME);
        final String str = cursor.getString(columnIndex);
        return str;
	}
	
	

	

	@Override
	public void onItemClick(AdapterView<?> listView, View view, int position,
			long id) {
        // Get the cursor, positioned to the corresponding row in the result set
        Cursor cursor = (Cursor) listView.getItemAtPosition(position);

        // Get the state's capital from this row in the database.
        String capital = cursor.getString(cursor.getColumnIndexOrThrow(TableFlatOwnerDetails.FLAT_OWNER_CODE));

        // Update the parent class's TextView
        //Toast.makeText(context, "", Toast.LENGTH_LONG);
    }

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view =
                inflater.inflate(android.R.layout.simple_dropdown_item_1line,
                        parent, false);

       return view;
    }

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
        final String text = convertToString(cursor);
        ((TextView) view).setText(text);
		
	}

}
