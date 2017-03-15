package com.edplan.task;

import android.util.*;
import com.edplan.task.utils.*;
import java.util.*;

public abstract class Task implements Runnable,ExtraDataSaver,Descripable
{
	public static final int COMPLETE=2;
	
	public String description;
	
	public MessageHandler h;
	public TreeMap<String,Object> extraDatas;
	
	OnCompleteListener completeL;
	OnFailListener failL;
	OnPauseListener pauseL;
	
	public Task(){
		extraDatas=new TreeMap<String,Object>();
	}
	
	public interface MessageHandler{
		public void handlerMessage(Message m);
	}

	@Override
	public void addExtraData(String k, Object o)
	{
		// TODO: Implement this method
		extraDatas.put(k,o);
	}

	@Override
	public Object getExtraData(String k)
	{
		// TODO: Implement this method
		return extraDatas.get(k);
	}

	@Override
	public void setDescription(String s) {
		// TODO: Implement this method
		description=s;
	}

	@Override
	public String getDescription() {
		// TODO: Implement this method
		return description;
	}


	
	
	
	public abstract boolean isCompleted();
	
	
	public final void runOnThread(){
		(new Thread(this)).start();
	}
	
	public void postMessage(Message m){
		if(getMessageHandler()!=null){
			getMessageHandler().handlerMessage(m);
		}
	}
	
	public MessageHandler getMessageHandler(){
		return h;
	}
	
	public void setMessageHandler(MessageHandler h){
		this.h=h;
	}
	
	public void fail(Throwable e){
		Log.e("task","task class: "+this.toString());
		Log.e("task",e.getMessage(),e);
		if(failL!=null)failL.onFail(e,this);
	}
	
	public void complete(){
		if(completeL!=null)completeL.onComplete();
	}
	
	
	public class Message{
		public int what;
		public Object obj;
		public Object[] extra;
		
		public Message(int w,Object o){
			what=w;
			obj=o;
		}
		
		public void sendTo(MessageHandler h){
			if(h!=null)h.handlerMessage(this);
		}
	}
	
	

	public void setOnCompleteListener(OnCompleteListener l){
		completeL=l;
	}

	public interface OnCompleteListener{
		public void onComplete();
	}

	public void setOnFailedListener(OnFailListener l){
		failL=l;
	}

	public interface OnFailListener{
		public void onFail(Throwable e,Task t);
	}

	public void setOnPauseListener(OnPauseListener l){
		pauseL=l;
	}

	public interface OnPauseListener{
		public void onPause();
	}

	
	public enum statu{
		WAITING,RUNNING,FAILED
	}
	
}
