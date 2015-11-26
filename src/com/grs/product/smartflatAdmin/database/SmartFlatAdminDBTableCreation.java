package com.grs.product.smartflatAdmin.database;

import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableComplaintDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableNames;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableQueryDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableRequestDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyNotices;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyOwnerDetails;


public class SmartFlatAdminDBTableCreation {

	public static final String TABLE_FLAT_OWNER_DETAILS_CREATION_QUERY = "Create table if not exists "+TableNames.FLAT_OWNER_DETAILS
			+"( " 
			+ TableFlatOwnerDetails.ID + " INTEGER PRIMARY KEY, "
			+ TableFlatOwnerDetails.FLAT_OWNER_CODE + " TEXT, "
			+ TableFlatOwnerDetails.FLAT_OWNER_NAME + " TEXT, "
			//+ TableFlatOwnerDetails.FLAT_OWNER_DOB+ " TEXT, "
			+ TableFlatOwnerDetails.FLAT_OWNER_CONTACT_NO + " TEXT, "
			+ TableFlatOwnerDetails.FLAT_OWNER_EMAIL_ID + " TEXT, "
			+ TableFlatOwnerDetails.FLAT_BUILDING_NAME + " TEXT, "
			+ TableFlatOwnerDetails.FLOOR_NO + " TEXT, "
			+ TableFlatOwnerDetails.FLAT_NO + " TEXT, "
			+ TableFlatOwnerDetails.IS_ACTIVE + " BOOLEAN, "
			+ TableFlatOwnerDetails.FLAT_OWNER_CREATED_DATETIME + " TEXT); ";

	public static final String TABLE_SOCIETY_DETAILS_CREATION_QUERY = "Create table if not exists "+TableNames.SOCIETY_DETAILS
			+"( "
			+ TableSocietyDetails.ID + " INTEGER PRIMARY KEY, "
			+ TableSocietyDetails.SOCIETY_CODE + " TEXT, "
			+ TableSocietyDetails.SOCIETY_NAME + " TEXT, "
			+ TableSocietyDetails.BUILDING_NAME + " TEXT, "
			+ TableSocietyDetails.TOTAL_FLOOR_NUMBER + " INTEGER, "
			+ TableSocietyDetails.SOCIETY_ADDRESS_LINE1 + " TEXT, "
			+ TableSocietyDetails.SOCIETY_ADDRESS_LINE2 + " TEXT, "
			+ TableSocietyDetails.SOCIETY_ADDRESS_CITY + " TEXT, "
			+ TableSocietyDetails.SOCIETY_ADDRESS_STATE + " TEXT, "
			+ TableSocietyDetails.SOCIETY_ADDRESS_PIN + " TEXT ); ";

	public static final String TABLE_SOCIETY_OWNER_DETAILS_CREATION_QUERY = "Create table if not exists "+TableNames.SOCIETY_OWNER_DETAILS
			+"( "
			+ TableSocietyOwnerDetails.ID + " INTEGER PRIMARY KEY, "
			+ TableSocietyOwnerDetails.USERNAME + " TEXT, "
			+ TableSocietyOwnerDetails.PASSWORD + " TEXT, "
			+ TableSocietyOwnerDetails.SECURITY_QUESTION + " TEXT, "
			+ TableSocietyOwnerDetails.ANSWER + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_NAME + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_DOB + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_AGE + " TEXT, "
			+ TableSocietyOwnerDetails.GENDER + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_CONTACT_NO + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_EMAIL_ID + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_CODE + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_ADDRESS_LINE1 + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_ADDRESS_LINE2 + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_CITY + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_STATE + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_PIN + " TEXT, "
			+ TableSocietyOwnerDetails.SOCIETY_OWNER_CREATED_DATETIME + " TEXT, "
			+ TableSocietyOwnerDetails.PUSH_TOKEN + " TEXT, "
			+ TableSocietyOwnerDetails.ACCESS_TOKEN + " TEXT); ";
			

/*	public static final String TABLE_COMPLAINT_DETAILS_CREATION_QUERY = "Create table if not exists "+TableNames.COMPLAINT_DETAILS
			+"( "
			+ TableComplaintDetails.ID + " INTEGER PRIMARY KEY, "
			+ TableComplaintDetails.COMPLAINT_NUMBER + " TEXT, "
			+ TableComplaintDetails.COMPLAINT_RAISED_BY + " TEXT, "
			+ TableComplaintDetails.COMPLAINT_STATUS + " TEXT, "
			+ TableComplaintDetails.COMPLAINT_TYPE + " TEXT, "
			+ TableComplaintDetails.COMPLAINT_RAISED_DATETIME + " TEXT, "
			+ TableComplaintDetails.COMPLAINT_DETAILS + " TEXT);";*/

	public static final String TABLE_REQUEST_DETAILS_CREATION_QUERY = "Create table if not exists "+TableNames.REQUEST_DETAILS
			+"( "
			+ TableRequestDetails.ID + " INTEGER PRIMARY KEY, "
			+ TableRequestDetails.REQUEST_NUMBER + " TEXT, "
			+ TableRequestDetails.REQUEST_CATEGORY + " TEXT, "
			+ TableRequestDetails.REQUEST_RAISED_BY + " TEXT, "
			+ TableRequestDetails.REQUEST_PRIORITY + " TEXT, "
			+ TableRequestDetails.REQUEST_STATUS + " TEXT, "
			+ TableRequestDetails.REQUEST_TYPE + " TEXT, "
			+ TableRequestDetails.REQUEST_DATETIME + " TEXT, "
			+ TableRequestDetails.REQUEST_DETAILS + " TEXT);";

/*	public static final String TABLE_QUERY_DETAILS_CREATION_QUERY = "Create table if not exists "+TableNames.QUERY_DETAILS
			+"( "
			+ TableQueryDetails.ID + " INTEGER PRIMARY KEY, "
			+ TableQueryDetails.QUERY_NUMBER + " TEXT, "
			+ TableQueryDetails.QUERY_RAISED_BY + " TEXT, "
			+ TableQueryDetails.QUERY_STATUS + " TEXT, "
			+ TableQueryDetails.QUERY_DATETIME + " TEXT, "
			+ TableQueryDetails.QUERY_DETAILS + " TEXT);";*/

	public static final String TABLE_SOCIETY_NOTICES_CREATION_QUERY = "Create table if not exists "+TableNames.SOCIETY_NOTICES
			+"( "
			+ TableSocietyNotices.ID + " INTEGER PRIMARY KEY, "
			+ TableSocietyNotices.NOTICE_NUMBER + " TEXT, "
			+ TableSocietyNotices.NOTICE_FOR + " TEXT, "
			+ TableSocietyNotices.NOTICE_PRIORITY + " TEXT, "
			+ TableSocietyNotices.NOTICE_DATETIME + " TEXT, "
			+ TableSocietyNotices.NOTICE_DETAILS + " TEXT);";

















































































}
