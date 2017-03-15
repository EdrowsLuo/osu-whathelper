package com.edplan.task.staticPart;
import com.edplan.task.*;
import com.edplan.task.utils.*;

public class IOTaskThread
{
	public static ObjectPush waitingPool;
	
	public static TaskThread[] ioThreads;
	
	static{
		waitingPool=new ObjectPush();
	}
	
	public static void postTask(Task t){
		
	}
	
	public static void dropTask(Task t){
		
	}
}
