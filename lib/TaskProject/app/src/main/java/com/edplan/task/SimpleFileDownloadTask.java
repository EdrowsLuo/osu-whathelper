package com.edplan.task;
import java.net.*;
import java.io.*;
import android.util.*;
import java.util.*;
import com.edplan.task.download.*;
import com.edplan.task.download.Downloader.*;

public class SimpleFileDownloadTask extends Task implements Downloader
{

	

	/**
	 *本Task不提供续传，仅单线程
	 */
	
	
	public int bufferSize=1024*32;
	public int timeOut=10000;
	public int fileAutoMode;
	
	public URL url;
	public File defaultFile;
	public File saveFile;
	public int totalByte;
	public int downloadByte;
	
	public boolean flag=true;
	
	public ArrayList<Downloader.OnCompleteListener> downloader_completeL;
	public ArrayList<Downloader.FailedListener> downloader_failL;
	public Downloader.statu downloader_statu=Downloader.statu.Waiting;
	
	public static final int AUTO_DEFAULT=1;
	public static final int AUTO_URL_PATH=2;
	
	
	public SimpleFileDownloadTask(URL url,String f,int autoMode){
		this(url,new File(f),autoMode);
	}
	
	public SimpleFileDownloadTask(URL url,File file,int autoMode){
		this.fileAutoMode=autoMode;
		this.url=url;
		defaultFile=file;
		downloadByte=0;
		downloader_completeL=new ArrayList<Downloader.OnCompleteListener>();
		downloader_failL=new ArrayList<Downloader.FailedListener>();
		Log.v("Stask","url path: "+url.getPath());
		Log.v("Stask","url file: "+url.getFile());
		Log.v("Stask","url userInfor: "+url.getUserInfo());
		Log.v("Stask","url author: "+url.getAuthority());
	}
	
	public SimpleFileDownloadTask(URL url,String file){
		this(url,file,AUTO_DEFAULT);
	}
	
	public SimpleFileDownloadTask(URL url,File save){
		this(url,save,AUTO_DEFAULT);
	}

	@Override
	public boolean isCompleted()
	{
		// TODO: Implement this method
		return downloader_statu==Downloader.statu.Completed;
	}

	
	
	
	@Override
	public void run()
	{
		// TODO: Implement this method
		try
		{
			downloader_statu=Downloader.statu.Downloading;
			URLConnection c=url.openConnection();
			//c.setRequestProperty("Referer","http://osu.mengsky.net/");
			if(c instanceof HttpURLConnection){
				HttpURLConnection httpConnection=(HttpURLConnection)c;
				if(doForC!=null)doForC.doFor(httpConnection);
				httpConnection.setConnectTimeout(timeOut);
				totalByte=httpConnection.getContentLength();
				
				//Log.v("Stask","http connection content type: "+httpConnection.getContentType());
				//Log.v("Stask","http connection content encoding: "+httpConnection.getContentEncoding());
				//Log.v("Stask","http connection content header Content-Disposition: "+httpConnection.getHeaderField("Content-Disposition"));
				
				/*
				//get all headers
				Map<String, List<String>> map = httpConnection.getHeaderFields();
				for (Map.Entry<String, List<String>> entry : map.entrySet()) {
					Log.v("Stask","Key : " + entry.getKey() + 
									   " ,Value : " + entry.getValue());
					if(entry.getKey()!=null&&entry.getKey().equals("Content-Disposition")){
						String tmps=httpConnection.getHeaderField("Content-Disposition");
						Log.v("Stask","tmps: "+tmps);
						Log.v("Stask","tmps index: "+tmps.indexOf("filename")+" | "+(tmps.length()-1));
						String tmpStr1=tmps.substring(tmps.indexOf("filename")+10, tmps.length()-1); //获取filename="xxx"中的xxx
						String tmpStr2=new String(tmpStr1.getBytes("ISO-8859-1"), "GB2312"); //编码转换，正确识别中文
						//System.out.println(tmpStr2);
						
					}
				}
				

				//get header by 'key'
				String server = httpConnection.getHeaderField("Server");
				Log.v("Stask","http head server: "+server);
				*/
				
				
				
				Log.v("Stask","start copy");
				InputStream in=httpConnection.getInputStream();
				Log.v("Stask","get input "+httpConnection.getContentLength());
				byte[] buffer=new byte[bufferSize];
				int l;
				String fileName=HttpUtils.getHttpConnectionFileName(httpConnection);
				switch(fileAutoMode){
					case AUTO_URL_PATH:
						saveFile=new File(defaultFile.getAbsolutePath()+"/"+fileName+".dltmp");
						break;
					
					case AUTO_DEFAULT:
					default:
						saveFile=defaultFile;
						break;
				}
				
				Log.v("task",saveFile.getAbsolutePath());
				if(!saveFile.exists())saveFile.createNewFile();
				if(saveFile.exists()&&saveFile.isFile()&&saveFile.canWrite()){
					OutputStream out=new FileOutputStream(saveFile);
					long t=System.currentTimeMillis();
					while((l=in.read(buffer))!=-1){
						if(!flag){
							fail(new Exception("stop download"));
							return;
						}
						out.write(buffer,0,l);
						downloadByte+=l;
					}
					Log.v("SdownLoad","cost time: "+(System.currentTimeMillis()-t)+"ms");
					out.close();
					in.close();
					
					if(fileAutoMode==AUTO_URL_PATH){
						if(saveFile.renameTo(new File(defaultFile.getAbsolutePath()+"/"+fileName))){
							//if(completeL!=null)completeL.onComplete();
							complete();
							return;
						}else{
							fail(new Exception("rename err: ["+saveFile.toString()+"] to ["+defaultFile.getAbsolutePath()+"/"+fileName+"]"));
							//if(failL!=null)failL.onFail();
						}
					}
					downloader_statu=Downloader.statu.Completed;
					complete();
					//if(completeL!=null)completeL.onComplete();
				}else{
					Log.v("Stask","file err: "+saveFile.toString());
					fail(new Exception("bad File: "+saveFile.toString()));
				}
			}else{
				Log.v("http connection","can not cast ["+c.toString()+"] to HttpURLConnection");
				fail(new Exception("connection err"));
			}
		}
		catch (Exception e)
		{
			Log.e("err",e.getMessage(),e);
			fail(e);
			//if(failL!=null)failL.onFail(e);
		}

	}

	@Override
	public void fail(Throwable e)
	{
		// TODO: Implement this method
		super.fail(e);
		downloader_statu=Downloader.statu.Failed;
		//if(downloader_failL!=null)downloader_failL.onFailed(e,this);
		for(int i=0;i<downloader_failL.size();i++){
			if(downloader_failL.get(i)!=null)downloader_failL.get(i).onFailed(e,this);
		}
	}

	@Override
	public void complete() {
		// TODO: Implement this method
		super.complete();
		for(int i=0;i<downloader_completeL.size();i++){
			if(downloader_completeL.get(i)!=null)downloader_completeL.get(i).onComplete();
			Log.v("test down",i+" Completed");
		}
	}
	
	public DoForHttpURLConnection doForC;
	
	public void setDoForHttpURLConnection(DoForHttpURLConnection d){
		doForC=d;
	}
	
	public interface DoForHttpURLConnection{
		public void doFor(HttpURLConnection c);
	}
	
	public float getDownloadProgress(){
		return (totalByte==0)?0:((float)downloadByte)/totalByte;
	}
	
	
	public int getDownloadByte(){
		return downloadByte;
	}
	
	public int getTotalByte(){
		return totalByte;
	}

	@Override
	public float getProgress() {
		// TODO: Implement this method
		return getDownloadProgress();
	}


	
	
	@Override
	public Downloader.statu getDownloadStatu()
	{
		// TODO: Implement this method
		return null;
	}
	
	@Override
	public boolean startDownload()
	{
		// TODO: Implement this method
		if(getDownloadStatu()!=Downloader.statu.Waiting){
			return false;
		}else{
			downloader_statu=Downloader.statu.Downloading;
			this.runOnThread();
			return true;
		}
	}

	@Override
	public boolean stopDownload()
	{
		// TODO: Implement this method
		if(getDownloadStatu()==Downloader.statu.Downloading){
			flag=false;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void addFailedListener(Downloader.FailedListener l)
	{
		// TODO: Implement this method
		downloader_failL.add(l);
	}

	@Override
	public void addOnCompleteListener(Downloader.OnCompleteListener l)
	{
		// TODO: Implement this method
		downloader_completeL.add(l);
	}

	@Override
	public void dispose()
	{
		// TODO: Implement this method
		
	}
	
}
