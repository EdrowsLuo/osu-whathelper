package com.edplan.task.image;

import java.util.*;
import android.graphics.*;
import com.edplan.task.*;
import com.edplan.task.download.*;
import java.net.*;
import java.io.*;

public abstract class ImagePool<K>
{
	public String cacheDir;
	
	
	
	public TreeMap<K,Bitmap> images;
	public DownloadQuery<K> downloadQuery;
	
	public void loadBitmap(K key){
		
	}
	
	public void encodeBitmap(InputStream in){
		
	}
	
	public abstract URL getURLByKey(K key);
	
	public abstract String getFileNameByKey(K key);
	
	public abstract File getCacheDir();
	
}
