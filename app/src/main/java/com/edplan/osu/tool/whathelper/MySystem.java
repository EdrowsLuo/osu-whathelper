package com.edplan.osu.tool.whathelper;
import android.app.*;
import android.os.*;
import java.io.*;

public abstract class MySystem
{
	public static final String UPDATE_URL="https://raw.githubusercontent.com/EdrowsLuo/gitStorage/master/storage/osu!whathelper_update.json";
	
	public static File EXTERNAL_SAVE_DIR;
	public static File EXTERNAL_SAVE_IMAGE_DIR;
	public static File EXTERNAL_CACHE_IMAGE_DIR;
	public static File EXTERNAL_OPTION_DIR;
	
	public static File DB_DIR;
	public static File DB_SAVEDBEATMAP;
	
	public static File OSU_SONGS_DIR;//=new File("/storage/sdcard1/testFile/osu_dir");
	
	public static void init(){
		EXTERNAL_SAVE_DIR=new File(Environment.getExternalStorageDirectory(),"osu!whathelper");
		if(!EXTERNAL_SAVE_DIR.exists())EXTERNAL_SAVE_DIR.mkdirs();
		EXTERNAL_SAVE_IMAGE_DIR=new File(EXTERNAL_SAVE_DIR,"Image/Saved");
		if(!EXTERNAL_SAVE_IMAGE_DIR.exists())EXTERNAL_SAVE_IMAGE_DIR.mkdirs();
		EXTERNAL_CACHE_IMAGE_DIR=new File(EXTERNAL_SAVE_DIR,"Image/Cache");
		if(!EXTERNAL_CACHE_IMAGE_DIR.exists())EXTERNAL_CACHE_IMAGE_DIR.mkdirs();
		EXTERNAL_OPTION_DIR=new File(EXTERNAL_SAVE_DIR,"Option");
		if(!EXTERNAL_OPTION_DIR.exists())EXTERNAL_OPTION_DIR.mkdirs();
		OSU_SONGS_DIR=new File(Options.getString("OsuSongsDir"));
		if(!OSU_SONGS_DIR.exists())OSU_SONGS_DIR.mkdirs();
		DB_DIR=new File(EXTERNAL_SAVE_DIR,"db");
		if(!DB_DIR.exists())DB_DIR.mkdirs();
		DB_SAVEDBEATMAP=new File(DB_DIR.getAbsolutePath()+"/savedBeatmap.db");
		try {
			if(!DB_SAVEDBEATMAP.exists())DB_SAVEDBEATMAP.createNewFile();
		} catch(IOException e) {}
	}
	
	public static String getExternalOptionDirPath(){
		return getExternalOptionDir().getAbsolutePath();
	}
	
	public static File getExternalOptionDir(){
		if(!EXTERNAL_OPTION_DIR.exists())EXTERNAL_OPTION_DIR.mkdirs();
		return EXTERNAL_OPTION_DIR;
	}
	
	public static String getOsuSongsDirPath(){
		return getOsuSongsDir().getAbsolutePath();
	}
	
	public static File getOsuSongsDir(){
		if(!OSU_SONGS_DIR.exists())OSU_SONGS_DIR.mkdirs();
		return OSU_SONGS_DIR;
	}
	
	public static String getExternalSavePath(){
		return getExternalSaveDir().getAbsolutePath();
	}
	
	public static File getExternalSaveDir(){
		if(!EXTERNAL_SAVE_DIR.exists())EXTERNAL_SAVE_DIR.mkdirs();
		return EXTERNAL_SAVE_DIR;
	}
	
	public static File getExternalImageSaveDir(){
		if(!EXTERNAL_SAVE_IMAGE_DIR.exists())EXTERNAL_SAVE_IMAGE_DIR.mkdirs();
		return EXTERNAL_SAVE_IMAGE_DIR;
	}
	
	public static String getExternalImageSavePath(){
		return getExternalImageSaveDir().getAbsolutePath();
	}
	
	public static File getExternalImageCacheDir(){
		if(!EXTERNAL_CACHE_IMAGE_DIR.exists())EXTERNAL_CACHE_IMAGE_DIR.mkdirs();
		return EXTERNAL_CACHE_IMAGE_DIR;
	}

	public static String getExternalImageCachePath(){
		return getExternalImageCacheDir().getAbsolutePath();
	}
	
	public static File getDBDir(){
		if(!DB_DIR.exists())DB_DIR.mkdirs();
		return DB_DIR;
	}
	
	public static String getDBPath(){
		return getDBDir().getAbsolutePath();
	}
	
	public static File getSavedBeatmapDBFile(){
		try {
			if(!DB_SAVEDBEATMAP.exists())DB_SAVEDBEATMAP.createNewFile();
		}catch(IOException e){
			
		}
		return DB_SAVEDBEATMAP;
	}
	
}
