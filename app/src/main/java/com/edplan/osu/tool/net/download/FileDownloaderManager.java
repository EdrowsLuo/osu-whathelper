package com.edplan.osu.tool.net.download;
import java.util.*;

public class FileDownloaderManager
{
	public TreeMap<String,FileDownloader> downloaderMap;
	
	public FileDownloaderManager(){
		downloaderMap=new TreeMap<String,FileDownloader>();
	}
	
	public void addFileDownloader(String name,FileDownloader f){
		downloaderMap.put(name,f);
	}
	
	public void addFileDownloader(FileDownloader f){
		addFileDownloader(f.toString(),f);
	}
	
	public FileDownloader deletFileDownloader(String name){
		return downloaderMap.remove(name);
	}
	
}
