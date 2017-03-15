package com.edplan.osu.tool.task;
import java.util.*;
import com.edplan.osu.tool.task.Task.*;

public class Task
{
	public static TreeMap<String,Task> taskMap=new TreeMap<String,Task>();
	
	
	String name;
	Runnable taskBody;
	Thread taskThread;
	onCompleteListener completeL;
	OnFailListener failL;
	OnPauseListener pauseL;
	ErrorListener errorL;
	CallBackListener callBackL;
	
	public Task(){
		
	}
	
	public Task(Runnable r,String name_) throws Task.TaskNameAllreadyHasException{
		init(r,name_);
	}
	
	public Task(Runnable r) throws Task.TaskNameAllreadyHasException{
		this(r,r.toString());
	}
	
	public void init(Runnable r,String name_) throws Task.TaskNameAllreadyHasException{
		if(taskMap.containsKey(name_))throw new TaskNameAllreadyHasException(name);
		this.name=name_;
		taskBody=r;
		taskMap.put(name,this);
		taskThread=new TaskThread(this);
	}
	
	public void init(Runnable r) throws Task.TaskNameAllreadyHasException{
		this.init(r,r.toString());
	}
	
	public void start(){
		if(taskThread!=null)
			taskThread.start();
	}
	
	
	public void dropTask(){
		
	}
	
	public void setOnCompleteListener(onCompleteListener l){
		completeL=l;
	}
	
	public interface onCompleteListener{
		public void onComplete();
	}
	
	public void setOnFailedListener(OnFailListener l){
		failL=l;
	}
	
	public interface OnFailListener{
		public void onFail();
	}
	
	public void setOnPauseListener(OnPauseListener l){
		pauseL=l;
	}
	
	public interface OnPauseListener{
		public void onPause();
	}
	
	public void setErrorListener(ErrorListener l){
		errorL=l;
	}
	
	public interface ErrorListener{
		public boolean postError(Throwable e) default false  ;
	}
	
	public void setCallBackListener(CallBackListener l){
		callBackL=l;
	}
	
	public interface CallBackListener{
		public boolean postCallBack(CallBackData data) default true;
	}
	
	
	public class CallBackData<T>{
		T data;
		public CallBackData(T data){
			this.data=data;
		}
		
		public T getData(){
			return data;
		}
	}
	
	public class TaskNameAllreadyHasException extends Exception{
		public TaskNameAllreadyHasException(String name){
			super("TaskName: "+name+" allready has");
		}
	}
	
	
	public abstract class TaskRunnable implements Runnable{
		Task task;
		public TaskRunnable(Task task){
			this.task=task;
		}
		
		public void poatData(CallBackData data){
			if(task.callBackL!=null)task.callBackL.postCallBack(data);
		}
	}
}
