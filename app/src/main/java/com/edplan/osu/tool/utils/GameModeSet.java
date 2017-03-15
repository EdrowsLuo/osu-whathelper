package com.edplan.osu.tool.utils;


public class GameModeSet{
	boolean[] hasModeA;
	public GameModeSet(){
		hasModeA=new boolean[GameMode.getModeCount()];
		rest();
	}
	
	public void rest(){
		for(int i=0;i<hasModeA.length;i++){
			hasModeA[i]=false;
		}
	}

	public void addAll(){
		for(int i=0;i<hasModeA.length;i++){
			hasModeA[i]=true;
		}
	}
	
	public void addMode(int m){
		setMode(m,true);
	}
	
	public void setMode(int m,boolean b){
		hasModeA[m]=b;
	}

	public boolean hasMode(int m){
		return hasModeA[m];
	}
}
