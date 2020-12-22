package com.lib.common.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;


import androidx.multidex.MultiDex;

import com.lib.common.baseUtils.LogUtil;

import java.io.File;
import java.util.List;
import java.util.Stack;


/**
 * @author 17712
 */
public class BaseApp extends Application {
	private static BaseApp instance;

	public static final String ROOT_PACKAGE = "com.qyai";


	private List<ApplicationDelegate> mAppDelegateList;
	/** 缓存目录 */

	/**
	 * 缓存Activity的堆栈 管理Activity生命周期
	 */
	private Stack<Activity> records = new Stack<Activity>();

	private static final String TAG = "BaseApp";

	private static String deviceId;

	private static String phone;


    private String filepath;

    private File tmpFile;

	public static BaseApp getIns() {
		if (instance == null) {
			synchronized (BaseApp.class) {
				if (instance == null) {
					instance = new BaseApp();
				}
			}
		}

		return instance;
	}

    public String getPhone() {
		if (null == phone) {
			return "";
		}
		return phone;
	}

	public void setPhone(String phone) {
		BaseApp.phone = phone;
	}


	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		mAppDelegateList = ClassUtils.getObjectsWithInterface(this, ApplicationDelegate.class, ROOT_PACKAGE);
		for (ApplicationDelegate delegate : mAppDelegateList) {
			delegate.onCreate();
		}
	}

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		MultiDex.install(this);//65536解决
	}

	public int getAppVersion() {
		int versionCode = 1;
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			versionCode = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	public String getAppVersionName() {
		String versionCode = "";
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			versionCode = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	public void pushActvity(Activity activity) {
		records.push(activity);
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : records) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束指定的Activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			popActvity(activity);
			activity.finish();
			activity = null;
		}
	}

	public void popActvity(Activity activity) {
		try {
			records.remove(activity);
		} catch (Exception e) {
			LogUtil.e(TAG, "e", e);
		}

	}
	
	public Activity getCurrentAct(){
		if(records == null){
			return null;
		}
		return records.lastElement();
	}

	public void exit(boolean isExit) {
		if (null == records) {
			return;
		}
		for (Activity activity : records) {
			Log.d(TAG, "clear activity");
			activity.finish();
		}
		records.clear();
		// try {
		//
		// // 解除service绑定
		// LogUtil.i(TAG, "unbind listen service");
		// unbindService(this);
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
		if (isExit) {
			android.os.Process.killProcess(android.os.Process.myPid());
		}

	}

	/**
	 * 异常处理  回到登录页面
	 * @param isException
	 */
	public void Exception(boolean isException) {
		if (null == records) {
			return;
		}
		for (Activity activity : records) {
			Log.d(TAG, activity.getClass().getSimpleName());
			if(!"LoginAct".equals(activity.getClass().getSimpleName())){
				activity.finish();
			}

		}
		records.clear();
	}

}
