package com.lib.common.baseUtils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * sharedpreferences 工具类
 * 
 * @author Dingyajun
 */
public class SPValueUtil {
	
	private SPValueUtil(){}
	
	private static final String SP_NAME = "SHAR_NAME";
	


	public static final int DEFAULT_INT = - 999;
	
	/**
	 * 存储Int值
	 * 
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void saveIntValue(Context context, String name, int value){
		SharedPreferences sp =
				context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putInt(name, value).commit();
	}
	
	/**
	 * 存储Boolean值
	 * 
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void saveBooleanValue(Context context, String name,
                                        boolean value){
		SharedPreferences sp =
				context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(name, value).commit();
	}
	
	/**
	 * 存储String值
	 * 
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void saveStringValue(Context context, String name,
                                       String value){
		SharedPreferences sp =
				context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putString(name, value).commit();
	}
	
	/**
	 * get Int value
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getIntValue(Context context, String name){
		SharedPreferences sp =
				context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getInt(name, DEFAULT_INT);
	}

	/**
	 * get Int value
	 *
	 * @param context
	 * @param name
	 * @param def
	 * @return
	 */
	public static int getIntValue(Context context, String name, int def){
		SharedPreferences sp =
				context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getInt(name, def);
	}
	
	/**
	 * get boolean value
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static boolean getBooleanValue(Context context, String name){
		SharedPreferences sp =
				context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(name, false);
	}
	
	public static boolean getBooleanValue(Context context, String name,
                                          boolean d){
		SharedPreferences sp =
				context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(name, d);
		
	}
	
	/**
	 * get String value
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getStringValue(Context context, String name){
		SharedPreferences sp =
				context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getString(name, "");
	}
	
	public static String getStringValue(Context context, String name,
                                        String defValue){
		SharedPreferences sp =
				context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getString(name, defValue);
	}
	
	public static void clearSession(Context context){
		SharedPreferences sp =
				context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		Editor edit = sp.edit();
//		edit.putString(SEARCH_HISTORY, "");
//		edit.putString(LOGIN_KEY, "");
//		edit.putString(USER_PHONE, "");
//		edit.putString(UPDATE_VERSION, "");
//		edit.putString(SP_BANNER, "");
		edit.commit();
	}

	public static boolean isEmpty(String str){
		if(null!=str&&!str.equals("")){
			return true;
		}else {
			return false;
		}
	}
}
