package com.edplan.task;
import java.io.*;
import java.net.*;
import java.util.*;

public class MultipleDownloadTaskEntry extends Task
{
	public long indexNow;
	public long startPoint;
	
	public RandomAccessFile fileInput;
	public RandomAccessFile dataInput;
	public URL url;
	
	public MultipleDownloadTaskEntry(URL url,File f,File dataF,long offset,long startPoint){
		
	}

	@Override
	public void run()
	{
		// TODO: Implement this method
	}

	@Override
	public boolean isCompleted()
	{
		// TODO: Implement this method
		return false;
	}

	
	
	
	
	/*
	@Override
	public void fail(Throwable t)
	{
		// TODO: Implement this method
	}*/
	
	
	public long getDownloadBytes(){
		return indexNow-startPoint;
	}
	
	
	public static ArrayList<MultipleDownloadTaskEntry> parseFromFile(File dataF){
		
		return null;
	}
	
}
