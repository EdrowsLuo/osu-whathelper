package com.edplan.osu.tool.task;

public interface OldTask extends Runnable
{
	public enum statu{
		Waiting,Running,Finished,Failed
	}
	
	public statu getStatu();
	
	public void setStatu(statu s);
	
	public void setTaskEventListener(TaskEventListener l);
	
	public interface TaskEventListener{
		public void onTaskPostEvent(OldTaskEvent event);
	}
}
