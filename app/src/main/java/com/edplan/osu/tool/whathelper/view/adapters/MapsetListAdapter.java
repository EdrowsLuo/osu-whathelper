package com.edplan.osu.tool.whathelper.view.adapters;
import com.edplan.osu.tool.whathelper.R;
import android.widget.*;
import android.view.*;
import android.database.*;
import android.content.*;
import java.util.*;
import com.edplan.osu.framework.net.BeatmapDownload.*;
import android.app.*;
import com.edplan.osu.tool.task.*;
import android.graphics.*;
import java.net.*;
import com.edplan.osu.tool.task.Task.*;
import java.io.*;
import android.util.*;
import com.edplan.osu.framework.net.BeatmapDownload.Mengsky.*;
import android.os.*;
import com.edplan.osu.framework.utils.*;
import com.edplan.osu.tool.whathelper.*;
import android.view.animation.*;

public class MapsetListAdapter extends ViewCatchableAdapter
{
	
	public static final int UPDATE_COVER=1;
	
	public ListView listView;
	
	public Activity mActivity;
	public int resid;
	public ArrayList<BeatmapSetInfor> mapsets;
	public Handler handler;
	public TreeMap<Integer,Bitmap> covers;
	public Bitmap DEFAULT_COVER;
	
	public BeatMapFilter mapFilter;

	public GameModeSet modes;
	
	
	public interface BeatMapFilter{
		public boolean accept(BeatmapSetInfor b);
	}
	
	public MapsetListAdapter(Activity c,int resource,ListView listView){
		//super(c,mapsets,resource,null,null);
		mActivity=c;
		resid=resource;
		this.listView=listView;
		DEFAULT_COVER=BitmapFactory.decodeResource(mActivity.getResources(),R.drawable.default_bg);
		mapsets=new ArrayList<BeatmapSetInfor>();
		covers=new TreeMap<Integer,Bitmap>();
		modes=new GameModeSet();
		//modes.addAll();
		handler=new Handler(){
			@Override
			public void handleMessage(Message m){
				if(m.what==UPDATE_COVER){
					int pos=(int)((Object[])m.obj)[2];
					int mapId=(int)((Object[])m.obj)[1];
					Bitmap map=(Bitmap)((Object[])m.obj)[0];
					if(map!=null){
						covers.put(pos,map);
						MapsetListAdapter.this.notifyDataSetChanged();
						//cover.setImageBitmap(map);
						//Log.v("cover","update cover: "+cover.toString()+"  image:"+map.toString());
						}
				}
			}
		};
		listView.setAdapter(this);
		testInit();
	}
	
	public void testInit(){
		modes.addMode(GameMode.STD);
		mapFilter = new BeatMapFilter(){
			@Override
			public boolean accept(BeatmapSetInfor b)
			{
				// TODO: Implement this method
				for(int i=0;i<GameMode.getModeCount();i++){
					if(modes.hasMode(GameMode.getModeId(i))){
						if(b.getModes().hasMode(GameMode.getModeId(i))){
							return true;
						}
					}
				}
				return false;
			}
		};
	}
	
	public Activity getActivity(){
		return mActivity;
	}
	
	public ListView getListView(){
		return listView;
	}
	
	public void addBeatmapSet(BeatmapSetInfor si){
		if((mapFilter!=null)? mapFilter.accept(si):true){
			mapsets.add(si);
			this.notifyDataSetChanged();
			
		}
	}
	
	public void addBeatmapSet(List<? extends BeatmapSetInfor> maps){
		for(BeatmapSetInfor s:maps){
			addBeatmapSet(s);
		}
		//mapsets.addAll(maps);
	}
	
	public void clearAllMapset(){
		mapsets.clear();
		covers.clear();
		this.notifyDataSetInvalidated();
	}
	
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return mapsets.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO: Implement this method
		return mapsets.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO: Implement this method
		return mapsets.get(position).hashCode();
	}
	
	public ArrayList<Integer> getVisibleViewPosition(){
		
		return null;
	}

	public View newInView(final int position, View convertView, ViewGroup parent){
		
		
		
		return convertView;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO: Implement this method
		super.notifyDataSetChanged();
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// TODO: Implement this method
		boolean update=false;
		Log.v("cover","call getView p: "+position);
		if(convertView==null){
			Log.v("cover","new! position: "+position); 
			
			convertView=mActivity.getWindow().getLayoutInflater().inflate(resid,parent,false);
			update=true;
		}
		/*
		if((getListView().getY()>=convertView.getY()&&getListView().getY()<=convertView.getBottom())
		   ||(getListView().getBottom()>=convertView.getY()&&getListView().getBottom()<=convertView.getBottom())){
			Animation anim=convertView.getAnimation();
			if(anim!=null){
				anim.cancel();
			}
			//getListView().seto
			Animation a=AnimationUtils.loadAnimation(getActivity(),android.R.anim.fade_in);
			a.setDuration(1000);
			convertView.startAnimation(a);
		}*/
		TextView title=(TextView)convertView.findViewById(R.id.mapsetTitle);
		TextView mapsetId=(TextView)convertView.findViewById(R.id.mapsetId);
		TextView artist=(TextView)convertView.findViewById(R.id.artist);
		TextView mapper=(TextView)convertView.findViewById(R.id.mapperName);
		TextView bpm=(TextView)convertView.findViewById(R.id.bpm);
		TextView length=(TextView)convertView.findViewById(R.id.length);
		TextView rankStatu=(TextView)convertView.findViewById(R.id.rankStatu);
		TextView syncedDateTime=(TextView)convertView.findViewById(R.id.syncedDateTime);
		final BeatmapSetInfor infor=(BeatmapSetInfor)getItem(position);
		title.setText(infor.getTitle());
		mapsetId.setText(infor.getBeatmapSetId()+"");
		artist.setText("Artist: "+infor.getArtistU());
		mapper.setText("Creator: "+infor.getCreator());
		rankStatu.setText(infor.getRankedStatusName());
		String bpmS=String.valueOf(infor.getBpm());
		bpm.setText("BPM: "+bpmS.substring(0,(bpmS.length()<=5)?bpmS.length():5));
		length.setText("Length: "+StringUtils.intoTime(infor.getLength()));
		syncedDateTime.setText(infor.getSyncedDateTime());
		
		View[] modeImages=new View[]{
			convertView.findViewById(R.id.modeImage_STD),
			convertView.findViewById(R.id.modeImage_TAIKO),
			convertView.findViewById(R.id.modeImage_CTB),
			convertView.findViewById(R.id.modeImage_MANIA)
			};
		
		int i;
		for(i=0;i<GameMode.getModeCount();i++){
			if(!infor.getModes().hasMode(GameMode.getModeId(i))){
				modeImages[i].setVisibility(View.GONE);
				//Log.v("mode",infor.titleU+" mode "+i+" gone");
			}else{
				modeImages[i].setVisibility(View.VISIBLE);
			}
		}
		
		
		final ImageView cover=(ImageView)convertView.findViewById(R.id.beatmapCover);
		if(DEFAULT_COVER==null){
			
		}
		if(covers.containsKey(position)){
			cover.setImageBitmap(covers.get(position));
		}else{
			cover.setImageBitmap(DEFAULT_COVER);
			try
			{
				final TestDownloadTask task=new TestDownloadTask(MengskyUrlUtils.getBeatmapSetIconLargeURL(infor.getBeatmapSetId()));
				task.setOnCompleteListener(new Task.onCompleteListener(){
						@Override
						public void onComplete()
						{
							// TODO: Implement this method
							Message massage=new Message();
							massage.what=UPDATE_COVER;
							massage.obj=new Object[]{task.image,infor.getBeatmapSetId(),position};
							handler.sendMessage(massage);
							//Log.v("cover","send Message: cover["+cover.toString()+"]  image["+task.image+"] mapid:"+infor.getBeatmapSetId());
							//cover.setImageBitmap(task.image);
						}
				});
				task.start();
			}
			catch (Task.TaskNameAllreadyHasException e)
			{
				Log.e("task","name",e);
			}
			catch (MalformedURLException e)
			{
				Log.e("task","url",e);
			}
		}
		return super.getView(position,convertView,parent);
	}
	
	public class TestDownloadTask extends Task{
		public Bitmap image;
		TaskRunnable r;
		
		public TestDownloadTask(final URL url) throws Task.TaskNameAllreadyHasException{
			r = new TaskRunnable(this){
				@Override
				public void run()
				{
					// TODO: Implement this method
					try
					{
						HttpURLConnection c=(HttpURLConnection) url.openConnection();
						c.setConnectTimeout(0);
						image=BitmapFactory.decodeStream(c.getInputStream());
					}
					catch (IOException e)
					{
						Log.e("test","download",e);
					}
				}
			};
			init(r);
		}
	}
	
	/*
	@Override
	public void registerDataSetObserver(DataSetObserver p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver p1)
	{
		// TODO: Implement this method
	}

	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public int getItemViewType(int p1)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getViewTypeCount()
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		// TODO: Implement this method
		return false;
	}

	@Override
	public boolean isEnabled(int p1)
	{
		// TODO: Implement this method
		return false;
	}*/

}
