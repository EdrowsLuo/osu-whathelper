package com.edplan.osu.tool.net.JsonHelper;
import org.json.*;
import java.io.*;

public class BigJsonReader
{
	
	public static JSONObject readJSONObjectFromStream(InputStream in) throws IOException, JSONException{
		StringBuilder sb=new StringBuilder();
		InputStreamReader r=new InputStreamReader(in);
		char[] c=new char[1024];
		int i;
		while((i = r.read(c, 0, c.length)) != -1){
			sb.append(c,0,i);
		}
		return new JSONObject(sb.toString());
	}
	
	public static JSONArray readJSONArrayFromStream(InputStream in) throws JSONException, IOException{
		StringBuilder sb=new StringBuilder();
		InputStreamReader r=new InputStreamReader(in);
		char[] c=new char[1024];
		int i;
		while((i = r.read(c, 0, c.length)) != -1){
			sb.append(c,0,i);
		}
		return new JSONArray(sb.toString());
	}
	
}
