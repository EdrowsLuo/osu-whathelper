package com.edplan.osu.tool.net.JsonHelper;
import org.json.*;
import java.net.*;
import org.apache.http.*;
import java.io.*;

public class NetJsonHelper
{
	/**
	 *需要在ui线程外使用
	 */
	public static JSONObject getJSONObjectByURL(URL url) throws JSONException, IOException{
		return BigJsonReader.readJSONObjectFromStream(((HttpURLConnection)url.openConnection()).getInputStream());
	}
	
	public static JSONArray getJSONArrayByURL(URL url) throws JSONException, IOException{
		return BigJsonReader.readJSONArrayFromStream(((HttpURLConnection)url.openConnection()).getInputStream());
	}
}
