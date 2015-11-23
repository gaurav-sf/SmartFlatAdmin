package com.grs.product.smartflatAdmin.apicall;

import com.grs.product.smartflatAdmin.error.SmartFlatAdminError;

public interface AsyncTaskCompleteListener<T>
{
    public abstract void onStarted(); 
	public abstract void onTaskComplete(T result);
	public abstract void onStoped(); 
	public abstract void onStopedWithError(SmartFlatAdminError e);
}
