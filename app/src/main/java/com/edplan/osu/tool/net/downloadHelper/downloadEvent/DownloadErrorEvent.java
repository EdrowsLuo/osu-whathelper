package com.edplan.osu.tool.net.downloadHelper.downloadEvent;
import com.edplan.osu.tool.net.downloadHelper.*;

public abstract class DownloadErrorEvent<T extends Exception> implements DownloadEvent
{
	public T e;
	
	public DownloadErrorEvent(T e){
		this.e=e;
	}
	
}
