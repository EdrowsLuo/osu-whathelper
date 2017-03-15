package com.edplan.task.download;

public interface RetryAbleDownloader extends Downloader
{
	
	public boolean reset();
	
}
