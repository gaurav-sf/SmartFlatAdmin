package com.grs.product.smartflatAdmin.apicall;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;

import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.models.SocietyOwnerDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.Param;

public class SmartFlatAdminAPI {

	final Context mContext;

	public  SmartFlatAdminAPI(Context context) {
		this.mContext = context;
	}

	public Response getRegistration(SocietyDetails societyDetails, SocietyOwnerDetails societyOwnerDetails)
			throws SmartFlatAdminError{
		return getRegistrationCall(societyDetails, societyOwnerDetails);
	}

	public Response getSaveSocietyOwnerCredential(SocietyDetails societyDetails)
			throws SmartFlatAdminError{		
		return getSaveSocietyOwnerCredentialCall(societyDetails);		
	}

	public Response getLogin(String username, String password)
			throws SmartFlatAdminError{
		return getLoginCall(username, password);
	}

	public List<FlatOwnerDetails> getFlatUsers()
			throws SmartFlatAdminError{

		return getFlatUsersCall();

	}

	private Response getRegistrationCall(SocietyDetails societyDetails, SocietyOwnerDetails societyOwnerDetails)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("ownerName", societyOwnerDetails.getmSocietyOwnerName()));
			object.add(new BasicNameValuePair("ownerName", societyOwnerDetails.getmSocietyOwnerName()));
			object.add(new BasicNameValuePair("ownerGender",societyOwnerDetails.getmGender() ));
			object.add(new BasicNameValuePair("ownerDOB",societyOwnerDetails.getmSocietyOwnerDOB()));
			object.add(new BasicNameValuePair("ownerAge",societyOwnerDetails.getmSocietyOwnerAge() ));
			object.add(new BasicNameValuePair("ownerContactNo", societyOwnerDetails.getmSocietyOwnerContactNo()));
			object.add(new BasicNameValuePair("ownerEmailId", societyOwnerDetails.getmSocietyOwnerEmailId()));
			object.add(new BasicNameValuePair("ownerAddressLine1",societyOwnerDetails.getmSocietyOwnerAddressLine1()) );
			object.add(new BasicNameValuePair("ownerAddressLine2",societyOwnerDetails.getmSocietyOwnerAddressLine2()) );
			object.add(new BasicNameValuePair("ownerCity", societyOwnerDetails.getmSocietyOwnerCity()));
			object.add(new BasicNameValuePair("ownerState", societyOwnerDetails.getmSocietyOwnerState()));
			object.add(new BasicNameValuePair("ownerPIN", societyOwnerDetails.getmSocietyOwnerPIN()));			
			object.add(new BasicNameValuePair("societyName", societyDetails.getmSocietyName()));	
			object.add(new BasicNameValuePair("societyAddressLine1", societyDetails.getmSocietyAddressLine1()));
			object.add(new BasicNameValuePair("societyAddressLine2", societyDetails.getmSocietyAddressLine2()));
			object.add(new BasicNameValuePair("societyCity", societyDetails.getmSocietyAddressCity()));
			object.add(new BasicNameValuePair("societyState",societyDetails.getmSocietyAddressState()));
			object.add(new BasicNameValuePair("societyPIN", societyDetails.getmSocietyAddressPIN()));
			object.add(new BasicNameValuePair("buildingName", societyDetails.getmBuildingName()));
			object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			JSONParserNVP obj = new JSONParserNVP();
			String URL = Param.baseURL+ "registerSocietyOwner.php";
			JSONObject objJson = obj.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getStatus(objJson);	

		} 
		catch (JSONException e) 
		{
			throw new SmartFlatAdminError("Server error occured. Please try again later", "Server Error");
		}
		catch (Exception e)
		{
			throw new SmartFlatAdminError("Please try again later", "Server Error");
		}
	}

	private Response getSaveSocietyOwnerCredentialCall(SocietyDetails societyDetails)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("username", societyDetails.getmUsername()));
			object.add(new BasicNameValuePair("password",societyDetails.getmPassword()));
			object.add(new BasicNameValuePair("securityQuestion", societyDetails.getmSecurityQuestion()));
			object.add(new BasicNameValuePair("answer", societyDetails.getmAnswer()));
			//object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			JSONParserNVP obj = new JSONParserNVP();
			String URL = Param.baseURL+ "saveSocietyOwnerCredential.php";
			JSONObject objJson = obj.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getStatus(objJson);	

		} 
		catch (JSONException e) 
		{
			throw new SmartFlatAdminError("Server error occured. Please try again later", "Server Error");
		}
		catch (Exception e)
		{
			throw new SmartFlatAdminError("Please try again later", "Server Error");
		}
	}

	private Response getLoginCall(String username, String password)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("username", username));
			object.add(new BasicNameValuePair("password",password));
			//object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			JSONParserNVP obj = new JSONParserNVP();
			String URL = Param.baseURL + "getSocietyLogin.php";
			JSONObject objJson = obj.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getStatus(objJson);	

		} 
		catch (JSONException e) 
		{
			throw new SmartFlatAdminError("Server error occured. Please try again later", "Server Error");
		}
		catch (Exception e)
		{
			throw new SmartFlatAdminError("Please try again later", "Server Error");
		}
	}

	private List<FlatOwnerDetails> getFlatUsersCall() 
			throws SmartFlatAdminError{

		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("societyCode", SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));
			//object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			JSONParserNVP obj = new JSONParserNVP();
			String URL = Param.baseURL + "getFlatUserDetails.php";
			JSONObject objJson = obj.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getFlatUsers(objJson);	

		} 
		catch (JSONException e) 
		{
			throw new SmartFlatAdminError("Server error occured. Please try again later", "Server Error");
		}
		catch (Exception e)
		{
			throw new SmartFlatAdminError("Please try again later", "Server Error");
		}
	}


}
