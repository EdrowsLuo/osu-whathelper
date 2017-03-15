package com.edplan.task.download;
import java.util.*;
import android.util.*;
import com.edplan.task.utils.*;
import com.edplan.task.download.Downloader.*;

public class DownloadQuery<K> implements Disposable{
	
	
	public static final int UPDATE_EVENT_UNKOWN=0;
	public static final int UPDATE_EVENT_ADD=1;
	public static final int UPDATE_EVENT_POOLCHANGE_WAITING=2;
	public static final int UPDATE_EVENT_POOLCHANGE_COMPLETE=3;
	public static final int UPDATE_EVENT_POOLCHANGE_DOWNLOADING=4;
	public static final int UPDATE_EVENT_POOLCHANGE_FAILED=4;
	public static final int POOL_IN=1;
	public static final int POOL_OUT=2;
	
	
	private ObjectPush<K> waitingPool;
	private ObjectPush<K> completePool;
	private ObjectPush<K> downloadingPool;
	private ObjectPush<K> failedPool;
	
	private ArrayMap<K,DownloadEntry> downloaders;
	private ControllerThread cThread;
	
	private ArrayMap<String,OnUpdateListener> updateListeners;
	
	public DownloadQuery(){
		init();
	}
	
	public void init(){
		downloaders=new ArrayMap<K,DownloadEntry>();
		waitingPool=new ObjectPush<K>();
		completePool=new ObjectPush<K>();
		failedPool=new ObjectPush<K>();
		downloadingPool=new ObjectPush<K>();
		updateListeners=new ArrayMap<String,OnUpdateListener>();
		cThread=new ControllerThread(this);
	}
	
	public void startRun(){
		cThread.start();
	}
	
	public int getDownloadingCount(){
		int count=0;
		int ind=waitingPool.getObjectCount();
		for(int i=0;i<=ind-1;i++){
			if(getDownloadEntry(waitingPool.getEAtIndex(i)).getDownloader().getDownloadStatu()==Downloader.statu.Downloading){
				count++;
			}
		}
		return count;
	}
	
	
	public void addDownloader(K key,DownloadEntry d,boolean ifWait){
		boolean hasK=downloaders.containsKey(key);
		downloaders.put(key,d);
		if(d.getDownloader().getDownloadStatu()==Downloader.statu.Completed){
			addToCompletePool(key);
		}else if(d.getDownloader().getDownloadStatu()==Downloader.statu.Waiting){
			if(ifWait){
				addToWaitingPool(key);
			}
		}else if(d.getDownloader().getDownloadStatu()==Downloader.statu.Downloading){
			addToDownloadingPool(key);
		}else if(d.getDownloader().getDownloadStatu()==Downloader.statu.Failed){
			addToFailedPool(key);
		}
	}
	
	public DownloadEntry getDownloadEntry(K key){
		return downloaders.get(key);
	}
	
	public ObjectPush<K> getWaitingPool(){
		return waitingPool;
	}
	
	public ObjectPush<K> getCompletePool(){
		return completePool;
	}
	
	public ObjectPush<K> getDownloadingPool(){
		return downloadingPool;
	}
	
	public ControllerThread getControllerThread(){
		return cThread;
	}
	
	private boolean addToWaitingPool(K k){
		waitingPool.addObj(k);
		onUpdate(UPDATE_EVENT_POOLCHANGE_WAITING,new Object[]{k,POOL_IN});
		return true;
	}
	
	private boolean addToCompletePool(K k){
		completePool.addObj(k);
		onUpdate(UPDATE_EVENT_POOLCHANGE_COMPLETE,new Object[]{k,POOL_IN});
		return true;
	}
	
	private boolean addToFailedPool(K k){
		failedPool.addObj(k);
		onUpdate(UPDATE_EVENT_POOLCHANGE_FAILED,new Object[]{k,POOL_IN});
		return true;
	}
	
	private boolean addToDownloadingPool(K k){
		downloadingPool.addObj(k);
		onUpdate(UPDATE_EVENT_POOLCHANGE_DOWNLOADING,new Object[]{k,POOL_IN});
		return true;
	}
	
	private boolean quitWaitingPool(K key){
		waitingPool.push(waitingPool.getIndex(key));
		onUpdate(UPDATE_EVENT_POOLCHANGE_WAITING,new Object[]{key,POOL_OUT});
		return true;
	}
	
	public void addOnUpdateListener(OnUpdateListener l,String name){
		updateListeners.put(name,l);
	}
	
	public void addOnUpdateListener(OnUpdateListener l){
		addOnUpdateListener(l,l.toString());
	}
	
	public void onUpdate(int eventCode,Object data){
		for(Map.Entry<String,OnUpdateListener> k:updateListeners.entrySet()){
			if(k.getValue().isEnable()){
				k.getValue().onUpdate(this,eventCode,data);
			}
		}
	}
	
	public void onUpdate(int eventCode){
		onUpdate(eventCode,null);
	}

	@Override
	public void dispose()
	{
		// TODO: Implement this method
		init();
	}
	
	public abstract class OnUpdateListener{
		protected boolean enable=true;
		
		
		/**
		 *@param data:
		 *    poolchange: [(K)changeKey,(boolean)inOrOut]
		 */
		public abstract void onUpdate(DownloadQuery d,int eventCode,Object data);
		
		public void setEnable(boolean b){
			enable=b;
		}
		
		public void disable(){
			setEnable(false);
		}
		
		public void enable(){
			setEnable(true);
		}
		
		public boolean isEnable(){
			return enable;
		}
	}
	
	
	public class ControllerThread extends Thread{
		
		public int sleepTime=500;
		public int maxDownloaderCount=3;
		
		public Flag flag=Flag.Pause;
		
		
		public enum Flag{
			Stop,Pause,Continue,Dead
		}
		
		public DownloadQuery query;
		public ObjectPush<K> waitingPool;
		
		public ControllerThread(DownloadQuery d){
			query=d;
			waitingPool=query.getWaitingPool();
		}

		@Override
		public void run()
		{
			// TODO: Implement this method
			super.run();
			//try
			//{
				while(flag!=null){
					try{
						sleep(sleepTime);
					}catch(InterruptedException e){
						
					}
					if(flag==Flag.Pause){
						
					}else if(flag==Flag.Continue){
						update();
					}else if(flag==Flag.Stop){
						break;
					}
				}
			//}
			//catch (InterruptedException e)
			//{
				
			//}
			Log.v("download query",query.toString()+"thread stop!");
		}
		
		public synchronized void update(){
			int c=waitingPool.getObjectCount();
			for(int i=0;i<=c-1;i++){
				K k=waitingPool.getEAtIndex(i);
				DownloadEntry d=query.getDownloadEntry(k);
				if(d.getDownloader().getDownloadStatu()==Downloader.statu.Completed){
					query.quitWaitingPool(k);
					query.addToCompletePool(k);
				}else if(d.getDownloader().getDownloadStatu()==Downloader.statu.Waiting){
					if(getDownloadingCount()<maxDownloaderCount){
						d.getDownloader().startDownload();
					}
				}else if(d.getDownloader().getDownloadStatu()==Downloader.statu.Failed){
					query.quitWaitingPool(k);
					query.addToFailedPool(k);
				}
			}
		}
		
		public void setFlag(Flag f){
			flag=f;
		}
		
	}
	
	public class DownloaderOnComplete implements Downloader.OnCompleteListener{
		@Override
		public void onComplete(){
			// TODO: Implement this method
		}
	}
	
}
