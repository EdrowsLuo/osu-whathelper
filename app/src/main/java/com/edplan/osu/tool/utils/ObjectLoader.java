package com.edplan.osu.tool.utils;

public class ObjectLoader<T extends Loadable>
{
	private T res;
	private boolean ifNewThread;
	
	public ObjectLoader(T toLoad,boolean ifNewThread_){
		res=toLoad;
		ifNewThread=ifNewThread_;
	}
	
	public void startLoad(){
		
	}
	
	
}
