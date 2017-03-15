package com.edplan.task.download;
import com.edplan.task.utils.*;

public interface Downloader extends Disposable,ProgressGetter
{
	public enum statu{
		Downloading,Waiting,Failed,Completed
	}
	
	public statu getDownloadStatu();
	
	public boolean startDownload();
	
	public boolean stopDownload();
	
	public float getDownloadProgress();
	
	public int getDownloadByte();
	
	public int getTotalByte();
	
	public String getDescription();
	
	public void setDescription(String d)
	
	public void addFailedListener(FailedListener l);
	
	public void addOnCompleteListener(OnCompleteListener l);
	
	public interface FailedListener{
		public void onFailed(Throwable t,Object ext);
	}
	
	public interface OnCompleteListener{
		public void onComplete();
	}
}
