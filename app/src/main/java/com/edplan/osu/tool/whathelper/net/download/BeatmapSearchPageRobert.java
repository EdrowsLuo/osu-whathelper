package com.edplan.osu.tool.whathelper.net.download;
import com.edplan.osu.tool.whathelper.view.adapters.*;
import com.edplan.task.*;
import com.edplan.osu.framework.net.BeatmapDownload.Mengsky.*;
import java.net.*;
import java.io.*;
import com.edplan.task.Task.*;
import android.os.*;
import com.edplan.osu.framework.net.JsonHelper.*;
import org.json.*;
import android.util.*;
import android.widget.*;
import com.edplan.osu.tool.whathelper.*;
import com.edplan.task.utils.*;
import com.edplan.osu.tool.whathelper.view.*;
import com.edplan.osu.framework.utils.*;
import com.edplan.osu.framework.net.BeatmapDownload.*;
import com.edplan.osu.tool.whathelper.util.*;
import com.edplan.debug.Pointer.*;

public class BeatmapSearchPageRobert implements Progresser
{
	public static final int ADD_PAGE=2;
	public static final int SHOW_TOAST=3;
	public static final String TASK_KEY="TaskKey";
	
	
	public MapsetListAdapter mapList;
	
	public Handler handler;
	
	public Site site=Site.Mengsky;
	//Site.Bloodcat;
	public String searchWord="";
	public boolean hasUnranked=true;
	public boolean hasRanked=true;
	public boolean hasApproved=true;
	public boolean hasQualified=true;
	public int pageCurrent=0;
	public int pageTotal=99999;
	public boolean isLoading=false;
	public long searchStartTime;
	public GameModeSet modes;
	public int waitTimeOut=8000;
	
	public long unlockBlockTime=0;
	
	public SmallFileLoadTask task;
	public int taskKey=0;
	
	public ProgressBarCenterIndeterminate progresser;
	
	public BeatmapSearchPageRobert(MapsetListAdapter target){
		mapList=target;
		handler=new Handler(){
			@Override
			public void handleMessage(android.os.Message m){
				if(m.what==ADD_PAGE){
					if(((int)((SmallFileLoadTask)((Object[])m.obj)[1]).getExtraData(TASK_KEY))!=taskKey){
						Log.v("page","ignore load");
						return;
					}
					BeatmapPageData data=(BeatmapPageData)((Object[])m.obj)[0];
					mapList.addBeatmapSet(data.getBeatmapSets());
					Log.v("task","page: "+data.getPageCurrent());
					pageTotal=data.getPageTotal();
					pageCurrent=(data.getPageCurrent()==-1)?(pageCurrent+1):(data.getPageCurrent());
					stopProgress();
					Toast.makeText(mapList.getActivity(),data.getPageCurrent()+"/"+data.getPageTotal(),Toast.LENGTH_SHORT).show();
					if(data instanceof MengskyPageData)
					if(((MengskyPageData)data).errMsg!=null){
						Log.v("bad pageData","errMsg: "+((MengskyPageData)data).errMsg+"errCode: "+((MengskyPageData)data).errCode);
						Toast.makeText(mapList.getActivity(),"errMsg: "+((MengskyPageData)data).errMsg+"errCode: "+((MengskyPageData)data).errCode,Toast.LENGTH_SHORT).show();
					}
				}else if(m.what==SHOW_TOAST){
					//((Toast)m.obj).show();
					Toast.makeText(mapList.getActivity(),(String)m.obj,Toast.LENGTH_SHORT).show();
				}
			}
		};
		reset();
	}
	
	public void block(int blockTime){
		unlockBlockTime=System.currentTimeMillis()+blockTime;
	}
	
	public void unlock(){
		unlockBlockTime=0;
	}
	
	public long getUnlockBlockTime(){
		return unlockBlockTime;
	}
	
	public void resetTaskKey(){
		taskKey+=1;
	}
	
	public void reset(){
		pageCurrent=0;
		pageTotal=-1;
		unlock();
		resetTaskKey();
		mapList.clearAllMapset();
		stopProgress();
	}
	
	public void search(String keyWords) throws MalformedURLException, UnsupportedEncodingException{
		searchWord=keyWords;
		reset();
		addPage();
	}
	
	public void changeSite(Site s){
		site=s;
		reset();
	}
	
	public Site getSite(){
		return site;
	}
	
	public void setProgresser(ProgressBarCenterIndeterminate p){
		progresser=p;
	}

	@Override
	public void startProgress()
	{
		// TODO: Implement this method
		if(progresser!=null){
			progresser.post(new Runnable(){
					@Override
					public void run()
					{
						// TODO: Implement this method
						progresser.startProgress();
					}
			});
		}
	}

	@Override
	public void stopProgress()
	{
		// TODO: Implement this method
		task=null;
		isLoading=false;
		if(progresser!=null){
			progresser.post(new Runnable(){
					@Override
					public void run()
					{
						// TODO: Implement this method
						progresser.stopProgress();
					}
				});
		}
	}

	/*
	@Override
	public float getProgress() {
		// TODO: Implement this method
		return 0;
	}*/
	
	
	
	public void setRobertData(){
		
	}
	
	public Handler getHandler(){
		return handler;
	}
	
	public boolean isLoading(){
		return isLoading;
	}
	
	public boolean addPage() throws MalformedURLException, UnsupportedEncodingException{
		//Log.v("task","try addPage " +pageCurrent);
		//Log.v("test","def: "+Options.getString("def"));
		if(isLoading){
			//Toast.makeText(mapList.getActivity(),"blocking",Toast.LENGTH_LONG).show();
			//Log.v("page","fail add page: isLoading: "+isLoading);
			//PD.p(0,this);
			return false;
		}else if(pageCurrent>=pageTotal&&pageTotal!=-1){
			//PD.p(-1,this);
			Log.v("load page","jump: "+pageCurrent+"|"+pageTotal);
			return false;
		}
		
		//PD.p(1,this);
		
		if(getUnlockBlockTime()>System.currentTimeMillis()){
			//blocking
			//PD.p(2,this);
			return false;
		}
		//PD.p(-2,this);
		URL url=site.getSiteCenter().getURLFactory().getApiBeatmapinfor(searchWord,hasUnranked,hasRanked,hasApproved,hasQualified,pageCurrent+1);
		//MengskyUrlUtils.getApiBeatmapinfor(searchWord,hasUnranked,hasRanked,hasApproved,hasQualified,pageCurrent+1);
		//PD.p(3,this);
		Log.v("url",url.toString()+"|");
		//PD.p(4,this);
		task=new SmallFileLoadTask(url);
		task.setTimeOut(waitTimeOut);
		task.addExtraData(TASK_KEY,taskKey);
		task.setMessageHandler(new Task.MessageHandler(){
				@Override
				public void handlerMessage(Task.Message m)
				{
					// TODO: Implement this method
					if(m.what==Task.COMPLETE){
						//InputStream
						try
						{
							if(((int)((Task)m.extra[0]).getExtraData(TASK_KEY))==taskKey){
								BeatmapPageData data=site.getSiteCenter().parseBeatmapPage(new String((byte[])m.obj) /*StringU.tString((InputStream)m.obj)*/);
								//new BeatmapPageData(BigJsonReader.readJSONObjectFromStream((InputStream)m.obj));
								if(data!=null){
									android.os.Message ms=new android.os.Message();
									ms.what=ADD_PAGE;
									ms.obj=new Object[]{data,m.extra[0]};
									ms.setTarget(getHandler());
									ms.sendToTarget();
								}else
									task.fail(new Exception("parse fail"));
							}
						}
						//catch (JSONException e)
						//{
						//	task.fail(e);
							//Log.e("err",e.getMessage(),e);
						//}
						//catch (IOException e)
						//{
						//	task.fail(e);
							//Log.e("err",e.getMessage(),e);
						//}
						catch(Exception e){
							task.fail(e);
							//Log.e("err",e.getMessage(),e);
						}
					}
				}
		});
		task.setOnFailedListener(new Task.OnFailListener(){
				@Override
				public void onFail(Throwable e,Task t)
				{
					// TODO: Implement this method
					if(((int)t.getExtraData(TASK_KEY))==taskKey){
						return;
					}
					stopProgress();
					block(4000);
					android.os.Message m=new android.os.Message();
					m.what=SHOW_TOAST;
					m.obj="page "+pageCurrent+" load failed! "+e.getMessage();
					m.setTarget(handler);
					m.sendToTarget();
					Log.v("task","fail load "+pageCurrent);
				}
		});
		PD.p(9,this);
		startProgress();
		task.runOnThread();
		isLoading=true;
		PD.p(10,this);
		Toast.makeText(mapList.getActivity(),"loading",Toast.LENGTH_SHORT).show();
		Log.v("task","task start!");
		return true;
	}
	
	
	
}
