package com.grs.product.smartflatAdmin.response;

public class Response {
	private String mStatus;
	private String mMessage;
	private String mSocietycode;

	public Response() {
		super();
	}

	public Response(String status, String message, String societyCode) {
		this.mStatus = status;
		this.mMessage = message;
		this.mSocietycode = societyCode;

	}


	public void setStatus(String status) {
		this.mStatus = status;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setMessage(String message) {
		this.mMessage = message;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setSocietycode(String societyCode) {
		this.mSocietycode = societyCode;
	}

	public String getSocietycode() {
		return mSocietycode;
	}
}