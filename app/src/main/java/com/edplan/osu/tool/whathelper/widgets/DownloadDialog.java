package com.edplan.osu.tool.whathelper.widgets;
import android.content.*;
import com.edplan.task.download.*;
import com.edplan.task.*;
import com.edplan.osu.tool.whathelper.*;
import com.edplan.task.utils.*;
import android.widget.*;

public class DownloadDialog extends ProgressBarDialog
{
	public Downloader dld;
	ClockTask refreshClock;
	
	public DownloadDialog(Context c,String title,boolean clickDismiss,Downloader dld){
		super(c,title,clickDismiss);
		this.dld=dld;
		refreshClock=new ClockTask(new Runnable(){
				@Override
				public void run()
				{
					// TODO: Implement this method
					DownloadDialog.this.setProgress((int)(DownloadDialog.this.getDownloader().getDownloadProgress() * 100), new Runnable(){
							@Override
							public void run()
							{
								// TODO: Implement this method
								DownloadDialog.this.getProgressTextView().setText(StringUtils.intToMemorySize(DownloadDialog.this.getDownloader().getDownloadByte())+"/"
																				  +StringUtils.intToMemorySize(DownloadDialog.this.getDownloader().getTotalByte()));
							}
						});
					//Log.v("pTask",""+st.getDownloadProgress());
				}
			},200,ClockTask.ENDLESS);
		getDownloader().addOnCompleteListener(new Downloader.OnCompleteListener(){
				@Override
				public void onComplete()
				{
					// TODO: Implement this method
					/*
					Message m=new Message();
					m.what=SHOW_TOAST;
					m.obj="Complete download "+mapId;
					m.setTarget(handler);
					m.sendToTarget();*/
					refreshClock.setFlag(Pauseable.Flag.stop);
					ContextUtils.getHandler().post(new Runnable(){
							@Override
							public void run()
							{
								// TODO: Implement this method
								Toast.makeText(DownloadDialog.this.context,"Complete download: "+DownloadDialog.this.getDownloader().getDescription(),Toast.LENGTH_SHORT).show();
								DownloadDialog.this.dismiss();
							}
						});
				}
			});
		getDownloader().addFailedListener(new Downloader.FailedListener(){
				@Override
				public void onFailed(Throwable e,Object t) {
					// TODO: Implement this method
					refreshClock.setFlag(Pauseable.Flag.stop);
					
					
					ContextUtils.getHandler().post(new Runnable(){

							@Override
							public void run() {
								// TODO: Implement this method
								Toast.makeText(DownloadDialog.this.context,
								               "Fail download: "+DownloadDialog.this.getDownloader().getDescription(),
											   Toast.LENGTH_SHORT).
											   show();
								
								DownloadDialog.this.dismiss();
							}
					});
				}
			});
			refreshClock.runOnThread();
	}
	
	public Downloader getDownloader(){
		return dld;
	}

	@Override
	public void dismiss() {
		// TODO: Implement this method
		super.dismiss();
	}
}
