package com.edplan.task.utils;

public interface Pauseable
{
	public enum Flag{
		stop,pause,start
	}
	
	public Flag getFlag();
	
	public void setFlag(Flag f);
}
