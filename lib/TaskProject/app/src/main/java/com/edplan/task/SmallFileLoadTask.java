package com.edplan.task;
import java.io.*;
import java.net.*;
import android.util.*;
import java.util.*;
import com.edplan.task.download.*;
import com.edplan.task.download.Downloader.*;

public class SmallFileLoadTask extends Task // implements Downloader
{

	

	
	public int bufferSize=1024;
	public byte[] result;
	
	public PipedInputStream inputStream;
	public PipedOutputStream outputStream;
	
	public URL url;
	public int timeOut=10000;
	public boolean isComp=false;
	
	
	public SmallFileLoadTask(URL url){
		this.url=url;
	}
	
	public SmallFileLoadTask(String s) throws MalformedURLException{
		this.url=new URL(s);
	}
	
	public InputStream getInputStream(){
		return inputStream;
	}
	
	
	@Override
	public void run()
	{
		// TODO: Implement this method
		try
		{
			Log.v("task","try open connection: "+url.toString());
			URLConnection c=url.openConnection();
			Log.v("task","start connect: "+url.toString());
			if(c instanceof HttpURLConnection){
				HttpURLConnection httpConnection=(HttpURLConnection)c;
				httpConnection.setConnectTimeout(timeOut);
				httpConnection.setRequestMethod("GET");
				Log.v("task","start copy");
				Log.v("task","r: "+httpConnection.getResponseCode()+"|"+httpConnection.getResponseMessage());
				Log.v("task","url: "+httpConnection.toString());
				InputStream in=httpConnection.getInputStream();
				Log.v("task","get input "+httpConnection.getContentLength()+"|"+in.available());
				byte[] buffer=new byte[bufferSize];
				int l;
				ByteArrayOutputStream bOut=new ByteArrayOutputStream();
				//outputStream=new PipedOutputStream();
				//inputStream=new PipedInputStream(outputStream,(httpConnection.getContentLength()!=-1)?httpConnection.getContentLength():(int)httpConnection.getContentLengthLong());
				
				Log.v("task","1");
				while((l=in.read(buffer))!=-1){
					Log.v("task","2");
					bOut.write(buffer,0,l);
				}
				Log.v("task","3");
				//outputStream.close();
				//in.close();
				httpConnection.disconnect();
				Log.v("http connection","stream copy finished");
				//old: Message m=new Message(Task.COMPLETE,inputStream);
				result=bOut.toByteArray();
				Message m=new Message(Task.COMPLETE,result);
				m.extra=new Object[]{this};
				postMessage(m);
				if(completeL!=null)completeL.onComplete();
				isComp=true;
			}else{
				Log.v("http connection","can not cast ["+c.toString()+"] to HttpURLConnection");
				fail(new Exception("can not cast ["+c.toString()+"] to HttpURLConnection"));
			}
			
		}
		catch(Exception e){
			fail(e);
		}
	}
	
	public void setTimeOut(int t){
		timeOut=t;
	}

	@Override
	public boolean isCompleted()
	{
		// TODO: Implement this method
		return isComp;
	}
	
	/*
	@Override
	public Downloader.statu getDownloadStatu() {
		// TODO: Implement this method
		return null;
	}

	@Override
	public boolean startDownload() {
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean stopDownload() {
		// TODO: Implement this method
		return false;
	}

	@Override
	public float getDownloadProgress() {
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getDownloadByte() {
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getTotalByte() {
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void addFailedListener(Downloader.FailedListener l) {
		// TODO: Implement this method
	}

	@Override
	public void addOnCompleteListener(Downloader.OnCompleteListener l) {
		// TODO: Implement this method
	}

	@Override
	public void dispose() {
		// TODO: Implement this method
	}

	@Override
	public float getProgress() {
		// TODO: Implement this method
		return 0;
	}
	*/
}
