package com.edplan.osu.tool.whathelper.widgets;
import android.content.*;
import com.edplan.osu.tool.whathelper.*;
import android.widget.*;
import android.os.*;
import android.view.*;
import com.gc.materialdesign.views.*;
import android.view.animation.*;
import android.view.View.*;
import com.edplan.task.utils.*;
import com.edplan.osu.tool.whathelper.R;
import com.edplan.task.*;

public class ProgressBarDialog extends MyDialog
{

	public Context context;
	
	public View rootView;
	public View mainView;
	public String title;
	public boolean clickDismiss;
	
	public ProgressGetter progressgetter;
	
	public TextView titleView;
	public ProgressBarDeterminate progressBar;
	public TextView progressTextView;
	
	public ProgressBarDialog(Context c,String title,boolean clickDismiss){
		super(c);
		context=c;
		this.title=title;
		this.clickDismiss=clickDismiss;
	}
	
	public void setProgress(final int i){
		setProgress(i,null);
	}
	
	public void setProgress(final float i,final Runnable toPost){
		if(progressBar!=null)
			progressBar.post(new Runnable(){
					@Override
					public void run()
					{
						// TODO: Implement this method
						progressBar.setProgress(i);
						if(toPost!=null)toPost.run();
					}
			});
	}
	
	
	ClockTask refreshClock;
	public void setProgresser(ProgressGetter p){
		progressgetter=p;
		refreshClock=new ClockTask(new Runnable(){
				@Override
				public void run()
				{
					// TODO: Implement this method
					ProgressBarDialog.this.setProgress(progressgetter.getProgress()*100 , new Runnable(){
							@Override
							public void run()
							{
								// TODO: Implement this method
								ProgressBarDialog.this.getProgressTextView().setText(((int)progressgetter.getProgress()*100)+"%");
							}
						});
					//Log.v("pTask",""+st.getDownloadProgress());
				}
			},200,ClockTask.ENDLESS);
		refreshClock.runOnThread();
	}
	
	public void endClock(){
		if(refreshClock!=null)refreshClock.setFlag(Pauseable.Flag.stop);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progress_bar);
		progressBar=(ProgressBarDeterminate)findViewById(R.id.dialogProgressBar);
		rootView=findViewById(R.id.dialogRootView);
		mainView=findViewById(R.id.dialogMainView);
		progressTextView=(TextView)findViewById(R.id.dialogProgressText);
		((TextView)findViewById(R.id.dialogTitle)).setText(title);
		
		
		rootView.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getX() < mainView.getLeft() 
						|| event.getX() >mainView.getRight()
						|| event.getY() > mainView.getBottom() 
						|| event.getY() < mainView.getTop()) {
						if(clickDismiss)dismiss();
					}
					return false;
				}
			});
		
	}

	@Override
	public void dismiss() {
		// TODO: Implement this method
		super.dismiss();
		endClock();
	}
	
	

	/*
	@Override
	public void dismiss()
	{
		// TODO: Implement this method
		Animation anim=AnimationUtils.loadAnimation(context,R.anim.dialog_main_hide_amination);
		anim.setAnimationListener(new Animation.AnimationListener(){

				@Override
				public void onAnimationStart(Animation p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationEnd(Animation p1)
				{
					// TODO: Implement this method
					mainView.post(new Runnable() {
							@Override
							public void run() {
								ProgressBarDialog.super.dismiss();
							}
						});
				}

				@Override
				public void onAnimationRepeat(Animation p1)
				{
					// TODO: Implement this method
				}

			
		});
		
		rootView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.dialog_root_hide_amin));
		mainView.startAnimation(anim);
		
		//super.dismiss();
	}

	@Override
	public void show()
	{
		// TODO: Implement this method
		super.show();
		rootView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.dialog_root_show_amin));
		mainView.startAnimation(AnimationUtils.loadAnimation(context,R.anim.dialog_main_show_amination));
	}
	*/ 
	
	
	
	public TextView getProgressTextView(){
		return progressTextView;
	}

	@Override
	public View getRootView()
	{
		// TODO: Implement this method
		return rootView;
	}

	@Override
	public View getMainView()
	{
		// TODO: Implement this method
		return mainView;
	}
	
	
}
