package com.edplan.osu.tool.whathelper.activitys;
import android.animation.*;
import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.widget.*;
import android.widget.AdapterView.*;
import com.edplan.osu.framework.net.BeatmapDownload.*;
import com.edplan.osu.framework.net.BeatmapDownload.Mengsky.*;
import com.edplan.osu.framework.utils.*;
import com.edplan.osu.tool.whathelper.*;
import com.edplan.osu.tool.whathelper.fragments.*;
import com.edplan.osu.tool.whathelper.net.download.*;
import com.edplan.osu.tool.whathelper.test.*;
import com.edplan.osu.tool.whathelper.view.*;
import com.edplan.osu.tool.whathelper.view.adapters.*;
import com.edplan.osu.tool.whathelper.widgets.*;
import com.edplan.task.*;
import com.edplan.task.utils.*;
import com.gc.materialdesign.utils.*;
import com.gc.materialdesign.views.*;
import java.io.*;
import java.net.*;
import material.edplan.animation.interpolators.*;
import org.json.*;

import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.edplan.osu.tool.whathelper.R;
import android.graphics.drawable.*;
import com.edplan.test.*;
import com.edplan.osu.tool.whathelper.net.*;

public class MainMapListPageActivity extends Activity implements SideOptionBar.OnRankStatuOptionChangeListener,SideOptionBar.OnModeOptionChangeListener,SideOptionBar.OnSiteChangeListener
{
	
	
	public RelativeLayout sideOptionBar;
	public View listCover;
	public ImageView optionIcon;
	public float optionIconDefautX;
	public MyListView mapsetList;
	public MapsetListAdapter mAdapter;
	public BeatmapSearchPageRobert pageRobert;
	public View listAll;
	public ButtonFlat searchB;
	public FrameLayout searchFram;
	public EditText searchKeyWordEditText;
	public View topLayout;
	public ProgressBarCenterIndeterminate mainLoadProgressBar;
	public TextView topTitle;
	public ImageView topTitleBackground;
	
	
	
	public Handler handler;
	public static final int SHOW_TOAST=1;
	
	
	
	public SideOptionBarStatu optionBarStatu=SideOptionBarStatu.Hiden;
	
	public void setSideOptionBarStatu(SideOptionBarStatu s){
		optionBarStatu=s;
	}
	
	public enum SideOptionBarStatu{
		Hiden,Showing,Show
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainmaplist);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		
		
		Options.init(this);
		MySystem.init();
		ContextUtils.init(this,new Handler());
		MainTest.main(null);
		//UpdateCheck.checkUpdate(this);
		
		//long t=System.currentTimeMillis();
		//File[] fs=MySystem.getOsuSongsDir().listFiles(FilterCenter.CHEACK_IS_BEATMAPSET_DIR);
		//Log.v("time test","list File cost: "+(System.currentTimeMillis()-t)+"ms");
		
		handler=new Handler(){
			@Override
			public void handleMessage(Message m){
				if(m.what==SHOW_TOAST){
					Toast.makeText(MainMapListPageActivity.this,(String)m.obj,Toast.LENGTH_SHORT).show();
				}
			}
		};
		
		//ActionBar a=getActionBar();
		//a.gett
		
		
		sideOptionBar=(RelativeLayout)findViewById(R.id.sideOptionBar);
		mapsetList=(MyListView)findViewById(R.id.mapsetList);
		mAdapter=new MapsetListAdapter(this,R.layout.entry_beatmaplist,mapsetList);
		
		//LayoutAnimationController listAnimC=new LayoutAnimationController(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
		//mapsetList.setLayoutAnimation(listAnimC);
		
		View v=getWindow().getLayoutInflater().inflate(R.layout.footer_beatmaplist,null,false);
		mapsetList.addFooterView(v);
		final com.gc.materialdesign.views.Button ent=(com.gc.materialdesign.views.Button)findViewById(R.id.sideOptionBarEntrance);
		optionIcon=(ImageView)findViewById(R.id.sideOptionBarEntranceIcon);
		listCover=findViewById(R.id.listCover);
		optionIconDefautX=optionIcon.getX();
		listAll=findViewById(R.id.listAll);
		topLayout=findViewById(R.id.touchBoard);
		mainLoadProgressBar=(ProgressBarCenterIndeterminate)findViewById(R.id.mainLoadProgressBar);
		mainLoadProgressBar.startProgress();
		topTitle=(TextView)findViewById(R.id.siteName);
		topTitleBackground=(ImageView)findViewById(R.id.topTitleBackground);
		
		//optionIcon.set
		
		searchB=(ButtonFlat)findViewById(R.id.searchBackG);
		searchFram=(FrameLayout)findViewById(R.id.searchBarFrame);


		//mapsetList.setAdapter(mAdapter);
		pageRobert=new BeatmapSearchPageRobert(mAdapter);
		pageRobert.setProgresser(mainLoadProgressBar);
		
		try
		{
			pageRobert.addPage();
		}
		catch (MalformedURLException e)
		{}
		catch (UnsupportedEncodingException e)
		{}
		makeSideOptionBar();
		
		searchB.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View p1)
			{
				// TODO: Implement this method
				makeSearchBar();
			}
		});
		
		ent.setOnClickListener(new OnClickListener(){
			boolean b=false;
			@Override
			public void onClick(View p1){
				// TODO: Implement this method
				if(optionBarStatu==SideOptionBarStatu.Showing)return;
				if(optionBarStatu==SideOptionBarStatu.Hiden){
					showSideOptionBar();
				}else if(optionBarStatu==SideOptionBarStatu.Show){
					hideSideOptionBar();
				}
			}
		});
		
		listCover.setOnTouchListener(new OnTouchListener(){
				@Override
				public boolean onTouch(View v, MotionEvent m)
				{
					// TODO: Implement this method
					if(optionBarStatu==SideOptionBarStatu.Show){
						if(m.getX()>v.getX()){
							hideSideOptionBar();
						}
					}
					return false;
				}
		});
		
		
		View moreB=v.findViewById(R.id.moreButton);
		moreB.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					if(false)
					try
					{
						MainMapListPageActivity.this.getMapsetListAdapter().addBeatmapSet(TestUtils.getSamplePageData().data);
						//Log.v("addMap","ok! "+mAdapter.toString());
						return;
					}
					catch (IOException e)
					{
						Log.e("addMap","IO",e);
					}
					catch (JSONException e)
					{
						Log.e("addMap","JSON",e);
					}
					
					
					try
					{
						if(!pageRobert.isLoading())pageRobert.addPage();
					}
					catch (MalformedURLException e)
					{
						Log.e("err",e.getMessage(),e);
					}
					catch (UnsupportedEncodingException e)
					{
						Log.e("err",e.getMessage(),e);
					}
					catch(Exception e){
						Log.e("err",e.getMessage(),e);
					}
					Log.v("event","click moreB");
				}
		});

		
		mapsetList.setOnViewIOListener(new MyListView.OnViewIOListener(){
			
				@Override
				public void onViewIO(AbsListView parent, View view, int position, boolean iOrO) {
					// TODO: Implement this method
					Log.v("view io","in 2: "+position+" view: "+view);
					if(view!=null&&iOrO){
						
						Animation anim=view.getAnimation();
						if(anim!=null){
							anim.cancel();
						}
						Animation a=AnimationUtils.loadAnimation(MainMapListPageActivity.this,android.R.anim.fade_in);
						a.setDuration(400);
						a.setStartOffset(10);
						//view.setAlpha(0);
						view.startAnimation(a);
					}
				}
		});
		
		mapsetList.setOnScrollListener(new AbsListView.OnScrollListener(){
				@Override
				public void onScrollStateChanged(AbsListView view, int state)
				{
					// TODO: Implement this method
					//Log.v("onScroll","change: "+state);
					switch(state){
						case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
							mapsetList.setAlpha(1f);
							break;
						case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
							mapsetList.setAlpha(1);
							break;
						case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
							mapsetList.setAlpha(1);
							break;
						default:
							mapsetList.setAlpha(1);
							break;
					}
				}

				@Override
				public void onScroll(AbsListView p1, int first, int count, int total)
				{
					// TODO: Implement this method
					//Log.v("onScroll","scroll: "+first+"|"+count+"|"+(first+count)+"|"+total);
					if(total-first-count<=5){
						try
						{
							pageRobert.addPage();
						}
						catch (MalformedURLException e)
						{
							Log.e("err load page","url",e);
						}
						catch (UnsupportedEncodingException e){
							Log.e("err load page","url",e);
						}
					}
				}
		});
		
		
		mapsetList.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				{
					// TODO: Implement this method
					//BeatmapSetInfor map= (BeatmapSetInfor) parent.getItemAtPosition(position);
					//TextViewDialog mapInforDialog=new TextViewDialog(MainMapListPageActivity.this,map.getTitle(),map.toString(),true);
					//mapInforDialog.show();
				}
		});
		
		mapsetList.setOnItemLongClickListener(new OnItemLongClickListener(){
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
				{
					// TODO: Implement this method
					final int mapId=((BeatmapSetInfor)mAdapter.getItem(position)).id;
					
					try
					{
						
						//final ProgressBarDialog dialog=new ProgressBarDialog(MainMapListPageActivity.this,"Downloading!",false);
						SimpleFileDownloadTask st=new SimpleFileDownloadTask(pageRobert.site.getSiteCenter().getURLFactory().getBeatmapSetDownloadURL(mapId),
						                                                     MySystem.getOsuSongsDirPath(),
						                                                     SimpleFileDownloadTask.AUTO_URL_PATH);
						st.setDescription("BeatmapSet "+mapId);
						if(pageRobert.site==Site.Mengsky){
							st.setDoForHttpURLConnection(new SimpleFileDownloadTask.DoForHttpURLConnection(){
									@Override
									public void doFor(HttpURLConnection c) {
										// TODO: Implement this method
										c.setRequestProperty("Referer","http://osu.mengsky.net/");
									}
								});
						}
						final DownloadDialog dialog=new DownloadDialog(MainMapListPageActivity.this,"Downloading!",false,st);
						st.runOnThread();
						dialog.show();
						/*
						final ClockTask refreshClock=new ClockTask(new Runnable(){
								@Override
								public void run()
								{
									// TODO: Implement this method
									dialog.setProgress((int)(st.getDownloadProgress() * 100), new Runnable(){
											@Override
											public void run()
											{
												// TODO: Implement this method
												dialog.getProgressTextView().setText(StringUtils.intToMemorySize(st.getDownloadByte())+"/"
													+StringUtils.intToMemorySize(st.getTotalByte()));
											}
									});
									//Log.v("pTask",""+st.getDownloadProgress());
								}
						},200,ClockTask.ENDLESS);
						st.setOnCompleteListener(new Task.OnCompleteListener(){
								@Override
								public void onComplete()
								{
									// TODO: Implement this method
									Message m=new Message();
									m.what=SHOW_TOAST;
									m.obj="Complete download "+mapId;
									m.setTarget(handler);
									m.sendToTarget();
									refreshClock.setFlag(Pauseable.Flag.stop);
									dialog.rootView.post(new Runnable(){
											@Override
											public void run()
											{
												// TODO: Implement this method
												dialog.dismiss();
											}
									});
								}
						});
						st.setOnFailedListener(new Task.OnFailListener(){
								@Override
								public void onFail(Throwable e, Task t) {
									// TODO: Implement this method
									refreshClock.setFlag(Pauseable.Flag.stop);
									Message m=new Message();
									m.what=SHOW_TOAST;
									m.obj="Fail download: "+mapId;
									m.setTarget(handler);
									m.sendToTarget();
								}
						});
						*/
						
						
						Log.v("Stask","start "+mapId);
						Toast.makeText(MainMapListPageActivity.this,"start download "+mapId,Toast.LENGTH_SHORT).show();
						/*
						st.runOnThread();
						refreshClock.runOnThread();
						dialog.show();*/
						//com.gc.materialdesign.widgets.ProgressDialog pDia=new com.gc.materialdesign.widgets.ProgressDialog(MainMapListPageActivity.this,"Download "+mapId);
						
					}
					catch (MalformedURLException e)
					{
						
					}

					return true;
				}
		});
		
		topLayout.setOnTouchListener(new OnTouchListener(){
			
				float firstDownX=0;
				float firstDownSideOptionBarX=0;
				boolean isDragingOut=false;
				boolean isDragingBack=false;
				long dragStartTime;
				
			
				@Override
				public boolean onTouch(View p1, MotionEvent event)
				{
					// TODO: Implement this method
					//Log.v("touch","start "+event.toString());
					//Log.v("view","x: "+sideOptionBar.getX()+"/"+sideOptionBar.getWidth());
					if(event.getAction()==MotionEvent.ACTION_DOWN){
						if(optionBarStatu==SideOptionBarStatu.Hiden&&!isDragingOut&&!isDragingBack){
							if(event.getX()<50){
								//Log.v("touch","down "+event.getX());
								firstDownX=event.getX();
								isDragingOut=true;
								dragStartTime=System.currentTimeMillis();
								return true;
							}
							
						}/*else if(optionBarStatu==SideOptionBarStatu.Show&&!isDragingBack&&!isDragingOut){
							if(event.getX()<sideOptionBar.getWidth()){
								Log.v("touch","down "+event.getX());
								firstDownX=event.getX();
								isDragingBack=true;
								dragStartTime=System.currentTimeMillis();
								return true;
							}
							
						}*/
					}else if(event.getAction()==MotionEvent.ACTION_MOVE){
						if(isDragingOut){
							setSideOptionBarX(event.getX()-firstDownX-sideOptionBar.getWidth()/2);
							return true;
						}/*else if(isDragingBack){
							setSideOptionBarX(event.getX()-firstDownX);
							return true;
						}*/
					}else if(event.getAction()==MotionEvent.ACTION_UP){
						if(isDragingOut){
							//Log.v("touch","up");
							if(event.getX()-firstDownX>sideOptionBar.getWidth()/4||(System.currentTimeMillis()-dragStartTime<700)){
								setSideOptionBarStatu(SideOptionBarStatu.Hiden);
								showSideOptionBar();//(int)(animTime_sb*0.5*(event.getX()-firstDownX)/sideOptionBar.getWidth()));
							}else{
								setSideOptionBarStatu(SideOptionBarStatu.Show);
								hideSideOptionBar();
							}
							isDragingOut=false;
							return true;
						}/*else if(isDragingBack){
							Log.v("touch","up");
							if(firstDownX-event.getX()<sideOptionBar.getWidth()/2&&!(System.currentTimeMillis()-dragStartTime<700)){
								setSideOptionBarStatu(SideOptionBarStatu.Hiden);
								showSideOptionBar();//(int)(animTime_sb*0.5*(event.getX()-firstDownX)/sideOptionBar.getWidth()));
							}else{
								setSideOptionBarStatu(SideOptionBarStatu.Show);
								hideSideOptionBar();
							}
							isDragingBack=false;
							return true;
						}*/
					}
					return false;
				}
		});
	}
	
	
	
	
	public MapsetListAdapter getMapsetListAdapter(){
		return mAdapter;
	}
	
	
	
	
	public ViewGroup sideOptionList;
	public LinearLayout sideDownloadList;
	public SideOptionBar sideOptionBarF;
	
	public void makeSideOptionBar(){
		
		FragmentManager fm=getFragmentManager();
		FragmentTransaction ft=fm.beginTransaction();
		sideOptionBarF=new SideOptionBar(this);
		sideOptionBarF.setOnModeOptionChangeListener(this);
		sideOptionBarF.setOnSiteChangeListener(this);
		ft.replace(R.id.sideFrame,sideOptionBarF);
		ft.commit();
	}
	
	public void makeSearchBar(){
		FragmentManager fm=getFragmentManager();
		if(fm.findFragmentByTag("songsSearchBar")==null){
			SearchBarFragment bf=new SearchBarFragment();
			bf.setOnSearchListener(new SearchBarFragment.OnSearchListener(){
					@Override
					public boolean onSearch(String keyWords)
					{
						// TODO: Implement this method
						try
						{
							pageRobert.search(keyWords);
						}
						catch (MalformedURLException e)
						{
							Log.e("search",e.getMessage(),e);
						}
						catch (UnsupportedEncodingException e)
						{
							Log.e("search",e.getMessage(),e);
						}
						return true;
					}
				});
			FragmentTransaction ft=fm.beginTransaction();
			ft.replace(R.id.searchBarFrame,bf,"songsSearchBar");
			ft.addToBackStack("searchBar");
			ft.commit();
		}else{
			SearchBarFragment bf=(SearchBarFragment)fm.findFragmentByTag("songsSearchBar");
			bf.callOnSearch();
		}
	}

	@Override
	public void onRankStatuOptionChange(boolean[] r)
	{
		// TODO: Implement this method
		pageRobert.hasUnranked=r[0];
		pageRobert.hasRanked=r[1];
		pageRobert.hasApproved=r[2];
		pageRobert.hasQualified=r[3];
		pageRobert.reset();
		try
		{
			pageRobert.addPage();
		}
		catch (UnsupportedEncodingException e)
		{}
		catch (MalformedURLException e)
		{}
	}

	@Override
	public void onModeOptionChange(GameModeSet modes)
	{
		// TODO: Implement this method
		mAdapter.modes=modes;
		pageRobert.reset();
		try
		{
			pageRobert.addPage();
		}
		catch (UnsupportedEncodingException e)
		{}
		catch (MalformedURLException e)
		{}
	}

	@Override
	public void onSiteChange(Site site) {
		// TODO: Implement this method
		pageRobert.changeSite(site);
	}
	
	
	int animTime_sb=180;
	
	public void showSideOptionBar(){
		Log.v("touch","show time: "+(int)Math.abs((float)(animTime_sb*2*sideOptionBar.getX()/sideOptionBar.getWidth())));
		showSideOptionBar((int)Math.abs((float)(animTime_sb*2*sideOptionBar.getX()/sideOptionBar.getWidth())));
	}
	
	public void showSideOptionBar(int animDur){
		//Button anim
		if(optionBarStatu!=SideOptionBarStatu.Hiden)return;
		AnimatorSet buttonAnim=new AnimatorSet();
		ObjectAnimator rotationAnim=new ObjectAnimator();
		rotationAnim.setDuration(animDur)
		            .setFloatValues(new float[]{-180,0});
		rotationAnim.setPropertyName("rotation");
		rotationAnim.setInterpolator(new CubeInterpolator(0.6f));
		ObjectAnimator xAnim=new ObjectAnimator();
		xAnim.setDuration(animDur)
			.setFloatValues(new float[]{-Utils.dpToPx(10,getResources())});
		xAnim.setPropertyName("x");
		buttonAnim.play(xAnim).with(rotationAnim);
		buttonAnim.setTarget(optionIcon);
		buttonAnim.start();
		
		//SideOptionBar anim
		ObjectAnimator barAnim=new ObjectAnimator();
		barAnim.setDuration(animDur)
		       .setFloatValues(0);
		barAnim.setPropertyName("sideOptionBarX");
		//barAnim.setInterpolator(new CubeInterpolator(0.65f));
		barAnim.setInterpolator(new LinearInterpolator());
		barAnim.setTarget(this);
		barAnim.addListener(new Animator.AnimatorListener(){

				@Override
				public void onAnimationStart(Animator p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationEnd(Animator p1)
				{
					// TODO: Implement this method
					setSideOptionBarStatu(SideOptionBarStatu.Show);
				}

				@Override
				public void onAnimationCancel(Animator p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onAnimationRepeat(Animator p1)
				{
					// TODO: Implement this method
				}

			
		});
		sideOptionBar.setVisibility(View.VISIBLE);
		barAnim.start();
		optionBarStatu=SideOptionBarStatu.Showing;
	}
	
	public void hideSideOptionBar(){
		Log.v("view","times: "+(int)Math.abs((float)(animTime_sb*(2*(sideOptionBar.getWidth()/2+sideOptionBar.getX())/sideOptionBar.getWidth()))));
		hideSideOptionBar((int)Math.abs((float)(animTime_sb*(2*(sideOptionBar.getWidth()/2+sideOptionBar.getX())/sideOptionBar.getWidth()))));
	}
	
	public void hideSideOptionBar(int animDur){
		//Button anim
		if(optionBarStatu!=SideOptionBarStatu.Show)return;
		AnimatorSet buttonAnim=new AnimatorSet();
		ObjectAnimator rotationAnim=new ObjectAnimator();
		rotationAnim.setDuration(animDur)
			.setFloatValues(new float[]{180});
		rotationAnim.setPropertyName("rotation");
		rotationAnim.setInterpolator(new CubeInterpolator(0.6f));
		ObjectAnimator xAnim=new ObjectAnimator();
		xAnim.setDuration(animDur)
			.setFloatValues(new float[]{optionIconDefautX});
		xAnim.setPropertyName("x");
		buttonAnim.play(rotationAnim).with(xAnim);
		buttonAnim.setTarget(optionIcon);
		buttonAnim.start();
		
		//SideOptionBar anim
		ObjectAnimator barAnim=new ObjectAnimator();
		barAnim.setDuration(animDur)
			.setFloatValues(-sideOptionBar.getWidth()/2);
		barAnim.setPropertyName("sideOptionBarX");
		//barAnim.setInterpolator(new CubeInterpolator(0.65f));
		barAnim.setInterpolator(new LinearInterpolator());
		barAnim.setTarget(this);
		barAnim.addListener(new Animator.AnimatorListener(){

			@Override
			public void onAnimationStart(Animator p1)
			{
				// TODO: Implement this method
			}
			
			@Override
			public void onAnimationEnd(Animator p1)
			{
				// TODO: Implement this method
				//sideOptionBar.setVisibility(View.GONE);
				setSideOptionBarStatu(SideOptionBarStatu.Hiden);
			}
			
			@Override
			public void onAnimationCancel(Animator p1)
			{
				// TODO: Implement this method
			}
			
			@Override
			public void onAnimationRepeat(Animator p1)
			{
				// TODO: Implement this method
			}
		});
		barAnim.start();
		optionBarStatu=SideOptionBarStatu.Showing;
	}
	
	public void setSideOptionBarX(float x){
		if(x>5)return;
		sideOptionBar.setX(x);
		listAll.setX(2*x+sideOptionBar.getWidth());
		float coverAlpha=2*x/sideOptionBar.getWidth()+1;
		if(coverAlpha<0)coverAlpha=0;
		if(coverAlpha>1)coverAlpha=1;
		listCover.setAlpha(coverAlpha);
		sideOptionBar.setAlpha(coverAlpha);
		topTitle.setTranslationX(sideOptionBar.getWidth()*0.01f*coverAlpha);
		topTitle.setAlpha(1-coverAlpha/10);
		float scale=1+coverAlpha*0.02f;
		topTitleBackground.setPivotX(0);
		topTitleBackground.setPivotY(topTitleBackground.getHeight()*0.54f);
		topTitleBackground.setScaleX(scale);
		topTitleBackground.setScaleY(scale);
		//float scale = this.getResources().getDisplayMetrics().density;
		//topTitleBackground.setCameraDistance(1920*(1-coverAlpha*0.1f)* scale);
		//Log.v("anim","alpha:"+listCover.getAlpha()+" data:"+coverAlpha+" rota:"+optionIcon.getRotation());
	}
	
	public float getSideOptionBarX(){
		return sideOptionBar.getX();
	}

	
	public void testCode(){
		Drawable d=topTitleBackground.getDrawable();
	}
	
	
	
	/*
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		// TODO: Implement this method
		Log.v("touch","start");
		
		
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			if(optionBarStatu==SideOptionBarStatu.Hiden&&!isDragingOut){
				if(event.getX()<200){
					Log.v("touch","down "+event.getX());
					firstDownX=event.getX();
					isDragingOut=true;
				}
			}
		}else if(event.getAction()==MotionEvent.ACTION_MOVE){
			if(isDragingOut){
				setSideOptionBarX(event.getX()-firstDownX-sideOptionBar.getWidth()/2);
			}
		}else if(event.getAction()==MotionEvent.ACTION_UP){
			if(isDragingOut){
				Log.v("touch","up");
				if(event.getX()-firstDownX>sideOptionBar.getWidth()/2){
					setSideOptionBarStatu(SideOptionBarStatu.Hiden);
					showSideOptionBar();
				}else{
					setSideOptionBarStatu(SideOptionBarStatu.Show);
					hideSideOptionBar();
				}
				isDragingOut=false;
			}
		}
		
		return super.onTouchEvent(event);
	}*/
}
