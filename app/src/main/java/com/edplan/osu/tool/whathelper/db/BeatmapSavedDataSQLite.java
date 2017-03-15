package com.edplan.osu.tool.whathelper.db;
import java.io.*;
import com.edplan.osu.tool.whathelper.*;
import android.database.sqlite.*;
import android.database.*;

public class BeatmapSavedDataSQLite
{
	private static SQLiteDatabase db;
	
	//private static SQLiteProgram sp;
	
	
	public void readSavedBeatmaps(){
		
	}
	
	public static SQLiteDatabase getDB(){
		cheakHasDB();
		return db;
	}
	
	//public static void addSavedBeatmapSet(Savv
	
	public static void clear(){
		
	}
	
	private synchronized static void cheakHasDB(){
		if(db==null||(!db.isOpen())){
			db=SQLiteDatabase.openOrCreateDatabase(MySystem.getSavedBeatmapDBFile(),null);
		}
	}
	
	private synchronized static void initDB(){
		cheakHasDB();
		db.execSQL("DROP TABLE IF EXISTS index,expendData,beatmapSetDataBuffer");
		db.execSQL("CREATE TABLE IF NOT EXISTS indexes(tablename,index)");
		db.execSQL("CREATE TABLE IF NOT EXISTS expendData(dbindex integer primary key autoincrement,dataname,context)");
		db.execSQL("CREATE TABLE IF NOT EXISTS beatmapSetDataBuffer(dbindex integer primary key autoincrement,dirName,beatmapSetId)");
	}
	
	private synchronized static void addIndexNumber(String tableName){
		int i=getIndex(tableName);
		if(i!=-1){
			db.execSQL("UPDATE indexes SET index=? WHERE tableNlname=?",new Object[]{i+1,tableName});
		}else{
			insertIndex(tableName);
		}
	}
	
	/*
	public static void updateData(String tableName,String[] clName,String[] value){
		if(clName.length!=value.length){
			return;
		}
		//db.execSQL("UPDATE ? 
	}*/
	
	private synchronized static void insertIndex(String tableName){
		db.execSQL("INSERT INTO indexes(tablename,index) VALUES(?,?)",new String[]{tableName,"0"});
	}
	
	public synchronized static int getIndex(String tableName){
		Cursor crs=db.rawQuery("SELECT index FROM indexes WHERE tablename=?",new String[]{tableName});
		if(crs.moveToFirst()){
			return crs.getInt(crs.getColumnIndex("index"));
		}else{
			return -1;
		}
	}
	
	
	
	public static void insert(String tableName){
		//
	}
	
	public class SavedBeatmapSetData{
		public String dirName;
		public int mapsetId;
	}
}
