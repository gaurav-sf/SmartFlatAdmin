package com.grs.product.smartflatAdmin.apicall;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
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
		JSONArray requestComplaintArray = new JSONArray(json.getString("requestComplaintDetails"));
		if(requestComplaintArray!=null && requestComplaintArray.length()>0)
		{
			if(requestComplaintArray.getString(0)==null||
					requestComplaintArray.getString(0).equalsIgnoreCase(null)||
					requestComplaintArray.getString(0).equalsIgnoreCase("null"))
			{
				return null;
			}
			List<RequestDetails> listRequestComplaint= new ArrayList<RequestDetails>();
			for (int i = 0; i < requestComplaintArray.length(); i++) 
			{
				listRequestComplaint.add(getSingleRequestDetails(requestComplaintArray.getJSONObject(i)));
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
		requestDetails.setmRequestType(json.getString("type"));
		requestDetails.setmRequestNumber(json.getString("requestNumber"));
		requestDetails.setmRequestStatus(json.getString("requestStatus"));
		requestDetails.setmRequestPriority(json.getString("requestPriority"));
		requestDetails.setmRequestDetails(json.getString("requestDetails"));
		requestDetails.setmRequestRaisedBy(json.getString("raisedBy"));
		requestDetails.setmRequestDateTime(json.getString("raisedDate"));
		
		return requestDetails;
		
	}

}
