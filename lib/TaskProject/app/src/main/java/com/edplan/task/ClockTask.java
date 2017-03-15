package com.edplan.task;
import android.util.*;
import java.util.*;
import com.edplan.task.utils.*;
import com.edplan.task.utils.Pauseable.*;

public class ClockTask extends Task implements Pauseable
{
	
	public static final int ENDLESS=-1;
	public static TreeMap<String,ClockTask> allClocks=new TreeMap<String,ClockTask>();
	
	public int sleepTime;
	public int lifeTime;
	public int times;
	public int timesNow;
	public boolean fixTime;
	public SchedulableTask task;
	public Runnable toRun;
	
	public Pauseable.Flag flag;
	
	public ClockTask(Runnable r/*SchedulableTask task*/,String id,int sleepTime,int times){
		flag=Pauseable.Flag.pause;
		//this.task=task;
		toRun=r;
		this.sleepTime=sleepTime;
		this.times=times;
		lifeTime=0;
		timesNow=0;
		allClocks.put(id,this);
	}
	
	public ClockTask(Runnable/*SchedulableTask*/ task,int sleepTime,int times){
		this(task,task.toString(),sleepTime,times);
	}
	
	
	@Override
	public void setFlag(Pauseable.Flag f){
		flag=f;
	}

	@Override
	public Pauseable.Flag getFlag()
	{
		// TODO: Implement this method
		return flag;
	}

	@Override
	public boolean isCompleted()
	{
		// TODO: Implement this method
		return getFlag()==Pauseable.Flag.stop;
	}
	
	
	@Override
	public void run()
	{
		// TODO: Implement this method
		setFlag(Pauseable.Flag.start);
		Thread t=Thread.currentThread();
		while(true){
			try{
				if(flag==Pauseable.Flag.pause){
					t.sleep(sleepTime);
				}else if(flag==Pauseable.Flag.stop){
					if(completeL!=null)completeL.onComplete();
					break;
				}else if(flag==Pauseable.Flag.start){
					toRun.run();
					//Log.v("Clock task","run!");
					timesNow++;
					if(times!=ENDLESS){
						if(timesNow>=times){
							if(completeL!=null)completeL.onComplete();
							break;
						}
					}
					t.sleep(sleepTime);
				}
			}catch(Exception e){
				fail(e);
			}
			
		}
		
	}

	/*
	@Override
	public void fail(Throwable e)
	{
		// TODO: Implement this method
		Log.e("Clock task",e.getMessage(),e);
		if(failL!=null)failL.onFail(e);
	}*/

	
	public abstract class SchedulableTask extends Task
	{
		
	}
	
}
