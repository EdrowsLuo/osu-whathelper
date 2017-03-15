package com.edplan.task.image;
import android.graphics.*;
import com.edplan.task.utils.*;
import java.io.*;

public interface ImageEntry extends Disposable
{
	public boolean hasLoaded();
	
	public boolean hasCache();
	
	public Bitmap getBitmap();
	
	//public InputStream getCacheInput();
	
	//public InputStream getHttpInput();
	
	public ImageLoadTask getDownloadTask();
	
	public ImageLoadTask getLoadFromCacheTask();
	
	
	
}
