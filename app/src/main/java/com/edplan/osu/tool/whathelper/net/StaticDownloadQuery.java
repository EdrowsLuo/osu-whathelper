package com.edplan.osu.tool.whathelper.net;
import com.edplan.task.download.*;

public abstract class StaticDownloadQuery
{
	public static DownloadQuery<String> q;
	
	static{
		q=new DownloadQuery<String>();
		q.startRun();
	}
	
	public static DownloadQuery get(){
		return q;
	}
	
	public static void addDownloader(DownloadEntry d,boolean ifWait){
		get().addDownloader(d.toString(),d,ifWait);
	}
}
