package com.edplan.osu.tool.utils;

public abstract class LoadEventListener{

	public abstract void completeLoad();

	public abstract void startLoad();

	public abstract void loading(float progress);

	public abstract void pauseLoad(float progress);

	public abstract void continueLoad(float progress);

	public abstract void loadFailed(float progress);

}
