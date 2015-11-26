package com.grs.product.smartflatAdmin.database;

import android.database.Cursor;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.NoticeDetails;
import com.grs.product.smartflatAdmin.models.QueryDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.SocietyDetails;


public class SmartFlatAdminDBManager {

	public boolean saveSocietyDetails(SocietyDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveSocietyDetails(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getSocietyDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getSocietyDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;	
	}
	
	public boolean saveFlatOwnerDeatils(FlatOwnerDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveFlatOwnerDeatils(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getAllFlatOwnerDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getAllFlatOwnerDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	

	
/*	public boolean saveVehicleDetails(VehicleDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveVehicleDetails(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getVehicleDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getVehicleDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}*/
	
	public boolean saveRequestDetails(RequestDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveRequestDetails(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getAllRequestDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getAllRequestDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	
/*	public boolean saveSocietyNoticeDetails(NoticeDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveSocietyNoticeDetails(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getAllSocietyNoticeDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getAllSocietyNoticeDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}*/
	
/*	public Cursor getRaisedComplaintDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getRaisedComplaintDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public Cursor getClosedComplaintDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getClosedComplaintDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}*/
		
/*	public Cursor getRaisedRequestDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getRaisedRequestDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public Cursor getRaisedRequestDetailsByType(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getRaisedRequestDetailsByType();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}*/
	
/*	public Cursor getRaisedRequestDetailsByCategory(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getRaisedRequestDetailsByCategory();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public Cursor getRaisedRequestDetailsByPriorityHtoL(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getRaisedRequestDetailsByPriorityHtoL();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}*/
	
/*	public Cursor getRaisedRequestDetailsByPriorityLtoH(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getRaisedRequestDetailsByPriorityLtoH();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public Cursor getClosedRequestDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getClosedRequestDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}*/
/*	
	public Cursor getRaisedQueryDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getRaisedQueryDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public Cursor getClosedQueryDetails(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getClosedQueryDetails();
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}*/
}
