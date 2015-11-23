package com.grs.product.smartflatAdmin.apicall;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Paint.Join;

import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
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
		JSONArray flatOwnerArray = new JSONArray(json.getString("flatusers"));
		if(flatOwnerArray!=null && flatOwnerArray.length()>0){
			List<FlatOwnerDetails> listFlatUsers= new ArrayList<FlatOwnerDetails>();
			for (int i = 0; i < flatOwnerArray.length(); i++) {
				listFlatUsers.add(getSingleFlatOwner(flatOwnerArray.getJSONObject(i)));

			}
			return listFlatUsers;
		}else{
			return null;
		}		
	}

	private FlatOwnerDetails getSingleFlatOwner(JSONObject json)
			 throws JSONException{
		FlatOwnerDetails flatOwnerDetails = new FlatOwnerDetails();
		flatOwnerDetails.setmFlatOwnerName(json.getString("flatOwnerName"));
		flatOwnerDetails.setmBuildingName(json.getString("buildingName"));
		flatOwnerDetails.setmFloorNo(json.getString("floorNo"));
		flatOwnerDetails.setmFlatno(json.getString("flatNo"));
		flatOwnerDetails.setmFlatOwnerContactNo(json.getString("contactNo"));
		flatOwnerDetails.setmFlatOwnerEmailId(json.getString("emailId"));
		flatOwnerDetails.setActive(Boolean.parseBoolean(json.getString("isActive")));
		
		return flatOwnerDetails;

	}

}
