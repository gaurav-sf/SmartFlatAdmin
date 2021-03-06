package com.grs.product.smartflatAdmin.apicall;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;
import com.grs.product.smartflatAdmin.models.ContactDetails;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.models.SocietyOwnerDetails;
import com.grs.product.smartflatAdmin.models.SocietyPollDetails;
import com.grs.product.smartflatAdmin.models.VisitorDetails;
import com.grs.product.smartflatAdmin.response.Response;
import com.grs.product.smartflatAdmin.utils.Param;
import com.grs.product.smartflatAdmin.utils.Utilities;

public class SmartFlatAdminAPI {

	final Context mContext;

	public  SmartFlatAdminAPI(Context context) {
		this.mContext = context;
	}

	public Response getRegistration(SocietyDetails societyDetails, SocietyOwnerDetails societyOwnerDetails)
			throws SmartFlatAdminError{
		return getRegistrationCall(societyDetails, societyOwnerDetails);
	}

	public Response getSaveSocietyOwnerCredential(SocietyOwnerDetails societyDetails)
			throws SmartFlatAdminError{		
		return getSaveSocietyOwnerCredentialCall(societyDetails);		
	}

	public Response getLogin(String username, String password)
			throws SmartFlatAdminError
	{
		return getLoginCall(username, password);
	}

	public List<FlatOwnerDetails> getFlatUsers()
			throws SmartFlatAdminError
	{
		return getFlatUsersCall();
	}
	
	public Response activateFlatOwner(String flatOwnerCode) 
			throws SmartFlatAdminError{
		return activateFlatOwnerCall(flatOwnerCode);
	}
	
	public List<RequestDetails> getRequestAndComplaint()
			throws SmartFlatAdminError
	{
		return getRequestAndComplaintCall();
	} 
	
	public List<RequestMessages> getMessages()
			throws SmartFlatAdminError
	{
		return getMessagesCall();
	}
	
	public Response sendMesssage(String message, String requestNumber, String flatOwnerCode) throws SmartFlatAdminError{
		return sendMessageCall(message, requestNumber, flatOwnerCode);
	}
	
	public Response saveVisitor(VisitorDetails details) throws SmartFlatAdminError{
		return saveVisitorCall(details);
	}
	
	public Response saveContact(ContactDetails details) throws SmartFlatAdminError{
		return saveContactCall(details);
	}
	
	public Response sendPushToken(String pushToen,String societyCode) throws SmartFlatAdminError{
		return sendPushTokenCall(pushToen,societyCode);
	}
	
	public Response sendNotice(String noticeTo, String noticeSubject, String noticeMessage) throws SmartFlatAdminError{
		return sendNoticeCall(noticeTo, noticeSubject, noticeMessage);
	}
	
	public Response getPreviousData(String societyCode)throws SmartFlatAdminError{
		return getPreviousDataCall(societyCode);
	}
	
	public Response updateFlatOwnerDetails(FlatOwnerDetails flatOwnerDetails)throws SmartFlatAdminError{
		return updateFlatOwnerDetailsCall(flatOwnerDetails);
	}
	
	public Response getSignOut()
			throws SmartFlatAdminError
	{
		return getSignOutCall();
	}
	
	public Response getUploadSocietyPoll(SocietyPollDetails societyPollDetails)
			throws SmartFlatAdminError
	{
		return getUploadSocietyPollCall(societyPollDetails);
	}
	
	public List<SocietyPollDetails> getAllPolls()
			throws SmartFlatAdminError
	{
		return getAllPollsCall();
	}
	
	public Response getUploadPollVote(SocietyPollDetails societyPollDetails, String selectedOption)
			throws SmartFlatAdminError
	{
		return getUploadPollVoteCall(societyPollDetails,selectedOption);
	}

	private Response getRegistrationCall(SocietyDetails societyDetails, SocietyOwnerDetails societyOwnerDetails)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
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
			//Building name will be separated by @ sign
			object.add(new BasicNameValuePair("buildingName", societyDetails.getmBuildingName()));
			object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL+ "registerSocietyOwner.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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

	private Response getSaveSocietyOwnerCredentialCall(SocietyOwnerDetails societyDetails)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("username", societyDetails.getmUsername()));
			object.add(new BasicNameValuePair("password",societyDetails.getmPassword()));
			object.add(new BasicNameValuePair("securityQuestion", societyDetails.getmSecurityQuestion()));
			object.add(new BasicNameValuePair("answer", societyDetails.getmAnswer()));
			//object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL+ "saveSocietyOwnerCredentials.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
			Log.e("GAURAV", objJson.toString());
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

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "SocietyOwnerLogin.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
			throws SmartFlatAdminError
	{
		try
		{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("societyCode", SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter obj = new ServerConnecter();
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
			throw new SmartFlatAdminError("Server error occured. Please try again later", "Server Error");
		}
	}
	
	private Response activateFlatOwnerCall(String flatOwnerCode)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("flatOwnerCode", flatOwnerCode));
			object.add(new BasicNameValuePair("societyCode", SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));
			//object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "activateFlatOwner.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private List<RequestDetails> getRequestAndComplaintCall() 
			throws SmartFlatAdminError
	{
		try
		{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("societyCode", SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter obj = new ServerConnecter();
			String URL = Param.baseURL + "getRequestsAndComplaints.php";
			JSONObject objJson = obj.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getRequestAndComplaint(objJson);

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
	
	private List<RequestMessages> getMessagesCall() 
			throws SmartFlatAdminError
	{
		try
		{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("societyCode", SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter obj = new ServerConnecter();
			String URL = Param.baseURL + "getSocietyMessages.php";
			JSONObject objJson = obj.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getMessages(objJson);

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
	
	private Response sendMessageCall(String message, String requestNumber, String flatOwnerCode) throws SmartFlatAdminError{

		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("message", message));
			object.add(new BasicNameValuePair("requestNumber",requestNumber));
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));
			object.add(new BasicNameValuePair("flatOwnerCode",flatOwnerCode));
			object.add(new BasicNameValuePair("isSocietyMessage","1"));
			object.add(new BasicNameValuePair("messageDateTime",Utilities.getCurrentDateTime()));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "sendMessage.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private Response saveVisitorCall(VisitorDetails details) throws SmartFlatAdminError{

		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("visitorName",details.getmVisitorName()));
			object.add(new BasicNameValuePair("noOfVisitors",details.getmNoofVisitors()));
			object.add(new BasicNameValuePair("visitorInTime",details.getmVisitorInTime()));
			object.add(new BasicNameValuePair("visitorContactNo",details.getmVisitorContacNo()));
			object.add(new BasicNameValuePair("visitorVehicleNo",details.getmVisitorVehicleNo()));
			object.add(new BasicNameValuePair("visitPurpose",details.getmVisitPurpose()));
			object.add(new BasicNameValuePair("flatOwnerCode",details.getmFlatOwnerCode()));
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));
			
			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "saveVisitor.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private Response saveContactCall(ContactDetails details) throws SmartFlatAdminError{

		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("contactName",details.getmContactName()));
			object.add(new BasicNameValuePair("contactNo",details.getmContactNumber()));
			object.add(new BasicNameValuePair("emailId",details.getmContactEmailId()));
			object.add(new BasicNameValuePair("occupation",details.getmContactOccupation()));
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));
			
			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "saveContact.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private Response sendPushTokenCall(String pushToken,String societyCode)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("pushToken", pushToken));
			object.add(new BasicNameValuePair("societyCode",societyCode));
			//object.add(new BasicNameValuePair("totalFloorNo", societyDetails.getmTotalFloorNumber()+""));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "saveSocietyOwnerPushToken.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private Response sendNoticeCall(String noticeTo, String noticeSubject, String noticeMessage) throws SmartFlatAdminError{

		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("noticeTo", noticeTo));
			object.add(new BasicNameValuePair("noticeSubject",noticeSubject));
			object.add(new BasicNameValuePair("noticeMessage",noticeMessage));
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));
			object.add(new BasicNameValuePair("noticeDateTime",Utilities.getCurrentDateTime()));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "sendNotice.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private Response getPreviousDataCall(String societyCode) throws SmartFlatAdminError{

		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("societyCode",societyCode));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "getPreviousDataForSociety.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getPreviousData(objJson);	

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
	
	private Response updateFlatOwnerDetailsCall(FlatOwnerDetails flatOwnerDetails) 
			throws SmartFlatAdminError{	

		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("flatOwnerName",flatOwnerDetails.getmFlatOwnerName()));
			object.add(new BasicNameValuePair("flatOwnerDOB",flatOwnerDetails.getmFlatOwnerDOB()));
			object.add(new BasicNameValuePair("gender",flatOwnerDetails.getmGender()));
			object.add(new BasicNameValuePair("flatOwnerContactNo",flatOwnerDetails.getmFlatOwnerContactNo()));
			object.add(new BasicNameValuePair("flatOwnerEmailId",flatOwnerDetails.getmFlatOwnerEmailId()));
			object.add(new BasicNameValuePair("buildingName",flatOwnerDetails.getmBuildingName()));
			object.add(new BasicNameValuePair("floorNo",flatOwnerDetails.getmFloorNo()));
			object.add(new BasicNameValuePair("flatNo",flatOwnerDetails.getmFlatno()));
			object.add(new BasicNameValuePair("flatOwnerCode",flatOwnerDetails.getmFlatOwnerCode()));
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "updateFlatOwnerDetails.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private Response getSignOutCall()
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL + "signOutSocietyOwner.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private Response getUploadSocietyPollCall(SocietyPollDetails societyPollDetails)
			throws SmartFlatAdminError{
		try{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("pollTopic", societyPollDetails.getmPollTopic()));
			object.add(new BasicNameValuePair("pollTopicDetails", societyPollDetails.getmPollTopicDetails()));
			object.add(new BasicNameValuePair("pollOption1", societyPollDetails.getmPollOption1()));
			object.add(new BasicNameValuePair("pollOption2", societyPollDetails.getmPollOption2()));
			object.add(new BasicNameValuePair("pollOption3", societyPollDetails.getmPollOption3()));
			object.add(new BasicNameValuePair("pollOption4", societyPollDetails.getmPollOption4()));
			object.add(new BasicNameValuePair("pollDuration", societyPollDetails.getmPollDuration()));
			object.add(new BasicNameValuePair("pollCreatedBy", societyPollDetails.getmPollCreatedBy()));
			object.add(new BasicNameValuePair("pollCreatedDateTime", societyPollDetails.getmPollCreatedDateTime()));
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL+ "uploadSocietyPoll.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
	
	private List<SocietyPollDetails> getAllPollsCall() 
			throws SmartFlatAdminError
	{
		try
		{
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("societyCode", SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter obj = new ServerConnecter();
			String URL = Param.baseURL + "getAllPolls.php";
			JSONObject objJson = obj.getJSONFromUrl(URL, object);
			JSONSingleObjectDecode objectjson = new JSONSingleObjectDecode();
			return objectjson.getPolls(objJson);	

		} 
		catch (JSONException e) 
		{
			throw new SmartFlatAdminError("Server error occured. Please try again later", "Server Error");
		}
		catch (Exception e)
		{
			throw new SmartFlatAdminError("Server error occured. Please try again later", "Server Error");
		}
	}
	
	private Response getUploadPollVoteCall(SocietyPollDetails societyPollDetails, String selectedOption)
			throws SmartFlatAdminError{
		try{		
			ArrayList<NameValuePair> object = new ArrayList<NameValuePair>();
			object.add(new BasicNameValuePair("pollId", societyPollDetails.getmPollId()));
			object.add(new BasicNameValuePair("pollOptionSelected", selectedOption));
			object.add(new BasicNameValuePair("votedBy", SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));
			object.add(new BasicNameValuePair("societyCode",SmartFlatAdminApplication.getSocietyCodeFromSharedPreferences()));

			ServerConnecter serverConnecter = new ServerConnecter();
			String URL = Param.baseURL+ "uploadSocietyPollVote.php";
			JSONObject objJson = serverConnecter.getJSONFromUrl(URL, object);
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
}
