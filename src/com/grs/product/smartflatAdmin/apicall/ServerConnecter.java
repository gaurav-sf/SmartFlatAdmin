package com.grs.product.smartflatAdmin.apicall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;

import android.util.Log;

public class ServerConnecter {

	 InputStream mInputStream = null;
	 JSONObject mJsonObject = null;
	 String jsonString = "";
	public static final int JSON_CONNECTION_TIMEOUT = 30000;
	public static final int JSON_SOCKET_TIMEOUT = 30000;

	public ServerConnecter() {
		 mInputStream = null;
		 mJsonObject = null;
		jsonString = "";

	}
	

	public JSONObject getJSONFromUrl(String url , ArrayList<NameValuePair> nameValuePairs) 
			throws SmartFlatAdminError {

		// Making HTTP request
		try {
			// defaultHttpClient
			 HttpParams httpParameters = new BasicHttpParams();
			 setTimeOut(httpParameters);
			DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
			
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			mInputStream = httpEntity.getContent();			

		} catch (UnsupportedEncodingException e) {
			throw new SmartFlatAdminError("Please try again later", "Server Error");
		} catch (ClientProtocolException e) {
			throw new SmartFlatAdminError("Please try again later", "Server Error");
		} catch (IOException e) {
			throw new SmartFlatAdminError("Server error please try again later", "Server Error");
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					mInputStream, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			mInputStream.close();
			jsonString = sb.toString();
		} catch (Exception e) {
			throw new SmartFlatAdminError("Please try again later", "Server Error");
		}

		// try parse the string to a JSON object
		try {
			mJsonObject = new JSONObject(jsonString);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
			throw new SmartFlatAdminError("Please try again later",e.toString());

		}

		// return JSON String
		return mJsonObject;

	}
	
	private void setTimeOut(HttpParams params) {
		HttpConnectionParams.setConnectionTimeout(params,
				JSON_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, JSON_SOCKET_TIMEOUT);
	}
}
