package com.grs.product.smartflatAdmin.database;

import android.provider.BaseColumns;

public class SmartFlatAdminDBTables {

	private SmartFlatAdminDBTables(){

	}
	
	public static final class TableNames{
		public static final String SOCIETY_OWNER_DETAILS = "Society_Owner_Details";
		public static final String SOCIETY_DETAILS = "Society_Details";
		//public static final String COMPLAINT_DETAILS = "Complaint_Details";
		public static final String REQUEST_DETAILS = "Request_Details";
		//public static final String QUERY_DETAILS = "Query_Details";
		public static final String SOCIETY_NOTICES= "Society_Notices";
		public static final String FLAT_OWNER_DETAILS = "Flat_Owner_Details";
		public static final String CONTACT_DETAILS = "Contact_Details";
		public static final String MESSAGE_DETAILS = "Message_Details";
		public static final String VISITOR_DETAILS = "Visitor_Details";
		
	}

	public static final class TableSocietyOwnerDetails implements BaseColumns
	{
		private TableSocietyOwnerDetails(){}
		public static final String ID = "ID";
		public static final String USERNAME = "Username";	
		public static final String PASSWORD = "Password";	
		public static final String SECURITY_QUESTION = "Security_Question";	
		public static final String ANSWER = "Answer";	
		public static final String SOCIETY_OWNER_NAME = "Society_Owner_Name"	;
		public static final String SOCIETY_OWNER_DOB = "Society_Owner_DOB";	
		public static final String SOCIETY_OWNER_AGE = "Society_Owner_Age";
		public static final String GENDER = "Gender";
		public static final String SOCIETY_OWNER_CONTACT_NO = "Society_Owner_Contact_No";
		public static final String SOCIETY_OWNER_EMAIL_ID = "Society_Owner_Email_id";
		public static final String SOCIETY_CODE = "Society_Code";	
		public static final String SOCIETY_OWNER_ADDRESS_LINE1 = "Society_Owner_Address_Line1";
		public static final String SOCIETY_OWNER_ADDRESS_LINE2  = "Society_Owner_Address_Line2";	
		public static final String SOCIETY_OWNER_CITY = "Society_Owner_City";	
		public static final String SOCIETY_OWNER_STATE = "Society_Owner_State";	
		public static final String SOCIETY_OWNER_PIN = "Society_Owner_PIN";	
		public static final String SOCIETY_OWNER_CREATED_DATETIME = "Society_Owner_Created_DateTime";	
		public static final String PUSH_TOKEN = "Push_Token";	
		public static final String ACCESS_TOKEN = "Access_Token";
		public static final String AVAILABLE_CREDITS = "Available_Credits";
		public static final String NO_OF_USER_REGISTERED = "No_of_user_Registered";
		public static final String NO_OF_USER_ACTIVATED ="No_of_user_Activated";
	}

	public static final class TableSocietyDetails implements BaseColumns{
		private TableSocietyDetails(){}
		public static final String ID = "ID";	
		public static final String SOCIETY_CODE = "Society_Code";	
		public static final String SOCIETY_NAME = "Society_Name";	
		public static final String BUILDING_NAME = "Building_Name";	
		public static final String TOTAL_FLOOR_NUMBER = "Total_Floor_Number";	
		public static final String SOCIETY_ADDRESS_LINE1 = "Society_Address_Line1";	
		public static final String SOCIETY_ADDRESS_LINE2 = "Society_Address_Line2";	
		public static final String SOCIETY_ADDRESS_CITY = "Society_Address_City";	
		public static final String SOCIETY_ADDRESS_STATE = "Society_Address_State";	
		public static final String SOCIETY_ADDRESS_PIN = "Society_Address_PIN";	
	}

	public static final class TableComplaintDetails implements BaseColumns
	{
		private TableComplaintDetails(){}
		public static final String ID = "ID";	
		public static final String COMPLAINT_NUMBER = "Complaint_Number";	
		public static final String COMPLAINT_TYPE = "Complaint_Type";	
		public static final String COMPLAINT_STATUS = "Complaint_Status";	
		public static final String COMPLAINT_RAISED_DATETIME = "Complaint_Raised_DateTime";	
		public static final String COMPLAINT_DETAILS = "Complaint_Details";
		public static final String COMPLAINT_RAISED_BY = "Complaint_Raised_By";
	}

	public static final class TableRequestDetails implements BaseColumns
	{
		private TableRequestDetails(){}
		public static final String ID = "ID";
		public static final String REQUEST_NUMBER = "Request_Number";
		public static final String REQUEST_TYPE = "Request_Type";	
		public static final String REQUEST_CATEGORY = "Request_Category";
		public static final String REQUEST_PRIORITY = "Request_Priority";	
		public static final String REQUEST_DATETIME = "Request_DateTime";	
		public static final String REQUEST_STATUS = "Request_Status";
		public static final String REQUEST_DETAILS = "Request_Details";
		public static final String REQUEST_RAISED_BY = "Request_Raised_By";
	}

	public static final class TableQueryDetails implements BaseColumns
	{
		private TableQueryDetails(){}
		public static final String ID = "ID";	
		public static final String QUERY_NUMBER = "Query_Number";	
		public static final String QUERY_DATETIME = "Query_DateTime";	
		public static final String QUERY_STATUS = "Query_Status";	
		public static final String QUERY_DETAILS = "Query_Details";
		public static final String QUERY_RAISED_BY = "Query_Raised_By";
	}

	public static final class TableSocietyNotices implements BaseColumns
	{
		private TableSocietyNotices(){}
		public static final String ID = "ID";	
		public static final String NOTICE_NUMBER = "Notice_Number";	
		public static final String NOTICE_DATETIME = "Notice_DateTime";	
		public static final String NOTICE_TO = "Notice_To";	
		public static final String NOTICE_SUBJECT = "Notice_Subject";
		public static final String NOTICE_MESSAGE = "Notice_Message";
	}
	
	public static final class TableFlatOwnerDetails implements BaseColumns
	{
		private TableFlatOwnerDetails(){}
		public static final String ID = "_id";
		public static final String FLAT_OWNER_CODE = "Flat_Owner_Code";
		public static final String FLAT_OWNER_NAME = "Flat_Owner_Name"	;
		public static final String FLAT_OWNER_DOB = "Flat_Owner_DOB";	
		public static final String FLAT_OWNER_CONTACT_NO = "Flat_Owner_Contact_No";
		public static final String FLAT_OWNER_EMAIL_ID = "Flat_Owner_Email_id";
		public static final String FLAT_BUILDING_NAME = "Building_Name";	
		public static final String FLOOR_NO = "Floor_No";	
		public static final String FLAT_NO = "Flat_no";	
		public static final String FLAT_OWNER_CREATED_DATETIME = "Flat_Owner_Created_DateTime";	
		public static final String GENDER = "Gender";
		public static final String IS_ACTIVE = "Is_Active";			
	}
	
	public static final class TableContactDetails implements BaseColumns{
		private TableContactDetails(){}
		public static final String ID = "ID";
		public static final String CONTACT_NAME = "Contact_Name";
		public static final String CONTACT_NUMBER = "Contact_Number";
		public static final String CONTACT_EMAIL_ID = "Contact_Email_ID";
		public static final String CONTACT_OCCUPATION = "Contact_Occupation";
	}
	
	public static final class TableMessageDetails implements BaseColumns{
		private TableMessageDetails(){}
		public static final String ID = "ID";
		public static final String MESSAGE_NUMBER = "Message_Number";
		public static final String MESSAGE_CONTENT = "Message_Content";
		public static final String REQUEST_NUMBER = "Request_Number";
		public static final String FLAT_OWNER_CODE = "Flat_Owner_Code";
		public static final String SOCIETY_CODE = "Society_Code";
		public static final String IS_SOCIETY_MESSAGE = "Is_Society_Message";
		public static final String MESSAGE_DATETIME = "Message_DateTime";
		public static final String IS_READ = "Is_Read";
	}
	
	public static final class TableVisitorDetails implements BaseColumns{
		private TableVisitorDetails(){}
		public static final String ID = "ID";
		public static final String VISITOR_CODE = "Visitor_Code";
		public static final String VISITOR_NAME = "Visitor_Name";
		public static final String NO_OF_VISITORS = "No_of_Visitors";
		public static final String VISITOR_IN_TIME = "Visitor_In_Time";
		public static final String VISITOR_OUT_TIME = "Visitor_Out_Time";
		public static final String VISIT_PURPOSE = "Visit_Purpose";
		public static final String VISITOR_CONTACT_NO = "Visitor_Contac_No";
		public static final String VISITOR_VEHICLE_NO = "Visitor_Vehicle_No";
		public static final String SOCIETY_CODE = "Society_Code";
		public static final String FLAT_OWNER_CODE = "Flat_Owner_Code";
		public static final String IS_OFFLINE_ENTRY = "Is_Offline_Entry";
		
/*		//THINING TO USE IN NEXT VERSION
  		public static final String VISITOR_EXPECTED_DATE = "Visitor_Expected_Date";
		public static final String VISITOR_EXPECTED_ARRIVE_TIME_FROM = "Visitor_Expected_Arrive_Time_from";
		public static final String VISITOR_EXPECTED_ARRIVE_TIME_TO = "Visitor_Expected_Arrive_Time_to";
*/
	}

}
