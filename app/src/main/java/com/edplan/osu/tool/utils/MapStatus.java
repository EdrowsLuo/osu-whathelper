package com.edplan.osu.tool.utils;

public class MapStatus
{
	public static String[] names=
	new String[]{
		"Unrank",
		"Ranked",
		"Approved",
		"Qualified"
	};
	
	public static String getRandStatusName(int id){
		return names[id];
	}
}
