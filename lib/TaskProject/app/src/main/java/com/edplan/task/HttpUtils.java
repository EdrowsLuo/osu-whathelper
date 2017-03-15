package com.edplan.task;
import java.net.*;
import java.io.*;
import android.util.*;


public class HttpUtils
{
	
	public static String getHttpConnectionFileName(HttpURLConnection h) throws UnsupportedEncodingException{
		String tmps=h.getHeaderField("Content-Disposition");
		//Log.v("Stask","tmps: "+tmps);
		//Log.v("Stask","tmps index: "+tmps.indexOf("filename")+" | "+(tmps.length()-1));
		
		String tmpStr1=tmps.substring(tmps.indexOf("filename")+10,tmps.length()-1);//获取filename="xxx"中的xxx
		String mTmp=(tmpStr1.indexOf(";")!=-1)? tmpStr1.substring(0,tmpStr1.indexOf(";")-1):tmpStr1.substring(0,tmpStr1.length());
		String tmpStr2=new String(mTmp.getBytes("ISO-8859-1"),"GB2312"); //编码转换，正确识别中文
		//Log.v("Stask","encode: "+tmpStr2);
		return tmpStr2;
	}
	
}
