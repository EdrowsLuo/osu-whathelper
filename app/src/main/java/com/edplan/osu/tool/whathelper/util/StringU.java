package com.edplan.osu.tool.whathelper.util;
import java.io.*;
import android.util.*;

public class StringU
{
	
	public static String tString(InputStream in) throws IOException{
		StringBuilder s=new StringBuilder();
		BufferedReader r=new BufferedReader(new InputStreamReader(in));
		String b="";
		//try {
			while((b=r.readLine())!=null) {
				s.append(b);
			}
		//} catch(IOException e) {
		//	Log.e("string","input er",e);
		//}
		return s.toString();
	}
	
}
