package com.edplan.osu.tool.utils;

public interface Loadable
{
	public void setLoadEventListener(LoadEventListener l);
	
	public float getLoadProgress();
	
	public void startLoad();
	
	public float pauseLoad();
	
	public float continueLoad();
	
}
