package com.edplan.osu.tool.net.download;
import java.net.*;
import java.io.*;
import com.edplan.osu.tool.utils.*;
import android.util.*;
import com.edplan.osu.tool.net.download.FileDownloader.*;

public class HttpFileDownloader implements FileDownloader
{
	public static int DEFAULT_BUFFER_SIZE=1024*128;
	
	public HttpFileDownloadThread downloadThread;
	
	public int bufferSize;
	
	public HttpFileDownloader(URL netFile,String saveFile) throws IOException{
		this(netFile,saveFile,DEFAULT_BUFFER_SIZE);
	}
	
	public HttpFileDownloader(URL netFile,String saveFile,int bufferSize_) throws IOException{
		HttpURLConnection netFileConnection=(HttpURLConnection)netFile.openConnection();
		bufferSize=bufferSize_;
		File f=new File(saveFile);
		Log.v("download","0");
		if(!f.exists()){
			f.createNewFile();
			//Add Listener
		}
		downloadThread=new HttpFileDownloadThread(netFileConnection,new File(saveFile),bufferSize);
		Log.v("download","1");
	}

	@Override
	public FileDownloader.statu getStatu()
	{
		// TODO: Implement this method
		
		return null;
	}
	
	@Override
	public void setLoadEventListener(LoadEventListener l)
	{
		// TODO: Implement this method
		downloadThread.setLoadEventListener(l);
	}

	@Override
	public float getLoadProgress()
	{
		// TODO: Implement this method
		return downloadThread.getProgress();
	}

	@Override
	public void startLoad()
	{
		// TODO: Implement this method
		downloadThread.startDownload();
	}

	@Override
	public float pauseLoad()
	{
		// TODO: Implement this method
		return downloadThread.pauseDownload();
	}

	@Override
	public float continueLoad()
	{
		// TODO: Implement this method
		return downloadThread.continueDownload();
	}
	
	
	public class HttpFileDownloadThread extends Thread
	{
		FileDownloader.statu statu=FileDownloader.statu.Waiting;
		int downloadByteCount=0;
		int downloadFileSize;
		boolean flag=false;
		InputStream httpInput;
		OutputStream fileOut;
		LoadEventListener l=null;
		HttpConnecteEventListener httpListener=null;
		HttpURLConnection con;
		File saveFile;
		
		public HttpFileDownloadThread(HttpURLConnection con_,File save,int bufferSize) throws FileNotFoundException, IOException{
			saveFile=save;
			con=con_;
		}
		
		@Override
		public void run()
		{
			// TODO: Implement this method
			super.run();
			Log.v("download","2");
			try{
				fileOut=new FileOutputStream(saveFile);
				try{
					long t=System.currentTimeMillis();
					httpInput=con.getInputStream();
					if(httpListener!=null)httpListener.connected((int)(System.currentTimeMillis()-t));
					Log.v("download","get connection cost time "+(System.currentTimeMillis()-t)+"ms");
				}catch(UnknownHostException e){
					if(httpListener!=null)httpListener.unkonwHost(e);
					Log.v("exception",e.toString()+" | "+e.getClass().getName());
					return;
				}catch(FileNotFoundException e){
					if(httpListener!=null)httpListener.fileNotFound(e);
					return;
				}
				downloadFileSize=con.getContentLength();
				//Log.v("download","get size "+downloadFileSize);
				byte[] b=new byte[bufferSize];
				int i=0;
				statu=FileDownloader.statu.Downloading;
				do{
					if(!flag){
						try{
							sleep(500);
						}
						catch(InterruptedException e){
							Log.e("err download",e.getMessage(),e);
						}
						continue;
					}
					if(statu!=FileDownloader.statu.Downloading)statu=FileDownloader.statu.Downloading;
					i=httpInput.read(b,0,b.length);
					if(i==-1)break;
					downloadByteCount+=i;
					//Log.v("download","down:"+downloadByteCount);
					fileOut.write(b,0,i);
					fileOut.flush();
					//if(l!=null)l.continueLoad(getProgress());
				}while(i!=-1);
				fileOut.close();
				statu=FileDownloader.statu.Completed;
				if(l!=null)l.completeLoad();
			}catch(Exception e){
				Log.e("err download",e.getMessage(),e);
			}
		}
		
		public void setLoadEventListener(LoadEventListener l_){
			l=l_;
		}
		
		public void setHttpConnecteEventListener(HttpConnecteEventListener cl){
			httpListener=cl;
		}
		
		public float getProgress(){
			return ((float)downloadByteCount)/downloadFileSize;
		}
		
		public FileDownloader.statu getStatu(){
			return statu;
		}
		
		public float pauseDownload(){
			flag=false;
			statu=FileDownloader.statu.Waiting;
			if(l!=null)l.pauseLoad(getProgress());
			return getProgress();
		}
		
		public float continueDownload(){
			flag=true;
			if(l!=null)l.continueLoad(getProgress());
			return getProgress();
		}
		
		public void startDownload(){
			flag=true;
			if(l!=null)l.startLoad();
			start();
		}
		
		public int getDownloadByteCount(){
			return downloadByteCount;
		}
		
		public int getDownloadFileSize(){
			return downloadFileSize;
		}
	}
	
	
	
}
