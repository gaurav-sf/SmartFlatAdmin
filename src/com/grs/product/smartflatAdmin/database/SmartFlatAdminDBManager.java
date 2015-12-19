package com.grs.product.smartflatAdmin.database;

import android.database.Cursor;
import com.grs.product.smartflatAdmin.models.ContactDetails;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.NoticeDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.models.SocietyOwnerDetails;
import com.grs.product.smartflatAdmin.models.VisitorDetails;


public class SmartFlatAdminDBManager {
	
	public boolean saveSocietyOwnerDetails(SocietyOwnerDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveSocietyOwnerDetails(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}

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
	
	public Cursor getAllFlatOwnerDetails(String activationCode){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getAllFlatOwnerDetails(activationCode);
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
		
	public Cursor getRaisedRequestDetails(){
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
	}
	
	public Cursor getRaisedRequestDetailsByCategory(){
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
	}
	
	public Cursor getRaisedRequestDetailsByPriorityLtoH(){
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
	}
	
	public Cursor getSingleRequestDetails(String requestNumber){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getSingleRequestDetails(requestNumber);
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public boolean saveMessage(RequestMessages messages){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveMessage(messages);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	} 
	
	public Cursor getMessages(String requestNumber){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getMessages(requestNumber);
		SmartFlatAdminDatabase.getInstance().close();
		return details;	
	}
	
	public void UpdateActivationFlag(String flatOwnerCode){
		SmartFlatAdminDatabase.getInstance().open();
		SmartFlatAdminDatabase.getInstance().UpdateActivationFlag(flatOwnerCode);
		SmartFlatAdminDatabase.getInstance().close();
	}
	
	public Cursor getFlatOwnerCode(String searchValue){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getFlatOwnerCode(searchValue);
		SmartFlatAdminDatabase.getInstance().close();
		return details;	
	}
	
	public boolean saveVisitor(VisitorDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveVisitor(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getVisitors(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getVisitors();
		SmartFlatAdminDatabase.getInstance().close();
		return details;	
	}
	
	public boolean saveContact(ContactDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveContact(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getAllContacts(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getAllContacts();
		SmartFlatAdminDatabase.getInstance().close();
		return details;	
	}
	
	public Cursor getFlatOwnerData(String searchText){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getFlatOwnerData(searchText);
		SmartFlatAdminDatabase.getInstance().close();
		return details;		
	}
	
	public boolean saveNotice(NoticeDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveNotice(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getAllNotice(){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getAllNotice();
		SmartFlatAdminDatabase.getInstance().close();
		return details;		
	}
	
	public Cursor getSingleVisitorDetails(String visitorNumber){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getSingleVisitorDetails(visitorNumber);
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public Cursor getSingleFlatOwnerData(String flatOwnerCode){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getSingleFlatOwnerData(flatOwnerCode);
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public void deleteDataFromAllTables(){
		SmartFlatAdminDatabase.getInstance().open();
		SmartFlatAdminDatabase.getInstance().deleteDataFromAllTables();
		SmartFlatAdminDatabase.getInstance().close();
	}
	
	public boolean saveSocietyNoticeDetails(NoticeDetails details){
		boolean isAdded = false;
		SmartFlatAdminDatabase.getInstance().open();
		isAdded = SmartFlatAdminDatabase.getInstance().saveSocietyNoticeDetails(details);
		SmartFlatAdminDatabase.getInstance().close();
		return isAdded;
	}
	
	public Cursor getUnreadMessageCountForRequest(String requestNumber){
		SmartFlatAdminDatabase.getInstance().open();
		Cursor details = SmartFlatAdminDatabase.getInstance().getUnreadMessageCountForRequest(requestNumber);
		SmartFlatAdminDatabase.getInstance().close();
		return details;
	}
	
	public void setMessagesRead(String requestNumber)
	{
		SmartFlatAdminDatabase.getInstance().open();
		SmartFlatAdminDatabase.getInstance().setMessagesRead(requestNumber);
		SmartFlatAdminDatabase.getInstance().close();
	}
	
}
