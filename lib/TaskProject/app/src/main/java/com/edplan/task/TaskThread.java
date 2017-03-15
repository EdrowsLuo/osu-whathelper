package com.edplan.task;
import com.edplan.task.utils.*;
import com.edplan.task.utils.Pauseable.*;
import android.util.*;

public class TaskThread<T extends Task> extends Thread implements ForcedPauseable
{
	public int waitingTime=1000;
	
	public Pauseable.Flag flag;
	public ObjectPush<T> waitingTasks;
	
	public TaskThread(ObjectPush<T> w){
		waitingTasks=w;
		flag=Pauseable.Flag.pause;
	}
	
	public TaskThread(){
		this(new ObjectPush<T>());
	}
	
	public ObjectPush<T> getWaitingTasks(){
		return waitingTasks;
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
		super.run();
		flag=Pauseable.Flag.start;
		while(flag!=Pauseable.Flag.stop){
			if(flag==Pauseable.Flag.pause){
				try
				{
					sleep(waitingTime);
				}
				catch (InterruptedException e)
				{}
			}else if(flag==Pauseable.Flag.start){
				T task=waitingTasks.push();
				if(task==null){
					try
					{
						sleep(waitingTime);
					}
					catch (InterruptedException e)
					{}
				}else{
					task.run();
					if(task.isCompleted()){
						onTaskFinished(task);
					}else{
						Object o=task.getExtraData("Retry");
						if(o!=null&&(boolean)o){
							waitingTasks.addObj(task,0);
							Object time=task.getExtraData("Retry_Block_Time");
							int sleepT=waitingTime;
							if(time==null){
								sleepT=time;
							}
							try
							{
								sleep(sleepT);
							}
							catch (InterruptedException e)
							{}
						}
					}
					
				}
			}
		}
		Log.v("TaskThread","ThreadStop: "+this.toString());
	}
	
	public void onTaskFinished(T t){
		
	}
	
	public void onTaskFail(T t){
		
	}
	
	@Override
	public void setFlag(Pauseable.Flag f)
	{
		// TODO: Implement this method
		flag=f;
	}

	@Override
	public Pauseable.Flag getFlag()
	{
		// TODO: Implement this method
		return flag;
	}

	@Override
	public void awake()
	{
		// TODO: Implement this method
		interrupt();
	}
}
