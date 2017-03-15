package com.edplan.osu.tool.whathelper.view;
import com.gc.materialdesign.views.*;
import android.util.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.animation.*;
import android.view.animation.*;
import com.edplan.task.utils.*;
//import com.nineoldandroids.animation.*;

public class ProgressBarCenterIndeterminate extends ProgressBarDeterminate implements Progresser
{
	boolean isProgressing=false;
	float progress;
	
	float progressSpeed=140;
	
	public ProgressBarCenterIndeterminate(Context c,AttributeSet attr){
		super(c,attr);
		RelativeLayout.LayoutParams layoutParam=(RelativeLayout.LayoutParams)getProgressView().getLayoutParams();
		layoutParam.addRule(RelativeLayout.CENTER_IN_PARENT);
		getProgressView().setLayoutParams(layoutParam);
		setMax(200);
	}
	
	@Override
	public void setProgress(final float progress){
		 super.setProgress(2*progress);
		 getProgressView().setAlpha(//1-getProgress()/100);
		 (float)Math.sin(Math.PI*getProgress()/100));
	}

	@Override
	public float getProgress()
	{
		// TODO: Implement this method
		return super.getProgress()/2;
	}
	
	ObjectAnimator proAnim;
	
	@Override
	public void stopProgress(){
		if(proAnim!=null)proAnim.cancel();
		proAnim=new ObjectAnimator();
		proAnim.setPropertyName("progress");
		proAnim.setDuration((int)(1000*(100-getProgress())/progressSpeed));
		proAnim.setFloatValues(100);
		proAnim.setTarget(this);
		proAnim.setInterpolator(new LinearInterpolator());
		//proAnim.setRepeatMode(ObjectAnimator.INFINITE);
		proAnim.start();
		
		post(new Runnable(){
				@Override
				public void run()
				{
					// TODO: Implement this method
					
				}
		});
	}
	
	@Override
	public void startProgress(){
		if(proAnim!=null)proAnim.cancel();
		proAnim=new ObjectAnimator();
		proAnim.setPropertyName("progress");
		proAnim.setDuration((int)(1000*100/progressSpeed));
		proAnim.setFloatValues(0,100);
		proAnim.setTarget(this);
		proAnim.setInterpolator(new LinearInterpolator());
		proAnim.setRepeatCount(ObjectAnimator.INFINITE);
		proAnim.setRepeatMode(ObjectAnimator.RESTART);
		proAnim.start();
		post(new Runnable(){
				@Override
				public void run()
				{
					// TODO: Implement this method
					
				}
		});
		
	}
	
	/*
	public void setEndProgress(int x){
		
	}
	
	public void setStartProgress(int x){
		
	}*/
	
	public View getProgressView(){
		return progressView;
	}
	
}
