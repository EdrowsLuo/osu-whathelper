package com.edplan.osu.tool.net.download;
import com.edplan.osu.tool.utils.*;

public interface FileDownloader extends Loadable
{
	
	public statu getStatu();
	
	public enum statu{
		Downloading,
		Waiting,
		Completed
	}
	
	//public abstract void startDownload();
	
}
