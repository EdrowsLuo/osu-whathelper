package com.edplan.osu.tool.task;


public class OldTaskThread<T extends OldTask> extends Thread
{
	T task;
	//Task.statu statu=Task.statu.Waiting;
	
	public OldTaskThread(T task){
		this.task=task;
		this.task.setStatu(OldTask.statu.Waiting);
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
		super.run();
		task.setStatu(OldTask.statu.Running);
		task.run();
	}
}
