package com.edplan.osu.tool.net.downloadHelper.downloadEvent;
import com.edplan.osu.tool.net.downloadHelper.*;

public class DownloadErrorEvent_bin implements DownloadEvent
{
	public static final String name="DownloadErrorEvent";

	public Exception e;
	
	public DownloadErrorEvent_bin(Exception e){
		this.e=e;
	}
	
	@Override
	public String getEventType()
	{
		// TODO: Implement this method
		return name;
	}
	
	public Exception getException(){
		return e;
	}
	
}
