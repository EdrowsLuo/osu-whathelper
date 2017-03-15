package com.edplan.osu.tool.whathelper.net;


import org.json.*;

public class VersionData {
	String version_name;
	String version_url;
	int version_code;
	String version_update_time;
	String update_log;
	public VersionData(JSONObject obj){
		version_name=obj.optString("version-name","unkonwn");
		version_code=obj.optInt("version-code",-1);
		version_update_time=obj.optString("version-update-time","1970.12.30 00:00");
		version_url=obj.optString("version-url",null);
		update_log=obj.optString("version-update-log","所以到底更新了什么呢？\n  (｡•ˇ‸ˇ•｡)");
	}
}
