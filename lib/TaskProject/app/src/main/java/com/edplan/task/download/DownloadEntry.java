package com.edplan.task.download;
import java.net.*;

public abstract class DownloadEntry
{
	
	public URL url;
	
	public String outPutDir;
	
	public String fileName;
	
	public long totalByte;
	public long downloadByte;
	
	public DownloadEntry(URL url,String outPutDir,String fileName){
		this.url=url;
		this.outPutDir=outPutDir;
		this.fileName=fileName;
	}
	
	public DownloadEntry(URL url,String outPutDir){
		this(url,outPutDir,null);
	}
	
	public abstract Downloader getDownloader();
	
	public boolean resetDownloader(){
		if(isResetAble()){
			return ((RetryAbleDownloader)getDownloader()).reset();
		}else{
			return false;
		}
	}
	
	public boolean isResetAble(){
		return getDownloader() instanceof RetryAbleDownloader;
	}
	
}
