package com.edplan.osu.tool.whathelper.view;
import com.edplan.osu.tool.whathelper.R;
import android.view.*;
import android.content.*;
import com.edplan.osu.tool.net.BeatmapDownload.*;
import android.widget.*;
import android.app.*;

public class BeatmapSetView extends RelativeLayout
{
	protected BeatmapSetInfor mapsetInfor;
	
	public BeatmapSetView(Activity c,BeatmapSetInfor mapsetInfor){
		super(c);
		this.mapsetInfor=mapsetInfor;
		c.getWindow().getLayoutInflater().inflate(R.layout.entry_beatmaplist,this,true);
		
	}
}
