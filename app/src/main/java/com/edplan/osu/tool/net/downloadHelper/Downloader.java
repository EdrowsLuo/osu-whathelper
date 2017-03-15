package com.edplan.osu.tool.net.downloadHelper;

public interface Downloader
{
	public void startDownload();
	
	public void pauseDownload();
	
	public void continueDownload();
	
	public statu getDownloadStatu();
	
	public float getDownloadPercent();
	
	public int getDownloadBytes();
	
	public int getDownloadFileSize();
	
	public void addDownloadEventListener(DownloadEventListener l);
	
	public enum statu{
		Downloading,
		Waiting,
		Stopping,
		Completed,
		Failed
	}
}
