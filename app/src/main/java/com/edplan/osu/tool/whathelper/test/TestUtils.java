package com.edplan.osu.tool.whathelper.test;
import com.edplan.osu.framework.net.BeatmapDownload.Mengsky.*;
import java.io.*;
import com.edplan.osu.framework.net.JsonHelper.*;
import org.json.*;

public class TestUtils
{
	public static MengskyPageData getSamplePageData() throws FileNotFoundException, IOException, JSONException{
		FileInputStream in=new FileInputStream("/storage/sdcard1/testFile/beatmapinfo.json");
		return new MengskyPageData(BigJsonReader.readJSONObjectFromStream(in));
	}
}
