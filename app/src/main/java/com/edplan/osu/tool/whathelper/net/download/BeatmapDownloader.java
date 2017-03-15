package com.edplan.osu.tool.whathelper.net.download;
import com.edplan.task.*;
import java.io.*;
import java.net.*;
import com.edplan.osu.tool.net.BeatmapDownload.Mengsky.*;
import com.edplan.osu.tool.whathelper.*;

public class BeatmapDownloader extends SimpleFileDownloadTask
{
	public BeatmapDownloader(URL url,File file,int autoMode){
		super(url,file,autoMode);
	}
	
	public BeatmapDownloader(URL url,String f,int autoMode){
		this(url,new File(f),autoMode);
	}
	
	public BeatmapDownloader(URL url,String file){
		this(url,file,SimpleFileDownloadTask.AUTO_URL_PATH);
	}
	
	public BeatmapDownloader(URL url,File save){
		this(url,save,SimpleFileDownloadTask.AUTO_URL_PATH);
	}
	
	public BeatmapDownloader(int mapId) throws MalformedURLException{
		this(MengskyUrlUtils.getBeatmapDownloadURL(mapId),MySystem.getOsuSongsDirPath());
	}
	
}
