package com.edplan.osu.tool.net.BeatmapDownload;
import org.json.*;
import java.util.*;

public abstract class BeatmapSetListPageData
{
	

	public int errCode;
	public int pageCurrent;
	public int pageTotal;
	public ArrayList<BeatmapSetInfor> data;
	
	public BeatmapSetListPageData(){
		
	}
	
	public void clearData(){
		data.clear();
	}
	
	public void addPageData(JSONObject page){
		data.addAll(parsePageData(page));
	}
	
	public abstract ArrayList<BeatmapSetInfor> parsePageData(JSONObject page);
}
