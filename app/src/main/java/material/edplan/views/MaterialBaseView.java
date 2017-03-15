package material.edplan.views;
import android.view.*;
import android.util.*;
import android.content.*;
import android.widget.*;
import material.edplan.util.*;

public class MaterialBaseView extends RelativeLayout implements ViewFactory
{
	public final MaterialBaseView body=new MaterialBaseView(null);
	
	
	public MaterialBaseView(Context c,AttributeSet a){
		super(c,a);
	}
	public MaterialBaseView(Context c){
		super(c);
	}

	/**
	 *不建议使用
	 */
	@Override
	public Object createObject()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public View createObject(Context c)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public View createObject(Context c, AttributeSet s)
	{
		// TODO: Implement this method
		return null;
	}

	
}
