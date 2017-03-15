package com.edplan.osu.tool.net.BeatmapDownload.Mengsky;
import org.json.*;
import java.util.*;
import android.util.*;

public class MengskyPageData
{
	
	/**
	 *对data格式说明:
	 *{
	 *	"data":[#JSONObject:BeatmapSetInfor#,,,],
	 *	"errCode":#int#
	 *	"pageCurrent":#int#
	 *	"pageTotal":#int#
	 *}
	 */
	
	public int errCode;
	public String errMsg;
	public int pageCurrent;
	public int pageTotal;
	public ArrayList<MengskyBeatmapSetInfor> data;
	
	public MengskyPageData(JSONObject pageData) throws JSONException{
		JSONArray data_=pageData.getJSONArray("data");
		data=new ArrayList<MengskyBeatmapSetInfor>();
		for(int i=0;i<data_.length();i++){
			data.add(new MengskyBeatmapSetInfor(data_.getJSONObject(i)));
		}
		errCode=pageData.getInt("errCode");
		errMsg=pageData.optString("errMsg",null);
		
		if(errMsg==null){
			pageCurrent=pageData.getInt("pageCurrent");
			pageTotal=pageData.getInt("pageTotal");
		}else{
			Log.v("test",errMsg);
			pageCurrent=1;
			pageTotal=1;
		}
	}
	
}
