package com.grs.product.smartflatAdmin.apicall;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
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
		flatOwnerDetails.setmBuildingName(json.getString("Building_Name"));
		flatOwnerDetails.setmFloorNo(json.getString("Floor_No"));
		flatOwnerDetails.setmFlatno(json.getString("Flat_No"));
		flatOwnerDetails.setmFlatOwnerCreatedDateTime(json.getString("Flat_Owner_Created_DateTime"));
		flatOwnerDetails.setmFlatOwnerCode(json.getString("Flat_Owner_Code"));
		flatOwnerDetails.setActive(Boolean.parseBoolean(json.getString("Is_Active")));

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
		singleMessage.setmIsSocietyMessage(Boolean.parseBoolean(json.getString("Is_Society_Message")));
		singleMessage.setmMessageDateTime(json.getString("Message_DateTime"));
		singleMessage.setmSocietyCode(json.getString("Society_Code"));
		
		return singleMessage;		
	}

}
