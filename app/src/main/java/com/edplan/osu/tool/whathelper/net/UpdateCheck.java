package com.edplan.osu.tool.whathelper.net;
import android.app.*;
import com.edplan.task.*;
import com.edplan.osu.tool.whathelper.*;
import java.net.*;
import android.util.*;
import org.json.*;
import android.widget.*;
import android.os.*;
import com.edplan.task.Task.*;
import android.content.*;
import android.view.*;

public class UpdateCheck
{
	public static final int SHOW_TOAST=1;
	static Handler handler;
	static{
		handler=new Handler(){
			@Override
			public void handleMessage(android.os.Message msg){
				if(msg.what==SHOW_TOAST){
					Object[] o=(Object[])msg.obj;
					Toast.makeText((Context)o[0],(String)o[1],(o.length<3)?Toast.LENGTH_SHORT:(int)o[2]).show();
				}
			}
		};
	}
	
	public static void checkUpdate(final Activity act,View v){
		try {
			final SmallFileLoadTask st=new SmallFileLoadTask(MySystem.UPDATE_URL);
			st.setOnCompleteListener(new Task.OnCompleteListener(){
					@Override
					public void onComplete() {
						// TODO: Implement this method
						try {
							UpdateAllData ud=new UpdateAllData(new JSONObject(new String(st.result)));
							if(ud.latest_version_index>getVersionIndex()){
								
							}
						} catch(JSONException e) {
							Log.e("update","bad UPDATE file! please check raw in github!",e);
							android.os.Message msg=new android.os.Message();
							msg.what=SHOW_TOAST;
							msg.obj=new Object[]{act,e.getMessage()};
							msg.setTarget(handler);
							msg.sendToTarget();
						}
					}
			});
			st.setMessageHandler(new Task.MessageHandler(){
					@Override
					public void handlerMessage(Task.Message m) {
						// TODO: Implement this method
					}
				});
			// st.runOnThread();
			
		} catch(MalformedURLException e) {
			Log.e("check update","System URL err",e);
			Toast.makeText(act,"err encode sytem url: "+MySystem.UPDATE_URL,Toast.LENGTH_LONG).show();
		}
	}
	
	
	
	private static void showUpdateDialog(Activity act,View v,UpdateAllData ud){
		v.post(new Runnable(){
				@Override
				public void run() {
					// TODO: Implement this method
					
				}
		});
	}
	
	
	public static int getVersionCode(){
		return 1;
	}
	
	public static String getVersionName(){
		return "0.1.1 test";
	}
	
	public static int getVersionIndex(){
		return 5;
	}
	
	public static int getEncoderVersion(){
		return 1;
	}
	
}
