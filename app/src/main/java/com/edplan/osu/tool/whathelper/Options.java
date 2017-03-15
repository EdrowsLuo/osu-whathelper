package com.edplan.osu.tool.whathelper;
import android.content.*;
import java.util.*;
import android.os.*;

public abstract class Options
{
	private static SharedPreferences preferences;
	
	private static Context context;
	
	public static int DEFAUT_MODE=Context.MODE_PRIVATE;
	public static String DEFAUT_TABLE="defautTable";
	
	public static Context getContext(){
		return context;
	}
	
	public static Map<String,?> getAll(){
		return preferences.getAll();
	}
	
	public static String getString(String key,String defV){
		return preferences.getString(key,defV);
	}
	
	public static String getString(String key){
		return getString(key,null);
	}
	
	public static boolean putString(String key,String value){
		return preferences.edit().putString(key,value).commit();
	}
	
	public static int getInt(String key){
		return getInt(key,-1);
	}
	
	public static int getInt(String key,int defV){
		return preferences.getInt(key,defV);
	}
	
	public static boolean putInt(String key,int value){
		return preferences.edit().putInt(key,value).commit();
	}
	
	public static boolean getBoolean(String key,boolean defV){
		return preferences.getBoolean(key,defV);
	}
	
	public static boolean putBoolean(String key,boolean value){
		return preferences.edit().putBoolean(key,value).commit();
	}
	
	public static float getFloat(String key,float defV){
		return preferences.getFloat(key,defV);
	}
	
	public static float getFloat(String key){
		return preferences.getFloat(key,-1);
	}
	
	public static boolean putFloat(String key,float value){
		return preferences.edit().putFloat(key,value).commit();
	}
	
	public static long getLong(String key,long defV){
		return preferences.getLong(key,defV);
	}
	
	public static long getLong(String key){
		return preferences.getLong(key,-1);
	}
	
	public static void putLong(String key,long value){
		preferences.edit().putLong(key,value);
	}
	
	public static Set<String> getStringSet(String key,Set<String> defV){
		return preferences.getStringSet(key,defV);
	}
	
	public static Set<String> getStringSet(String key){
		return preferences.getStringSet(key,null);
	}
	
	public static boolean putStringSet(String key,Set<String> value){
		return preferences.edit().putStringSet(key,value).commit();
	}
	
	public static boolean addToStringSet(String key,String value){
		Set<String> s=getStringSet(key);
		s.add(value);
		return putStringSet(key,s);
	}
	
	
	public static void init(Context c,String tableName,int mode){
		context=c;
		preferences=c.getSharedPreferences(tableName,mode);
		if(getString("isFirstOpen","yes").equals("yes")){
			firstInit();
		}
	}
	
	public static void init(Context c,String tableName){
		init(c,tableName,DEFAUT_MODE);
	}
	
	public static void init(Context c){
		init(c,DEFAUT_TABLE,DEFAUT_MODE);
	}
	
	public static void firstInit(){
		putString("isFirstOpen","no");
		putString("OsuSongsDir",Environment.getExternalStorageDirectory().getAbsolutePath()+"/osu!droid/Songs/");
	}
	
}
