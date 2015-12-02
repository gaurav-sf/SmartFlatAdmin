package com.grs.product.smartflatAdmin.database;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.grs.product.smartflatAdmin.SmartFlatAdminApplication;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableFlatOwnerDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableMessageDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableNames;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableRequestDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyDetails;
import com.grs.product.smartflatAdmin.database.SmartFlatAdminDBTables.TableSocietyOwnerDetails;
import com.grs.product.smartflatAdmin.models.FlatOwnerDetails;
import com.grs.product.smartflatAdmin.models.RequestDetails;
import com.grs.product.smartflatAdmin.models.RequestMessages;
import com.grs.product.smartflatAdmin.models.SocietyDetails;
import com.grs.product.smartflatAdmin.models.SocietyOwnerDetails;

public class SmartFlatAdminDatabase {
	private SQLiteDatabase mDb;
	private SQLiteOpenHelper helper;
	private static SmartFlatAdminDatabase instance;
	private int openCount = 0;
	private static final String DATABASE_NAME = "SmartFlatAdmin";
	private static final int DATABASE_VERSION = 1; //

	public static SmartFlatAdminDatabase getInstance() 
	{
		if (instance == null) 
		{
			instance = new SmartFlatAdminDatabase(SmartFlatAdminApplication.getInstance().getApplicationContext());
		}
		return instance;
	}

	private SmartFlatAdminDatabase(Context context){
		helper = new SmartFlatAdminDatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		helper.getWritableDatabase();
	}

	public synchronized void open() 
	{
		if (mDb == null || !mDb.isOpen()) {
			mDb = helper.getWritableDatabase();
		}
		openCount++;
	}

	public synchronized void close() 
	{
		openCount--;
		if (openCount <= 0) 
		{
			openCount = 0;
			helper.close();
		}
	}


	//This method will be used while altering the tables
	private boolean checkIfColumnsExist(SQLiteDatabase db, String tableName,
			String[] columns) {
		Cursor cur = db.query(tableName, null, null, null, null, null, null);
		String[] dbColumns = cur.getColumnNames();
		Set<String> dbColumnNameSet = new HashSet<String>(
				Arrays.asList(dbColumns));
		Set<String> columnsToFindSet = new HashSet<String>(
				Arrays.asList(columns));
		columnsToFindSet.removeAll(dbColumnNameSet);

		return !(columnsToFindSet.size() > 0);
	}

	private void createFlatOwnerDetailsTable(SQLiteDatabase db)
	{
		try 
		{
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_FLAT_OWNER_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}
	
	private void createSocietyOwnerDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_SOCIETY_OWNER_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}

	private void createSocietyDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_SOCIETY_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}


/*	private void createComplaintDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_COMPLAINT_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}*/

	private void createRequestDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_REQUEST_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}

/*	private void createQueryDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_QUERY_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}*/

	private void createSocietyNoticesTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_SOCIETY_NOTICES_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}
	
	private void createContactDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_CONTACT_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}
	
	private void createMessageDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_MESSAGE_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}
	
	private void createVisitorDetailsTable(SQLiteDatabase db){
		try {
			db.beginTransaction();
			db.execSQL(SmartFlatAdminDBTableCreation.TABLE_VISITOR_DETAILS_CREATION_QUERY);
			db.setTransactionSuccessful();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}	
	}


	//inner class
	public class SmartFlatAdminDatabaseHelper extends SQLiteOpenHelper{

		public SmartFlatAdminDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {

			try {
				createFlatOwnerDetailsTable(db);
				createSocietyOwnerDetailsTable(db);
				createSocietyDetailsTable(db);
				//createComplaintDetailsTable(db);
				createRequestDetailsTable(db);
				//createQueryDetailsTable(db);
				createSocietyNoticesTable(db);
				createContactDetailsTable(db);
				createMessageDetailsTable(db);
				createVisitorDetailsTable(db);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			onCreate(db);
		}

	}
	
	public boolean saveSocietyOwnerDetails(SocietyOwnerDetails details){
		boolean isAdded = false;
		ContentValues values = new ContentValues();
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_NAME,details.getmSocietyOwnerName());
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_ADDRESS_LINE1,details.getmSocietyOwnerAddressLine1());
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_ADDRESS_LINE2,details.getmSocietyOwnerAddressLine2());
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_CITY,details.getmSocietyOwnerCity());
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_STATE,details.getmSocietyOwnerState());
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_PIN,details.getmSocietyOwnerPIN());
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_CONTACT_NO,details.getmSocietyOwnerContactNo());
		values.put(TableSocietyOwnerDetails.SOCIETY_OWNER_EMAIL_ID,details.getmSocietyOwnerEmailId());

		try {
			mDb.beginTransaction();
			isAdded = mDb.insert(TableNames.SOCIETY_OWNER_DETAILS, null, values) > 0;
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			mDb.endTransaction();
		}
		return isAdded;
		}

	public boolean saveSocietyDetails(SocietyDetails details)
	{
		boolean isAdded = false;
		ContentValues values = new ContentValues();
		values.put(TableSocietyDetails.SOCIETY_CODE,details.getmSocietyCode());
/*		values.put(TableSocietyDetails.SOCIETY_OWNER_NAME,details.getmSocietyOwnerName());
		values.put(TableSocietyDetails.SOCIETY_OWNER_ADDRESS_LINE1,details.getmSocietyOwnerAddressLine1());
		values.put(TableSocietyDetails.SOCIETY_OWNER_ADDRESS_LINE2,details.getmSocietyOwnerAddressLine2());
		values.put(TableSocietyDetails.SOCIETY_OWNER_CITY,details.getmSocietyOwnerCity());
		values.put(TableSocietyDetails.SOCIETY_OWNER_STATE,details.getmSocietyOwnerState());
		values.put(TableSocietyDetails.SOCIETY_OWNER_PIN,details.getmSocietyOwnerPIN());
		values.put(TableSocietyDetails.SOCIETY_OWNER_CONTACT_NO,details.getmSocietyOwnerContactNo());
		values.put(TableSocietyDetails.SOCIETY_OWNER_EMAIL_ID,details.getmSocietyOwnerEmailId());
*/		values.put(TableSocietyDetails.SOCIETY_NAME,details.getmSocietyName());
		values.put(TableSocietyDetails.BUILDING_NAME,details.getmBuildingName());
		values.put(TableSocietyDetails.TOTAL_FLOOR_NUMBER,details.getmTotalFloorNumber());
		values.put(TableSocietyDetails.SOCIETY_ADDRESS_LINE1,details.getmSocietyAddressLine1());
		values.put(TableSocietyDetails.SOCIETY_ADDRESS_LINE2,details.getmSocietyAddressLine2());
		values.put(TableSocietyDetails.SOCIETY_ADDRESS_CITY,details.getmSocietyAddressCity());
		values.put(TableSocietyDetails.SOCIETY_ADDRESS_STATE,details.getmSocietyAddressState());
		values.put(TableSocietyDetails.SOCIETY_ADDRESS_PIN,details.getmSocietyAddressPIN());	
		try {
			mDb.beginTransaction();
			isAdded = mDb.insert(TableNames.SOCIETY_DETAILS, null, values) > 0;
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			mDb.endTransaction();
		}
		return isAdded;
		}

	public Cursor getSocietyDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.SOCIETY_DETAILS;
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;		
	}

	public boolean saveFlatOwnerDeatils(FlatOwnerDetails details){
		boolean isAdded = false;
		ContentValues values = new ContentValues();
		values.put(TableFlatOwnerDetails.FLAT_OWNER_NAME, details.getmFlatOwnerName());
		//values.put(TableFlatOwnerDetails.FLAT_OWNER_DOB, details.getmFlatOwnerDOB());
		values.put(TableFlatOwnerDetails.FLAT_OWNER_CONTACT_NO, details.getmFlatOwnerContactNo());
		values.put(TableFlatOwnerDetails.FLAT_OWNER_EMAIL_ID, details.getmFlatOwnerEmailId());
		values.put(TableFlatOwnerDetails.FLAT_BUILDING_NAME, details.getmBuildingName());
		values.put(TableFlatOwnerDetails.FLOOR_NO, details.getmFloorNo());		
		values.put(TableFlatOwnerDetails.FLAT_NO, details.getmFlatno());
		values.put(TableFlatOwnerDetails.FLAT_OWNER_CREATED_DATETIME, details.getmFlatOwnerCreatedDateTime());
		values.put(TableFlatOwnerDetails.FLAT_OWNER_CODE, details.getmFlatOwnerCode());
		values.put(TableFlatOwnerDetails.IS_ACTIVE, details.isActive());
		try {
			if(getFlatOwnerDetails(details.getmFlatOwnerCode()).getCount()<=0)
			{
				mDb.beginTransaction();
				isAdded = mDb.insert(TableNames.FLAT_OWNER_DETAILS, null, values) > 0;
				mDb.setTransactionSuccessful();	
				mDb.endTransaction();
			}else{
				String where_clause = TableFlatOwnerDetails.FLAT_OWNER_CODE +"= '"+ details.getmFlatOwnerCode()+"'";
				mDb.beginTransaction();
				isAdded = mDb.update(TableNames.FLAT_OWNER_DETAILS, values, where_clause, null) > 0;
				mDb.setTransactionSuccessful();	
				mDb.endTransaction();
			}

		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			//mDb.endTransaction();
		}	
		return isAdded;
	}


	public Cursor getAllFlatOwnerDetails(String activationCode){
		String selectQuery = "SELECT  * FROM " + TableNames.FLAT_OWNER_DETAILS + "WHERE "+ TableFlatOwnerDetails.IS_ACTIVE+" = '"+activationCode+"'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;		
	}
	
	private Cursor getFlatOwnerDetails(String flatOwnerCode){
		String selectQuery = "SELECT  * FROM " + TableNames.FLAT_OWNER_DETAILS
				+ " WHERE "+ TableFlatOwnerDetails.FLAT_OWNER_CODE +" = '"+flatOwnerCode+"'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;		
	}
	
	
/*	public boolean saveComplaintDetails(ComplaintDetails details){
		boolean isAdded = false;
		ContentValues values = new ContentValues();		
		values.put(TableFlatOwnerComplaintDetails.COMPLAINT_NUMBER, details.getmComplaintNumber());
		values.put(TableFlatOwnerComplaintDetails.COMPLAINT_TYPE, details.getmComplaintType());
		values.put(TableFlatOwnerComplaintDetails.COMPLAINT_STATUS, details.getmComplaintStatus());
		values.put(TableFlatOwnerComplaintDetails.COMPLAINT_DETAILS, details.getmComplaintDetails());
		values.put(TableFlatOwnerComplaintDetails.COMPLAINT_RAISED_DATETIME, details.getmComplaintRaisedDateTime());
		
		try {
			mDb.beginTransaction();
			isAdded = mDb.insert(TableNames.COMPLAINT_DETAILS, null, values) > 0;
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			mDb.endTransaction();
		}	
		return isAdded;
	}*/
	
/*	public Cursor getAllComplaintDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.COMPLAINT_DETAILS;
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}*/
	
	public boolean saveRequestDetails(RequestDetails details){
		boolean isAdded = false;
		ContentValues values = new ContentValues();		
		values.put(TableRequestDetails.REQUEST_NUMBER, details.getmRequestNumber());
		values.put(TableRequestDetails.REQUEST_TYPE, details.getmRequestType());
		values.put(TableRequestDetails.REQUEST_CATEGORY, details.getmRequestCategory());
		values.put(TableRequestDetails.REQUEST_PRIORITY, details.getmRequestPriority());
		values.put(TableRequestDetails.REQUEST_STATUS, details.getmRequestStatus());
		values.put(TableRequestDetails.REQUEST_DETAILS, details.getmRequestDetails());
		values.put(TableRequestDetails.REQUEST_DATETIME, details.getmRequestDateTime());
		values.put(TableRequestDetails.REQUEST_RAISED_BY, details.getmRequestRaisedBy());
		try {
			if(getRequestDetails(details.getmRequestNumber()).getCount()<=0)
			{			
				mDb.beginTransaction();
				isAdded = mDb.insert(TableNames.REQUEST_DETAILS, null, values) > 0;
				mDb.setTransactionSuccessful();	
				mDb.endTransaction();
			}else{
				String where_clause = TableRequestDetails.REQUEST_NUMBER +"= '"+ details.getmRequestNumber()+"'";
				mDb.beginTransaction();
				isAdded = mDb.update(TableNames.REQUEST_DETAILS, values, where_clause, null) > 0;
				mDb.setTransactionSuccessful();	
				mDb.endTransaction();		
			}

		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
		//	mDb.endTransaction();
		}	
		return isAdded;
	}
	
	public Cursor getAllRequestDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS;
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	private Cursor getRequestDetails(String requestNumber){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS
				+ " WHERE "+ TableRequestDetails.REQUEST_NUMBER +" = '"+requestNumber+"'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;		
	}
	
/*	public boolean saveQueryDetails(QueryDetails details){
		boolean isAdded = false;
		ContentValues values = new ContentValues();		
		values.put(TableFlatOwnerQueryDetails.QUERY_NUMBER, details.getmQueryNumber());
		values.put(TableFlatOwnerQueryDetails.QUERY_STATUS, details.getmQueryStatus());
		values.put(TableFlatOwnerQueryDetails.QUERY_DETAILS, details.getmQueryDetails());
		values.put(TableFlatOwnerQueryDetails.QUERY_DATETIME, details.getmQueryDateTime());
		try {
			mDb.beginTransaction();
			isAdded = mDb.insert(TableNames.QUERY_DETAILS, null, values) > 0;
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			mDb.endTransaction();
		}	
		return isAdded;
	}*/
	
/*	public Cursor getAllQueryDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.QUERY_DETAILS;
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}*/
	
/*	public boolean saveSocietyNoticeDetails(NoticeDetails details){
		boolean isAdded = false;
		ContentValues values = new ContentValues();		
		values.put(TableSocietyNotices.NOTICE_NUMBER, details.getmNoticeNumber());
		values.put(TableSocietyNotices.NOTICE_PRIORITY, details.getmNoticePriority());
		values.put(TableSocietyNotices.NOTICE_DETAILS, details.getmNoticeDetails());
		values.put(TableSocietyNotices.NOTICE_DATETIME, details.getmNoticeDateTime());
		try {
			mDb.beginTransaction();
			isAdded = mDb.insert(TableNames.SOCIETY_NOTICES, null, values) > 0;
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			mDb.endTransaction();
		}	
		return isAdded;
	}*/
	
	public Cursor getAllSocietyNoticeDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.SOCIETY_NOTICES;
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
/*	public Cursor getRaisedComplaintDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.COMPLAINT_DETAILS + " WHERE "+TableFlatOwnerComplaintDetails.COMPLAINT_STATUS +" IN ('Raised','Processed')";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}*/
	
/*	public Cursor getClosedComplaintDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.COMPLAINT_DETAILS + " WHERE "+TableFlatOwnerComplaintDetails.COMPLAINT_STATUS +" = 'Closed'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}*/
	
/*	public Cursor getRaisedRequestDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableFlatOwnerRequestDetails.REQUEST_STATUS +" IN ('Raised','Processed')";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}*/
	
/*	public Cursor getClosedRequestDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableFlatOwnerRequestDetails.REQUEST_STATUS +" = 'Closed'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public Cursor getRaisedQueryDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.QUERY_DETAILS + " WHERE "+TableFlatOwnerQueryDetails.QUERY_STATUS +" IN ('Raised','Processed')";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public Cursor getClosedQueryDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.QUERY_DETAILS + " WHERE "+TableFlatOwnerQueryDetails.QUERY_STATUS +" = 'Closed'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}*/
	
	public Cursor getRaisedRequestDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableRequestDetails.REQUEST_STATUS +" IN ('Raised','Processed') ORDER BY "+TableRequestDetails.REQUEST_DATETIME + "  DESC";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public Cursor getRaisedRequestDetailsByType(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableRequestDetails.REQUEST_STATUS +" IN ('Raised','Processed') ORDER BY "+TableRequestDetails.REQUEST_TYPE + "  ASC";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public Cursor getRaisedRequestDetailsByCategory(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableRequestDetails.REQUEST_STATUS +" IN ('Raised','Processed') ORDER BY "+TableRequestDetails.REQUEST_CATEGORY + "  ASC";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public Cursor getRaisedRequestDetailsByPriorityHtoL(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableRequestDetails.REQUEST_STATUS +" IN ('Raised','Processed') ORDER BY "+TableRequestDetails.REQUEST_PRIORITY + "  ASC";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public Cursor getRaisedRequestDetailsByPriorityLtoH(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableRequestDetails.REQUEST_STATUS +" IN ('Raised','Processed') ORDER BY "+TableRequestDetails.REQUEST_PRIORITY + "  DESC";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	
	
	public Cursor getClosedRequestDetails(){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE "+TableRequestDetails.REQUEST_STATUS +" = 'Closed'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public Cursor getSinbleRequestDetails(String requestNumber){
		String selectQuery = "SELECT  * FROM " + TableNames.REQUEST_DETAILS + " WHERE " + TableRequestDetails.REQUEST_NUMBER +"= '"+ requestNumber+"'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public boolean saveMessage(RequestMessages message){
		boolean isAdded = false;
		ContentValues values = new ContentValues();		
		values.put(TableMessageDetails.MESSAGE_NUMBER,message.getmMessageNumber());
		values.put(TableMessageDetails.MESSAGE_CONTENT,message.getmMessageContent());
		values.put(TableMessageDetails.REQUEST_NUMBER,message.getmRequestNumber());
		values.put(TableMessageDetails.FLAT_OWNER_CODE,message.getmFlatOwnerCode());
		values.put(TableMessageDetails.IS_SOCIETY_MESSAGE,message.ismIsSocietyMessage());
		values.put(TableMessageDetails.MESSAGE_DATETIME,message.getmMessageDateTime());

		try {
			if(getSingleMessages(message.getmMessageNumber()).getCount()<=0){
				mDb.beginTransaction();
				isAdded = mDb.insert(TableNames.MESSAGE_DETAILS, null, values) > 0;
				mDb.setTransactionSuccessful();	
				mDb.endTransaction();
			}
		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			//mDb.endTransaction();
		}	
		return isAdded;	
	}
	
	public Cursor getMessages(String requestNumber){
		String selectQuery = "SELECT  * FROM " + TableNames.MESSAGE_DETAILS + " WHERE " + TableMessageDetails.REQUEST_NUMBER +"= '"+ requestNumber+"' ORDER BY "+TableMessageDetails.MESSAGE_DATETIME + "  ASC";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}
	
	public void UpdateActivationFlag(String flatOwnerCode){
		ContentValues values = new ContentValues();		
		values.put(TableFlatOwnerDetails.IS_ACTIVE,"1");		
		String WHERE = TableFlatOwnerDetails.FLAT_OWNER_CODE + "= '"+flatOwnerCode+"'";

		try {
			mDb.beginTransaction();
			mDb.update(TableNames.FLAT_OWNER_DETAILS, values, WHERE, null);
			mDb.setTransactionSuccessful();
		} catch (Exception e) {
			Log.e("Error in transaction", e.toString());
		} finally {
			mDb.endTransaction();
		}		
	}
	
	public Cursor getSingleMessages(String messageNumber){
		String selectQuery = "SELECT  * FROM " + TableNames.MESSAGE_DETAILS + " WHERE " + TableMessageDetails.MESSAGE_NUMBER +"= '"+ messageNumber+"'";
		Cursor cursor = mDb.rawQuery(selectQuery, null);	
		if (cursor != null && cursor.getCount()>0) {
			cursor.moveToNext();
		}
		return cursor;			
	}

}
