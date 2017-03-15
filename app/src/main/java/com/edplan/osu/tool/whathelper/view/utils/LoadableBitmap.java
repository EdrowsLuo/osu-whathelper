package com.edplan.osu.tool.whathelper.view.utils;
import com.edplan.osu.tool.utils.*;
import com.edplan.osu.tool.utils.Loadable.*;
import android.graphics.*;

public abstract class LoadableBitmap implements Loadable
{

	
	private Bitmap res;
	
	private boolean completed;
	
	private LoadEventListener l;
	
	
	public Bitmap getBitmap(){
		if(completed){
			return res;
		}else{
			return null;
		}
	}

	@Override
	public void setLoadEventListener(LoadEventListener l_)
	{
		// TODO: Implement this method
		l=l_;
	}

	@Override
	public float getLoadProgress()
	{
		// TODO: Implement this method
		return 0;
	}
	
	@Override
	public void startLoad()
	{
		// TODO: Implement this method
	}

	@Override
	public float pauseLoad()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public float continueLoad()
	{
		// TODO: Implement this method
		return 0;
	}
	

}
