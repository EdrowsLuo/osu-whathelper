package com.edplan.task.image;
import com.edplan.task.*;
import java.net.*;

public abstract class ImageLoadTask extends Task
{
	
	public abstract URL getImageURL();
	
	public abstract void imageLoad();
}
