package com.edplan.osu.tool.whathelper;

import android.content.*;
import android.os.*;
import android.widget.*;

public class ContextUtils 
{
	static Handler handler;
	static Context context;
	
	public static void init(Context c,Handler h){
		context=c;
		handler=h;
	}
	
	public static Handler getHandler(){
		return handler;
	}
	
	public static Context getContext(){
		return context;
	}
	
	//Thread safe
	public static void makeToast(final String s,final int time){
		handler.post(new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					Toast.makeText(ContextUtils.getContext(),s,time).show();
				}
		});
	}
}
