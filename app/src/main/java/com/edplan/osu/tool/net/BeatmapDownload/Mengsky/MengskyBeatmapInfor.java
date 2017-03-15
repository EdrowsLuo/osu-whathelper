package com.edplan.osu.tool.net.BeatmapDownload.Mengsky;

import org.json.*;
import com.edplan.osu.tool.net.BeatmapDownload.*;

public class MengskyBeatmapInfor extends BeatmapInfor
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
	
	public MengskyBeatmapInfor(){
		super();
	}
	
	public MengskyBeatmapInfor(JSONObject b) throws JSONException{
		super(b);
		ar=b.getDouble("ar");
		bpm=b.getDouble("bpm");
		cs=b.getDouble("cs");
		hp=b.getDouble("hp");
		id=b.getInt("id");
		lenght=b.getInt("length");
		mode=b.getInt("mode");
		name=b.getString("name");
		od=b.getDouble("od");
	}

	@Override
	public BeatmapInfor getSample(JSONObject map) throws JSONException 
	{
		// TODO: Implement this method
		return new MengskyBeatmapInfor(map);
	}

	
	
}
