package com.grs.product.smartflatAdmin.apicall;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBManager;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.NoticeDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.models.SocietyOwnerDetails;
import com.grs.product.smartflatAdmin.models.VisitorDetails;
import com.grs.product.smartflatAdmin.response.Response;

public class JSONSingleObjectDecode {

	public JSONSingleObjectDecode() throws JSONException {
		super();
	}

	public Response getStatus(JSONObject json) throws JSONException {
		String status = "";
		if (!json.isNull("status"))
			status = json.getString("status");
		String message = "";
		if (!json.isNull("message"))
			message = json.getString("message");
		String societycode = "";
		if (!json.isNull("societycode"))
			societycode = json.getString("societycode");		
		return new Response(status, message, societycode);
	}

	public List<FlatOwnerDetails> getFlatUsers(JSONObject json) throws JSONException 
	{
		JSONArray flatOwnerArray = new JSONArray(json.getString("userDetails"));
		if(flatOwnerArray!=null && flatOwnerArray.length()>0)
		{
			if(flatOwnerArray.getString(0)==null||
					flatOwnerArray.getString(0).equalsIgnoreCase(null)||
					flatOwnerArray.getString(0).equalsIgnoreCase("null"))
			{
				return null;
			}
			List<FlatOwnerDetails> listFlatUsers= new ArrayList<FlatOwnerDetails>();
			for (int i = 0; i < flatOwnerArray.length(); i++) 
			{
				listFlatUsers.add(getSingleFlatOwner(flatOwnerArray.getJSONObject(i)));
			}
			return listFlatUsers;
		}
		else{
			return null;
		}		
	}

	private FlatOwnerDetails getSingleFlatOwner(JSONObject json)
			throws JSONException{
		FlatOwnerDetails flatOwnerDetails = new FlatOwnerDetails();
		flatOwnerDetails.setmFlatOwnerName(json.getString("Flat_Owner_Name"));
		flatOwnerDetails.setmFlatOwnerContactNo(json.getString("Flat_Owner_Contact_No"));
		flatOwnerDetails.setmFlatOwnerEmailId(json.getString("Flat_Owner_Email_Id"));
		flatOwnerDetails.setmFlatOwnerDOB(json.getString("Flat_Owner_DOB"));
		flatOwnerDetails.setmBuildingName(json.getString("Building_Name"));
		flatOwnerDetails.setmFloorNo(json.getString("Floor_No"));
		flatOwnerDetails.setmFlatno(json.getString("Flat_No"));
		flatOwnerDetails.setmFlatOwnerCreatedDateTime(json.getString("Flat_Owner_Created_DateTime"));
		flatOwnerDetails.setmFlatOwnerCode(json.getString("Flat_Owner_Code"));
		flatOwnerDetails.setmGender(json.getString("Gender"));
		flatOwnerDetails.setActive(Boolean.parseBoolean(json.getString("Is_Active")));
		if(json.getString("Is_Active").equals("0")){
			flatOwnerDetails.setActive(false);
		}else{
			flatOwnerDetails.setActive(true);			
		}


		return flatOwnerDetails;

	}

	public List<RequestDetails> getRequestAndComplaint(JSONObject json) throws JSONException 
	{
		JSONArray requestComplaintArray = new JSONArray(json.getString("complaints"));
		JSONArray requestArray ;
		if(requestComplaintArray!=null && requestComplaintArray.length()>0)
		{
			if(requestComplaintArray.getString(0)==null||
					requestComplaintArray.getString(0).equalsIgnoreCase(null)||
					requestComplaintArray.getString(0).equalsIgnoreCase("null"))
			{
				requestArray = new JSONArray(json.getString("requests"));
				if(requestArray!=null && requestArray.length()>0)
				{
					if(requestArray.getString(0)==null||
							requestArray.getString(0).equalsIgnoreCase(null)||
							requestArray.getString(0).equalsIgnoreCase("null"))
					{
						return null;
					}
					List<RequestDetails> listRequestComplaint= new ArrayList<RequestDetails>();
					for (int i = 0; i < requestArray.length(); i++) 
					{
						listRequestComplaint.add(getSingleRequestDetails(requestArray.getJSONObject(i)));
					}
					return listRequestComplaint;

				}else{
					return null;
				}		
			}			
			List<RequestDetails> listRequestComplaint= new ArrayList<RequestDetails>();
			for (int i = 0; i < requestComplaintArray.length(); i++) 
			{
				listRequestComplaint.add(getSingleComplaintDetails(requestComplaintArray.getJSONObject(i)));
			}
			//Get Request
			requestArray = new JSONArray(json.getString("requests"));				
			if(requestArray!=null && requestArray.length()>0)
			{
				if(requestArray.getString(0)==null||
						requestArray.getString(0).equalsIgnoreCase(null)||
						requestArray.getString(0).equalsIgnoreCase("null"))
				{
					//return null;
				}else{
					for (int i = 0; i < requestArray.length(); i++) 
					{
						listRequestComplaint.add(getSingleRequestDetails(requestArray.getJSONObject(i)));
					}
				}
			}	
			return listRequestComplaint;
		}
		else{
			return null;
		}		
	}

	private RequestDetails getSingleRequestDetails(JSONObject json)
			throws JSONException{
		
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setmRequestType("Request");
		requestDetails.setmRequestNumber(json.getString("Request_Number"));
		requestDetails.setmRequestStatus(json.getString("Request_Status"));
		requestDetails.setmRequestPriority(json.getString("Request_Priority"));
		requestDetails.setmRequestDetails(json.getString("Request_Details"));
		requestDetails.setmRequestRaisedBy(json.getString("Request_Raised_By"));
		requestDetails.setmRequestCategory(json.getString("Request_Category"));
		requestDetails.setmRequestDateTime(json.getString("Request_DateTime"));

		return requestDetails;

	}

	private RequestDetails getSingleComplaintDetails(JSONObject json)
			throws JSONException{

		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setmRequestType("Complaint");
		requestDetails.setmRequestNumber(json.getString("Complaint_Number"));
		requestDetails.setmRequestStatus(json.getString("Complaint_Status"));
		requestDetails.setmRequestPriority(json.getString("Complaint_Priority"));
		requestDetails.setmRequestDetails(json.getString("Complaint_Details"));
		requestDetails.setmRequestRaisedBy(json.getString("Complaint_Raised_By"));
		requestDetails.setmRequestCategory(json.getString("Complaint_Category"));
		requestDetails.setmRequestDateTime(json.getString("Complaint_Raised_DateTime"));

		return requestDetails;

	}
	
	public List<RequestMessages> getMessages(JSONObject json)
			throws JSONException{
		JSONArray messageArray = new JSONArray(json.getString("messages"));
		if(messageArray!=null && messageArray.length()>0)
		{
			if(messageArray.getString(0)==null||
					messageArray.getString(0).equalsIgnoreCase(null)||
					messageArray.getString(0).equalsIgnoreCase("null"))
			{
				return null;
			}
			List<RequestMessages> listMessages= new ArrayList<RequestMessages>();
			for (int i = 0; i < messageArray.length(); i++) 
			{
				listMessages.add(getSingleMessage(messageArray.getJSONObject(i)));
			}
			return listMessages;			
		}
		return null;
	}
	
	private RequestMessages getSingleMessage(JSONObject json) 
			throws JSONException{
		
		RequestMessages singleMessage = new RequestMessages();
		
		singleMessage.setmMessageNumber(json.getString("Message_Number"));
		singleMessage.setmMessageContent(json.getString("Message_Content"));
		singleMessage.setmRequestNumber(json.getString("Request_Number"));
		singleMessage.setmFlatOwnerCode(json.getString("Flat_Owner_Code"));
		if(json.getString("Is_Society_Message").equals("0")){
			singleMessage.setmIsSocietyMessage(false);
			singleMessage.setmIsRead(false);
		}else{
			singleMessage.setmIsSocietyMessage(true);		
			singleMessage.setmIsRead(true);
		}
		singleMessage.setmIsRead(false);
		singleMessage.setmMessageDateTime(json.getString("Message_DateTime"));
		singleMessage.setmSocietyCode(json.getString("Society_Code"));
		
		return singleMessage;		
	}
	
	public Response getPreviousData(JSONObject json) throws JSONException{
		Response response = new Response();
		SocietyOwnerDetails societyOwnerDetails = new SocietyOwnerDetails();
		SocietyDetails societyDetails = new SocietyDetails();
		List<FlatOwnerDetails> listFlatOwnerDetails = new ArrayList<FlatOwnerDetails>();
		List<RequestDetails> listRequestDetails = new ArrayList<RequestDetails>();
		List<RequestMessages> listRequestMessages = new ArrayList<RequestMessages>();
		List<VisitorDetails> listVisitorDetails = new ArrayList<VisitorDetails>();
		List<NoticeDetails> listNoticeDetails = new ArrayList<NoticeDetails>();
		String status = "";
		if (!json.isNull("status"))
			status = json.getString("status");
		if(status.equalsIgnoreCase("success"))
		{	
			societyOwnerDetails = getSocietyOwnerDetails(json);
			if (societyOwnerDetails!=null) {
				saveSocietyOwnerDataInDB(societyOwnerDetails);
			}
			societyDetails = getSocietyDetails(json);
			if(societyDetails!=null){
				saveSocietyDataInDB(societyDetails);
			}
			listFlatOwnerDetails = getFlatUsers(json);
			if(listFlatOwnerDetails!=null){
				saveFlatOwnerDataInDB(listFlatOwnerDetails);
			}
			listRequestDetails = getRequestAndComplaint(json);
			if(listRequestDetails!=null){
				saveRequestAndComplaintInDB(listRequestDetails);	
			}
			listRequestMessages = getMessages(json);
			if(listRequestMessages!=null){
				saveMessagesInDB(listRequestMessages);
			}
			listVisitorDetails = getVisitors(json);
			if(listVisitorDetails!=null){
				saveVisitorInLocalDB(listVisitorDetails);
			}
			listNoticeDetails = getNotices(json);
			if(listNoticeDetails!=null){
				saveNoticeInDB(listNoticeDetails);
			}			
		}
		response.setStatus(status);
		return response;
		
	}
	
	private SocietyOwnerDetails getSocietyOwnerDetails(JSONObject json) throws JSONException{

		JSONArray societyDetailsArray = new JSONArray(json.getString("societyDetails"));
		if(societyDetailsArray!=null && societyDetailsArray.length()>0){
			if(societyDetailsArray.getString(0)==null||
					societyDetailsArray.getString(0).equalsIgnoreCase(null)||
					societyDetailsArray.getString(0).equalsIgnoreCase("null")){
				return null;
			}
			JSONObject societyJsonObject = societyDetailsArray.getJSONObject(0);

			SocietyOwnerDetails societyOwnerDetails = new SocietyOwnerDetails();
			societyOwnerDetails.setmSocietyCode(!societyJsonObject.isNull("Society_Code")?societyJsonObject.getString("Society_Code"):"");
			societyOwnerDetails.setmSocietyOwnerName(!societyJsonObject.isNull("Society_Owner_Name")?societyJsonObject.getString("Society_Owner_Name"):"");
			societyOwnerDetails.setmSocietyOwnerDOB(!societyJsonObject.isNull("Society_Owner_DOB")?societyJsonObject.getString("Society_Owner_DOB"):"");
			societyOwnerDetails.setmGender(!societyJsonObject.isNull("Society_Owner_Gender")?societyJsonObject.getString("Society_Owner_Gender"):"");
			societyOwnerDetails.setmSocietyOwnerAddressLine1(!societyJsonObject.isNull("Society_Owner_Address_Line1")?societyJsonObject.getString("Society_Owner_Address_Line1"):"");
			societyOwnerDetails.setmSocietyOwnerAddressLine2(!societyJsonObject.isNull("Society_Owner_Address_Line2")?societyJsonObject.getString("Society_Owner_Address_Line2"):"");
			societyOwnerDetails.setmSocietyOwnerCity(!societyJsonObject.isNull("Society_Owner_City")?societyJsonObject.getString("Society_Owner_City"):"");
			societyOwnerDetails.setmSocietyOwnerState(!societyJsonObject.isNull("Society_Owner_State")?societyJsonObject.getString("Society_Owner_State"):"");
			societyOwnerDetails.setmSocietyOwnerPIN(!societyJsonObject.isNull("Society_Owner_PIN")?societyJsonObject.getString("Society_Owner_PIN"):"");
			societyOwnerDetails.setmSocietyOwnerContactNo(!societyJsonObject.isNull("Society_Owner_Contact_number")?societyJsonObject.getString("Society_Owner_Contact_number"):"");
			societyOwnerDetails.setmSocietyOwnerEmailId(!societyJsonObject.isNull("Society_Owner_Email_id")?societyJsonObject.getString("Society_Owner_Email_id"):"");
			societyOwnerDetails.setmAvailableCredits(!societyJsonObject.isNull("Society_owner_User_Credits")?societyJsonObject.getInt("Society_owner_User_Credits"):0);
			societyOwnerDetails.setmNoOfRegisteredUsers(!societyJsonObject.isNull("No_of_user_Registered")?societyJsonObject.getInt("No_of_user_Registered"):0);
			societyOwnerDetails.setmNoOfActivatedUsers(!societyJsonObject.isNull("No_of_user_Activated")?societyJsonObject.getInt("No_of_user_Activated"):0);

			return societyOwnerDetails;

		}
		else{
			return null;
		}
	}
	
	private void saveSocietyOwnerDataInDB(SocietyOwnerDetails societyOwnerDetails){
		SmartFlatAdminDBManager objDbManager = new SmartFlatAdminDBManager();
		boolean isAdded = objDbManager.saveSocietyOwnerDetails(societyOwnerDetails);
		if(isAdded){
			Log.e("Society Owner Details", "Inserted Successfully");
		}
	}
	
	private SocietyDetails getSocietyDetails(JSONObject json) throws JSONException{
		JSONArray societyDetailsArray = new JSONArray(json.getString("societyDetails"));
		if(societyDetailsArray!=null && societyDetailsArray.length()>0){
			if(societyDetailsArray.getString(0)==null||
					societyDetailsArray.getString(0).equalsIgnoreCase(null)||
					societyDetailsArray.getString(0).equalsIgnoreCase("null")){
				return null;
			}
			JSONObject societyJsonObject = societyDetailsArray.getJSONObject(0);

			SocietyDetails societyDetails = new SocietyDetails();
			societyDetails.setmSocietyCode(!societyJsonObject.isNull("Society_Code")?societyJsonObject.getString("Society_Code"):"");
			societyDetails.setmSocietyName(!societyJsonObject.isNull("Society_Name")?societyJsonObject.getString("Society_Name"):"");
			societyDetails.setmBuildingName(!societyJsonObject.isNull("Building_Name")?societyJsonObject.getString("Building_Name"):"");
			societyDetails.setmTotalFloorNumber(!societyJsonObject.isNull("Total_Floor_Number")?societyJsonObject.getInt("Total_Floor_Number"):0);
			societyDetails.setmSocietyAddressLine1(!societyJsonObject.isNull("Society_Address_Line1")?societyJsonObject.getString("Society_Address_Line1"):"");
			societyDetails.setmSocietyAddressLine2(!societyJsonObject.isNull("Society_Address_Line2")?societyJsonObject.getString("Society_Address_Line2"):"");
			societyDetails.setmSocietyAddressCity(!societyJsonObject.isNull("Society_Address_City")?societyJsonObject.getString("Society_Address_City"):"");
			societyDetails.setmSocietyAddressState(!societyJsonObject.isNull("Society_Address_State")?societyJsonObject.getString("Society_Address_State"):"");
			societyDetails.setmSocietyAddressPIN(!societyJsonObject.isNull("Society_Address_PIN")?societyJsonObject.getString("Society_Address_PIN"):"");

			return societyDetails;

		}
		else{
			return null;
		}

	}
	
	private void saveSocietyDataInDB(SocietyDetails societDetails){
		SmartFlatAdminDBManager objDbManager = new SmartFlatAdminDBManager();
		boolean isAdded = objDbManager.saveSocietyDetails(societDetails);
		if(isAdded){
			Log.e("Society Owner Details", "Inserted Successfully");
		}
	}

	private void saveFlatOwnerDataInDB(List<FlatOwnerDetails> listDetails){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listDetails.size(); i++)
		{
			boolean result = objManager.saveFlatOwnerDeatils(listDetails.get(i));
			if(result)
			{
				Log.e("Prev Data", "Flat Owner Details Insertion Successful");
			}
		}		
	}
	private void saveRequestAndComplaintInDB(List<RequestDetails> listRequestDetails){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listRequestDetails.size(); i++)
		{
			boolean result = objManager.saveRequestDetails(listRequestDetails.get(i));
			if(result)
			{
				Log.e("Prev Data", "Request Insertion Successful");
			}
		}		
	}
	
	private void saveMessagesInDB(List<RequestMessages> listMessages){		
		SmartFlatAdminDBManager objManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listMessages.size(); i++)
		{
			boolean result = objManager.saveMessage(listMessages.get(i));
			if(result)
			{
				Log.e("Prev Data", "Message Insertion Successful");
			}
		}		
	}
	
	public List<VisitorDetails> getVisitors(JSONObject json)
			throws JSONException{
		JSONArray visitorsArray = new JSONArray(json.getString("visitors"));
		if(visitorsArray!=null && visitorsArray.length()>0)
		{
			if(visitorsArray.getString(0)==null||
					visitorsArray.getString(0).equalsIgnoreCase(null)||
					visitorsArray.getString(0).equalsIgnoreCase("null"))
			{
				return null;
			}
			List<VisitorDetails> listVisitors= new ArrayList<VisitorDetails>();
			for (int i = 0; i < visitorsArray.length(); i++) 
			{
				listVisitors.add(getSingleVisitor(visitorsArray.getJSONObject(i)));
			}
			return listVisitors;			
		}
		return null;
	}
	
	private VisitorDetails getSingleVisitor(JSONObject json) 
			throws JSONException{

		VisitorDetails singleVisitor = new VisitorDetails();	
		singleVisitor.setmVisitorCode(json.getString("Visitor_Code"));
		singleVisitor.setmVisitorName(json.getString("Visitor_Name"));
		singleVisitor.setmVisitorInTime(json.getString("Visitor_In_Time"));		
		singleVisitor.setmVisitorContacNo(json.getString("Visitor_Contac_No"));
		singleVisitor.setmVisitorVehicleNo(json.getString("Visitor_Vehicle_No"));
		singleVisitor.setmNoofVisitors(json.getString("No_of_Visitors"));
		singleVisitor.setmVisitPurpose(json.getString("Visit_Purpose"));
		singleVisitor.setmFlatOwnerCode(json.getString("Flat_Owner_Code"));
		return singleVisitor;		
	}
	
	private void saveVisitorInLocalDB(List<VisitorDetails> listVisitor){
		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listVisitor.size(); i++) {
			boolean isAdded = dbManager.saveVisitor(listVisitor.get(i));
			if (isAdded) {
				Log.e("Visitor Details", "Inserted Successfully");
			}
		}
	}
	
	public List<NoticeDetails> getNotices(JSONObject json)
			throws JSONException{
		JSONArray noticesArray = new JSONArray(json.getString("notices"));
		if(noticesArray!=null && noticesArray.length()>0)
		{
			if(noticesArray.getString(0)==null||
					noticesArray.getString(0).equalsIgnoreCase(null)||
					noticesArray.getString(0).equalsIgnoreCase("null"))
			{
				return null;
			}
			List<NoticeDetails> listNotices= new ArrayList<NoticeDetails>();
			for (int i = 0; i < noticesArray.length(); i++) 
			{
				listNotices.add(getSingleNotice(noticesArray.getJSONObject(i)));
			}
			return listNotices;			
		}
		return null;
	}
	
	private NoticeDetails getSingleNotice(JSONObject json) 
			throws JSONException{
		
		NoticeDetails singleNotice = new NoticeDetails();
		singleNotice.setmNoticeNumber(json.getString("Notice_Number"));
		singleNotice.setmNoticeTo(json.getString("Notice_To"));
		singleNotice.setmNoticeSubject(json.getString("Notice_Subject"));
		singleNotice.setmNoticeMessage(json.getString("Notice_Message"));
		singleNotice.setmNoticeDateTime(json.getString("Notice_DateTime"));
		return singleNotice;
	}
	
	private void saveNoticeInDB(List<NoticeDetails> listNotices){

		SmartFlatAdminDBManager dbManager = new SmartFlatAdminDBManager();
		for (int i = 0; i < listNotices.size(); i++) {
			boolean isAdded = dbManager.saveSocietyNoticeDetails(listNotices.get(i));
			if (isAdded) {
				Log.e("Notice Details", "Inserted Successfully");
			}
		}
	
	}
	
}
