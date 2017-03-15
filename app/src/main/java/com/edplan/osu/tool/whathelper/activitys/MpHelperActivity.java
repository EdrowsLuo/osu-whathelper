package com.edplan.osu.tool.whathelper.activitys;
import android.app.*;
import android.os.*;
import com.edplan.osu.tool.whathelper.*;
import android.view.*;

public class MpHelperActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mphelper);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
			localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
		}
		
	}
}
