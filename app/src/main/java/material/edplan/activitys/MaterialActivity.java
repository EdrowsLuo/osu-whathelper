package material.edplan.activitys;
import android.app.*;
import material.edplan.views.*;
import android.view.*;
import android.view.ViewGroup.*;

public abstract class MaterialActivity<T extends MaterialBaseView> extends Activity
{
	
	public abstract ViewGroup.LayoutParams getContentViewParams();
	
	public abstract T getMainView();
	
	@Override
	public void setContentView(int layoutResID)
	{
		// TODO: Implement this method
		setContentView(getWindow().getLayoutInflater().inflate(layoutResID,null,false));
	}

	@Override
	public void setContentView(View view)
	{
		// TODO: Implement this method
		setContentView(view,getContentViewParams());
	}

	@Override
	public void setContentView(View view, ViewGroup.LayoutParams params)
	{
		// TODO: Implement this method
		
		T mainView=getMainView();
		mainView.addView(view,params);
		super.setContentView(mainView, params);
	}
	
}
