package com.edplan.osu.tool.whathelper;

public class StringUtils
{
	public static String intoTime(int t){
		int m=t/60;
		int s=t%60;
		String r=m+":"+((s<10)?("0"+s):String.valueOf(s));
		return r;
		
	}
	
	public static String intToMemorySize(int i){
		if(i<1000){
			return i+"B";
		}else if(i<1000000){
			return floatToString(((float)i)/1000,4)+"kB";
		}else{
			return floatToString(((float)i)/1000000,4)+"MB";
		}
	}
	
	public static String floatToString(float f,int length){
		//String s=String.valueOf(f)
		String s=String.valueOf(f);
		return s.substring(0,(s.length()<length)?s.length():
			((s.indexOf(".")<=length)?length:(s.indexOf(".")-1)));
	}
	
}
