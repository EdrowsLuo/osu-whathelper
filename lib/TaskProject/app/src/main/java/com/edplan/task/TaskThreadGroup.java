package com.edplan.task;
import com.edplan.task.utils.*;
import java.util.*;

public class TaskThreadGroup<T extends Task>
{
	public static int DEFAULT_THREAD_COUNT=5;
	
	public int threadCount=5;
	
	public ObjectPush<T> waitingPool;

	public ArrayList<TaskThread<T>> taskThreads;

	public TaskThreadGroup(){
		this(new ObjectPush<T>());
	}
	
	public TaskThreadGroup(ObjectPush<T> o){
		this(o,DEFAULT_THREAD_COUNT);
	}
	
	public TaskThreadGroup(ObjectPush<T> o,int tcount){
		waitingPool=o;
		threadCount=tcount;
		taskThreads=new ArrayList<TaskThread<T>>(tcount);
		for(int i=0;i<threadCount;i++){
			TaskThread<T> t=new TaskThread<T>(waitingPool);
			t.start();
			taskThreads.add(t);
		}
	}
	
	public ArrayList<TaskThread<T>> getAllThread(){
		return taskThreads;
	}
	
	public TaskThread getTaskThread(int pos){
		return taskThreads.get(pos);
	}
	
	public int getThreadCount(){
		return taskThreads.size();
	}

	public synchronized void postTask(T t){
		waitingPool.addObj(t);
	}

	public synchronized void dropTask(T t){
		waitingPool.push(waitingPool.getIndex(t));
	}
	
	
}
