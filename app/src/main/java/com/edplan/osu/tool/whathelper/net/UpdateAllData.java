package com.edplan.osu.tool.whathelper.net;

import android.util.*;
import org.json.*;

public class UpdateAllData {
	int encoder_version;
	int latest_version_index;
	ArrayMap<String,VersionData> versions;

	public UpdateAllData(JSONObject json){
		latest_version_index=json.optInt("latest-version",-1);
		JSONArray ary=json.optJSONArray("version-description");
		if(ary!=null){
			versions=new ArrayMap<String,VersionData>();
			for(int i=0;i<ary.length();i++){
				VersionData vd=new VersionData(ary.optJSONObject(i));
				versions.put(vd.version_name,vd);
			}
		}
	}

	//public  UpdateAllData parseString(String s){

	//}
}
