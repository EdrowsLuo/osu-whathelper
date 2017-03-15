package material.edplan.util;
import android.view.*;
import android.content.*;
import android.util.*;

public interface ViewFactory<T extends View> extends ObjectFactory<T>
{
	
	
	public T createObject(Context c);
	
	public T createObject(Context c,AttributeSet s)
}
