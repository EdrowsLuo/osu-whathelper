package com.edplan.osu.tool.task;

public class TaskThread extends Thread
{
	final Task task;
	
	public TaskThread(final Task t){
		super(new Runnable(){
				@Override
				public void run()
				{
					// TODO: Implement this method
					t.taskBody.run();
					if(t.completeL!=null)t.completeL.onComplete();
				}
			});
		task=t;
	}
}
