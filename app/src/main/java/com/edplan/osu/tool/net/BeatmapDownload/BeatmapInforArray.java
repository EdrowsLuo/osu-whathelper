package com.edplan.osu.tool.net.BeatmapDownload;
import org.json.*;
import java.util.*;
import com.edplan.osu.tool.net.BeatmapDownload.Mengsky.*;
import com.edplan.osu.tool.utils.*;
import android.util.*;

public class BeatmapInforArray<T extends BeatmapInfor>
{
	public ArrayList<T> beatmaps;
	public GameModeSet modes;
	
	public BeatmapInforArray(JSONArray arr,T m) throws JSONException{
		beatmaps=new ArrayList<T>();
		modes=new GameModeSet();
		int i=0;
		for(i=0;i<arr.length();i++){
			T t=(T)m.getSample(arr.getJSONObject(i));
			modes.addMode(t.mode);
			//Log.v("mode","add Mode: "+t.mode);
			beatmaps.add(t);
		}
	}
	
	public float getBpm(){
		int c=0;
		float bpm=0;
		for(T map:beatmaps){
			c++;
			bpm+=map.getBpm();
		}
		if(c==0)return -1;
		return bpm/c;
	}
	
	public int getLength(){
		int c=0;
		int length=0;
		for(T map:beatmaps){
			c++;
			length+=map.getLenght();
		}
		if(c==0)return 0;
		return length/c;
	}
	
	public GameModeSet getModeList(){
		return modes;
	}

	@Override
	public String toString()
	{
		// TODO: Implement this method
		StringBuilder s=new StringBuilder();
		for(BeatmapInfor ss:beatmaps){
			s.append("\n");
			s.append(ss.toString());
		}
		return s.toString();
	}
	
}
