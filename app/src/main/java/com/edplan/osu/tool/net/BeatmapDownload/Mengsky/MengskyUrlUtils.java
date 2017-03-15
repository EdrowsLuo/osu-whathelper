package com.edplan.osu.tool.net.BeatmapDownload.Mengsky;
import java.net.*;
import java.io.*;
import com.edplan.osu.tool.net.download.*;

public class MengskyUrlUtils
{
	//encode:UTF-8
	//http://osu.mengsky.net/api/beatmapinfo?query=%E5%B0%B1%E5%A5%BD%20l%20k%20%E5%BC%80&unrank=1&ranked=1&approved=1&qualified=1&page=1
	public static final String downloadHead="http://osu.mengsky.net/api/download/";
	public static final String downloadBeatmapIconLargeHead="http://b.ppy.sh/thumb/";
	public static final String apiBeatmapinfor="http://osu.mengsky.net/api/beatmapinfo";
	
	public static URL getApiBeatmapinfor(String search,boolean unrank,boolean ranked,boolean approved,boolean qualified,int page) throws UnsupportedEncodingException, MalformedURLException{
		StringBuilder url=new StringBuilder(apiBeatmapinfor);
		url.append("?query=");
		url.append(URLEncoder.encode(search,"UTF-8"));
		url.append("&unrank=");
		urlAddBoolean(url,unrank);
		url.append("&ranked=");
		urlAddBoolean(url,ranked);
		url.append("&approved=");
		urlAddBoolean(url,approved);
		url.append("&qualified=");
		urlAddBoolean(url,qualified);
		url.append("&page=");
		url.append(""+page);
		
		return new URL(url.toString());
	}
	
	static void urlAddBoolean(StringBuilder s,boolean b){
		if(b){
			s.append("1");
		}else{
			s.append("0");
		}
	}
	
	public static URL getBeatmapDownloadURL(int beatmapId) throws MalformedURLException{
		return new URL(downloadHead+beatmapId);
	}
	
	public static URL getBeatmapSetIconLargeURL(int beatmapSetId) throws MalformedURLException{
		return new URL(downloadBeatmapIconLargeHead+beatmapSetId+"l.jpg");
	}
	
	public static HttpFileDownloader getBeatmapSetDownloaderById(int id,String toSave) throws IOException{
		return new HttpFileDownloader(getBeatmapDownloadURL(id),toSave);
	}
	
	public static HttpFileDownloader getBeatmapSetIconLargeDownloaderById(int id,String toSave) throws IOException{
		return new HttpFileDownloader(getBeatmapSetIconLargeURL(id),toSave);
	}
	
	public static final String spaceCode="%20";
	/**
	 *可以不在此转换，直接URLEncod.encode(,"UTF-8");
	 */
	@Deprecated
	public static String encodeStringQuery(String code) throws UnsupportedEncodingException{
		String[] s=code.split(" ");
		StringBuilder r=new StringBuilder();
		for(int i=0;i<s.length;i++){
			r.append(URLEncoder.encode(s[i],"UTF-8"));
			if(i<s.length-1){
				r.append(spaceCode);
			}
		}
		return r.toString();
	}
}
