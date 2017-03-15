package material.edplan.activitys;
import android.view.ViewGroup.*;
import android.view.*;
import material.edplan.views.*;
import android.os.*;

public class TestMaterialActivity extends MaterialActivity<TestMaterialView>
{
	private ViewGroup.LayoutParams defaultParams;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		defaultParams=new ViewGroup.LayoutParams(this,null);
	}
	
	
		

	@Override
	public ViewGroup.LayoutParams getContentViewParams()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public TestMaterialView getMainView()
	{
		// TODO: Implement this method
		return null;
	}

}
