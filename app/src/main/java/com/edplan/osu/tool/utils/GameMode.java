package com.edplan.osu.tool.utils;

public class GameMode
{
	public static final int STD=0;
	public static final int TAIKO=1;
	public static final int CTB=2;
	public static final int MANIA=3;
	
	public static final int[] allMode=new int[]{STD,TAIKO,CTB,MANIA};
	
	public static int getModeCount(){
		return 4;
	}
	
	public static int getModeId(int i){
		return allMode[i];
	}
	
}
