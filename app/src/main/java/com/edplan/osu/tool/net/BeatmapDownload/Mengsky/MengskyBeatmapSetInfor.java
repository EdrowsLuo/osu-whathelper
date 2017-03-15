package com.edplan.osu.tool.net.BeatmapDownload.Mengsky;
import org.json.*;
import com.edplan.osu.tool.net.BeatmapDownload.*;

public class MengskyBeatmapSetInfor extends BeatmapSetInfor
{
	/**
	{
		"artist": "Ebanko", 
		"artistU": "\u0415\u0431\u0430\u043d\u044c\u043a\u043e", 
		"beatmaps": [
		{
			"ar": 9.1, 
			"bpm": 142.91, 
			"cs": 4.0, 
			"hp": 4.0, 
			"id": 905745, 
			"length": 166, 
			"mode": 0, 
			"name": "This Song Is About Tragic Love", 
			"od": 7.5
		}
	 	], 
	 	"creator": "kkk", 
	 	"creatorId": 4735736, 
	 	"download": 4, 
	 	"file": "/api/download/418412", 
	 	"id": 418412, 
	 	"img": "http://b.ppy.sh/thumb/418412l.jpg", 
	 	"status": 0, //unrank=0,rank=1,approved=2,qualified=3,
	 	"synced": 1478167468, 
	 	"syncedDateTime": "2016-11-03 18:04:28", 
	 	"title": "Lyoshka", 
	 	"titleU": "\u041b\u0451\u0448\u043a\u0430"
	}
	*/
	
	public String file;
	public String img;
	
	public MengskyBeatmapSetInfor(JSONObject set)throws BeatmapJsonParseException, JSONException{
		artist=set.getString("artist");
		artistU=set.getString("artistU");
		beatmaps=new BeatmapInforArray<MengskyBeatmapInfor>(set.getJSONArray("beatmaps"),new MengskyBeatmapInfor());
		creator=set.getString("creator");
		download=set.getInt("download");
		file=set.getString("file");
		id=set.getInt("id");
		img=set.getString("img");
		status=set.getInt("status");
		synced=set.getLong("synced");
		syncedDateTime=set.getString("syncedDateTime");
		title=set.getString("title");
		titleU=set.getString("titleU");
	}

	@Override
	public String toString()
	{
		// TODO: Implement this method
		String s="";
		s="Artist: "+getArtist()+"\n"+
		  "Beatmaps:\n"+
		  getBeatmaps().toString()+"\n\n"+
		  "id: "+getBeatmapSetId();
		return s;
	}

	@Override
	public int getRankedStatusId()
	{
		// TODO: Implement this method
		return status;
	}
	
}
